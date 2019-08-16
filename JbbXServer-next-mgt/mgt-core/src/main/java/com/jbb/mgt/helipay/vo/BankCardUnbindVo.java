package com.jbb.mgt.helipay.vo;

import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;

/**
 * 银行卡解绑 请求参数 实体类
 *
 * @author wyq
 * @date 2018/8/29 16:13
 */
public class BankCardUnbindVo {
    private String P1_bizType;
    private String P2_customerNumber;
    private String P3_userId;
    private String P4_bindId;
    private String P5_orderId;
    private String P6_timestamp;
    private String signatureType;
    private String sign;

    public BankCardUnbindVo() {
        super();
    }

    public BankCardUnbindVo(String userId, String bindId, String orderId) {
        P1_bizType = HeliPayConstants.BIZ_TYPE_BANK_CARD_UNBIND;
        P2_customerNumber = PropertyManager.getProperty("jbb.pay.heli.customer.number");
        P3_userId = userId;
        P4_bindId = bindId;
        P5_orderId = orderId;
        P6_timestamp = HeliUtil.getCurrentTs();;
        this.signatureType = "MD5WITHRSA";

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
        sb.append(this.getP4_bindId() == null ? "" : this.getP4_bindId());
        sb.append("&");
        sb.append(this.getP5_orderId() == null ? "" : this.getP5_orderId());
        sb.append("&");
        sb.append(this.getP6_timestamp() == null ? "" : this.getP6_timestamp());
        return sb.toString();
    }

    public String getRequestFormStr() {
        StringBuffer sb = new StringBuffer();
        sb.append("P1_bizType=");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&P2_customerNumber=");
        sb.append(this.getP2_customerNumber() == null ? "" : this.getP2_customerNumber());
        sb.append("&P3_userId=");
        sb.append(this.getP3_userId() == null ? "" : this.getP3_userId());
        sb.append("&P4_bindId=");
        sb.append(this.getP4_bindId() == null ? "" : this.getP4_bindId());
        sb.append("&P5_orderId=");
        sb.append(this.getP5_orderId() == null ? "" : this.getP5_orderId());
        sb.append("&P6_timestamp=");
        sb.append(this.getP6_timestamp() == null ? "" : this.getP6_timestamp());
        sb.append("&signatureType=");
        sb.append(this.getSignatureType() == null ? "" : this.getSignatureType());
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

    public String getP3_userId() {
        return P3_userId;
    }

    public void setP3_userId(String p3_userId) {
        P3_userId = p3_userId;
    }

    public String getP4_bindId() {
        return P4_bindId;
    }

    public void setP4_bindId(String p4_bindId) {
        P4_bindId = p4_bindId;
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

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "BankCardUnbindVo{" + "P1_bizType='" + P1_bizType + '\'' + ", P2_customerNumber='" + P2_customerNumber
            + '\'' + ", P3_userId='" + P3_userId + '\'' + ", P4_bindId='" + P4_bindId + '\'' + ", P5_orderId='"
            + P5_orderId + '\'' + ", P6_timestamp='" + P6_timestamp + '\'' + ", signatureType='" + signatureType + '\''
            + ", sign='" + sign + '\'' + '}';
    }
}
