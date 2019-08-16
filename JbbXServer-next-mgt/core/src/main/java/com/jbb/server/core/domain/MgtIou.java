package com.jbb.server.core.domain;

/**
 * Created by inspironsun on 2018/5/31
 */
public class MgtIou {

    private String iouCode;
    private String borrowingDate;
    private int borrowingAmount;
    private String repaymentDate;
    private int annualRate;
    private int lenderId;
    private int borrowerId;
    private int status;

    public String getIouCode() {
        return iouCode;
    }

    public void setIouCode(String iouCode) {
        this.iouCode = iouCode;
    }

    public String getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(String borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public int getBorrowingAmount() {
        return borrowingAmount;
    }

    public void setBorrowingAmount(int borrowingAmount) {
        this.borrowingAmount = borrowingAmount;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public int getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(int annualRate) {
        this.annualRate = annualRate;
    }

    public int getLenderId() {
        return lenderId;
    }

    public void setLenderId(int lenderId) {
        this.lenderId = lenderId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MgtIou{" + "iouCode='" + iouCode + '\'' + ", borrowingDate='" + borrowingDate + '\''
            + ", borrowingAmount=" + borrowingAmount + ", repaymentDate='" + repaymentDate + '\'' + ", annualRate="
            + annualRate + ", lenderId=" + lenderId + ", borrowerId=" + borrowerId + ", status=" + status + '}';
    }
}
