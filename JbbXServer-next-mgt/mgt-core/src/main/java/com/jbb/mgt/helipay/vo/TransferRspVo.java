package com.jbb.mgt.helipay.vo;

public class TransferRspVo {
    public static String SUCCESS_CODE = "0000";
    public static String FAIL1_CODE = "0001";
    public static String FAIL2_CODE = "0002";
    public static String FAIL3_CODE = "0003";
    public static String FAIL4_CODE = "0004";
    public static String FAIL5_CODE = "0005";
    public static String FAIL6_CODE = "0006";

    public String rt1_bizType;
    public String rt2_retCode;
    public String rt3_retMsg;
    public String rt4_customerNumber;
    public String rt5_orderId;
    public String rt6_serialNumber;
    public String rt7_orderStatus;
    public String rt8_reason;
    public String sign;

    public String getSigned() {
        StringBuffer sb = new StringBuffer();
        sb.append("&");
        sb.append(this.getRt1_bizType() == null ? "" : this.getRt1_bizType());
        sb.append("&");
        sb.append(this.getRt2_retCode() == null ? "" : this.getRt2_retCode());
        sb.append("&");
        sb.append(this.getRt3_retMsg() == null ? "" : this.getRt3_retMsg());
        sb.append("&");
        sb.append(this.getRt4_customerNumber() == null ? "" : this.getRt4_customerNumber());
        sb.append("&");
        sb.append(this.getRt5_orderId() == null ? "" : this.getRt5_orderId());
        sb.append("&");
        sb.append(this.getRt6_serialNumber() == null ? "" : this.getRt6_serialNumber());
        return sb.toString();
    }

    public String getRt1_bizType() {
        return rt1_bizType;
    }

    public void setRt1_bizType(String rt1_bizType) {
        this.rt1_bizType = rt1_bizType;
    }

    public String getRt2_retCode() {
        return rt2_retCode;
    }

    public void setRt2_retCode(String rt2_retCode) {
        this.rt2_retCode = rt2_retCode;
    }

    public String getRt3_retMsg() {
        return rt3_retMsg;
    }

    public void setRt3_retMsg(String rt3_retMsg) {
        this.rt3_retMsg = rt3_retMsg;
    }

    public String getRt4_customerNumber() {
        return rt4_customerNumber;
    }

    public void setRt4_customerNumber(String rt4_customerNumber) {
        this.rt4_customerNumber = rt4_customerNumber;
    }

    public String getRt5_orderId() {
        return rt5_orderId;
    }

    public void setRt5_orderId(String rt5_orderId) {
        this.rt5_orderId = rt5_orderId;
    }

    public String getRt6_serialNumber() {
        return rt6_serialNumber;
    }

    public void setRt6_serialNumber(String rt6_serialNumber) {
        this.rt6_serialNumber = rt6_serialNumber;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRt7_orderStatus() {
        return rt7_orderStatus;
    }

    public void setRt7_orderStatus(String rt7_orderStatus) {
        this.rt7_orderStatus = rt7_orderStatus;
    }

    public String getRt8_reason() {
        return rt8_reason;
    }

    public void setRt8_reason(String rt8_reason) {
        this.rt8_reason = rt8_reason;
    }

    @Override
    public String toString() {
        return "TransferRspVo [rt1_bizType=" + rt1_bizType + ", rt2_retCode=" + rt2_retCode + ", rt3_retMsg="
            + rt3_retMsg + ", rt4_customerNumber=" + rt4_customerNumber + ", rt5_orderId=" + rt5_orderId
            + ", rt6_serialNumber=" + rt6_serialNumber + ", rt7_orderStatus=" + rt7_orderStatus + ", rt8_reason="
            + rt8_reason + ", sign=" + sign + "]";
    }

}
