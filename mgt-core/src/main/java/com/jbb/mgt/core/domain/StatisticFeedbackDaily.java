package com.jbb.mgt.core.domain;

import java.util.Date;
import java.util.List;

/**
 * mgt_statistic_feedback_daily 表实体类
 *
 * @author wyq
 * @date 2018/8/8 19:42
 */
public class StatisticFeedbackDaily {
    private Date statisticDate;
    private String channelCode;
    private String channelName;
    private int userType;
    private int totalRecommandCnt;
    private int rejectCnt;
    private double rejectRate;
    private int hangupCnt;
    private double hangupRate;
    private List<FeedBack> feedback;

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

    public int getTotalRecommandCnt() {
        return totalRecommandCnt;
    }

    public void setTotalRecommandCnt(int totalRecommandCnt) {
        this.totalRecommandCnt = totalRecommandCnt;
    }

    public int getRejectCnt() {
        return rejectCnt;
    }

    public void setRejectCnt(int rejectCnt) {
        this.rejectCnt = rejectCnt;
    }

    public int getHangupCnt() {
        return hangupCnt;
    }

    public void setHangupCnt(int hangupCnt) {
        this.hangupCnt = hangupCnt;
    }

    public List<FeedBack> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<FeedBack> feedback) {
        this.feedback = feedback;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public double getRejectRate() {
        return rejectRate;
    }

    public void setRejectRate(double rejectRate) {
        this.rejectRate = rejectRate;
    }

    public double getHangupRate() {
        return hangupRate;
    }

    public void setHangupRate(double hangupRate) {
        this.hangupRate = hangupRate;
    }

    public StatisticFeedbackDaily() {
        super();
    }

    public StatisticFeedbackDaily(Date statisticDate, String channelCode, String channelName, int userType,
        int totalRecommandCnt, int rejectCnt, int hangupCnt, List<FeedBack> feedback) {
        this.statisticDate = statisticDate;
        this.channelCode = channelCode;
        this.channelName = channelName;
        this.userType = userType;
        this.totalRecommandCnt = totalRecommandCnt;
        this.rejectCnt = rejectCnt;
        this.hangupCnt = hangupCnt;
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "StatisticFeedbackDaily{" + "statisticDate=" + statisticDate + ", channelCode='" + channelCode + '\''
            + ", channelName='" + channelName + '\'' + ", userType=" + userType + ", totalRecommandCnt="
            + totalRecommandCnt + ", rejectCnt=" + rejectCnt + ", hangupCnt=" + hangupCnt + ", feedback=" + feedback
            + '}';
    }
}
