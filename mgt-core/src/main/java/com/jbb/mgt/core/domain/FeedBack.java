package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 *
 * @author wyq
 * @date 2018/8/1 19:21
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedBack {
    private String reason;
    private int cnt;
    private double rate;

    // 页面返回需要的数据
    private int totalRecommandTotal;
    private int rejectTotal;
    private double rejectRateTotal;
    private int hangupTotal;
    private double hangupRateTotal;
    private List<FeedBack> feedBackTotal;

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

    public int getTotalRecommandTotal() {
        return totalRecommandTotal;
    }

    public void setTotalRecommandTotal(int totalRecommandTotal) {
        this.totalRecommandTotal = totalRecommandTotal;
    }

    public int getRejectTotal() {
        return rejectTotal;
    }

    public void setRejectTotal(int rejectTotal) {
        this.rejectTotal = rejectTotal;
    }

    public double getRejectRateTotal() {
        return rejectRateTotal;
    }

    public void setRejectRateTotal(double rejectRateTotal) {
        this.rejectRateTotal = rejectRateTotal;
    }

    public int getHangupTotal() {
        return hangupTotal;
    }

    public void setHangupTotal(int hangupTotal) {
        this.hangupTotal = hangupTotal;
    }

    public double getHangupRateTotal() {
        return hangupRateTotal;
    }

    public void setHangupRateTotal(double hangupRateTotal) {
        this.hangupRateTotal = hangupRateTotal;
    }

    public List<FeedBack> getFeedBackTotal() {
        return feedBackTotal;
    }

    public void setFeedBackTotal(List<FeedBack> feedBackTotal) {
        this.feedBackTotal = feedBackTotal;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public FeedBack() {
        super();
    }

    public FeedBack(String reason, int cnt, double rate) {
        this.reason = reason;
        this.cnt = cnt;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "FeedBack{" + "reason='" + reason + '\'' + ", cnt=" + cnt + ", rate=" + rate + ", totalRecommandTotal="
            + totalRecommandTotal + ", rejectTotal=" + rejectTotal + ", rejectRateTotal=" + rejectRateTotal
            + ", hangupTotal=" + hangupTotal + ", hangupRateTotal=" + hangupRateTotal + '}';
    }
}
