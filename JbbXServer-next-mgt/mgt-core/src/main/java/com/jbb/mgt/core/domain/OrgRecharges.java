package com.jbb.mgt.core.domain;

public class OrgRecharges {
    private Integer orgId;// 组织ID
    private Integer totalDataAmount;// 总充值金额
    private Integer totalSmsAmount;// 总消费金额
    private Integer totalDataExpense;// 总流量消费金额
    private Integer totalSmsExpense;// 总短信消费金额

    public OrgRecharges(Integer orgId, Integer totalDataAmount, Integer totalSmsAmount, Integer totalDataExpense,
        Integer totalSmsExpense) {
        this.orgId = orgId;
        this.totalDataAmount = totalDataAmount;
        this.totalSmsAmount = totalSmsAmount;
        this.totalDataExpense = totalDataExpense;
        this.totalSmsExpense = totalSmsExpense;
    }

    public OrgRecharges(Integer orgId) {
        this.orgId = orgId;
    }

    public OrgRecharges() {}

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getTotalDataAmount() {
        return totalDataAmount;
    }

    public void setTotalDataAmount(Integer totalDataAmount) {
        this.totalDataAmount = totalDataAmount;
    }

    public Integer getTotalSmsAmount() {
        return totalSmsAmount;
    }

    public void setTotalSmsAmount(Integer totalSmsAmount) {
        this.totalSmsAmount = totalSmsAmount;
    }

    public Integer getTotalDataExpense() {
        return totalDataExpense;
    }

    public void setTotalDataExpense(Integer totalDataExpense) {
        this.totalDataExpense = totalDataExpense;
    }

    public Integer getTotalSmsExpense() {
        return totalSmsExpense;
    }

    public void setTotalSmsExpense(Integer totalSmsExpense) {
        this.totalSmsExpense = totalSmsExpense;
    }

    public int getDataBudget() {
        int totalDataAmount = this.totalDataAmount == null ? 0 : this.totalDataAmount;
        int totalDataExpense = this.totalDataExpense == null ? 0 : this.totalDataExpense;
        return totalDataAmount - totalDataExpense;
    }

    public int getSmsBudget() {
        int totalSmsAmount = this.totalSmsAmount == null ? 0 : this.totalSmsAmount;
        int totalSmsExpense = this.totalSmsExpense == null ? 0 : this.totalSmsExpense;
        return totalSmsAmount - totalSmsExpense;
    }

    @Override
    public String toString() {
        return "OrgRecharges [orgId=" + orgId + ", totalDataAmount=" + totalDataAmount + ", totalSmsAmount="
            + totalSmsAmount + ", totalDataExpense=" + totalDataExpense + ", totalSmsExpense=" + totalSmsExpense + "]";
    }

}
