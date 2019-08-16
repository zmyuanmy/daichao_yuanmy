package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class FlowBackLog {

    private int id;
    private int userId;
    private String deviceId;
    private String status;
    private String type;
    private Timestamp creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "FlowBackLog{" + "id=" + id + ", userId=" + userId + ", deviceId='" + deviceId + '\'' + ", status='"
            + status + '\'' + ", type='" + type + '\'' + ", creationDate=" + creationDate + '}';
    }
}
