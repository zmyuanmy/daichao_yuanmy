package com.jbb.mgt.changjiepay.contants;

public class ChangJiePayConstants {


    //1 商户采集方式

    //1.1 鉴权绑卡请求
    public static String SERVICE_NMG_BIZ_API_AUTH_REQ = "nmg_biz_api_auth_req";

    //1.2鉴权绑卡确认接口
    public static String SERVICE_NMG_API_AUTH_SMS = "nmg_api_auth_sms";

    //1.3 短信验证码发送接口
    public static String SERVICE_NMG_API_QUICK_PAYMENT_RESEND = "nmg_api_quick_payment_resend";

    //1.4 绑卡查询接口
    public static String SERVICE_NMG_API_AUTH_INFO_QRY = "nmg_api_auth_info_qry";

    //1.5 支付请求接口
    public static String SERVICE_NMG_BIZ_API_QUICK_PAYMENT = "nmg_biz_api_quick_payment";

    //1.6 支付确认接口
    public static String SERVICE_NMG_API_QUICK_PAYMENT_SMSCONFIRM = "nmg_api_quick_payment_smsconfirm";

    // 2 畅捷采集方式

    //2.1鉴权绑卡请求（前台模式）
    public static String SERVICE_NMG_PAGE_API_AUTH_REQ = "nmg_page_api_auth_req";

    //2.2 短信验证码发送接口 同1.3

    //2.3 绑卡查询接口 同1.4

    //2.4 支付请求接口 同1.5

    //2.5.支付确认接口 同 1.6

    //3 银行采集方式

    //3.1鉴权绑卡请求
    public static String SERVICE_NMG_CANAL_API_REQ = "nmg_canal_api_auth_req";

    //3.2 短信验证码发送接口 同1.3

    //3.3 绑卡查询接口 同1.4

    //3.4 支付请求接口
    public static String SERVICE_NMG_CANAL_API_QUICK_PAYMENT = "nmg_canal_api_quick_payment";

    //3.5 支付确认接口 同 1.6


    //4 商户采集模式

    //4.1 支付请求接口（直接支付）
    public static String SERVICE_NMG_ZFT_API_QUICK_PAYMENT = "nmg_zft_api_quick_payment";

    //4.2 短信验证码发送接口  同1.3

    //4.3 支付确认接口  同 1.6

    //4.4 快捷代扣请求接口
    public static String SERVICE_NMG_API_QUICKPAY_WITHHOLD = "nmg_api_quickpay_withhold";

    //5 公共类

    //5.1 订单查询
    public static String SERVICE_NMG_API_QUERY_TRADE = "nmg_api_query_trade";

    //5.2 退款接口
    public static String SERVICE_NMG_API_REFUND = "nmg_api_refund";

    //5.3 交易对账单
    public static String SERVICE_NMG_API_EVERYDAY_TRADE_FILE = "nmg_api_everyday_trade_file";

    //5.4 退款对账单
    public static String SERVICE_NMG_REFUND_TRADE_file = "nmg_api_refund_trade_file";

    //5.5 确认收货接口
    public static String SERVICE_NMG_QUICK_PAYMENT_RECEIPTCONFIRM = "nmg_api_quick_payment_receiptconfirm";



}
