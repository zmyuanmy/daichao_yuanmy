package com.jbb.mgt.helipay.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;

/**
 * 鉴权参数 请求参数 实体类
 *
 * @author wyq
 * @date 2018/8/29 15:33
 */
public class QuickPayBindCardVo {
    private String P1_bizType;
    private String P2_customerNumber;
    private String P3_userId;
    private String P4_orderId;
    private String P5_timestamp;
    private String P6_payerName;
    private String P7_idCardType;
    private String P8_idCardNo;
    private String P9_cardNo;
    private String P10_year;
    private String P11_month;
    private String P12_cvv2;
    private String P13_phone;
    private String P14_validateCode;
    private String P15_isEncrypt;
    private String signatureType;
    private String encryptionKey;
    private String sign;

    public QuickPayBindCardVo() {
        super();
    }

    public QuickPayBindCardVo(String userId, String orderId, String username, String idcard, String cardNo,
        String phone, String msgCode, String encryptionKey) {
        P1_bizType = HeliPayConstants.BIZ_TYPE_QUICK_PAY_BIND_CARD;
        P2_customerNumber = PropertyManager.getProperty("jbb.pay.heli.customer.number");
        P3_userId = userId;
        P4_orderId = orderId;
        P5_timestamp = HeliUtil.getCurrentTs();
        P6_payerName = username;
        P7_idCardType = "IDCARD";
        P8_idCardNo = idcard;
        P9_cardNo = cardNo;
        P13_phone = phone;
        P14_validateCode = msgCode;
        P15_isEncrypt = "TRUE";
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
        sb.append(this.getP3_userId() == null ? "" : this.getP3_userId());
        sb.append("&");
        sb.append(this.getP4_orderId() == null ? "" : this.getP4_orderId());
        sb.append("&");
        sb.append(this.getP5_timestamp() == null ? "" : this.getP5_timestamp());
        sb.append("&");
        sb.append(this.getP6_payerName() == null ? "" : this.getP6_payerName());
        sb.append("&");
        sb.append(this.getP7_idCardType() == null ? "" : this.getP7_idCardType());
        sb.append("&");
        sb.append(this.getP8_idCardNo() == null ? "" : this.getP8_idCardNo());
        sb.append("&");
        sb.append(this.getP9_cardNo() == null ? "" : this.getP9_cardNo());
        sb.append("&");
        sb.append(this.getP10_year() == null ? "" : this.getP10_year());
        sb.append("&");
        sb.append(this.getP11_month() == null ? "" : this.getP11_month());
        sb.append("&");
        sb.append(this.getP12_cvv2() == null ? "" : this.getP12_cvv2());
        sb.append("&");
        sb.append(this.getP13_phone() == null ? "" : this.getP13_phone());
        sb.append("&");
        sb.append(this.getP14_validateCode() == null ? "" : this.getP14_validateCode());
        return sb.toString();
    }

    public String getRequestFormStr() throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append("P1_bizType=");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&P2_customerNumber=");
        sb.append(this.getP2_customerNumber() == null ? "" : this.getP2_customerNumber());
        sb.append("&P3_userId=");
        sb.append(this.getP3_userId() == null ? "" : this.getP3_userId());
        sb.append("&P4_orderId=");
        sb.append(this.getP4_orderId() == null ? "" : this.getP4_orderId());
        sb.append("&P5_timestamp=");
        sb.append(this.getP5_timestamp() == null ? "" : this.getP5_timestamp());
        sb.append("&P6_payerName=");
        sb.append(this.getP6_payerName() == null ? "" : URLEncoder.encode(this.getP6_payerName(), "UTF-8"));
        sb.append("&P7_idCardType=");
        sb.append(this.getP7_idCardType() == null ? "" : this.getP7_idCardType());
        sb.append("&P8_idCardNo=");
        sb.append(this.getP8_idCardNo() == null ? "" : URLEncoder.encode(this.getP8_idCardNo(), "UTF-8"));
        sb.append("&P9_cardNo=");
        sb.append(this.getP9_cardNo() == null ? "" : URLEncoder.encode(this.getP9_cardNo(), "UTF-8"));
        sb.append("&P10_year=");
        sb.append(this.getP10_year() == null ? "" : URLEncoder.encode(this.getP10_year(), "UTF-8"));
        sb.append("&P11_month=");
        sb.append(this.getP11_month() == null ? "" : URLEncoder.encode(this.getP11_month(), "UTF-8"));
        sb.append("&P12_cvv2=");
        sb.append(this.getP12_cvv2() == null ? "" : URLEncoder.encode(this.getP12_cvv2(), "UTF-8"));
        sb.append("&P13_phone=");
        sb.append(this.getP13_phone() == null ? "" : URLEncoder.encode(this.getP13_phone(), "UTF-8"));
        sb.append("&P14_validateCode=");
        sb.append(this.getP14_validateCode() == null ? "" : URLEncoder.encode(this.getP14_validateCode(), "UTF-8"));
        sb.append("&P15_isEncrypt=");
        sb.append(this.getP15_isEncrypt() == null ? "" : this.getP15_isEncrypt());
        sb.append("&signatureType=");
        sb.append(this.getSignatureType() == null ? "" : this.getSignatureType());
        sb.append("&encryptionKey=");
        sb.append(this.getEncryptionKey() == null ? "" : URLEncoder.encode(this.getEncryptionKey(), "UTF-8"));
        sb.append("&sign=");
        sb.append(this.getSign() == null ? "" : URLEncoder.encode(this.getSign(), "UTF-8"));
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

