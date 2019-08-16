package com.jbb.mgt.core.domain;

import java.util.Date;

/**
 * 整体绩效实体类
 *
 * @author wyq
 * @date 2018/9/27 15:01
 */
public class XjlApplyRecordStatistic {
    private Date days;
    private int loanNumber; // 放款笔数 3,4,5,99
    private long loanAmount; // 放款金额    amount
    private int receiveNumber; // 应收笔数/代收笔数 3,4,5
    private long receiveAmount; // 应收金额/代收金额    amount + serviceFee
    private long receiveAmount2; // 代收本金    amount
    private int backNumber; // 回款笔数 99
    private long backAmount; // 回款金额    amount + serviceFee
    private int lateNumber; // 逾期笔数 4
    private long lateAmount; // 逾期金额/逾期应还   amount + serviceFee
    private long lateAmount2; // 逾期本金   amount
    private int collectionNumber; // 未逾期笔数 3,5
    private long collectionAmount; // 未逾期应还 amount + serviceFee
    private long collectionAmount2; // 未逾期本金    amount
    private long interestAmount; // 收到利息    service_fee
    private long marginAmount; // 实现毛利 =所有渠道所有到期客户的回款额-放款额（时间累积从渠道建立到今日）

    public XjlApplyRecordStatistic() {
        super();
    }

    public XjlApplyRecordStatistic(Date days, int loanNumber, long loanAmount, int receiveNumber, long receiveAmount,
        long receiveAmount2, int backNumber, long backAmount, int lateNumber, long lateAmount, long lateAmount2,
        int collectionNumber, long collectionAmount, long collectionAmount2, long interestAmount, long marginAmount) {
        this.days = days;
        this.loanNumber = loanNumber;
        this.loanAmount = loanAmount;
        this.receiveNumber = receiveNumber;
        this.receiveAmount = receiveAmount;
        this.receiveAmount2 = receiveAmount2;
        this.backNumber = backNumber;
        this.backAmount = backAmount;
        this.lateNumber = lateNumber;
        this.lateAmount = lateAmount;
        this.lateAmount2 = lateAmount2;
        this.collectionNumber = collectionNumber;
        this.collectionAmount = collectionAmount;
        this.collectionAmount2 = collectionAmount2;
        this.interestAmount = interestAmount;
        this.marginAmount = marginAmount;
    }

    public Date getDays() {
        return days;
    }

    public void setDays(Date days) {
        this.days = days;
    }

    public int getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(int loanNumber) {
        this.loanNumber = loanNumber;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(int receiveNumber) {
        this.receiveNumber = receiveNumber;
    }

    public long getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(long receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public long getReceiveAmount2() {
        return receiveAmount2;
    }

    public void setReceiveAmount2(long receiveAmount2) {
        this.receiveAmount2 = receiveAmount2;
    }

    public int getBackNumber() {
        return backNumber;
    }

    public void setBackNumber(int backNumber) {
        this.backNumber = backNumber;
    }

    public long getBackAmount() {
        return backAmount;
    }

    public void setBackAmount(long backAmount) {
        this.backAmount = backAmount;
    }

    public int getLateNumber() {
        return lateNumber;
    }

    public void setLateNumber(int lateNumber) {
        this.lateNumber = lateNumber;
    }

    public long getLateAmount() {
        return lateAmount;
    }

    public void setLateAmount(long lateAmount) {
        this.lateAmount = lateAmount;
    }

    public long getLateAmount2() {
        return lateAmount2;
    }

    public void setLateAmount2(long lateAmount2) {
        this.lateAmount2 = lateAmount2;
    }

    public int getCollectionNumber() {
        return collectionNumber;
    }

    public void setCollectionNumber(int collectionNumber) {
        this.collectionNumber = collectionNumber;
    }

    public long getCollectionAmount() {
        return collectionAmount;
    }

    public void setCollectionAmount(long collectionAmount) {
        this.collectionAmount = collectionAmount;
    }

    public long getCollectionAmount2() {
        return collectionAmount2;
    }

    public void setCollectionAmount2(long collectionAmount2) {
        this.collectionAmount2 = collectionAmount2;
    }

    public long getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(long interestAmount) {
        this.interestAmount = interestAmount;
    }

    public long getMarginAmount() {
        return marginAmount;
    }

    public void setMarginAmount(long marginAmount) {
        this.marginAmount = marginAmount;
    }

    @Override
    public String toString() {
        return "XjlApplyRecordStatistic{" + "days=" + days + ", loanNumber=" + loanNumber + ", loanAmount=" + loanAmount
            + ", receiveNumber=" + receiveNumber + ", receiveAmount=" + receiveAmount + ", receiveAmount2="
            + receiveAmount2 + ", backNumber=" + backNumber + ", backAmount=" + backAmount + ", lateNumber="
            + lateNumber + ", lateAmount=" + lateAmount + ", lateAmount2=" + lateAmount2 + ", collectionNumber="
            + collectionNumber + ", collectionAmount=" + collectionAmount + ", collectionAmount2=" + collectionAmount2
            + ", interestAmount=" + interestAmount + ", marginAmount=" + marginAmount + '}';
    }
}
