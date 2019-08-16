package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

/**
 * Created by inspironsun on 2019/4/11
 */
public class PhoneBlacklist {

    private int blacklistId;
    private String phoneNumber;
    private Timestamp creationDate;
    private int record_cnt;
    private Timestamp recentRecordDate;
    private String recordReason;

    public int getBlacklistId() {
        return blacklistId;
    }

    public void setBlacklistId(int blacklistId) {
        this.blacklistId = blacklistId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public int getRecord_cnt() {
        return record_cnt;
    }

    public void setRecord_cnt(int record_cnt) {
        this.record_cnt = record_cnt;
    }

    public Timestamp getRecentRecordDate() {
        return recentRecordDate;
    }

    public void setRecentRecordDate(Timestamp recentRecordDate) {
        this.recentRecordDate = recentRecordDate;
    }

    public String getRecordReason() {
        return recordReason;
    }

    public void setRecordReason(String recordReason) {
        this.recordReason = recordReason;
    }

    @Override
    public String toString() {
        return "PhoneBlacklist{" + "blacklistId=" + blacklistId + ", phoneNumber='" + phoneNumber + '\''
            + ", creationDate=" + creationDate + ", record_cnt=" + record_cnt + ", recentRecordDate=" + recentRecordDate
            + ", recordReason='" + recordReason + '\'' + '}';
    }
}
