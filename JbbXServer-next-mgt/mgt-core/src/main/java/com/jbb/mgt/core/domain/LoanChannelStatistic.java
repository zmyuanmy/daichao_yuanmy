package com.jbb.mgt.core.domain;

import java.sql.Date;

public class LoanChannelStatistic {

    private Date statisticDate;
    private String channelCode;
    private int price;
    private String channelName;
    private int clickCnt;
    private int uvCnt;
    private int totalRegisterCnt;
    private int entryCnt;
    private int appLoginNum;
    private int platformUv;
    private int platformPv;
    private int expense;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getClickCnt() {
        return clickCnt;
    }

    public void setClickCnt(int clickCnt) {
        this.clickCnt = clickCnt;
    }

    public int getUvCnt() {
        return uvCnt;
    }

    public void setUvCnt(int uvCnt) {
        this.uvCnt = uvCnt;
    }

    public int getTotalRegisterCnt() {
        return totalRegisterCnt;
    }

    public void setTotalRegisterCnt(int totalRegisterCnt) {
        this.totalRegisterCnt = totalRegisterCnt;
    }

    public int getEntryCnt() {
        return entryCnt;
    }

    public void setEntryCnt(int entryCnt) {
        this.entryCnt = entryCnt;
    }

    public int getAppLoginNum() {
        return appLoginNum;
    }

    public void setAppLoginNum(int appLoginNum) {
        this.appLoginNum = appLoginNum;
    }

    public int getPlatformUv() {
        return platformUv;
    }

    public void setPlatformUv(int platformUv) {
        this.platformUv = platformUv;
    }

    public int getPlatformPv() {
        return platformPv;
    }

    public void setPlatformPv(int platformPv) {
        this.platformPv = platformPv;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    @Override
    public String toString() {
        return "LoanChannelStatistic{" + "statisticDate=" + statisticDate + ", channelCode='" + channelCode + '\''
            + ", price=" + price + ", channelName='" + channelName + '\'' + ", clickCnt=" + clickCnt + ", uvCnt="
            + uvCnt + ", totalRegisterCnt=" + totalRegisterCnt + ", entryCnt=" + entryCnt + ", appLoginNum="
            + appLoginNum + ", platformUv=" + platformUv + ", platformPv=" + platformPv + '}';
    }
}
