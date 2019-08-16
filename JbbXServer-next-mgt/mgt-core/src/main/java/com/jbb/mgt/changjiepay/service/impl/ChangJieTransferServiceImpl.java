package com.jbb.mgt.changjiepay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jbb.mgt.changjiepay.service.ChangJieTransferService;
import com.jbb.mgt.changjiepay.util.BaseConstant;
import com.jbb.mgt.changjiepay.util.ChanPayUtil;
import com.jbb.mgt.changjiepay.util.ChangJieUtil;
import com.jbb.mgt.core.dao.PayRecordDao;
import com.jbb.mgt.core.dao.XjlApplyRecordDao;
import com.jbb.mgt.core.domain.PayRecord;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.HeLiPayException;
import com.jbb.server.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("ChangJieTransferService")
public class ChangJieTransferServiceImpl implements ChangJieTransferService {

    private static Logger logger = LoggerFactory.getLogger(ChangJieTransferServiceImpl.class);

    private static String charset = "UTF-8";

    @Autowired
    private PayRecordDao payRecordDao;

    @Autowired
    private XjlApplyRecordDao xjlApplyRecordDao;

    @Override
    public void queryBalance(String acctNo, String acctName) {
        logger.debug(">queryBalance, acctNo={},  acctName = {}" + acctNo + acctName);
        String privateKey = PropertyManager.getProperty("jbb.pay.changjie.private.key");
        Map<String, String> map = requestBaseParameter();
        map.put("TransCode", "C00005");
        map.put("OutTradeNo", generateOutTradeNo());
        // map.put("AcctNo", ChangJieUtil.encrypt(acctNo, privateKey, charset));
        // map.put("AcctName", ChangJieUtil.encrypt(acctName, privateKey, charset));

        String rspString = ChanPayUtil.sendPost(map, charset, privateKey);
        if (rspString == null) {
            logger.info("<transferQuery, return with error");
            throw new HeLiPayException();
        }
        checkRetCode(rspString);
        logger.debug("<queryBalance");
    }

    @Override
    public void transfer(String cardNo, String userName, String amount, String bankCode,String applyId,int accountId,XjlApplyRecord xApplyRecord) {
        logger.debug(">transfer, cardNo={}, userName = {}" + cardNo + userName);
        String publicKey = PropertyManager.getProperty("jbb.pay.changjie.public.key");
        String privateKey = PropertyManager.getProperty("jbb.pay.changjie.private.key");
        Map<String, String> map = this.requestBaseParameter();
        map.put("TransCode", "T10100"); // 交易码
        String orderId = HeliUtil.generateOrderId();
        map.put("OutTradeNo", orderId); // 商户网站唯一订单号
        //map.put("CorpAcctNo", "1223332343"); // 59 的 1223332343
        map.put("BusinessType", "0"); // 业务类型
        String bankName = PropertyManager.getProperty("jbb.pay.changjie.transfer.bankName");
        map.put("BankCommonName", bankName); // 通用银行名称
        map.put("AccountType", "00"); // 账户类型
        map.put("AcctNo", ChangJieUtil.encrypt(cardNo, publicKey, charset)); // 对手人账号(此处需要用真实的账号信息)
        map.put("AcctName", ChangJieUtil.encrypt(userName, publicKey, charset)); // 对手人账户名称
        map.put("Currency", "CNY");
        map.put("TransAmt", amount);
        String notifyUrl = PropertyManager.getProperty("jbb.pay.changjie.transferCallBack.url");
        map.put("CorpPushUrl", notifyUrl);
        // ************** 以下信息可空 *******************
        // map.put("Province", "甘肃省"); // 省份信息
        // map.put("City", "兰州市"); // 城市信息
        // map.put("BranchBankName", "中国建设银行股份有限公司兰州新港城支行"); // 对手行行名
        // map.put("BranchBankCode", "105821005604");
        // map.put("DrctBankCode", "105821005604");
        // map.put("Currency", "CNY");
        // map.put("LiceneceType", "01");
        // map.put("LiceneceNo", ChangJieUtil.encrypt("430426198606056175", publicKey,
        // BaseConstant.CHARSET));
        // map.put("Phone", ChangJieUtil.encrypt("18575511205", publicKey, BaseConstant.CHARSET));
        // map.put("AcctExp", "exp");
        // map.put("AcctCvv2", ChanPayUtil.encrypt("cvv", BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
        // map.put("CorpCheckNo", "201703061413");
        // map.put("Summary", "");

        // map.put("PostScript", "用途");

        String rspString = ChanPayUtil.sendPost(map, charset, privateKey);
        if (rspString == null) {
            logger.info("<transfer, return with error");
            throw new HeLiPayException();
        }
        checkRetCode(rspString);
        JSONObject jsonObject = JSONObject.parseObject(rspString);
        String flowNo = jsonObject.getString("FlowNo");
        String retMsg = jsonObject.getString("RetMsg");
        String biz = PropertyManager.getProperty("jbb.pay.heli.biz");
        String feeType = PropertyManager.getProperty("jbb.pay.heli.fee.type");
        String urgency = "true";
        String summary = PropertyManager.getProperty("jbb.pay.heli.transfer.summary");

        // 调用代付接口成功，增加业务订单
        String customerNumber = PropertyManager.getProperty("jbb.pay.changjie.customer.number");
        PayRecord payRecord = new PayRecord(orderId, customerNumber, (int)(Double.valueOf(amount) * 100), bankCode, cardNo,
            userName, biz, null,feeType, true, summary,
            flowNo, PayRecord.RECEIVE_STATUS, DateUtil.getCurrentTimeStamp(), accountId,
            applyId, retMsg);
        payRecordDao.insertPayRecord(payRecord);

        //提交成功之后 将applyRecord的状态改为1
        xApplyRecord.setStatus(1);
        xjlApplyRecordDao.updateXjlApplyRecord(xApplyRecord);

        logger.debug("<transfer");
    }

