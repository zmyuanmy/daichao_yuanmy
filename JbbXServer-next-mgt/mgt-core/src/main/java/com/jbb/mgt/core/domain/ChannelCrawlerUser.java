package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

import com.jbb.server.common.util.DateUtil;

public class ChannelCrawlerUser {

    private int id;
    private String channelCode;
    private Integer merchantId;
    private String userName;
    private String phoneNumber;
    private String uId;
    private Timestamp registerDate;
    private Timestamp creationDate;
    
    

    public ChannelCrawlerUser() {
        super();
        
    }

    public ChannelCrawlerUser(String channelCode, Integer merchantId, String uId, String userName, String phoneNumber,
        String registerDate) {
        super();
        this.channelCode = channelCode;
        this.merchantId = merchantId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.registerDate = DateUtil.parseStrnew(registerDate);
        this.creationDate = DateUtil.getCurrentTimeStamp();
        this.uId = uId;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getMerchantId() { return merchantId;}

    public void setMerchantId(Integer merchantId) {this.merchantId = merchantId;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "ChannelCrawlerUser{" + "id=" + id + ", channelCode='" + channelCode + '\'' + ", merchantId="
            + merchantId + ", userName='" + userName + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", uId='" + uId
            + '\'' + ", registerDate=" + registerDate + ", creationDate=" + creationDate + '}';
    }
}
