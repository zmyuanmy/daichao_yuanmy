package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class OrganizationUser {

    private int orgId;
    private int userId;
    private Timestamp creationDate;
    private boolean entryStatus;
    private Timestamp entryDate;
    private String sChannelCode;
    private int sOrgId;
    private int sUserType;
    private boolean jbbFlag;

    public OrganizationUser() {
        super();
    }

    public OrganizationUser(int orgId, int userId, int sOrgId, int sUserType, String sChannelCode) {
        super();
        this.orgId = orgId;
        this.userId = userId;
        this.sOrgId = sOrgId;
        this.sUserType = sUserType;
        this.sChannelCode = sChannelCode;
    }

    public OrganizationUser(int orgId, int userId, int sOrgId, int sUserType, String sChannelCode, boolean jbbFlag) {
        super();
        this.orgId = orgId;
        this.userId = userId;
        this.sOrgId = sOrgId;
        this.sUserType = sUserType;
        this.sChannelCode = sChannelCode;
        this.jbbFlag = jbbFlag;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(boolean entryStatus) {
        this.entryStatus = entryStatus;
    }

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    public String getsChannelCode() {
        return sChannelCode;
    }

    public void setsChannelCode(String sChannelCode) {
        this.sChannelCode = sChannelCode;
    }

    public int getsOrgId() {
        return sOrgId;
    }

    public void setsOrgId(int sOrgId) {
        this.sOrgId = sOrgId;
    }

    public int getsUserType() {
        return sUserType;
    }

    public void setsUserType(int sUserType) {
        this.sUserType = sUserType;
    }

    public boolean isJbbFlag() {
        return jbbFlag;
    }

    public void setJbbFlag(boolean jbbFlag) {
        this.jbbFlag = jbbFlag;
    }

    @Override
    public String toString() {
        return "OrgUser [orgId=" + orgId + ", userId=" + userId + ", creationDate=" + creationDate + ", entryStatus="
            + entryStatus + ", entryDate=" + entryDate + ", sChannelCode=" + sChannelCode + ", sOrgId=" + sOrgId
            + ", sUserType=" + sUserType + "]";
    }

}
