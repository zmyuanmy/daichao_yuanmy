package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.changjiepay.service.impl.ChangJieQuickPayServiceImpl;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.domain.XjlUserOrder;
import com.jbb.mgt.core.service.PayService;
import com.jbb.mgt.core.service.QianChengService;
import com.jbb.mgt.core.service.XjlApplyRecordService;
import com.jbb.mgt.core.service.XjlUserOrderService;
import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.mgt.helipay.service.QuickPayService;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.HeLiPayException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service("HeLiPayServices")
public class HeLiPayServiceImpl implements PayService {

    private static Logger logger = LoggerFactory.getLogger(HeLiPayServiceImpl.class);

    @Autowired
    private XjlUserOrderService xjlUserOrderService;

    @Autowired
    private QuickPayService quickPayService;

    @Autowired
    private XjlApplyRecordService xjlApplyRecordService;

    @Override
    public String sendPayMsgCode(User user, String applyId, String cardNo, String terminalId, String terminalType,
        String ipAddress) throws Exception {

        String payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.helibao.id");
        // 如果没有，新建订单
        String orderId = HeliUtil.generateOrderId();

        XjlApplyRecord xjlApplyRecord = xjlApplyRecordService.getXjlApplyRecordByApplyId(applyId, null, null);

        if (xjlApplyRecord == null) {
            throw new ObjectNotFoundException();
        }

        double amount = (double)(xjlApplyRecord.getAmount() + xjlApplyRecord.getServiceFee()) / (double)100;
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String amountString = dFormat.format(amount);
        // 调用首次支付接口
        quickPayService.quickPayCreateOrder(String.valueOf(user.getUserId()), orderId, user.getUserName(),
            user.getIdCard(), cardNo, user.getPhoneNumber(), amountString, ipAddress, terminalId);

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
        xjlUserOrderNew.setType(HeliPayConstants.BIZ_TYPE_QUICK_PAY_CREATE_ORDER);
        xjlUserOrderNew.setUpdateDate(null);
        xjlUserOrderNew.setUserId(user.getUserId());
        xjlUserOrderService.insertXjlUserOrder(xjlUserOrderNew);
        // 调用首次支付短信接口
        quickPayService.quickPaySendValidateCode(orderId, user.getPhoneNumber());

        return orderId;
    }

    @Override
    public void payConfirm(String orderId, String msgCode, String ipAddress) throws Exception {
        XjlUserOrder xjlUserOrder = xjlUserOrderService.selectXjlUserOrderById(orderId);

        if (xjlUserOrder == null) {
            throw new ObjectNotFoundException();
        }

        if (!xjlUserOrder.getStatus().equals("COMMIT")) {
            throw new HeLiPayException("jbb.xjl.error.exception.hlb.notify." + xjlUserOrder.getStatus(), "zh");
        }
        // 调用支付接口
        quickPayService.quickPayConfirmPay(orderId, msgCode, ipAddress);
    }

    @Override
    public String directPay(User user, String applyId, String cardNo, String terminalId, String terminalType,
        String ipAddress) {
        return null;
    }
}
