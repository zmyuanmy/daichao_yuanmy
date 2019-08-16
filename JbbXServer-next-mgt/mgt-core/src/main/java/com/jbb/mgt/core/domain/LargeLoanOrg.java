package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LargeLoanOrg {
    private Integer orgId;
    private String orgName;
    private String desc;
    private String logo;
    private int price;
    private Boolean isDelete;
    private Timestamp creationDate;
    private String filterScript;
    // private List<String> required;
    private boolean isQualified;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getFilterScript() {
        return filterScript;
    }

    public void setFilterScript(String filterScript) {
        this.filterScript = filterScript;
    }

    public boolean isQualified() {
        return isQualified;
    }

    public void setQualified(boolean isQualified) {
        this.isQualified = isQualified;
    }

    @Override
    public String toString() {
        return "LargeLoanOrg [orgId=" + orgId + ", orgName=" + orgName + ", desc=" + desc + ", logo=" + logo
            + ", price=" + price + ", isDelete=" + isDelete + ", creationDate=" + creationDate + ", filterScript="
            + filterScript + ", isQualified=" + isQualified + "]";
    }

}
