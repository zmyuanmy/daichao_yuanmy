package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class MessageCode {

    private String phoneNumber;
    private String channelCode;
    private String msgCode;
    private Timestamp creationDate;
    private Timestamp expireDate;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Timestamp expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "MessageCode [phoneNumber=" + phoneNumber + ", channelCode=" + channelCode + ", msgCode=" + msgCode
            + ", creationDate=" + creationDate + ", expireDate=" + expireDate + "]";
    }

}
