package com.jbb.mgt.core.domain;

public class UserAdCounts {
    private String statisDate;
    private int adCnt;

    public String getStatisDate() {
        return statisDate;
    }

    public void setStatisDate(String statisDate) {
        this.statisDate = statisDate;
    }

    public int getAdCnt() {
        return adCnt;
    }

    public void setAdCnt(int adCnt) {
        this.adCnt = adCnt;
    }

    @Override
    public String toString() {
        return "UserAdCounts [statisDate=" + statisDate + ", adCnt=" + adCnt + "]";
    }

}
