package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class Agreement {
    private int agreementId;
    private String agreementName;
    private String desc;
    private String version;
    private boolean isRequired;
    private int orgId;
    private String agreementUrl;
    private Timestamp creationDate;

    public int getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public void setAgreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Agreement [agreementId=" + agreementId + ", agreementName=" + agreementName + ", desc=" + desc
            + ", version=" + version + ", isRequired=" + isRequired + ", orgId=" + orgId + ", agreementUrl="
            + agreementUrl + ", creationDate=" + creationDate + "]";
    }

}
