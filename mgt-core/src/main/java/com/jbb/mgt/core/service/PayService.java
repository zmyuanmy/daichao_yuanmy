package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.User;

public interface PayService {

    String sendPayMsgCode(User user, String applyId, String cardNo, String terminalId, String terminalType,String ipAddress)
        throws Exception;

    void payConfirm(String orderId, String msgCode, String ipAddress) throws Exception;

    String directPay(User user, String applyId, String cardNo, String terminalId, String terminalType,String ipAddress);

}
