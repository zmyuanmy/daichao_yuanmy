package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class UserCard {

    private int userId;
    private String bankCode;
    private String payProductId;
    private String cardNo;
    private String phoneNumber;
    private boolean isDeleted;
    private Timestamp creationDate;
    private Timestamp deleteDate;
    private boolean isAcceptLoanCard;
    private String bankName;
    private String bankColor;
    private String bankLogo;

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getPayProductId() {
        return payProductId;
    }

    public void setPayProductId(String payProductId) {
        this.payProductId = payProductId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Timestamp deleteDate) {
        this.deleteDate = deleteDate;
    }

    public boolean isAcceptLoanCard() {
        return isAcceptLoanCard;
    }

    public void setAcceptLoanCard(boolean acceptLoanCard) {
        isAcceptLoanCard = acceptLoanCard;
    }

    @Override
    public String toString() {
        return "UserCard{" + "userId=" + userId + ", bankCode='" + bankCode + '\'' + ", payProductId='" + payProductId
            + '\'' + ", cardNo='" + cardNo + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", isDeleted=" + isDeleted
            + ", creationDate=" + creationDate + ", deleteDate=" + deleteDate + ", isAcceptLoanCard=" + isAcceptLoanCard
            + '}';
    }
}
