package com.jbb.mgt.core.domain;

/**
 *
 * @author wyq
 * @date 2018/7/12 15:07
 */
public class OrgAssignSetting {
    private Integer orgId;
    private Integer assignType;
    private Boolean status;
    private String accounts;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getAssignType() {
        return assignType;
    }

    public void setAssignType(Integer assignType) {
        this.assignType = assignType;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public OrgAssignSetting(Integer orgId, Integer assignType, Boolean status, String accounts) {
        this.orgId = orgId;
        this.assignType = assignType;
        this.status = status;
        this.accounts = accounts;
    }

    public OrgAssignSetting() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
