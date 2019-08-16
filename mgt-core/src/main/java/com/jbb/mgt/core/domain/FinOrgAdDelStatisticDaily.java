package com.jbb.mgt.core.domain;

import java.util.Date;

/**
 * 代理导流报表
 *
 * @author wyq
 * @date 2018/9/14 15:33
 */
public class FinOrgAdDelStatisticDaily {
    private Date statisticDate;
    private int adCnt;
    private int adExpense;
    private double adPrice;
    private int adTotalCnt;
    private int adSupplyCnt;
    private int adBuyExpense;
    private double adBuyPrice;
    private double adSalePrice;
    private double adDiffPrice;
    private double adProfit;
    private int adAmount;
    private int adBalance;

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public int getAdCnt() {
        return adCnt;
    }

    public void setAdCnt(int adCnt) {
        this.adCnt = adCnt;
    }

    public int getAdExpense() {
        return adExpense;
    }

    public void setAdExpense(int adExpense) {
        this.adExpense = adExpense;
    }

    public double getAdPrice() {
        return adPrice;
    }

    public void setAdPrice(double adPrice) {
        this.adPrice = adPrice;
    }

    public int getAdTotalCnt() {
        return adTotalCnt;
    }

    public void setAdTotalCnt(int adTotalCnt) {
        this.adTotalCnt = adTotalCnt;
    }

    public int getAdSupplyCnt() {
        return adSupplyCnt;
    }

    public void setAdSupplyCnt(int adSupplyCnt) {
        this.adSupplyCnt = adSupplyCnt;
    }

    public int getAdBuyExpense() {
        return adBuyExpense;
    }

    public void setAdBuyExpense(int adBuyExpense) {
        this.adBuyExpense = adBuyExpense;
    }

    public double getAdBuyPrice() {
        return adBuyPrice;
    }

    public void setAdBuyPrice(double adBuyPrice) {
        this.adBuyPrice = adBuyPrice;
    }

    public double getAdSalePrice() {
        return adSalePrice;
    }

    public void setAdSalePrice(double adSalePrice) {
        this.adSalePrice = adSalePrice;
    }

    public double getAdDiffPrice() {
        return adDiffPrice;
    }

    public void setAdDiffPrice(double adDiffPrice) {
        this.adDiffPrice = adDiffPrice;
    }

    public double getAdProfit() {
        return adProfit;
    }

    public void setAdProfit(double adProfit) {
        this.adProfit = adProfit;
    }

    public int getAdAmount() {
        return adAmount;
    }

    public void setAdAmount(int adAmount) {
        this.adAmount = adAmount;
    }

    public int getAdBalance() {
        return adBalance;
    }

    public void setAdBalance(int adBalance) {
        this.adBalance = adBalance;
    }

    @Override
    public String toString() {
        return "FinOrgAdDelStatisticDaily [statisticDate=" + statisticDate + ", adCnt=" + adCnt + ", adExpense="
            + adExpense + ", adPrice=" + adPrice + ", adTotalCnt=" + adTotalCnt + ", adSupplyCnt=" + adSupplyCnt
            + ", adBuyExpense=" + adBuyExpense + ", adBuyPrice=" + adBuyPrice + ", adSalePrice=" + adSalePrice
            + ", adDiffPrice=" + adDiffPrice + ", adProfit=" + adProfit + ", adAmount=" + adAmount + ", adBalance="
            + adBalance + "]";
    }

}
