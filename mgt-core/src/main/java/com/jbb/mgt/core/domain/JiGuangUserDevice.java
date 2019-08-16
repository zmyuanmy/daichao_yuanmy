package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class JiGuangUserDevice {
    private int userId;
    private String deviceType;
    private String registrationId;
    private String alias;
    private Integer applicationId;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private boolean status;
    private String tag;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "JiGuangUserDevice [userId=" + userId + ", deviceType=" + deviceType + ", registrationId="
            + registrationId + ", alias=" + alias + ", creationDate=" + creationDate + ", updateDate=" + updateDate
            + ", status=" + status + "]";
    }

}
