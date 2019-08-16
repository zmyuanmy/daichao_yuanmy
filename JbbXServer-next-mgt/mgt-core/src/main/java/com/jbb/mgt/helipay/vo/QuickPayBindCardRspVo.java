package com.jbb.mgt.helipay.vo;

/**
 * 鉴权绑卡 返回列表 实体类
 * 
 * @author wyq
 * @date 2018/8/29 15:37
 */
public class QuickPayBindCardRspVo {
    private String rt1_bizType;
    private String rt2_retCode;
    private String rt3_retMsg;
    private String rt4_customerNumber;
    private String rt5_userId;
    private String rt6_orderId;
    private String rt7_bindStatus;
    private String rt8_bankId;
    private String rt9_cardAfterFour;
    private String rt10_bindId;
    private String rt11_serialNumber;
    private String sign;

    public QuickPayBindCardRspVo() {
        super();
    }

    public QuickPayBindCardRspVo(String rt1_bizType, String rt2_retCode, String rt3_retMsg, String rt4_customerNumber,
        String rt5_userId, String rt6_orderId, String rt7_bindStatus, String rt8_bankId, String rt9_cardAfterFour,
        String rt10_bindId, String rt11_serialNumber, String sign) {
        this.rt1_bizType = rt1_bizType;
        this.rt2_retCode = rt2_retCode;
        this.rt3_retMsg = rt3_retMsg;
        this.rt4_customerNumber = rt4_customerNumber;
        this.rt5_userId = rt5_userId;
        this.rt6_orderId = rt6_orderId;
        this.rt7_bindStatus = rt7_bindStatus;
        this.rt8_bankId = rt8_bankId;
        this.rt9_cardAfterFour = rt9_cardAfterFour;
        this.rt10_bindId = rt10_bindId;
        this.rt11_serialNumber = rt11_serialNumber;
        this.sign = sign;
    }

    public String getSigned() {
        StringBuffer sb = new StringBuffer();
        sb.append("&");
        sb.append(this.getRt1_bizType() == null ? "" : this.getRt1_bizType());
        sb.append("&");
        sb.append(this.getRt2_retCode() == null ? "" : this.getRt2_retCode());
        sb.append("&");
        sb.append(this.getRt4_customerNumber() == null ? "" : this.getRt4_customerNumber());
        sb.append("&");
        sb.append(this.getRt5_userId() == null ? "" : this.getRt5_userId());
        sb.append("&");
        sb.append(this.getRt6_orderId() == null ? "" : this.getRt6_orderId());
        sb.append("&");
        sb.append(this.getRt7_bindStatus() == null ? "" : this.getRt7_bindStatus());
        sb.append("&");
        sb.append(this.getRt8_bankId() == null ? "" : this.getRt8_bankId());
        sb.append("&");
        sb.append(this.getRt9_cardAfterFour() == null ? "" : this.getRt9_cardAfterFour());
        sb.append("&");
        sb.append(this.getRt10_bindId() == null ? "" : this.getRt10_bindId());
        sb.append("&");
        sb.append(this.getRt11_serialNumber() == null ? "" : this.getRt11_serialNumber());
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

    public String getRt5_userId() {
        return rt5_userId;
    }

    public void setRt5_userId(String rt5_userId) {
        this.rt5_userId = rt5_userId;
    }

    public String getRt6_orderId() {
        return rt6_orderId;
    }

    public void setRt6_orderId(String rt6_orderId) {
        this.rt6_orderId = rt6_orderId;
    }

    public String getRt7_bindStatus() {
        return rt7_bindStatus;
    }

    public void setRt7_bindStatus(String rt7_bindStatus) {
        this.rt7_bindStatus = rt7_bindStatus;
    }

    public String getRt8_bankId() {
        return rt8_bankId;
    }

    public void setRt8_bankId(String rt8_bankId) {
        this.rt8_bankId = rt8_bankId;
    }

    public String getRt9_cardAfterFour() {
        return rt9_cardAfterFour;
    }

    public void setRt9_cardAfterFour(String rt9_cardAfterFour) {
        this.rt9_cardAfterFour = rt9_cardAfterFour;
    }

    public String getRt10_bindId() {
        return rt10_bindId;
    }

    public void setRt10_bindId(String rt10_bindId) {
        this.rt10_bindId = rt10_bindId;
    }

    public String getRt11_serialNumber() {
        return rt11_serialNumber;
    }

    public void setRt11_serialNumber(String rt11_serialNumber) {
        this.rt11_serialNumber = rt11_serialNumber;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "QuickPayBindCardRspVo{" + "rt1_bizType='" + rt1_bizType + '\'' + ", rt2_retCode='" + rt2_retCode + '\''
            + ", rt3_retMsg='" + rt3_retMsg + '\'' + ", rt4_customerNumber='" + rt4_customerNumber + '\''
            + ", rt5_userId='" + rt5_userId + '\'' + ", rt6_orderId='" + rt6_orderId + '\'' + ", rt7_bindStatus='"
            + rt7_bindStatus + '\'' + ", rt8_bankId='" + rt8_bankId + '\'' + ", rt9_cardAfterFour='" + rt9_cardAfterFour
            + '\'' + ", rt10_bindId='" + rt10_bindId + '\'' + ", rt11_serialNumber='" + rt11_serialNumber + '\''
            + ", sign='" + sign + '\'' + '}';
    }
}
