package com.jbb.mgt.helipay.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.PayRecordDao;
import com.jbb.mgt.core.dao.XjlApplyRecordDao;
import com.jbb.mgt.core.domain.PayRecord;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserCard;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.service.XjlRemindMsgCodeService;
import com.jbb.mgt.helipay.service.TransferService;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.mgt.helipay.vo.TransferQueryRspVo;
import com.jbb.mgt.helipay.vo.TransferQueryVo;
import com.jbb.mgt.helipay.vo.TransferRspVo;
import com.jbb.mgt.helipay.vo.TransferVo;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.HeLiPayException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.RSA;

@Service("TransferService")
public class TransferServiceImpl implements TransferService {

    private static Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);
    private static int LOAN_STATUS = 3;

    @Autowired
    private PayRecordDao payRecordDao;
    @Autowired
    private XjlApplyRecordDao xjlApplyRecordDao;
    @Autowired
    private XjlRemindMsgCodeService xjlRemindMsgCodeService;

    @Override
    public synchronized void transfer(User user, UserCard userCard, XjlApplyRecord apply, int accountId)
        throws UnsupportedEncodingException {

        logger.debug(">transfer, user = {}, userCard={}, apply={}", user, userCard, apply);

        String orderId = HeliUtil.generateOrderId();
        double amountD = (double)apply.getAmount() / (double)100;
        TransferVo vo
            = new TransferVo(orderId, amountD, userCard.getBankCode(), userCard.getCardNo(), user.getUserName());

        String privateKey = PropertyManager.getProperty("jbb.pay.heli.private.key");
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.debug("getSigned：" + vo.getSigned());
        logger.debug("sign：" + sign);
        vo.setSign(sign);

        // 2. TODO 调用接口
        TransferRspVo rspVo
            = (TransferRspVo)HeliUtil.transferPost(vo.getRequestFormStr(), TransferRspVo.class, vo.getP1_bizType());
        if (rspVo != null) {
            // 处理逻辑
            if (rspVo.rt2_retCode.equals(TransferRspVo.SUCCESS_CODE)) {
                // 1. 插入数据库，有一笔支付
                PayRecord payRecord = new PayRecord(orderId, vo.getP3_customerNumber(), apply.getAmount(),
                    vo.getP5_bankCode(), vo.getP6_bankAccountNo(), vo.getP7_bankAccountName(), vo.getP8_biz(),
                    vo.getP9_bankUnionCode(), vo.getP10_feeType(), true, vo.getP12_summary(),
                    rspVo.getRt6_serialNumber(), PayRecord.RECEIVE_STATUS, DateUtil.getCurrentTimeStamp(), accountId,
                    apply.getApplyId(), rspVo.getRt8_reason());
                payRecordDao.insertPayRecord(payRecord);
                //提交成功之后 将applyRecord的状态改为1
                apply.setStatus(1);
                xjlApplyRecordDao.updateXjlApplyRecord(apply);
            } else {
                logger.info("transfer response code =" + rspVo.rt2_retCode + "&&" + "Msg =" + rspVo.rt3_retMsg);
                throw new HeLiPayException("jbb.error.exception.call3rdApi.error", "zh", rspVo.rt3_retMsg);
            }

        } else {
            logger.error("heli transfer response with error null ");
            throw new HeLiPayException("jbb.error.exception.call3rdApi.error");

        }

        // 3. 查询代付状态
        // tranferQuery(orderId);

        logger.debug("<transfer");

    }

    @Override
    public void tranferQuery(String orderId) throws UnsupportedEncodingException {
        logger.debug(">tranferQuery, orderId = {} = " + orderId);

        TransferQueryVo vo = new TransferQueryVo(orderId);

        String privateKey = PropertyManager.getProperty("jbb.pay.heli.private.key");
        String sign = RSA.sign(vo.getSigned(), privateKey);
        logger.debug("signed：" + vo.getSigned());
        logger.debug("sign：" + sign);
        vo.setSign(sign);

        // 1. 调用接口
        TransferQueryRspVo rspVo = (TransferQueryRspVo)HeliUtil.transferPost(vo.getRequestFormStr(),
            TransferQueryRspVo.class, vo.getP1_bizType());

        if (rspVo != null) {
            // 处理逻辑
            // 2. 更新代付状态
            // 更新代付状态
            logger.info("transfer response code =  " + rspVo.rt2_retCode);
            if (rspVo.rt2_retCode.equals(TransferRspVo.SUCCESS_CODE)) {
                payRecordDao.updatePayRecord(orderId, rspVo.rt7_orderStatus, rspVo.rt8_reason);
                if (rspVo.rt7_orderStatus.equals(PayRecord.SUCCESS_STATUS)) {
                    PayRecord payRecord = payRecordDao.selectByOrderId(orderId);
                    XjlApplyRecord xjlApplyRecord
                        = xjlApplyRecordDao.selectXjlApplyRecordByApplyId(payRecord.getApplyId(), null, false);
                    xjlApplyRecord.setStatus(LOAN_STATUS);
                    xjlApplyRecord.setLoanDate(DateUtil.getCurrentTimeStamp());
                    xjlApplyRecord.setRepaymentDate(new Timestamp(
                        DateUtil.getCurrentTime() + xjlApplyRecord.getDays() * DateUtil.DAY_MILLSECONDES));
                    xjlApplyRecordDao.updateXjlApplyRecord(xjlApplyRecord);
                    
                    boolean enabled = PropertyManager.getBooleanProperty("xjl.mgt.loan.sendMsg.enabled", false);
                    if (enabled) {
                        xjlRemindMsgCodeService.loanSendCode(payRecord, xjlApplyRecord);
                    }
                }

            } else {
                logger.info("transfer response code =" + rspVo.rt2_retCode + "&&" + "Msg =" + rspVo.rt3_retMsg);
            }
        } else {
            logger.error("heli transfer response with error null ");
        }
        logger.debug("<tranferQuery");

    }

}
