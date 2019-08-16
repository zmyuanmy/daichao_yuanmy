package com.jbb.mgt.core.domain;

public class TodayStatistic {
    private int registerCnt;// 注册数
    private int cnt;// 申请数
    private int newCnt;// 新增申请数
    private int oldCnt;// 复贷申请数
    private int botCnt;// 机审通过数
    private int newBotCnt;// 新增通过数
    private int oldBotCnt;// 复贷通过数
    private double loanRate;// 借款通过率
    private double newLoanRate;// 新增通过率
    private double oldLoanRate;// 复贷通过率
    private int loanCnt;// 放款订单数
    private int repaymentCnt;// 还款订单数
    private int torepaymentCnt;// 明日笔数
    private int loanAmount;// 明日应还本金
    private int torepaymentFee;// 明日应还手续费
    private int torepaymentAmount;// 明日应还总额
    private int torepaymentTotalCnt;// 未来笔数
    private int loanTotalAmount;// 未来应还本金
    private int torepaymentTotalFee;// 未来应还手续费
    private int torepaymentTotalAmount;// 未来应还总额

    public int getRegisterCnt() {
        return registerCnt;
    }

    public void setRegisterCnt(int registerCnt) {
        this.registerCnt = registerCnt;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getNewCnt() {
        return newCnt;
    }

    public void setNewCnt(int newCnt) {
        this.newCnt = newCnt;
    }

    public int getOldCnt() {
        return oldCnt;
    }

    public void setOldCnt(int oldCnt) {
        this.oldCnt = oldCnt;
    }

    public int getBotCnt() {
        return botCnt;
    }

    public void setBotCnt(int botCnt) {
        this.botCnt = botCnt;
    }

    public int getNewBotCnt() {
        return newBotCnt;
    }

    public void setNewBotCnt(int newBotCnt) {
        this.newBotCnt = newBotCnt;
    }

    public int getOldBotCnt() {
        return oldBotCnt;
    }

    public void setOldBotCnt(int oldBotCnt) {
        this.oldBotCnt = oldBotCnt;
    }

    public double getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(double loanRate) {
        this.loanRate = loanRate;
    }

    public double getNewLoanRate() {
        return newLoanRate;
    }

    public void setNewLoanRate(double newLoanRate) {
        this.newLoanRate = newLoanRate;
    }

    public double getOldLoanRate() {
        return oldLoanRate;
    }

    public void setOldLoanRate(double oldLoanRate) {
        this.oldLoanRate = oldLoanRate;
    }

    public int getLoanCnt() {
        return loanCnt;
    }

    public void setLoanCnt(int loanCnt) {
        this.loanCnt = loanCnt;
    }

    public int getRepaymentCnt() {
        return repaymentCnt;
    }

    public void setRepaymentCnt(int repaymentCnt) {
        this.repaymentCnt = repaymentCnt;
    }

    public int getTorepaymentCnt() {
        return torepaymentCnt;
    }

    public void setTorepaymentCnt(int torepaymentCnt) {
        this.torepaymentCnt = torepaymentCnt;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getTorepaymentFee() {
        return torepaymentFee;
    }

    public void setTorepaymentFee(int torepaymentFee) {
        this.torepaymentFee = torepaymentFee;
    }

    public int getTorepaymentAmount() {
        return torepaymentAmount;
    }

    public void setTorepaymentAmount(int torepaymentAmount) {
        this.torepaymentAmount = torepaymentAmount;
    }

    public int getTorepaymentTotalCnt() {
        return torepaymentTotalCnt;
    }

    public void setTorepaymentTotalCnt(int torepaymentTotalCnt) {
        this.torepaymentTotalCnt = torepaymentTotalCnt;
    }

    public int getLoanTotalAmount() {
        return loanTotalAmount;
    }

    public void setLoanTotalAmount(int loanTotalAmount) {
        this.loanTotalAmount = loanTotalAmount;
    }

    public int getTorepaymentTotalFee() {
        return torepaymentTotalFee;
    }

    public void setTorepaymentTotalFee(int torepaymentTotalFee) {
        this.torepaymentTotalFee = torepaymentTotalFee;
    }

    public int getTorepaymentTotalAmount() {
        return torepaymentTotalAmount;
    }

    public void setTorepaymentTotalAmount(int torepaymentTotalAmount) {
        this.torepaymentTotalAmount = torepaymentTotalAmount;
    }

    @Override
    public String toString() {
        return "TodayStatistic [registerCnt=" + registerCnt + ", cnt=" + cnt + ", newCnt=" + newCnt + ", oldCnt="
            + oldCnt + ", botCnt=" + botCnt + ", newBotCnt=" + newBotCnt + ", oldBotCnt=" + oldBotCnt + ", loanRate="
            + loanRate + ", newLoanRate=" + newLoanRate + ", oldLoanRate=" + oldLoanRate + ", loanCnt=" + loanCnt
            + ", repaymentCnt=" + repaymentCnt + ", torepaymentCnt=" + torepaymentCnt + ", loanAmount=" + loanAmount
            + ", torepaymentFee=" + torepaymentFee + ", torepaymentAmount=" + torepaymentAmount
            + ", torepaymentTotalCnt=" + torepaymentTotalCnt + ", loanTotalAmount=" + loanTotalAmount
            + ", torepaymentTotalFee=" + torepaymentTotalFee + ", torepaymentTotalAmount=" + torepaymentTotalAmount
            + "]";
    }

}
