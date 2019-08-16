package com.jbb.mgt.core.domain;

import java.sql.Timestamp;
import java.util.Date;

public class ChannelStatisticDaily {
    private Date statisticDate;
    private String channelCode;
    private String channelName;
    private int cnt;
    private int adCnt;
    private int uvCnt;
    private int mode;
    private int price;
    private int expense;
    private int amount;
    private int balance;
    private int status;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private Timestamp confirmDate;
    private Integer confrimAccountId;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getAdCnt() {
        return adCnt;
    }

    public void setAdCnt(int adCnt) {
        this.adCnt = adCnt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Timestamp confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getConfrimAccountId() {
        return confrimAccountId;
    }

    public void setConfrimAccountId(Integer confrimAccountId) {
        this.confrimAccountId = confrimAccountId;
    }

    public int getUvCnt() {
        return uvCnt;
    }

    public void setUvCnt(int uvCnt) {
        this.uvCnt = uvCnt;
    }

    @Override
    public String toString() {
        return "ChannelStatisticDaily [statisticDate=" + statisticDate + ", channelCode=" + channelCode + ", cnt=" + cnt
            + ", adCnt=" + adCnt + ", uvCnt=" + uvCnt + ", mode=" + mode + ", price=" + price + ", expense=" + expense
            + ", amount=" + amount + ", balance=" + balance + ", status=" + status + ", creationDate=" + creationDate
            + ", updateDate=" + updateDate + ", confirmDate=" + confirmDate + ", confrimAccountId=" + confrimAccountId
            + "]";
    }

}
