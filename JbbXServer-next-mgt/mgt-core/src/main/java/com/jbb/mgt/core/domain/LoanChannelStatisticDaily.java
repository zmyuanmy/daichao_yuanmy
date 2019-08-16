package com.jbb.mgt.core.domain;

import java.sql.Date;

public class LoanChannelStatisticDaily {

    private Date statisticDate;
    private String channelCode;
    private String channelName;
    private int appLoginNum;
    private int platformUv;
    private int platformPv;

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

    @Override
    public String toString() {
        return "LoanChannelStatisticDaily{" + "statisticDate=" + statisticDate + ", channelCode='" + channelCode + '\''
            + ", channelName='" + channelName + '\'' + ", appLoginNum=" + appLoginNum + ", platformUv=" + platformUv
            + ", platformPv=" + platformPv + '}';
    }
}
