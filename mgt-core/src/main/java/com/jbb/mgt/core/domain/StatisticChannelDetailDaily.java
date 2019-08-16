package com.jbb.mgt.core.domain;

import java.sql.Date;

/**
 * 渠道质量反馈明细表
 *
 * @author wyq
 * @date 2018/7/31 11:41
 */
public class StatisticChannelDetailDaily {
    private Date statisticDate;
    private String channelCode;
    private int userType;
    private String reason;
    private int cnt;

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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public StatisticChannelDetailDaily() {
        super();
    }

    public StatisticChannelDetailDaily(Date statisticDate, String channelCode, int userType, String reason, int cnt) {
        this.statisticDate = statisticDate;
        this.channelCode = channelCode;
        this.userType = userType;
        this.reason = reason;
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "StatisticChannelDetailDaily{" + "statisticDate=" + statisticDate + ", channelCode='" + channelCode
            + '\'' + ", userType=" + userType + ", reason='" + reason + '\'' + ", cnt=" + cnt + '}';
    }
}
