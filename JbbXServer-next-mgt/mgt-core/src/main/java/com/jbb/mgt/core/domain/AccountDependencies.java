package com.jbb.mgt.core.domain;

public class AccountDependencies {

    private Integer accountId;
    private Integer depAccountId;
    private Integer depRelation;
    private Integer roleId;
    private Integer index;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getDepAccountId() {
        return depAccountId;
    }

    public void setDepAccountId(Integer depAccountId) {
        this.depAccountId = depAccountId;
    }

    public Integer getDepRelation() {
        return depRelation;
    }

    public void setDepRelation(Integer depRelation) {
        this.depRelation = depRelation;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "AccountDependencies [accountId=" + accountId + ", depAccountId=" + depAccountId + ", depRelation="
            + depRelation + ", roleId=" + roleId + ", index=" + index + "]";
    }

}
