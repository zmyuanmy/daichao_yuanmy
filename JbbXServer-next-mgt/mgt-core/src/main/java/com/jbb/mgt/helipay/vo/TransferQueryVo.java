package com.jbb.mgt.helipay.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.server.common.PropertyManager;

public class TransferQueryVo {

    public String P1_bizType;
    public String P2_orderId;
    public String P3_customerNumber;
    public String sign;

    public TransferQueryVo() {
        super();
    }

    public TransferQueryVo(String orderId) {
        super();
        P1_bizType = HeliPayConstants.BIZ_TYPE_TRANSFER_QUERY;
        P2_orderId = orderId;
        P3_customerNumber = PropertyManager.getProperty("jbb.pay.heli.customer.number");
    }

    public String getSigned() {
        StringBuffer sb = new StringBuffer();
        sb.append("&");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&");
        sb.append(this.getP2_orderId() == null ? "" : this.getP2_orderId());
        sb.append("&");
        sb.append(this.getP3_customerNumber() == null ? "" : this.getP3_customerNumber());
        return sb.toString();
    }

    public String getRequestFormStr() throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append("P1_bizType=");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&P2_orderId=");
        sb.append(this.getP2_orderId() == null ? "" : this.getP2_orderId());
        sb.append("&P3_customerNumber=");
        sb.append(this.getP3_customerNumber() == null ? "" : this.getP3_customerNumber());
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

    public String getP2_orderId() {
        return P2_orderId;
    }

    public void setP2_orderId(String p2_orderId) {
        P2_orderId = p2_orderId;
    }

    public String getP3_customerNumber() {
        return P3_customerNumber;
    }

    public void setP3_customerNumber(String p3_customerNumber) {
        P3_customerNumber = p3_customerNumber;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "TransferQueryVo [P1_bizType=" + P1_bizType + ", P2_orderId=" + P2_orderId + ", P3_customerNumber="
            + P3_customerNumber + ", sign=" + sign + "]";
    }

}
