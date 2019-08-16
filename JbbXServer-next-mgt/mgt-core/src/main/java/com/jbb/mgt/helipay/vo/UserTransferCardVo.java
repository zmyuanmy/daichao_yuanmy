package com.jbb.mgt.helipay.vo;

/**
 * 用户交易卡信息列表 实体类
 *
 * @author wyq
 * @date 2018/8/29 16:22
 */
public class UserTransferCardVo {
    private String payerName;
    private String idCardType;
    private String idCardNo;
    private String cardNo;
    private String bankId;
    private String cardType;
    private String phone;
    private String bindStatus;
    private String bindId;

    public UserTransferCardVo() {
        super();
    }

    public UserTransferCardVo(String payerName, String idCardType, String idCardNo, String cardNo, String bankId,
        String cardType, String phone, String bindStatus, String bindId) {
        this.payerName = payerName;
        this.idCardType = idCardType;
        this.idCardNo = idCardNo;
        this.cardNo = cardNo;
        this.bankId = bankId;
        this.cardType = cardType;
        this.phone = phone;
        this.bindStatus = bindStatus;
        this.bindId = bindId;
    }

    public String getSigned() {
        StringBuffer sb = new StringBuffer();
        sb.append("&");
        sb.append(this.getPayerName() == null ? "" : this.getPayerName());
        sb.append("&");
        sb.append(this.getPhone() == null ? "" : this.getPhone());
        return sb.toString();
    }

    public String getRequestFormStr() {
        StringBuffer sb = new StringBuffer();
        sb.append("payerName=");
        sb.append(this.getPayerName() == null ? "" : this.getPayerName());
        sb.append("idCardType=");
        sb.append(this.getIdCardType() == null ? "" : this.getIdCardType());
        sb.append("idCardNo=");
        sb.append(this.getIdCardNo() == null ? "" : this.getIdCardNo());
        sb.append("cardNo=");
        sb.append(this.getCardNo() == null ? "" : this.getCardNo());
        sb.append("bankId=");
        sb.append(this.getBankId() == null ? "" : this.getBankId());
        sb.append("cardType=");
        sb.append(this.getCardType() == null ? "" : this.getCardType());
        sb.append("phone=");
        sb.append(this.getPhone() == null ? "" : this.getPhone());
        sb.append("bindStatus=");
        sb.append(this.getBindStatus() == null ? "" : this.getBindStatus());
        sb.append("bindId=");
        sb.append(this.getBindId() == null ? "" : this.getBindId());
        return sb.toString();
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    @Override
    public String toString() {
        return "UserTransferCardVo{" + "payerName='" + payerName + '\'' + ", idCardType='" + idCardType + '\''
            + ", idCardNo='" + idCardNo + '\'' + ", cardNo='" + cardNo + '\'' + ", bankId='" + bankId + '\''
            + ", cardType='" + cardType + '\'' + ", phone='" + phone + '\'' + ", bindStatus='" + bindStatus + '\''
            + ", bindId='" + bindId + '\'' + '}';
    }
}