    @Override
    public void transferQuery(String orderId) {
        logger.debug(">transferQuery , acctNo={} " + orderId);

        String privateKey = PropertyManager.getProperty("jbb.pay.changjie.private.key");
        Map<String, String> map = this.requestBaseParameter();
        map.put("TransCode", "C00000");

        map.put("OutTradeNo", generateOutTradeNo());
        map.put("OriOutTradeNo", orderId);
        String rspString = ChanPayUtil.sendPost(map, charset, privateKey);
        if (rspString == null) {
            logger.info("<transferQuery, return with error");
            throw new HeLiPayException();
        }
        checkRetCode(rspString);
        logger.debug("<transferQuery");
    }

    /**
     * 请求 @Title: requestBaseParam @Description: TODO(这里用一句话描述这个方法的作用) @param @return 设定文件 @return Map<String,String>
     * 返回类型 @throws
     */
    private Map<String, String> requestBaseParameter() {
        Map<String, String> origMap = new HashMap<String, String>();
        // 2.1 基本参数
        String customerNumber = PropertyManager.getProperty("jbb.pay.changjie.customer.number");
        origMap.put(BaseConstant.SERVICE, "cjt_dsf");// 鉴权绑卡确认的接口名
        origMap.put(BaseConstant.VERSION, "1.0");
        // origMap.put(BaseConstant.PARTNER_ID, "200000920146"); //生产
        origMap.put(BaseConstant.PARTNER_ID, customerNumber); // 生产环境测试商户号
        origMap.put(BaseConstant.TRADE_DATE, BaseConstant.DATE);
        origMap.put(BaseConstant.TRADE_TIME, BaseConstant.TIME);
        origMap.put(BaseConstant.INPUT_CHARSET, BaseConstant.CHARSET);// 字符集
        origMap.put(BaseConstant.MEMO, "");// 备注
        return origMap;

    }

    private String generateOutTradeNo() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
            + String.valueOf(new Double(Math.round(Math.random() * 1000000000)).longValue());
    }

    private void checkRetCode(String rspString) {
        JSONObject jsonObject = JSON.parseObject(rspString);
        String acceptStatus = "";
        if (jsonObject != null) {
            acceptStatus = jsonObject.getString("AcceptStatus");
        }
        logger.info("<HeLiPayResponse, Response = " + rspString);
        if (!acceptStatus.equals("S")) {
            String AppRetMsg = jsonObject.getString("PlatformErrorMessage");
            throw new HeLiPayException("代付接口调用失败 原因:" + AppRetMsg);
        }
    }
}
