package com.jbb.mgt.helipay.vo;

import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;

/**
 * 绑卡支付 请求参数 实体类
 *
 * @author wyq
 * @date 2018/8/29 15:47
 */
public class QuickPayBindPayVo {
    private String P1_bizType;
    private String P2_customerNumber;
    private String P3_bindId;
    private String P4_userId;
    private String P5_orderId;
    private String P6_timestamp;
    private String P7_currency;
    private String P8_orderAmount;
    private String P9_goodsName;
    private String P10_goodsDesc;
    private String P11_terminalType;
    private String P12_terminalId;
    private String P13_orderIp;
    private String P14_period;
    private String P15_periodUnit;
    private String P16_serverCallbackUrl;
    private String P17_validateCode;
    private String signatureType;
    private String encryptionKey;
    private String sign;

    public QuickPayBindPayVo() {
        super();
    }

    public QuickPayBindPayVo(String bindId, String userId, String orderId, String amount, String terminalId,
                             String orderIp, String serverCallbackUrl, String msgCode, String encryptionKey) {
        P1_bizType = HeliPayConstants.BIZ_TYPE_QUICK_PAY_SEND_VALIDATECODE;
        P2_customerNumber = PropertyManager.getProperty("jbb.pay.heli.customer.number");
        P3_bindId = bindId;
        P4_userId = userId;
        P5_orderId = orderId;
        P6_timestamp = HeliUtil.getCurrentTs();
        ;
        P7_currency = "CNY";
        P8_orderAmount = amount;
        P9_goodsName = PropertyManager.getProperty("jbb.pay.heli.goods.name");
        P10_goodsDesc = PropertyManager.getProperty("jbb.pay.heli.goods.desc");
        P11_terminalType = "IMEI";
        ;
        P12_terminalId = terminalId;
        P13_orderIp = orderIp;

        P16_serverCallbackUrl = serverCallbackUrl;
        P17_validateCode = msgCode;
        this.signatureType = "MD5WITHRSA";
        this.encryptionKey = encryptionKey;

    }

    public String getSigned() {
        StringBuffer sb = new StringBuffer();
        sb.append("&");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&");
        sb.append(this.getP2_customerNumber() == null ? "" : this.getP2_customerNumber());
        sb.append("&");
        sb.append(this.getP3_bindId() == null ? "" : this.getP3_bindId());
        sb.append("&");
        sb.append(this.getP4_userId() == null ? "" : this.getP4_userId());
        sb.append("&");
        sb.append(this.getP5_orderId() == null ? "" : this.getP5_orderId());
        sb.append("&");
        sb.append(this.getP6_timestamp() == null ? "" : this.getP6_timestamp());
        sb.append("&");
        sb.append(this.getP7_currency() == null ? "" : this.getP7_currency());
        sb.append("&");
        sb.append(this.getP8_orderAmount() == null ? "" : this.getP8_orderAmount());
        sb.append("&");
        sb.append(this.getP9_goodsName() == null ? "" : this.getP9_goodsName());
        sb.append("&");
        sb.append(this.getP10_goodsDesc() == null ? "" : this.getP10_goodsDesc());
        sb.append("&");
        sb.append(this.getP11_terminalType() == null ? "" : this.getP11_terminalType());
        sb.append("&");
        sb.append(this.getP12_terminalId() == null ? "" : this.getP12_terminalId());
        sb.append("&");
        sb.append(this.getP13_orderIp() == null ? "" : this.getP13_orderIp());
        sb.append("&");
        sb.append(this.getP14_period() == null ? "" : this.getP14_period());
        sb.append("&");
        sb.append(this.getP15_periodUnit() == null ? "" : this.getP15_periodUnit());
        sb.append("&");
        sb.append(this.getP16_serverCallbackUrl() == null ? "" : this.getP16_serverCallbackUrl());
        return sb.toString();
    }

    public String getRequestFormStr() {
        StringBuffer sb = new StringBuffer();
        sb.append("P1_bizType=");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&P2_customerNumber=");
        sb.append(this.getP2_customerNumber() == null ? "" : this.getP2_customerNumber());
        sb.append("&P3_bindId=");
        sb.append(this.getP3_bindId() == null ? "" : this.getP3_bindId());
        sb.append("&P4_userId=");
        sb.append(this.getP4_userId() == null ? "" : this.getP4_userId());
        sb.append("&P5_orderId=");
        sb.append(this.getP5_orderId() == null ? "" : this.getP5_orderId());
        sb.append("&P6_timestamp=");
        sb.append(this.getP6_timestamp() == null ? "" : this.getP6_timestamp());
        sb.append("&P7_currency=");
        sb.append(this.getP7_currency() == null ? "" : this.getP7_currency());
        sb.append("&P8_orderAmount=");
        sb.append(this.getP8_orderAmount() == null ? "" : this.getP8_orderAmount());
        sb.append("&P9_goodsName=");
        sb.append(this.getP9_goodsName() == null ? "" : this.getP9_goodsName());
        sb.append("&P10_goodsDesc=");
        sb.append(this.getP10_goodsDesc() == null ? "" : this.getP10_goodsDesc());
        sb.append("&P11_terminalType=");
        sb.append(this.getP11_terminalType() == null ? "" : this.getP11_terminalType());
        sb.append("&P12_terminalId=");
        sb.append(this.getP12_terminalId() == null ? "" : this.getP12_terminalId());
        sb.append("&P13_orderIp=");
        sb.append(this.getP13_orderIp() == null ? "" : this.getP13_orderIp());
        sb.append("&P14_period=");
        sb.append(this.getP14_period() == null ? "" : this.getP14_period());
        sb.append("&P15_periodUnit=");
        sb.append(this.getP15_periodUnit() == null ? "" : this.getP15_periodUnit());
        sb.append("&P16_serverCallbackUrl=");
        sb.append(this.getP16_serverCallbackUrl() == null ? "" : this.getP16_serverCallbackUrl());
        sb.append("&P17_validateCode=");
        sb.append(this.getP17_validateCode() == null ? "" : this.getP17_validateCode());
        sb.append("&signatureType=");
        sb.append(this.getSignatureType() == null ? "" : this.getSignatureType());
        sb.append("&encryptionKey=");
        sb.append(this.getEncryptionKey() == null ? "" : this.getEncryptionKey());
        sb.append("&sign=");
        sb.append(this.getSign() == null ? "" : this.getSign());
        return sb.toString();
    }

