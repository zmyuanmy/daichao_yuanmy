package com.jbb.server.rs.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by inspironsun on 2018/5/29
 */
public class ReMgtIou {
    @JsonProperty(required = true)
    private double borrowingAmount;
    @JsonProperty(required = true)
    private String borrowingDate;
    @JsonProperty(required = true)
    private String repaymentDate;
    @JsonProperty(required = true)
    private double annualRate;
    @JsonProperty(required = false)
    private String purpose;
    @JsonProperty(required = false)
    private String device;
    @JsonProperty(required = true)
    private boolean flag;

    @JsonProperty(required = true)
    private int borrowerId;

    @JsonProperty(required = true)
    private int lenderId;

    public double getBorrowingAmount() {
        return borrowingAmount;
    }

    public void setBorrowingAmount(double borrowingAmount) {
        this.borrowingAmount = borrowingAmount;
    }

    public String getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(String borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(double annualRate) {
        this.annualRate = annualRate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Integer getLenderId() {
        return lenderId;
    }

    public void setLenderId(Integer lenderId) {
        this.lenderId = lenderId;
    }

    @Override
    public String toString() {
        return "ReIouMgt{" + "borrowingAmount=" + borrowingAmount + ", borrowingDate='" + borrowingDate + '\''
            + ", repaymentDate='" + repaymentDate + '\'' + ", annualRate=" + annualRate + ", purpose='" + purpose + '\''
            + ", device='" + device + '\'' + ", flag=" + flag + ", borrowerId=" + borrowerId + ", lenderId=" + lenderId
            + '}';
    }
}
