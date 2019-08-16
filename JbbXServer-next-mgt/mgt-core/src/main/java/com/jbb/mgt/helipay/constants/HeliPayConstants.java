package com.jbb.mgt.helipay.constants;

public class HeliPayConstants {

    // 1. 单笔代付
    public static String BIZ_TYPE_TRANSFER = "Transfer";

    // 2. 单笔代付查询
    public static String BIZ_TYPE_TRANSFER_QUERY = "TransferQuery";
    
    // 4.1. 创建订单
    public static String BIZ_TYPE_QUICK_PAY_CREATE_ORDER = "QuickPayCreateOrder";

    // 4.2. 单笔代付查询
    public static String BIZ_TYPE_QUICK_PAY_SEND_VALIDATECODE = "QuickPaySendValidateCode";

    // 4.3. 确认支付
    public static String BIZ_TYPE_QUICK_PAY_CONFIRM_PAY = "QuickPayConfirmPay";

    // 4.4. 单笔代付查询
    public static String BIZ_TYPE_QUICK_AGREEMENT_PAY_BIND_CARD_CODE = "AgreementPayBindCardValidateCode";

    // 4.5. 鉴权绑卡
    public static String BIZ_TYPE_QUICK_PAY_BIND_CARD = "QuickPayBindCard";
    
    // 4.6. 绑卡支付短信
    public static String BIZ_TYPE_QUICK_PAY_BIND_VALIDATE_CODE = "QuickPayBindPayValidateCode";
    
    // 4.7. 绑卡支付
    public static String BIZ_TYPE_QUICK_PAY_BIND_PAY = "QuickPayBindPay";
    
    // 4.8. 订单查询
    public static String BIZ_TYPE_QUICK_PAY_CALLBACK = "QuickPayConfirmPay";

    // 4.9. 订单查询
    public static String BIZ_TYPE_QUICK_PAY_QUERY = "QuickPayQuery";

    // 4.10. 银行卡解绑
    public static String BIZ_TYPE_BANK_CARD_UNBIND = "BankCardUnbind";

    // 4.11. 银行卡解绑
    public static String BIZ_TYPE_BANK_CARD_BIND_LIST = "BankCardbindList";

}
