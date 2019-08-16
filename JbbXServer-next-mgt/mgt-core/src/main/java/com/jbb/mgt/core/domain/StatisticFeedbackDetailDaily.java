package com.jbb.mgt.core.domain;

import java.util.Date;

/**
 * mgt_statistic_feedback_detail_daily 表实体类
 *
 * @author wyq
 * @date 2018/8/8 19:42
 */
public class StatisticFeedbackDetailDaily {
    private Date startDate;
    private String channelCode;
    private int userType;
    private String reason;
    private int cnt;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public StatisticFeedbackDetailDaily() {
        super();
    }

    public StatisticFeedbackDetailDaily(Date startDate, String channelCode, int userType, String reason, int cnt) {
        this.startDate = startDate;
        this.channelCode = channelCode;
        this.userType = userType;
        this.reason = reason;
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "StatisticFeedbackDetailDaily{" + "startDate=" + startDate + ", channelCode='" + channelCode + '\''
            + ", userType=" + userType + ", reason='" + reason + '\'' + ", cnt=" + cnt + '}';
    }
}
