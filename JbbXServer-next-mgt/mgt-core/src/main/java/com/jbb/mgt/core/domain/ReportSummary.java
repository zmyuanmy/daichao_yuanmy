package com.jbb.mgt.core.domain;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportSummary {
    private Date statisticDate;
    private int cCpaCnt;
    private int cCpaPayCnt;
    private int cCpaExpense;
    private int cCpaPrice;
    private int cUvCnt;
    private int cUvExpense;
    private int cUvPrice;
    private int cTotalRegisterCnt;
    private int payCnt;
    private int cExpense;
    private int cAmount;
    private int cBalance;
    private int cTotalCpaPrice;
    private int cTotalUvPrice;
    private int cpaCnt;
    private int cpaExpense;
    private int cpaPrice;
    private int puvCnt;
    private int uvExpense;
    private int uvPrice;
    private int expense;
    private int amount;
    private int balance;
    private int uvCnt;
    private int totalUvPrice;
    private int grossProfile;

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public int getcCpaCnt() {
        return cCpaCnt;
    }

    public void setcCpaCnt(int cCpaCnt) {
        this.cCpaCnt = cCpaCnt;
    }

    public int getcCpaPayCnt() {
        return cCpaPayCnt;
    }

    public void setcCpaPayCnt(int cCpaPayCnt) {
        this.cCpaPayCnt = cCpaPayCnt;
    }

    public int getcCpaExpense() {
        return cCpaExpense;
    }

    public void setcCpaExpense(int cCpaExpense) {
        this.cCpaExpense = cCpaExpense;
    }

    public int getcCpaPrice() {
        return cCpaPrice;
    }

    public void setcCpaPrice(int cCpaPrice) {
        this.cCpaPrice = cCpaPrice;
    }

    public int getcUvCnt() {
        return cUvCnt;
    }

    public void setcUvCnt(int cUvCnt) {
        this.cUvCnt = cUvCnt;
    }

    public int getcUvExpense() {
        return cUvExpense;
    }

    public void setcUvExpense(int cUvExpense) {
        this.cUvExpense = cUvExpense;
    }

    public int getcUvPrice() {
        return cUvPrice;
    }

    public void setcUvPrice(int cUvPrice) {
        this.cUvPrice = cUvPrice;
    }

    public int getcTotalRegisterCnt() {
        return cTotalRegisterCnt;
    }

    public void setcTotalRegisterCnt(int cTotalRegisterCnt) {
        this.cTotalRegisterCnt = cTotalRegisterCnt;
    }

    public int getPayCnt() {
        return payCnt;
    }

    public void setPayCnt(int payCnt) {
        this.payCnt = payCnt;
    }

    public int getcExpense() {
        return cExpense;
    }

    public void setcExpense(int cExpense) {
        this.cExpense = cExpense;
    }

    public int getcAmount() {
        return cAmount;
    }

    public void setcAmount(int cAmount) {
        this.cAmount = cAmount;
    }

    public int getcBalance() {
        return cBalance;
    }

    public void setcBalance(int cBalance) {
        this.cBalance = cBalance;
    }

    public int getcTotalCpaPrice() {
        return cTotalCpaPrice;
    }

    public void setcTotalCpaPrice(int cTotalCpaPrice) {
        this.cTotalCpaPrice = cTotalCpaPrice;
    }

    public int getcTotalUvPrice() {
        return cTotalUvPrice;
    }

    public void setcTotalUvPrice(int cTotalUvPrice) {
        this.cTotalUvPrice = cTotalUvPrice;
    }

    public int getCpaCnt() {
        return cpaCnt;
    }

    public void setCpaCnt(int cpaCnt) {
        this.cpaCnt = cpaCnt;
    }

    public int getCpaExpense() {
        return cpaExpense;
    }

    public void setCpaExpense(int cpaExpense) {
        this.cpaExpense = cpaExpense;
    }

    public int getCpaPrice() {
        return cpaPrice;
    }

    public void setCpaPrice(int cpaPrice) {
        this.cpaPrice = cpaPrice;
    }

    public int getPuvCnt() {
        return puvCnt;
    }

    public void setPuvCnt(int puvCnt) {
        this.puvCnt = puvCnt;
    }

    public int getUvExpense() {
        return uvExpense;
    }

    public void setUvExpense(int uvExpense) {
        this.uvExpense = uvExpense;
    }

    public int getUvPrice() {
        return uvPrice;
    }

    public void setUvPrice(int uvPrice) {
        this.uvPrice = uvPrice;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getUvCnt() {
        return uvCnt;
    }

    public void setUvCnt(int uvCnt) {
        this.uvCnt = uvCnt;
    }

    public int getTotalUvPrice() {
        return totalUvPrice;
    }

    public void setTotalUvPrice(int totalUvPrice) {
        this.totalUvPrice = totalUvPrice;
    }

    public int getGrossProfile() {
        return grossProfile;
    }

    public void setGrossProfile(int grossProfile) {
        this.grossProfile = grossProfile;
    }

    @Override
    public String toString() {
        return "ReportSummary[" +
                "statisticDate=" + statisticDate +
                ", cCpaCnt=" + cCpaCnt +
                ", cCpaPayCnt=" + cCpaPayCnt +
                ", cCpaExpense=" + cCpaExpense +
                ", cCpaPrice=" + cCpaPrice +
                ", cUvCnt=" + cUvCnt +
                ", cUvExpense=" + cUvExpense +
                ", cUvPrice=" + cUvPrice +
                ", cTotalRegisterCnt=" + cTotalRegisterCnt +
                ", payCnt=" + payCnt +
                ", cExpense=" + cExpense +
                ", cAmount=" + cAmount +
                ", cBalance=" + cBalance +
                ", cTotalCpaPrice=" + cTotalCpaPrice +
                ", cTotalUvPrice=" + cTotalUvPrice +
                ", cpaCnt=" + cpaCnt +
                ", cpaExpense=" + cpaExpense +
                ", cpaPrice=" + cpaPrice +
                ", puvCnt=" + puvCnt +
                ", uvExpense=" + uvExpense +
                ", uvPrice=" + uvPrice +
                ", expense=" + expense +
                ", amount=" + amount +
                ", balance=" + balance +
                ", uvCnt=" + uvCnt +
                ", totalUvPrice=" + totalUvPrice +
                ", grossProfile=" + grossProfile +
                ']';
    }
}
