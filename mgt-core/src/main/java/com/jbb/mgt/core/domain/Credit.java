package com.jbb.mgt.core.domain;

public class Credit {
    private int maxAmount;// 单位分
    private int minAmount;
    private int amount;
    private int serviceFeePercent;// 息费%， 150表示 1.50%
    private int serviceFee;
    private String purpose;
    private int days;// 期限

    public Credit() {}

    public Credit(int maxAmount, int minAmount, int serviceFeePercent, int days) {
        super();
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.serviceFeePercent = serviceFeePercent;
        this.days = days;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getServiceFeePercent() {
        return serviceFeePercent;
    }

    public void setServiceFeePercent(int serviceFeePercent) {
        this.serviceFeePercent = serviceFeePercent;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Credit [maxAmount=" + maxAmount + ", minAmount=" + minAmount + ", amount=" + amount
            + ", serviceFeePercent=" + serviceFeePercent + ", serviceFee=" + serviceFee + ", purpose=" + purpose
            + ", days=" + days + "]";
    }

}
