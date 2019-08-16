package com.jbb.mgt.helipay.vo;

import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 首次支付短信 请求参数 实体类
 *
 * @author wyq
 * @date 2018/8/29 14:44
 */
public class QuickPaySendValidateCodeVo {
    private String P1_bizType;
    private String P2_customerNumber;
    private String P3_orderId;
    private String P4_timestamp;
    private String P5_phone;
    private String signatureType;
    private String encryptionKey;
    private String sign;

    public QuickPaySendValidateCodeVo() {
        super();
    }

    public QuickPaySendValidateCodeVo(String orderId, String phoneEncoded, String encryptionKey) {
        P1_bizType = HeliPayConstants.BIZ_TYPE_QUICK_PAY_SEND_VALIDATECODE;
        P2_customerNumber = PropertyManager.getProperty("jbb.pay.heli.customer.number");;
        P3_orderId = orderId;
        P4_timestamp = HeliUtil.getCurrentTs();
        P5_phone = phoneEncoded;
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
        sb.append(this.getP3_orderId() == null ? "" : this.getP3_orderId());
        sb.append("&");
        sb.append(this.getP4_timestamp() == null ? "" : this.getP4_timestamp());
        sb.append("&");
        sb.append(this.getP5_phone() == null ? "" : this.getP5_phone());
        return sb.toString();
    }

    public String getRequestFormStr() throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append("P1_bizType=");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&P2_customerNumber=");
        sb.append(this.getP2_customerNumber() == null ? "" : this.getP2_customerNumber());
        sb.append("&P3_orderId=");
        sb.append(this.getP3_orderId() == null ? "" : this.getP3_orderId());
        sb.append("&P4_timestamp=");
        sb.append(this.getP4_timestamp() == null ? "" : this.getP4_timestamp());
        sb.append("&P5_phone=");
        sb.append(this.getP5_phone() == null ? "" :  URLEncoder.encode(this.getP5_phone(),"UTF-8"));
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

    public String getP3_orderId() {
        return P3_orderId;
    }

    public void setP3_orderId(String p3_orderId) {
        P3_orderId = p3_orderId;
    }

    public String getP4_timestamp() {
        return P4_timestamp;
    }

    public void setP4_timestamp(String p4_timestamp) {
        P4_timestamp = p4_timestamp;
    }

    public String getP5_phone() {
        return P5_phone;
    }

    public void setP5_phone(String p5_phone) {
        P5_phone = p5_phone;
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
        return "QuickPaySendValidateCodeVo{" + "P1_bizType='" + P1_bizType + '\'' + ", P2_customerNumber='"
            + P2_customerNumber + '\'' + ", P3_orderId='" + P3_orderId + '\'' + ", P4_timestamp='" + P4_timestamp + '\''
            + ", P5_phone='" + P5_phone + '\'' + ", signatureType='" + signatureType + '\'' + ", encryptionKey='"
            + encryptionKey + '\'' + ", sign='" + sign + '\'' + '}';
    }
}
