package com.jbb.mgt.core.domain;

import java.sql.Date;

public class PlatformExpense {
    private Date statisticDate;
    private int platformId;
    private int price;
    private int uvCnt;
    private int cnt;
    private int expense;
    private int amount;
    private int balance;

    public int getCnt() {
        return cnt;
    }

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUvCnt() {
        return uvCnt;
    }

    public void setUvCnt(int uvCnt) {
        this.uvCnt = uvCnt;
    }

    public int getExpense() {
        return price * (uvCnt + cnt);
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
        return balance + expense - (price * (uvCnt + cnt));
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

}
