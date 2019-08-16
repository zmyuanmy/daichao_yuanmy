package com.jbb.mgt.core.domain;

import java.util.Date;

public class OrgChannelStatisticDaily {

    private Date statisticDate;
    private int cCnt;// 消耗量 所有组织
    private int cExpense;// 消耗额 所有组织
    private double cPrice;// 消耗单价
    private int pTotalCnt;// 总供应量 渠道
    private int pCnt;// 应付供应量 渠道
    private int pExpense;// 总采购额 渠道
    private double pPrice;// 平均采购价
    private double pOrgCnt;// 平均卖家
    private double pSalePrice;// 注册卖价
    private double pDiffPrice;// 注册毛利
    private double pProfit;// 注册总毛利
    private int pAmount;// 收款额
    private int pBalance;// 收款余额

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public int getcCnt() {
        return cCnt;
    }

    public void setcCnt(int cCnt) {
        this.cCnt = cCnt;
    }

    public int getcExpense() {
        return cExpense;
    }

    public void setcExpense(int cExpense) {
        this.cExpense = cExpense;
    }

    public double getcPrice() {
        return cPrice;
    }

    public void setcPrice(double cPrice) {
        this.cPrice = cPrice;
    }

    public int getpTotalCnt() {
        return pTotalCnt;
    }

    public void setpTotalCnt(int pTotalCnt) {
        this.pTotalCnt = pTotalCnt;
    }

    public int getpCnt() {
        return pCnt;
    }

    public void setpCnt(int pCnt) {
        this.pCnt = pCnt;
    }

    public int getpExpense() {
        return pExpense;
    }

    public void setpExpense(int pExpense) {
        this.pExpense = pExpense;
    }

    public double getpPrice() {
        return pPrice;
    }

    public void setpPrice(double pPrice) {
        this.pPrice = pPrice;
    }

    public double getpOrgCnt() {
        return pOrgCnt;
    }

    public void setpOrgCnt(double pOrgCnt) {
        this.pOrgCnt = pOrgCnt;
    }

    public double getpSalePrice() {
        return pSalePrice;
    }

    public void setpSalePrice(double pSalePrice) {
        this.pSalePrice = pSalePrice;
    }

    public double getpDiffPrice() {
        return pDiffPrice;
    }

    public void setpDiffPrice(double pDiffPrice) {
        this.pDiffPrice = pDiffPrice;
    }

    public double getpProfit() {
        return pProfit;
    }

    public void setpProfit(double pProfit) {
        this.pProfit = pProfit;
    }

    public int getpAmount() {
        return pAmount;
    }

    public void setpAmount(int pAmount) {
        this.pAmount = pAmount;
    }

    public int getpBalance() {
        return pBalance;
    }

    public void setpBalance(int pBalance) {
        this.pBalance = pBalance;
    }

    @Override
    public String toString() {
        return "OrgChannelStatisticDaily [statisticDate=" + statisticDate + ", cCnt=" + cCnt + ", cExpense=" + cExpense
            + ", cPrice=" + cPrice + ", pTotalCnt=" + pTotalCnt + ", pCnt=" + pCnt + ", pExpense=" + pExpense
            + ", pPrice=" + pPrice + ", pOrgCnt=" + pOrgCnt + ", pSalePrice=" + pSalePrice + ", pDiffPrice="
            + pDiffPrice + ", pProfit=" + pProfit + ", pAmount=" + pAmount + ", pBalance=" + pBalance + "]";
    }

}
