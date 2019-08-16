package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class UserKey {
    
    
    private String userKey;
    private int userId;
    private int applicationId;
    private Timestamp expiry;
    private boolean deleted;

    @Override
    public String toString() {
        return "userId=" + userId + ", size=" + (userKey != null ? userKey.length() : 0) + ", expiry=" + expiry;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Timestamp getExpiry() {
        return expiry;
    }

    public void setExpiry(Timestamp expiry) {
        this.expiry = expiry;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

}