    public String getP3_userId() {
        return P3_userId;
    }

    public void setP3_userId(String p3_userId) {
        P3_userId = p3_userId;
    }

    public String getP4_orderId() {
        return P4_orderId;
    }

    public void setP4_orderId(String p4_orderId) {
        P4_orderId = p4_orderId;
    }

    public String getP5_timestamp() {
        return P5_timestamp;
    }

    public void setP5_timestamp(String p5_timestamp) {
        P5_timestamp = p5_timestamp;
    }

    public String getP6_payerName() {
        return P6_payerName;
    }

    public void setP6_payerName(String p6_payerName) {
        P6_payerName = p6_payerName;
    }

    public String getP7_idCardType() {
        return P7_idCardType;
    }

    public void setP7_idCardType(String p7_idCardType) {
        P7_idCardType = p7_idCardType;
    }

    public String getP8_idCardNo() {
        return P8_idCardNo;
    }

    public void setP8_idCardNo(String p8_idCardNo) {
        P8_idCardNo = p8_idCardNo;
    }

    public String getP9_cardNo() {
        return P9_cardNo;
    }

    public void setP9_cardNo(String p9_cardNo) {
        P9_cardNo = p9_cardNo;
    }

    public String getP10_year() {
        return P10_year;
    }

    public void setP10_year(String p10_year) {
        P10_year = p10_year;
    }

    public String getP11_month() {
        return P11_month;
    }

    public void setP11_month(String p11_month) {
        P11_month = p11_month;
    }

    public String getP12_cvv2() {
        return P12_cvv2;
    }

    public void setP12_cvv2(String p12_cvv2) {
        P12_cvv2 = p12_cvv2;
    }

    public String getP13_phone() {
        return P13_phone;
    }

    public void setP13_phone(String p13_phone) {
        P13_phone = p13_phone;
    }

    public String getP14_validateCode() {
        return P14_validateCode;
    }

    public void setP14_validateCode(String p14_validateCode) {
        P14_validateCode = p14_validateCode;
    }

    public String getP15_isEncrypt() {
        return P15_isEncrypt;
    }

    public void setP15_isEncrypt(String p15_isEncrypt) {
        P15_isEncrypt = p15_isEncrypt;
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
        return "QuickPayBindCardVo{" + "P1_bizType='" + P1_bizType + '\'' + ", P2_customerNumber='" + P2_customerNumber
            + '\'' + ", P3_userId='" + P3_userId + '\'' + ", P4_orderId='" + P4_orderId + '\'' + ", P5_timestamp='"
            + P5_timestamp + '\'' + ", P6_payerName='" + P6_payerName + '\'' + ", P7_idCardType='" + P7_idCardType
            + '\'' + ", P8_idCardNo='" + P8_idCardNo + '\'' + ", P9_cardNo='" + P9_cardNo + '\'' + ", P10_year='"
            + P10_year + '\'' + ", P11_month='" + P11_month + '\'' + ", P12_cvv2='" + P12_cvv2 + '\'' + ", P13_phone='"
            + P13_phone + '\'' + ", P14_validateCode='" + P14_validateCode + '\'' + ", P15_isEncrypt='" + P15_isEncrypt
            + '\'' + ", signatureType='" + signatureType + '\'' + ", encryptionKey='" + encryptionKey + '\''
            + ", sign='" + sign + '\'' + '}';
    }
}
