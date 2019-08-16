package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.changjiepay.contants.ChangJiePayConstants;
import com.jbb.mgt.changjiepay.service.ChangJieQuickPayService;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.domain.XjlUserOrder;
import com.jbb.mgt.core.service.PayService;
import com.jbb.mgt.core.service.XjlApplyRecordService;
import com.jbb.mgt.core.service.XjlUserOrderService;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service("ChangJiePayServices")
public class ChangJiePayServiceImpl implements PayService {

    private static Logger logger = LoggerFactory.getLogger(ChangJiePayServiceImpl.class);

    @Autowired
    private XjlUserOrderService xjlUserOrderService;

    @Autowired
    private XjlApplyRecordService xjlApplyRecordService;

    @Autowired
    private ChangJieQuickPayService changJieQuickPayService;

    @Override
    public String sendPayMsgCode(User user,String applyId, String cardNo, String terminalId, String terminalType,
        String ipAddress) {
        String payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");

        String orderId = HeliUtil.generateOrderId();

        XjlApplyRecord xjlApplyRecord = xjlApplyRecordService.getXjlApplyRecordByApplyId(applyId, null, null);

        if (xjlApplyRecord == null) {
            throw new ObjectNotFoundException();
        }

        double amount = (double)(xjlApplyRecord.getAmount() + xjlApplyRecord.getServiceFee()) / (double)100;
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String amountString = dFormat.format(amount);
        changJieQuickPayService.bizApiQuickPayment(String.valueOf(user.getUserId()), cardNo, amountString, orderId);

        XjlUserOrder xjlUserOrderNew = new XjlUserOrder();
        xjlUserOrderNew.setApplyId(applyId);
        xjlUserOrderNew.setCardNo(cardNo);
        xjlUserOrderNew.setCreationDate(DateUtil.getCurrentTimeStamp());
        xjlUserOrderNew.setExpiryDate(DateUtil.calTimestamp(DateUtil.getCurrentTime(), DateUtil.HOUR_MILLSECONDES));
        xjlUserOrderNew.setIpAddress(ipAddress);
        xjlUserOrderNew.setOrderId(orderId);
        xjlUserOrderNew.setPayProductId(payProductId);
        xjlUserOrderNew.setStatus("COMMIT");// 此状态在回调中修改COMMIT表示初始值 SUCCESS,FAILED,CANCELLED,REFUNDED,DOING
        xjlUserOrderNew.setTerminalId(terminalId);
        xjlUserOrderNew.setTerminalType(terminalType);
        xjlUserOrderNew.setType(ChangJiePayConstants.SERVICE_NMG_BIZ_API_QUICK_PAYMENT);
        xjlUserOrderNew.setUpdateDate(null);
        xjlUserOrderNew.setUserId(user.getUserId());
        xjlUserOrderService.insertXjlUserOrder(xjlUserOrderNew);
        return orderId;
    }

    @Override
    public void payConfirm(String orderId, String msgCode, String ipAddress) throws Exception {

        XjlUserOrder xjlUserOrder = xjlUserOrderService.selectXjlUserOrderById(orderId);

        if (xjlUserOrder == null) {
            throw new ObjectNotFoundException();
        }
        // 调用支付接口
        changJieQuickPayService.apiQuickPaymentSmsConfirm(orderId, msgCode);
    }

    @Override
    public String directPay(User user, String applyId, String cardNo, String terminalId, String terminalType,
        String ipAddress) {
        String payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.changjie.id");

        XjlApplyRecord xjlApplyRecord = xjlApplyRecordService.getXjlApplyRecordByApplyId(applyId, null, null);

        if (xjlApplyRecord == null) {
            throw new ObjectNotFoundException();
        }
        double amount = (double)(xjlApplyRecord.getAmount() + xjlApplyRecord.getServiceFee()) / (double)100;
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String amountString = dFormat.format(amount);
        String orderId = HeliUtil.generateOrderId();
        changJieQuickPayService.apiQuickPayment(String.valueOf(user.getUserId()), user.getIdCard(), cardNo, amountString,
            orderId, user.getUserName(), user.getPhoneNumber());
        XjlUserOrder xjlUserOrderNew = new XjlUserOrder();
        xjlUserOrderNew.setApplyId(applyId);
        xjlUserOrderNew.setCardNo(cardNo);
        xjlUserOrderNew.setCreationDate(DateUtil.getCurrentTimeStamp());
        xjlUserOrderNew.setExpiryDate(DateUtil.calTimestamp(DateUtil.getCurrentTime(), DateUtil.HOUR_MILLSECONDES));
        xjlUserOrderNew.setIpAddress(ipAddress);
        xjlUserOrderNew.setOrderId(orderId);
        xjlUserOrderNew.setPayProductId(payProductId);
        xjlUserOrderNew.setStatus("COMMIT");// 此状态在回调中修改COMMIT表示初始值 SUCCESS,FAILED,CANCELLED,REFUNDED,DOING
        xjlUserOrderNew.setTerminalId(terminalId);
        xjlUserOrderNew.setTerminalType(terminalType);
        xjlUserOrderNew.setType(ChangJiePayConstants.SERVICE_NMG_ZFT_API_QUICK_PAYMENT);
        xjlUserOrderNew.setUpdateDate(null);
        xjlUserOrderNew.setUserId(user.getUserId());
        xjlUserOrderService.insertXjlUserOrder(xjlUserOrderNew);
        return orderId;
    }

}
