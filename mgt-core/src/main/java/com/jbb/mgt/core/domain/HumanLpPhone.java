package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class HumanLpPhone {
    private String phoneNumber;
    private String userName;
    private String sex;
    private Timestamp cDate;
    private String batchId;
    private String lastPhone;
    private String lastSendStatus;
    private Timestamp lastSendDate;
    private boolean sendMsg;
    private boolean pushStatus;
    private Timestamp creationDate;

    public HumanLpPhone() {}

    public HumanLpPhone(String phoneNumber, String userName, String sex, Timestamp cDate) {
        super();
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.sex = sex;
        this.cDate = cDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Timestamp getcDate() {
        return cDate;
    }

    public void setcDate(Timestamp cDate) {
        this.cDate = cDate;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public boolean isSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(boolean sendMsg) {
        this.sendMsg = sendMsg;
    }

    public boolean isPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(boolean pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getLastSendStatus() {
        return lastSendStatus;
    }

    public void setLastSendStatus(String lastSendStatus) {
        this.lastSendStatus = lastSendStatus;
    }

    public Timestamp getLastSendDate() {
        return lastSendDate;
    }

    public void setLastSendDate(Timestamp lastSendDate) {
        this.lastSendDate = lastSendDate;
    }

    public String getLastPhone() {
        return lastPhone;
    }

    public void setLastPhone(String lastPhone) {
        this.lastPhone = lastPhone;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "HumanLpPhone [phoneNumber=" + phoneNumber + ", userName=" + userName + ", sex=" + sex + ", cDate="
            + cDate + ", batchId=" + batchId + ", lastPhone=" + lastPhone + ", lastSendStatus=" + lastSendStatus
            + ", lastSendDate=" + lastSendDate + ", sendMsg=" + sendMsg + ", pushStatus=" + pushStatus
            + ", creationDate=" + creationDate + "]";
    }

}
