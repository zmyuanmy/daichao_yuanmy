package com.jbb.mgt.rs.action.xjlPay;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.changjiepay.service.ChangJieTransferService;
import com.jbb.mgt.changjiepay.util.BaseConstant;
import com.jbb.mgt.changjiepay.util.ChangJieUtil;
import com.jbb.mgt.core.domain.PayRecord;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserCard;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.service.PayRecordService;
import com.jbb.mgt.core.service.UserCardService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.core.service.XjlApplyRecordService;
import com.jbb.mgt.core.service.XjlRemindMsgCodeService;
import com.jbb.mgt.helipay.service.TransferService;
import com.jbb.mgt.server.core.util.PasswordUtil;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@Service(TransferAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TransferAction extends BasicAction {

    public static final String ACTION_NAME = "TransferAction";

    private static Logger logger = LoggerFactory.getLogger(TransferAction.class);

    private Response response;

    @Autowired
    private PayRecordService payRecordService;
    @Autowired
    private XjlApplyRecordService xApplyRecordService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private UserCardService userCardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChangJieTransferService changJieTransferService;
    @Autowired
    private XjlRemindMsgCodeService xjlRemindMsgCodeService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void payTransfer(String password, boolean force, String applyId) throws UnsupportedEncodingException {
        logger.debug(">payTransfer");
        String payProductType = PropertyManager.getProperty("jbb.mgt.pay.product.type");
        String payProductId = "";
        if (payProductType.equals("1")) {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.id");
        } else {
            payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");
        }
        if (applyId == null || StringUtil.isEmpty(applyId)) {
            throw new WrongParameterValueException("jbb.xjl.exception.apply.notFound");
        }
        XjlApplyRecord xApplyRecord
            = xApplyRecordService.getXjlApplyRecordByApplyId(applyId, this.account.getOrgId(), true);

        if (xApplyRecord == null) {
            throw new WrongParameterValueException("jbb.xjl.exception.apply.notFound");
        }
        //交易密码验证
        /*if (StringUtil.isEmpty(password) || !PasswordUtil.verifyPassword(password, this.account.getTradePassword())) {
            throw new WrongParameterValueException("jbb.xjl.error.tradePasswordError");
        }*/
        User user = userService.selectUserById(xApplyRecord.getUserId());
        String msg = "";
        String status = "";
        UserCard userCard = userCardService.selectAcceptUserCard(xApplyRecord.getUserId(), payProductId);
        PayRecord payrecord = payRecordService.selectByApplyId(applyId);
        if (userCard == null) {
            throw new WrongParameterValueException("jbb.xjl.error.userCard.notComplete");
        }
        String productType = PropertyManager.getProperty("jbb.mgt.pay.product.type", "1");

        double amount = (double)(xApplyRecord.getAmount() + xApplyRecord.getServiceFee()) / (double)100;
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String amountString = dFormat.format(amount);

        if (force) {
            if (payrecord == null || payrecord.getOrderStatus().equals(PayRecord.FAIL_STATUS)) {
                // 代付接口
                if (productType.equals("1")) {
                    transferService.transfer(user, userCard, xApplyRecord, this.account.getAccountId());
                } else {
                    changJieTransferService.transfer(userCard.getCardNo(), user.getUserName(), amountString,
                        userCard.getBankCode(), applyId, this.account.getAccountId(),xApplyRecord);
                }

            } else {
                msg = "存在未完成的代付记录！";
                status = payrecord.getOrderStatus();
            }
        } else {
            if (payrecord == null) {
                // 代付接口
                if (productType.equals("1")) {
                    transferService.transfer(user, userCard, xApplyRecord, this.account.getAccountId());
                } else {
                    changJieTransferService.transfer(userCard.getCardNo(), user.getUserName(), amountString,
                        userCard.getBankCode(), applyId, this.account.getAccountId(),xApplyRecord);
                }
            } else {
                if (payrecord.getOrderStatus().equals(PayRecord.SUCCESS_STATUS)) {
                    msg = "已付款成功，请勿重复打款。";
                } else if (payrecord.getOrderStatus().equals(PayRecord.FAIL_STATUS)) {
                    msg = "付款失败，原因：" + payrecord.getRetReason() + ",是否重新打款？";
                } else if (payrecord.getOrderStatus().equals(PayRecord.REFUND_STATUS)) {
                    msg = "用户退款，请核查。";
                } else {
                    msg = "代付处理中，状态：" + payrecord.getOrderStatus();
                }
                status = payrecord.getOrderStatus();
            }
        }
        this.response.status = status;
        this.response.msg = msg;
        logger.debug("<payTransfer()");

    }

    public void notifyTransfer(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(">notifyTransfer");
        Enumeration enu = request.getParameterNames();
        String requestString = "";
        while (enu.hasMoreElements()) {
            String paraName = (String)enu.nextElement();
            requestString += paraName + ": " + request.getParameter(paraName) + " , ";
        }
        logger.info("ChangJie Transfer RequestString " + requestString);
        String sign = request.getParameter("sign");
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("uid", request.getParameter("uid"));
        paramMap.put("notify_time", request.getParameter("notify_time"));
        paramMap.put("notify_id", request.getParameter("notify_id"));
        paramMap.put("notify_type", request.getParameter("notify_type"));
        paramMap.put("_input_charset", request.getParameter("_input_charset"));
        paramMap.put("version", request.getParameter("version"));
        paramMap.put("outer_trade_no", request.getParameter("outer_trade_no"));
        paramMap.put("inner_trade_no", request.getParameter("inner_trade_no"));
        paramMap.put("withdrawal_status", request.getParameter("withdrawal_status"));
        paramMap.put("withdrawal_amount", request.getParameter("withdrawal_amount"));
        paramMap.put("gmt_withdrawal", request.getParameter("gmt_withdrawal"));
        paramMap.put("return_code", request.getParameter("return_code"));
        paramMap.put("fail_reason", request.getParameter("fail_reason"));
        String text = ChangJieUtil.createLinkString(paramMap, false);
        boolean verified = true;
        String publicKey = PropertyManager.getProperty("jbb.pay.changjie.public.key");
        try {
            verified = com.jbb.mgt.changjiepay.util.RSA.verify(text, sign, publicKey, BaseConstant.CHARSET);
        } catch (Exception e) {
            logger.error("verify error e = " + e);
        }
        // 检查签名
        if (!verified) {
            logger.error("畅捷代付回调参数验签失败, requestString = " + requestString);
            return;
        }
        // 处理后续逻辑
        String tradeStatus = request.getParameter("withdrawal_status");
        if (!tradeStatus.equals("WITHDRAWAL_SUCCESS")) {
            logger.error("畅捷代付回调失败, tradeStatus is not 'WITHDRAWAL_SUCCESS',  requestString = " + requestString);
        }

        String failReason = request.getParameter("fail_reason");
        String orderId = request.getParameter("outer_trade_no");
        if (tradeStatus.equals("WITHDRAWAL_SUCCESS")) {
            tradeStatus = "SUCCESS";
        } else {
            tradeStatus = "FAIL";
        }
        payRecordService.updatePayRecord(orderId, tradeStatus, failReason);

        PayRecord payRecord = payRecordService.selectByOrderId(orderId);
        XjlApplyRecord xjlApplyRecord
            = xApplyRecordService.selectXjlApplyRecordByApplyId(payRecord.getApplyId(), null, false);
        xjlApplyRecord.setStatus(3);
        xjlApplyRecord.setLoanDate(DateUtil.getCurrentTimeStamp());
        xjlApplyRecord.setRepaymentDate(
            new Timestamp(DateUtil.getCurrentTime() + xjlApplyRecord.getDays() * DateUtil.DAY_MILLSECONDES));
        xApplyRecordService.updateXjlApplyRecord(xjlApplyRecord);
        
        boolean enabled = PropertyManager.getBooleanProperty("xjl.mgt.loan.sendMsg.enabled", false);
        if (enabled) {
            xjlRemindMsgCodeService.loanSendCode(payRecord, xjlApplyRecord);
        }

        logger.debug("<notifyTransfer()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private String msg;
        private String status;

        public String getMsg() {
            return msg;
        }

        public String getStatus() {
            return status;
        }

    }
}
