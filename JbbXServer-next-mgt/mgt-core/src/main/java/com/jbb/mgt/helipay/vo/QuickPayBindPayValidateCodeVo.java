package com.jbb.mgt.helipay.vo;

import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;

/**
 * 绑卡支付短信 请求参数 实体类
 *
 * @author wyq
 * @date 2018/8/29 15:40
 */
public class QuickPayBindPayValidateCodeVo {
    private String P1_bizType;
    private String P2_customerNumber;
    private String P3_bindId;
    private String P4_userId;
    private String P5_orderId;
    private String P6_timestamp;
    private String P7_currency;
    private String P8_orderAmount;
    private String P9_phone;
    private String signatureType;
    private String encryptionKey;
    private String sign;

    public QuickPayBindPayValidateCodeVo() {
        super();
    }

    public QuickPayBindPayValidateCodeVo(String bindId, String userId, String orderId, String amount, String phone,
        String encryptionKey) {
        P1_bizType = HeliPayConstants.BIZ_TYPE_QUICK_PAY_SEND_VALIDATECODE;
        P2_customerNumber = PropertyManager.getProperty("jbb.pay.heli.customer.number");
        P3_bindId = bindId;
        P4_userId = userId;
        P5_orderId = orderId;
        P6_timestamp = HeliUtil.getCurrentTs();
        P7_currency = "CNY";
        P8_orderAmount = amount;
        P9_phone = phone;
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
        sb.append(this.getP9_phone() == null ? "" : this.getP9_phone());
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
        sb.append("&P9_phone=");
        sb.append(this.getP9_phone() == null ? "" : this.getP9_phone());
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

    public String getP9_phone() {
        return P9_phone;
    }

    public void setP9_phone(String p9_phone) {
        P9_phone = p9_phone;
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
        return "QuickPayBindPayValidateCodeVo{" + "P1_bizType='" + P1_bizType + '\'' + ", P2_customerNumber='"
            + P2_customerNumber + '\'' + ", P3_bindId='" + P3_bindId + '\'' + ", P4_userId='" + P4_userId + '\''
            + ", P5_orderId='" + P5_orderId + '\'' + ", P6_timestamp='" + P6_timestamp + '\'' + ", P7_currency='"
            + P7_currency + '\'' + ", P8_orderAmount='" + P8_orderAmount + '\'' + ", P9_phone='" + P9_phone + '\''
            + ", signatureType='" + signatureType + '\'' + ", encryptionKey='" + encryptionKey + '\'' + ", sign='"
            + sign + '\'' + '}';
    }
}
