package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class UserLoginLog {

    private int userId;
    private String channelCode;
    private int applicationId;
    private String platform;
    private Integer userType;
    private String remoteAddress;
    private Timestamp creationDate;

    public UserLoginLog() {
        super();
    }

    public UserLoginLog(int userId, String channelCode, int applicationId, String platform, Integer userType,
        String remoteAddress) {
        super();
        this.userId = userId;
        this.channelCode = channelCode;
        this.applicationId = applicationId;
        this.platform = platform;
        this.userType = userType;
        this.remoteAddress = remoteAddress;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "UserLoginLog [userId=" + userId + ", channelCode=" + channelCode + ", applicationId=" + applicationId
            + ", platform=" + platform + ", userType=" + userType + ", remoteAddress=" + remoteAddress
            + ", creationDate=" + creationDate + "]";
    }

}
