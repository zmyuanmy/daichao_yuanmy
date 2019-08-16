package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

/**
 * Leancloud设备信息实体类
 * 
 * @author wyq
 * @date 2018/9/5 11:37
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLcDevice {
    private String objectId;
    private Integer userId;
    private String deviceType;
    private String installationId;
    private String deviceToken;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private Boolean status;
    private String appName;

    public UserLcDevice() {
        super();
    }

    public UserLcDevice(String objectId, Integer userId, String deviceType, String installationId, String deviceToken,
        Timestamp creationDate, Timestamp updateDate, Boolean status, String appName) {
        this.objectId = objectId;
        this.userId = userId;
        this.deviceType = deviceType;
        this.installationId = installationId;
        this.deviceToken = deviceToken;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.status = status;
        this.appName = appName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public boolean isIos() {
        return "ios".equals(this.deviceType);
    }

    public boolean isAndroid() {
        return "android".equals(this.deviceType);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "UserLcDevice{" + "objectId='" + objectId + '\'' + ", userId=" + userId + ", deviceType='" + deviceType
            + '\'' + ", installationId='" + installationId + '\'' + ", deviceToken='" + deviceToken + '\''
            + ", creationDate=" + creationDate + ", updateDate=" + updateDate + ", status=" + status + ", appName='"
            + appName + '\'' + '}';
    }
}
