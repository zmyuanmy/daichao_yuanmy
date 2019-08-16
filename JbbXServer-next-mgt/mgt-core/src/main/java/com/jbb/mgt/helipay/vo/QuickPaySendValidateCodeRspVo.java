package com.jbb.mgt.helipay.vo;

/**
 * 首次支付短信 返回列表 实体类
 *
 * @author wyq
 * @date 2018/8/29 14:48
 */
public class QuickPaySendValidateCodeRspVo {
    private String rt1_bizType;
    private String rt2_retCode;
    private String rt3_retMsg;
    private String rt4_customerNumber;
    private String rt5_orderId;
    private String rt6_phone;
    private String sign;

    public QuickPaySendValidateCodeRspVo() {
        super();
    }

    public QuickPaySendValidateCodeRspVo(String rt1_bizType, String rt2_retCode, String rt3_retMsg,
        String rt4_customerNumber, String rt5_orderId, String rt6_phone, String sign) {
        this.rt1_bizType = rt1_bizType;
        this.rt2_retCode = rt2_retCode;
        this.rt3_retMsg = rt3_retMsg;
        this.rt4_customerNumber = rt4_customerNumber;
        this.rt5_orderId = rt5_orderId;
        this.rt6_phone = rt6_phone;
        this.sign = sign;
    }

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
        sb.append(this.getRt6_phone() == null ? "" : this.getRt6_phone());
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

    public String getRt6_phone() {
        return rt6_phone;
    }

    public void setRt6_phone(String rt6_phone) {
        this.rt6_phone = rt6_phone;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "QuickPaySendValidateCodeRspVo{" + "rt1_bizType='" + rt1_bizType + '\'' + ", rt2_retCode='" + rt2_retCode
            + '\'' + ", rt3_retMsg='" + rt3_retMsg + '\'' + ", rt4_customerNumber='" + rt4_customerNumber + '\''
            + ", rt5_orderId='" + rt5_orderId + '\'' + ", rt6_phone='" + rt6_phone + '\'' + ", sign='" + sign + '\''
            + '}';
    }
}
