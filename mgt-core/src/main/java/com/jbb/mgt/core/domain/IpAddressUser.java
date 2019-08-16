package com.jbb.mgt.core.domain;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author lishaojie
 * @title: IpAddressStatistic
 * @projectName JBBXServer
 * @description: IP注册明细
 * @date 2019/5/28
 */
public class IpAddressUser {

    private String ipAddress;// IP地址
    private Integer userId;// 用户id
    private String phoneNumber;// 手机号（隐藏中间四位）
    private Timestamp creationDate;// 注册时间
    private int pClickCnt;// 产品点击pv数
    private String firstChannelCode;// 渠道号
    private String channelName;// 渠道名
    private String nickname;// 商务

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public int getpClickCnt() {
        return pClickCnt;
    }

    public void setpClickCnt(int pClickCnt) {
        this.pClickCnt = pClickCnt;
    }

    public String getFirstChannelCode() {
        return firstChannelCode;
    }

    public void setFirstChannelCode(String firstChannelCode) {
        this.firstChannelCode = firstChannelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "IpAddressUser{" + "ipAddress='" + ipAddress + '\'' + ", userId=" + userId + ", phoneNumber='" + phoneNumber
            + '\'' + ", creationDate=" + creationDate + ", pClickCnt=" + pClickCnt + ", firstChannelCode='"
            + firstChannelCode + '\'' + ", channelName='" + channelName + '\'' + ", nickname='" + nickname + '\'' + '}';
    }
}
