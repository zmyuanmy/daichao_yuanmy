package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

/**
 * 统计量实体类
 *
 * @author wyq
 * @date 2018/5/25 09:31
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelStatistic {
    private Timestamp creationDate;
    /** 点击量 */
    private int clickCnt;
    /** 点击量 纯进件 */
    private int clickCnt1;
    /** 点击量 非纯进件 */
    private int clickCnt2;
    /** 独立用户量 */
    private int uvCnt;
    /** 独立用户量 纯进件 */
    private int uvCnt1;
    /** 独立用户量 非纯进件 */
    private int uvCnt2;
    /** 提交量 */
    private int submitCnt;
    /** 提交量 纯进件 */
    private int submitCnt1;
    /** 提交量 非纯进件 */
    private int submitCnt2;
    /** 注册量 */
    private int registerCnt;

    private int registerNewCnt;
    private int registerOldCnt;
    /** 注册量 纯进件 */
    private int registerCnt1;
    /** 注册量 非纯进件 */
    private int registerCnt2;

    private int registerNewCnt2;
    /** 注册量2档 **/
    private int idCnt2;
    /** 注册量1档 **/
    private int noidCnt2;
    /** 放款量 */
    private int loanCnt;
    /** 放款量 纯进件 */
    private int loanCnt1;
    /** 放款量 非纯进件 */
    private int loanCnt2;
    /** 放款额 */
    private int loanAmount;
    /** 借款额 */
    private int borrowingAmount;
    /** 可卖进件量 */
    private int toSellNumber;

    /** 广告代理量 */
    private int adCnt;

    /** 羊毛检测B1 */
    private int woolCheckB1Cnt;
    /** 羊毛检测B2 */
    private int woolCheckB2Cnt;
    /** 空号 */
    private int mobileCheckCnt;
    /** 扣量 */
    private int decrementCnt;
    private int decrementNewCnt;
    private int decrementOldCnt;

    /** 总隐藏量 */
    private int hiddenCnt;

    /** 实现累计毛利 */
    private int profitTotal;

    /** h5注册量 */
    private int h5RegisterCnt;

    /** h5登录量 */
    private int appLoginCnt;

    /** 借款申请量 */
    private int borrowingCnt;

    public int getIdCnt2() {
        return idCnt2;
    }

    public void setIdCnt2(int idCnt2) {
        this.idCnt2 = idCnt2;
    }

    public int getNoidCnt2() {
        return noidCnt2;
    }

    public void setNoidCnt2(int noidCnt2) {
        this.noidCnt2 = noidCnt2;
    }

    public int getClickCnt() {
        return clickCnt;
    }

    public void setClickCnt1(int clickCnt1) {
        this.clickCnt1 = clickCnt1;
    }

    public void setClickCnt2(int clickCnt2) {
        this.clickCnt2 = clickCnt2;
    }

    public void setUvCnt1(int uvCnt1) {
        this.uvCnt1 = uvCnt1;
    }

    public void setUvCnt2(int uvCnt2) {
        this.uvCnt2 = uvCnt2;
    }

    public void setSubmitCnt1(int submitCnt1) {
        this.submitCnt1 = submitCnt1;
    }

    public void setSubmitCnt2(int submitCnt2) {
        this.submitCnt2 = submitCnt2;
    }

    public void setRegisterCnt1(int registerCnt1) {
        this.registerCnt1 = registerCnt1;
    }

    public void setRegisterCnt2(int registerCnt2) {
        this.registerCnt2 = registerCnt2;
    }

    public void setLoanCnt1(int loanCnt1) {
        this.loanCnt1 = loanCnt1;
    }

    public void setLoanCnt2(int loanCnt2) {
        this.loanCnt2 = loanCnt2;
    }

    public void setClickCnt(int clickCnt) {
        this.clickCnt = clickCnt;
    }

    public int getSubmitCnt() {
        return submitCnt;
    }

    public void setSubmitCnt(int submitCnt) {
        this.submitCnt = submitCnt;
    }

    public int getUvCnt() {
        return uvCnt;
    }

    public void setUvCnt(int uvCnt) {
        this.uvCnt = uvCnt;
    }

    public int getRegisterCnt() {
        return registerCnt;
    }

    public void setRegisterCnt(int registerCnt) {
        this.registerCnt = registerCnt;
    }

    public int getLoanCnt() {
        return loanCnt;
    }

    public void setLoanCnt(int loanCnt) {
        this.loanCnt = loanCnt;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getBorrowingAmount() {
        return borrowingAmount;
    }

    public void setBorrowingAmount(int borrowingAmount) {
        this.borrowingAmount = borrowingAmount;
    }

    public int getToSellNumber() {
        return toSellNumber;
    }

    public void setToSellNumber(int toSellNumber) {
        this.toSellNumber = toSellNumber;
    }

    public int getClickCnt1() {
        return clickCnt1;
    }

    public int getClickCnt2() {
        return clickCnt2;
    }

    public int getUvCnt1() {
        return uvCnt1;
    }

    public int getUvCnt2() {
        return uvCnt2;
    }

    public int getSubmitCnt1() {
        return submitCnt1;
    }

    public int getSubmitCnt2() {
        return submitCnt2;
    }

    public int getRegisterCnt1() {
        return registerCnt1;
    }

    public int getRegisterCnt2() {
        return registerCnt2;
    }

    public int getLoanCnt1() {
        return loanCnt1;
    }

    public int getLoanCnt2() {
        return loanCnt2;
    }

    public int getAdCnt() {
        return adCnt;
    }

    public void setAdCnt(int adCnt) {
        this.adCnt = adCnt;
    }

    public int getWoolCheckB1Cnt() {
        return woolCheckB1Cnt;
    }

    public void setWoolCheckB1Cnt(int woolCheckB1Cnt) {
        this.woolCheckB1Cnt = woolCheckB1Cnt;
    }

    public int getWoolCheckB2Cnt() {
        return woolCheckB2Cnt;
    }

    public void setWoolCheckB2Cnt(int woolCheckB2Cnt) {
        this.woolCheckB2Cnt = woolCheckB2Cnt;
    }

    public int getMobileCheckCnt() {
        return mobileCheckCnt;
    }

    public void setMobileCheckCnt(int mobileCheckCnt) {
        this.mobileCheckCnt = mobileCheckCnt;
    }

    public int getDecrementCnt() {
        return decrementCnt;
    }

    public void setDecrementCnt(int decrementCnt) {
        this.decrementCnt = decrementCnt;
    }

    public int getHiddenCnt() {
        return hiddenCnt;
    }

    public void setHiddenCnt(int hiddenCnt) {
        this.hiddenCnt = hiddenCnt;
    }

    public int getProfitTotal() {
        return profitTotal;
    }

    public void setProfitTotal(int profitTotal) {
        this.profitTotal = profitTotal;
    }

    public int getH5RegisterCnt() {
        return h5RegisterCnt;
    }

    public void setH5RegisterCnt(int h5RegisterCnt) {
        this.h5RegisterCnt = h5RegisterCnt;
    }

    public int getAppLoginCnt() {
        return appLoginCnt;
    }

    public void setAppLoginCnt(int appLoginCnt) {
        this.appLoginCnt = appLoginCnt;
    }

    public int getBorrowingCnt() {
        return borrowingCnt;
    }

    public void setBorrowingCnt(int borrowingCnt) {
        this.borrowingCnt = borrowingCnt;
    }

    public ChannelStatistic() {
        super();
    }

    public int getRegisterNewCnt2() {
        return registerNewCnt2;
    }

    public void setRegisterNewCnt2(int registerNewCnt2) {
        this.registerNewCnt2 = registerNewCnt2;
    }

    public int getRegisterNewCnt() {
        return registerNewCnt;
    }

    public void setRegisterNewCnt(int registerNewCnt) {
        this.registerNewCnt = registerNewCnt;
    }

    public int getRegisterOldCnt() {
        return registerOldCnt;
    }

    public void setRegisterOldCnt(int registerOldCnt) {
        this.registerOldCnt = registerOldCnt;
    }

    public int getDecrementNewCnt() {
        return decrementNewCnt;
    }

    public void setDecrementNewCnt(int decrementNewCnt) {
        this.decrementNewCnt = decrementNewCnt;
    }

    public int getDecrementOldCnt() {
        return decrementOldCnt;
    }

    public void setDecrementOldCnt(int decrementOldCnt) {
        this.decrementOldCnt = decrementOldCnt;
    }

    public ChannelStatistic(int clickCnt, int submitCnt, int uvCnt, int registerCnt, int loanCnt) {
        this.clickCnt = clickCnt;
        this.submitCnt = submitCnt;
        this.uvCnt = uvCnt;
        this.registerCnt = registerCnt;
        this.loanCnt = loanCnt;
    }

    @Override
    public String toString() {
        return "ChannelStatistic [creationDate=" + creationDate + ", clickCnt=" + clickCnt + ", clickCnt1=" + clickCnt1
            + ", clickCnt2=" + clickCnt2 + ", uvCnt=" + uvCnt + ", uvCnt1=" + uvCnt1 + ", uvCnt2=" + uvCnt2
            + ", submitCnt=" + submitCnt + ", submitCnt1=" + submitCnt1 + ", submitCnt2=" + submitCnt2
            + ", registerCnt=" + registerCnt + ", registerNewCnt=" + registerNewCnt + ", registerOldCnt="
            + registerOldCnt + ", registerCnt1=" + registerCnt1 + ", registerCnt2=" + registerCnt2
            + ", registerNewCnt2=" + registerNewCnt2 + ", idCnt2=" + idCnt2 + ", noidCnt2=" + noidCnt2 + ", loanCnt="
            + loanCnt + ", loanCnt1=" + loanCnt1 + ", loanCnt2=" + loanCnt2 + ", loanAmount=" + loanAmount
            + ", borrowingAmount=" + borrowingAmount + ", toSellNumber=" + toSellNumber + ", adCnt=" + adCnt
            + ", woolCheckB1Cnt=" + woolCheckB1Cnt + ", woolCheckB2Cnt=" + woolCheckB2Cnt + ", mobileCheckCnt="
            + mobileCheckCnt + ", decrementCnt=" + decrementCnt + ", decrementNewCnt=" + decrementNewCnt
            + ", decrementOldCnt=" + decrementOldCnt + ", hiddenCnt=" + hiddenCnt + ", profitTotal=" + profitTotal
            + ", h5RegisterCnt=" + h5RegisterCnt + ", appLoginCnt=" + appLoginCnt + ", borrowingCnt=" + borrowingCnt
            + "]";
    }
}
