package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class XjlPayBank {
    private int bankCardNumber;// 银行卡序号
    private String payProductId; // 产品编号 ， 如 hlb
    private String bankCode; // 银行编码 如：icbc
    private String bankName; // 银行名称 如：工商银行
    private int cardType; // 卡种 1-借记卡，2-贷记卡
    private int maxPer; // 每笔限额 ，单位分
    private int maxDay; // 每天限额 ，单位分
    private int maxMonth; // 每月限额 ，单位分
    private String bankColor;// 银行背景色
    private String bankLogo;// 银行图标名
    private Timestamp creationDate; // 创建时间

    public int getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(int bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBankColor() {
        return bankColor;
    }

    public void setBankColor(String bankColor) {
        this.bankColor = bankColor;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getPayProductId() {
        return payProductId;
    }

    public void setPayProductId(String payProductId) {
        this.payProductId = payProductId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public int getMaxPer() {
        return maxPer;
    }

    public void setMaxPer(int maxPer) {
        this.maxPer = maxPer;
    }

    public int getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(int maxDay) {
        this.maxDay = maxDay;
    }

    public int getMaxMonth() {
        return maxMonth;
    }

    public void setMaxMonth(int maxMonth) {
        this.maxMonth = maxMonth;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "XjlPayBank [bankCardNumber=" + bankCardNumber + ", payProductId=" + payProductId + ", bankCode="
            + bankCode + ", bankName=" + bankName + ", cardType=" + cardType + ", maxPer=" + maxPer + ", maxDay="
            + maxDay + ", maxMonth=" + maxMonth + ", bankColor=" + bankColor + ", bankLogo=" + bankLogo
            + ", creationDate=" + creationDate + "]";
    }

}
