package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

/**
 * 渠道账号信息维护实体类
 *
 * @author wyq
 * @date 2018/7/23 10:06
 */
public class ChannelAccountInfo {
    private String channelCode;
    private String name;
    private String number;
    private String bankInfo;
    private Timestamp updateDate;
    private Integer updateAccountId;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUpdateAccountId() {
        return updateAccountId;
    }

    public void setUpdateAccountId(Integer updateAccountId) {
        this.updateAccountId = updateAccountId;
    }

    public ChannelAccountInfo() {
        super();
    }

    public ChannelAccountInfo(String channelCode, String name, String number, String bankInfo, Timestamp updateDate,
        Integer updateAccountId) {
        this.channelCode = channelCode;
        this.name = name;
        this.number = number;
        this.bankInfo = bankInfo;
        this.updateDate = updateDate;
        this.updateAccountId = updateAccountId;
    }

    @Override
    public String toString() {
        return "ChannelAccountInfo{" + "channelCode='" + channelCode + '\'' + ", name='" + name + '\'' + ", number='"
            + number + '\'' + ", bankInfo='" + bankInfo + '\'' + ", updateDate=" + updateDate + ", updateAccountId="
            + updateAccountId + '}';
    }
}
