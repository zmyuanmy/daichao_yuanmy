package com.jbb.mgt.changjiepay.service;

public interface ChangJieQuickPayService {

    // 1.1 鉴权绑卡请求
    void bizApiAuthReq(String userId, String orderId, String idcard, String cardNo, String phone, String userName,
        String cardCvn2, String cardExprDt);

    // 1.2鉴权绑卡确认接口
    void apiAuthSms(String orderId,String msgCode);

    // 1.3 短信验证码重新发送接口
    void apiQuickPaymentResend(String orderId);

    // 1.4 绑卡查询接口
    void apiAuthInfoQry(String userId);

    // 1.5 支付请求接口
    void bizApiQuickPayment(String userId,String cardNo,String amount,String orderId);

    // 1.6 支付确认接口
    void apiQuickPaymentSmsConfirm(String orderId,String msgCode);

    //4.1 直接支付接口
    void apiQuickPayment(String userId,String idCard,String cardNo,String amount,String orderId,String userName,String phoneNumber);

}
