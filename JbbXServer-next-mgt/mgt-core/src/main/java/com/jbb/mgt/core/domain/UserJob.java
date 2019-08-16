package com.jbb.mgt.core.domain;

import java.sql.Timestamp;
import java.util.Date;

public class UserJob {
    private int userId;// 用户ID
    private String company;// 工作单位
    private int addressId;// 单位地址ID
    private String occupation; // 职业
    private Date startDate;
    private Date endDate;
    private Timestamp creationDate;
    private UserAddresses jobAddress;

    public UserAddresses getJobAddress() {
        return jobAddress;
    }

    public void setJobAddress(UserAddresses jobAddress) {
        this.jobAddress = jobAddress;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "UserJob [userId=" + userId + ", company=" + company + ", addressId=" + addressId + ", occupation="
            + occupation + ", startDate=" + startDate + ", endDate=" + endDate + ", creationDate=" + creationDate
            + ", jobAddress=" + jobAddress + "]";
    }

}
