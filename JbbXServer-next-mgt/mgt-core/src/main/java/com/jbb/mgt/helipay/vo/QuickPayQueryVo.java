package com.jbb.mgt.helipay.vo;

import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;

/**
 * 订单查询 请求参数 实体类
 *
 * @author wyq
 * @date 2018/8/29 16:07
 */
public class QuickPayQueryVo {
    private String P1_bizType;
    private String P2_orderId;
    private String P3_customerNumber;
    private String signatureType;
    private String sign;

    public QuickPayQueryVo() {
        super();
    }

    public QuickPayQueryVo(String orderId) {
        P1_bizType = HeliPayConstants.BIZ_TYPE_QUICK_PAY_QUERY;
        P2_orderId = orderId;
        P3_customerNumber = PropertyManager.getProperty("jbb.pay.heli.customer.number");
        this.signatureType = "MD5WITHRSA";
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

    public String getRequestFormStr() {
        StringBuffer sb = new StringBuffer();
        sb.append("P1_bizType=");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&P2_customerNumber=");
        sb.append(this.getP2_orderId() == null ? "" : this.getP2_orderId());
        sb.append("&P3_customerNumber=");
        sb.append(this.getP3_customerNumber() == null ? "" : this.getP3_customerNumber());
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
        return "QuickPayQueryVo{" + "P1_bizType='" + P1_bizType + '\'' + ", P2_orderId='" + P2_orderId + '\''
            + ", P3_customerNumber='" + P3_customerNumber + '\'' + ", signatureType='" + signatureType + '\''
            + ", sign='" + sign + '\'' + '}';
    }
}
