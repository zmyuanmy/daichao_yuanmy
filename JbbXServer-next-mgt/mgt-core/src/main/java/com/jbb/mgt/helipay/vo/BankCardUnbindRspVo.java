package com.jbb.mgt.helipay.vo;

/**
 * 银行卡解绑 返回列表 实体类
 *
 * @author wyq
 * @date 2018/8/29 16:16
 */
public class BankCardUnbindRspVo {
    private String rt1_bizType;
    private String rt2_retCode;
    private String rt3_retMsg;
    private String rt4_customerNumber;
    private String sign;

    public BankCardUnbindRspVo() {
        super();
    }

    public BankCardUnbindRspVo(String rt1_bizType, String rt2_retCode, String rt3_retMsg, String rt4_customerNumber,
        String sign) {
        this.rt1_bizType = rt1_bizType;
        this.rt2_retCode = rt2_retCode;
        this.rt3_retMsg = rt3_retMsg;
        this.rt4_customerNumber = rt4_customerNumber;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "BankCardUnbindRspVo{" + "rt1_bizType='" + rt1_bizType + '\'' + ", rt2_retCode='" + rt2_retCode + '\''
            + ", rt3_retMsg='" + rt3_retMsg + '\'' + ", rt4_customerNumber='" + rt4_customerNumber + '\'' + ", sign='"
            + sign + '\'' + '}';
    }
}
