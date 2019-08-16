package com.jbb.server.core.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jbb.server.shared.rs.Util;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVerifyResult {

    private String verifyType;
    private int verifyStep;
    private boolean verified;

    @JsonIgnore
    private Timestamp creationDate;
    @JsonProperty("creationDate")
    private String creationDateStr;
    private Long creationDateMs;
    @JsonIgnore
    private Timestamp updateDate;
    @JsonProperty("updateDae")
    private String updateDateStr;
    private Long updateDateMs;

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public int getVerifyStep() {
        return verifyStep;
    }

    public void setVerifyStep(int step) {
        this.verifyStep = step;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
        this.creationDateStr = Util.printDateTime(creationDate);
        this.creationDateMs = Util.getTimeMs(creationDate);
    }

    public String getCreationDateStr() {
        return creationDateStr;
    }

    public void setCreationDateStr(String creationDateStr) {
        this.creationDateStr = creationDateStr;
    }

    public Long getCreationDateMs() {
        return creationDateMs;
    }

    public void setCreationDateMs(Long creationDateMs) {
        this.creationDateMs = creationDateMs;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
        this.updateDateStr = Util.printDateTime(updateDate);
        this.updateDateMs = Util.getTimeMs(updateDate);
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public Long getUpdateDateMs() {
        return updateDateMs;
    }

    public void setUpdateDateMs(Long updateDateMs) {
        this.updateDateMs = updateDateMs;
    }

    @Override
    public String toString() {
        return "UserVerifyResult [verifyType=" + verifyType + ", verifyStep=" + verifyStep + ", verified="
            + verified + ", creationDate=" + creationDate + ", creationDateStr=" + creationDateStr + ", creationDateMs="
            + creationDateMs + ", updateDate=" + updateDate + ", updateDateStr=" + updateDateStr + ", updateDateMs="
            + updateDateMs + "]";
    }

}
