package com.jbb.mgt.core.domain;

import java.sql.Timestamp;
import java.util.Date;

public class OrganizationLender  {

    private Integer orgId;

    private Integer accountId;

    private Integer price;

    private String title;

    private String description;

    private Timestamp creationDate;

    private Timestamp updateDate;

    private Timestamp priceDate;

    public OrganizationLender(Integer orgId, Integer accountId, Integer price, String title, String description, Timestamp creationDate, Timestamp updateDate, Timestamp priceDate) {
        this.orgId = orgId;
        this.accountId = accountId;
        this.price = price;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.priceDate = priceDate;
    }

    public OrganizationLender(Integer orgId, Integer accountId, Integer price, String title, String description) {
        this.orgId = orgId;
        this.accountId = accountId;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public OrganizationLender() {
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Timestamp priceDate) {
        this.priceDate = priceDate;
    }
}