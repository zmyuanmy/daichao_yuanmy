package com.jbb.mgt.core.domain;

import java.sql.Date;

public class LoanStatistic {

    private Date loanDate;
    private int loanCnt;
    private int overdueCnt;
    private int amount;
    private int overdueAmount;
    private int serviceFee;
    private int overdueServiceFee;
    private int repayment;
    private int actualRepayment;

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public int getLoanCnt() {
        return loanCnt;
    }

    public void setLoanCnt(int loanCnt) {
        this.loanCnt = loanCnt;
    }

    public int getOverdueCnt() {
        return overdueCnt;
    }

    public void setOverdueCnt(int overdueCnt) {
        this.overdueCnt = overdueCnt;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(int overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public int getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getOverdueServiceFee() {
        return overdueServiceFee;
    }

    public void setOverdueServiceFee(int overdueServiceFee) {
        this.overdueServiceFee = overdueServiceFee;
    }

    public int getRepayment() {
        return repayment;
    }

    public void setRepayment(int repayment) {
        this.repayment = repayment;
    }

    public int getActualRepayment() {
        return actualRepayment;
    }

    public void setActualRepayment(int actualRepayment) {
        this.actualRepayment = actualRepayment;
    }

    @Override
    public String toString() {
        return "LoanStatistic [loanDate=" + loanDate + ", loanCnt=" + loanCnt + ", overdueCnt=" + overdueCnt
            + ", amount=" + amount + ", overdueAmount=" + overdueAmount + ", serviceFee=" + serviceFee
            + ", overdueServiceFee=" + overdueServiceFee + ", repayment=" + repayment + ", actualRepayment="
            + actualRepayment + "]";
    }

}