    public String getP1_bizType() {
        return P1_bizType;
    }

    public void setP1_bizType(String p1_bizType) {
        P1_bizType = p1_bizType;
    }

    public String getP2_customerNumber() {
        return P2_customerNumber;
    }

    public void setP2_customerNumber(String p2_customerNumber) {
        P2_customerNumber = p2_customerNumber;
    }

    public String getP3_bindId() {
        return P3_bindId;
    }

    public void setP3_bindId(String p3_bindId) {
        P3_bindId = p3_bindId;
    }

    public String getP4_userId() {
        return P4_userId;
    }

    public void setP4_userId(String p4_userId) {
        P4_userId = p4_userId;
    }

    public String getP5_orderId() {
        return P5_orderId;
    }

    public void setP5_orderId(String p5_orderId) {
        P5_orderId = p5_orderId;
    }

    public String getP6_timestamp() {
        return P6_timestamp;
    }

    public void setP6_timestamp(String p6_timestamp) {
        P6_timestamp = p6_timestamp;
    }

    public String getP7_currency() {
        return P7_currency;
    }

    public void setP7_currency(String p7_currency) {
        P7_currency = p7_currency;
    }

    public String getP8_orderAmount() {
        return P8_orderAmount;
    }

    public void setP8_orderAmount(String p8_orderAmount) {
        P8_orderAmount = p8_orderAmount;
    }

    public String getP9_goodsName() {
        return P9_goodsName;
    }

    public void setP9_goodsName(String p9_goodsName) {
        P9_goodsName = p9_goodsName;
    }

    public String getP10_goodsDesc() {
        return P10_goodsDesc;
    }

    public void setP10_goodsDesc(String p10_goodsDesc) {
        P10_goodsDesc = p10_goodsDesc;
    }

    public String getP11_terminalType() {
        return P11_terminalType;
    }

    public void setP11_terminalType(String p11_terminalType) {
        P11_terminalType = p11_terminalType;
    }

    public String getP12_terminalId() {
        return P12_terminalId;
    }

    public void setP12_terminalId(String p12_terminalId) {
        P12_terminalId = p12_terminalId;
    }

    public String getP13_orderIp() {
        return P13_orderIp;
    }

    public void setP13_orderIp(String p13_orderIp) {
        P13_orderIp = p13_orderIp;
    }

    public String getP14_period() {
        return P14_period;
    }

    public void setP14_period(String p14_period) {
        P14_period = p14_period;
    }

    public String getP15_periodUnit() {
        return P15_periodUnit;
    }

    public void setP15_periodUnit(String p15_periodUnit) {
        P15_periodUnit = p15_periodUnit;
    }

    public String getP16_serverCallbackUrl() {
        return P16_serverCallbackUrl;
    }

    public void setP16_serverCallbackUrl(String p16_serverCallbackUrl) {
        P16_serverCallbackUrl = p16_serverCallbackUrl;
    }

    public String getP17_validateCode() {
        return P17_validateCode;
    }

    public void setP17_validateCode(String p17_validateCode) {
        P17_validateCode = p17_validateCode;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "QuickPayBindPayVo{" + "P1_bizType='" + P1_bizType + '\'' + ", P2_customerNumber='" + P2_customerNumber
                + '\'' + ", P3_bindId='" + P3_bindId + '\'' + ", P4_userId='" + P4_userId + '\'' + ", P5_orderId='"
                + P5_orderId + '\'' + ", P6_timestamp='" + P6_timestamp + '\'' + ", P7_currency='" + P7_currency + '\''
                + ", P8_orderAmount='" + P8_orderAmount + '\'' + ", P9_goodsName='" + P9_goodsName + '\''
                + ", P10_goodsDesc='" + P10_goodsDesc + '\'' + ", P11_terminalType='" + P11_terminalType + '\''
                + ", P12_terminalId='" + P12_terminalId + '\'' + ", P13_orderIp='" + P13_orderIp + '\'' + ", P14_period='"
                + P14_period + '\'' + ", P15_periodUnit='" + P15_periodUnit + '\'' + ", P16_serverCallbackUrl='"
                + P16_serverCallbackUrl + '\'' + ", P17_validateCode='" + P17_validateCode + '\'' + ", signatureType='"
                + signatureType + '\'' + ", encryptionKey='" + encryptionKey + '\'' + ", sign='" + sign + '\'' + '}';
    }
}
