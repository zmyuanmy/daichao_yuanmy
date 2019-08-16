package com.jbb.mgt.helipay.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.jbb.mgt.helipay.constants.HeliPayConstants;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;

public class TransferVo {

    private String P1_bizType;
    private String P2_orderId;
    private String P3_customerNumber;
    private String P4_amount;
    private String P5_bankCode;
    private String P6_bankAccountNo;
    private String P7_bankAccountName;
    private String P8_biz;
    private String P9_bankUnionCode;
    private String P10_feeType;
    private String P11_urgency;
    private String P12_summary;
    private String sign;

    public TransferVo() {
        super();
    }

    public TransferVo(String orderId, Double amount, String bankCode, String accountNo, String accoutName) {
        super();
        P1_bizType = HeliPayConstants.BIZ_TYPE_TRANSFER;
        P2_orderId = orderId;
        P3_customerNumber = PropertyManager.getProperty("jbb.pay.heli.customer.number");
        P4_amount = HeliUtil.formatAmount(amount);
        P5_bankCode = bankCode;
        P6_bankAccountNo = accountNo;
        P7_bankAccountName = accoutName;
        P8_biz = PropertyManager.getProperty("jbb.pay.heli.biz");
        P9_bankUnionCode = null;
        P10_feeType = PropertyManager.getProperty("jbb.pay.heli.fee.type");
        P11_urgency = "true";
        P12_summary = PropertyManager.getProperty("jbb.pay.heli.transfer.summary");

    }

    public String getSigned() {
        StringBuffer sb = new StringBuffer();
        sb.append("&");
        sb.append(this.getP1_bizType() == null ? "" : this.getP1_bizType());
        sb.append("&");
        sb.append(this.getP2_orderId() == null ? "" : this.getP2_orderId());
        sb.append("&");
        sb.append(this.getP3_customerNumber() == null ? "" : this.getP3_customerNumber());
        sb.append("&");
        sb.append(this.getP4_amount() == null ? "" : this.getP4_amount());
        sb.append("&");
        sb.append(this.getP5_bankCode() == null ? "" : this.getP5_bankCode());
        sb.append("&");
        sb.append(this.getP6_bankAccountNo() == null ? "" : this.getP6_bankAccountNo());
        sb.append("&");
        sb.append(this.getP7_bankAccountName() == null ? "" : this.getP7_bankAccountName());
        sb.append("&");
        sb.append(this.getP8_biz() == null ? "" : this.getP8_biz());
        sb.append("&");
        sb.append(this.getP9_bankUnionCode() == null ? "" : this.getP9_bankUnionCode());
        sb.append("&");
        sb.append(this.getP10_feeType() == null ? "" : this.getP10_feeType());
        sb.append("&");
        sb.append(this.getP11_urgency() == null ? "" : this.getP11_urgency());
        sb.append("&");
        sb.append(this.getP12_summary() == null ? "" : this.getP12_summary());
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
        sb.append("&P4_amount=");
        sb.append(this.getP4_amount() == null ? "" : this.getP4_amount());
        sb.append("&P5_bankCode=");
        sb.append(this.getP5_bankCode() == null ? "" : this.getP5_bankCode());
        sb.append("&P6_bankAccountNo=");
        sb.append(this.getP6_bankAccountNo() == null ? "" : this.getP6_bankAccountNo());
        sb.append("&P7_bankAccountName=");
        sb.append(this.getP7_bankAccountName() == null ? "" : this.getP7_bankAccountName());
        sb.append("&P8_biz=");
        sb.append(this.getP8_biz() == null ? "" : this.getP8_biz());
        sb.append("&P9_bankUnionCode=");
        sb.append(this.getP9_bankUnionCode() == null ? "" : this.getP9_bankUnionCode());
        sb.append("&P10_feeType=");
        sb.append(this.getP10_feeType() == null ? "" : this.getP10_feeType());
        sb.append("&P11_urgency=");
        sb.append(this.getP11_urgency() == null ? "" : this.getP11_urgency());
        sb.append("&P12_summary=");
        sb.append(this.getP12_summary() == null ? "" : this.getP12_summary());
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

    public String getP4_amount() {
        return P4_amount;
    }

    public void setP4_amount(String p4_amount) {
        P4_amount = p4_amount;
    }

    public String getP5_bankCode() {
        return P5_bankCode;
    }

    public void setP5_bankCode(String p5_bankCode) {
        P5_bankCode = p5_bankCode;
    }

    public String getP6_bankAccountNo() {
        return P6_bankAccountNo;
    }

    public void setP6_bankAccountNo(String p6_bankAccountNo) {
        P6_bankAccountNo = p6_bankAccountNo;
    }

    public String getP7_bankAccountName() {
        return P7_bankAccountName;
    }

    public void setP7_bankAccountName(String p7_bankAccountName) {
        P7_bankAccountName = p7_bankAccountName;
    }

    public String getP8_biz() {
        return P8_biz;
    }

    public void setP8_biz(String p8_biz) {
        P8_biz = p8_biz;
    }

    public String getP9_bankUnionCode() {
        return P9_bankUnionCode;
    }

    public void setP9_bankUnionCode(String p9_bankUnionCode) {
        P9_bankUnionCode = p9_bankUnionCode;
    }

    public String getP10_feeType() {
        return P10_feeType;
    }

    public void setP10_feeType(String p10_feeType) {
        P10_feeType = p10_feeType;
    }

    public String getP11_urgency() {
        return P11_urgency;
    }

    public void setP11_urgency(String p11_urgency) {
        P11_urgency = p11_urgency;
    }

    public String getP12_summary() {
        return P12_summary;
    }

    public void setP12_summary(String p12_summary) {
        P12_summary = p12_summary;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "TransferVo [P1_bizType=" + P1_bizType + ", P2_orderId=" + P2_orderId + ", P3_customerNumber="
            + P3_customerNumber + ", P4_amount=" + P4_amount + ", P5_bankCode=" + P5_bankCode + ", P6_bankAccountNo="
            + P6_bankAccountNo + ", P7_bankAccountName=" + P7_bankAccountName + ", P8_biz=" + P8_biz
            + ", P9_bankUnionCode=" + P9_bankUnionCode + ", P10_feeType=" + P10_feeType + ", P11_urgency=" + P11_urgency
            + ", P12_summary=" + P12_summary + ", sign=" + sign + "]";
    }

}
