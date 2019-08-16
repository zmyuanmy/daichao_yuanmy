package com.jbb.mgt.helipay.service;

public interface QuickPayService {

    /**
     * 4.1首次支付
     */
    void quickPayCreateOrder(String userId, String orderId, String cardName, String idcard, String cardNo, String phone,
        String amount, String orderIp, String terminalId) throws Exception;

    /**
     * 4.2首次支付短信
     */
    void quickPaySendValidateCode(String orderId, String phone) throws Exception;

    /**
     * 4.3确认支付
     */
    void quickPayConfirmPay(String orderId, String msgCode, String ipAddress) throws Exception;

    /**
     * 4.4鉴权绑卡短信
     */
    void agreementPayBindCardValidateCode(String userId, String orderId, String phone, String cardNo, String cardName,
        String idcard) throws Exception;

    /**
     * 4.5鉴权绑卡
     */
    void quickPayBindCard(String orderId, String userId, String idcard, String cardName, String cardNo, String msgCode,
        String phone) throws Exception;

    /**
     * 4.6绑卡支付短信
     */
    void quickPayBindPayValidateCode(String bindId, String userId, String orderId, String amount, String phone)
        throws Exception;

    /**
     * 4.7绑卡支付
     */
    void quickPayBindPay(String bindId, String userId, String orderId, String amount, String terminalId, String orderIp,
        String msgCode) throws Exception;

    /**
     * 4.8异步通知
     */
    void quickPayCallback();

    /**
     * 4.9订单查询
     */
    void quickPayQuery(String orderId) throws Exception;

    /**
     * 4.10银行卡解绑
     */
    void bankCardUnbind(String userId, String bindId, String orderId) throws Exception;

    /**
     * 4.11用户绑定银行卡信息查询
     */
    void bankCardbindList(String userId, String bindId) throws Exception;

}
