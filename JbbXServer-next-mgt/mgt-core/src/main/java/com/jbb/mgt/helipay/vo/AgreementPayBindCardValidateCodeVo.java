package com.jbb.mgt.helipay.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;

/**
 * 鉴权绑卡短信 请求参数 实体类
 *
 * @author wyq
 * @date 2018/8/29 15:22
 */
public class AgreementPayBindCardValidateCodeVo {
    private String P1_bizType;
    private String P2_customerNumber;
    private String P3_userId;
    private String P4_orderId;
    private String P5_timestamp;
    private String P6_cardNo;
    private String P7_phone;
    private String P8_idCardNo;
    private String P9_idCardType;
    private String P10_payerName;
    private String signatureType;
    private String encryptionKey;
    private String sign;

    public AgreementPayBindCardValidateCodeVo() {
        super();
    }

    public AgreementPayBindCardValidateCodeVo(String userId, String orderId, String cardNo, String cardName,
        String phone, String idCardNo, String encryptionKey) {
        P1_bizType = HeliPayConstants.BIZ_TYPE_QUICK_AGREEMENT_PAY_BIND_CARD_CODE;
        P2_customerNumber = PropertyManager.getProperty("jbb.pay.heli.customer.number");
        P3_userId = userId;
        P4_orderId = orderId;
        P5_timestamp = HeliUtil.getCurrentTs();;
        P6_cardNo = cardNo;
        P7_phone = phone;
        P8_idCardNo = idCardNo;
        P9_idCardType = "IDCARD";
        P10_payerName = cardName;
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
        sb.append(this.getP6_cardNo() == null ? "" : this.getP6_cardNo());
        sb.append("&");
        sb.append(this.getP7_phone() == null ? "" : this.getP7_phone());
        sb.append("&");
        sb.append(this.getP8_idCardNo() == null ? "" : this.getP8_idCardNo());
        sb.append("&");
        sb.append(this.getP9_idCardType() == null ? "" : this.getP9_idCardType());
        sb.append("&");
        sb.append(this.getP10_payerName() == null ? "" : this.getP10_payerName());
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
        sb.append("&P6_cardNo=");
        sb.append(this.getP6_cardNo() == null ? "" :URLEncoder.encode(this.getP6_cardNo(),"UTF-8"));
        sb.append("&P7_phone=");
        sb.append(this.getP7_phone() == null ? "" : URLEncoder.encode(this.getP7_phone(),"UTF-8"));
        sb.append("&P8_idCardNo=");
        sb.append(this.getP8_idCardNo() == null ? "" : URLEncoder.encode(this.getP8_idCardNo(),"UTF-8"));
        sb.append("&P9_idCardType=");
        sb.append(this.getP9_idCardType() == null ? "" : this.getP9_idCardType());
        sb.append("&P10_payerName=");
        sb.append(this.getP10_payerName() == null ? "" : URLEncoder.encode(this.getP10_payerName(),"UTF-8"));
        sb.append("&signatureType=");
        sb.append(this.getSignatureType() == null ? "" : this.getSignatureType());
        sb.append("&encryptionKey=");
        sb.append(this.getEncryptionKey() == null ? "" : URLEncoder.encode(this.getEncryptionKey(),"UTF-8"));
        sb.append("&sign=");
        sb.append(this.getSign() == null ? "" : URLEncoder.encode(this.getSign(),"UTF-8"));
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

    public String getP6_cardNo() {
        return P6_cardNo;
    }

    public void setP6_cardNo(String p6_cardNo) {
        P6_cardNo = p6_cardNo;
    }

    public String getP7_phone() {
        return P7_phone;
    }

    public void setP7_phone(String p7_phone) {
        P7_phone = p7_phone;
    }

    public String getP8_idCardNo() {
        return P8_idCardNo;
    }

    public void setP8_idCardNo(String p8_idCardNo) {
        P8_idCardNo = p8_idCardNo;
    }

    public String getP9_idCardType() {
        return P9_idCardType;
    }

    public void setP9_idCardType(String p9_idCardType) {
        P9_idCardType = p9_idCardType;
    }

    public String getP10_payerName() {
        return P10_payerName;
    }

    public void setP10_payerName(String p10_payerName) {
        P10_payerName = p10_payerName;
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
        return "AgreementPayBindCardValidateCodeVo{" + "P1_bizType='" + P1_bizType + '\'' + ", P2_customerNumber='"
            + P2_customerNumber + '\'' + ", P3_userId='" + P3_userId + '\'' + ", P4_orderId='" + P4_orderId + '\''
            + ", P5_timestamp='" + P5_timestamp + '\'' + ", P6_cardNo='" + P6_cardNo + '\'' + ", P7_phone='" + P7_phone
            + '\'' + ", P8_idCardNo='" + P8_idCardNo + '\'' + ", P9_idCardType='" + P9_idCardType + '\''
            + ", P10_payerName='" + P10_payerName + '\'' + ", signatureType='" + signatureType + '\''
            + ", encryptionKey='" + encryptionKey + '\'' + ", sign='" + sign + '\'' + '}';
    }
}
