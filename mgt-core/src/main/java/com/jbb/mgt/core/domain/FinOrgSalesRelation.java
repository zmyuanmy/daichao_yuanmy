package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class FinOrgSalesRelation {

    private int orgId;
    private int accountId;
    private int creator;
    private boolean isDeleted;
    private Timestamp creationDate;
    private int defaultPrice;
    private int delegatePrice;

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public int getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(int defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public int getDelegatePrice() {
        return delegatePrice;
    }

    public void setDelegatePrice(int delegatePrice) {
        this.delegatePrice = delegatePrice;
    }

    public FinOrgSalesRelation() {
        super();
    }

    public FinOrgSalesRelation(int orgId, int accountId, int creator, boolean isDeleted, Timestamp creationDate,
        int defaultPrice, int delegatePrice) {
        this.orgId = orgId;
        this.accountId = accountId;
        this.creator = creator;
        this.isDeleted = isDeleted;
        this.creationDate = creationDate;
        this.defaultPrice = defaultPrice;
        this.delegatePrice = delegatePrice;
    }

    @Override
    public String toString() {
        return "FinOrgSalesRelation{" + "orgId=" + orgId + ", accountId=" + accountId + ", creator=" + creator
            + ", isDeleted=" + isDeleted + ", creationDate=" + creationDate + ", defaultPrice=" + defaultPrice + '}';
    }
}
