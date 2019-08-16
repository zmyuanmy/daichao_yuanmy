package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class DataWhtianbeiReport {

    private Integer reportId;
    private Integer userId;
    private Integer applyId;
    private Timestamp creationDate;
    private String jsonData;
    private String blacklistJson;
    private String mfJson;
    private Integer orgId;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getJsonData() {
        return jsonData;
    }

    public String getBlacklistJson() {
        return blacklistJson;
    }

    public void setBlacklistJson(String blacklistJson) {
        this.blacklistJson = blacklistJson;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getMfJson() {
        return mfJson;
    }

    public void setMfJson(String mfJson) {
        this.mfJson = mfJson;
    }

    @Override
    public String toString() {
        return "DataWhtianbeiReport [reportId=" + reportId + ", userId=" + userId + ", applyId=" + applyId
            + ", creationDate=" + creationDate + ", jsonData=" + jsonData + ", blacklistJson=" + blacklistJson
            + ", mfJson=" + mfJson + ", orgId=" + orgId + "]";
    }

}
