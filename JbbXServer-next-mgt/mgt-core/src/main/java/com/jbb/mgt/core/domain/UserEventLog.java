package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class UserEventLog {
    private int userId;
    private String sourceId;
    private String cookieId;
    private String eventName;
    private String eventAction;
    private String eventLable;
    private String eventValue;
    private Timestamp creationDate;
    private String remoteAddress;
    private String eventValue2;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventAction() {
        return eventAction;
    }

    public void setEventAction(String eventAction) {
        this.eventAction = eventAction;
    }

    public String getEventLable() {
        return eventLable;
    }

    public void setEventLable(String eventLable) {
        this.eventLable = eventLable;
    }

    public String getEventValue() {
        return eventValue;
    }

    public void setEventValue(String eventValue) {
        this.eventValue = eventValue;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getEventValue2() {
        return eventValue2;
    }

    public void setEventValue2(String eventValue2) {
        this.eventValue2 = eventValue2;
    }

    @Override
    public String toString() {
        return "UserEventLog [userId=" + userId + ", sourceId=" + sourceId + ", cookieId=" + cookieId + ", eventName="
            + eventName + ", eventAction=" + eventAction + ", eventLable=" + eventLable + ", eventValue=" + eventValue
            + ", creationDate=" + creationDate + ", remoteAddress=" + remoteAddress + ", eventValue2=" + eventValue2
            + "]";
    }

}
