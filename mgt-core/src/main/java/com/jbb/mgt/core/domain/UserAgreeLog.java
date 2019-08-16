package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class UserAgreeLog {

    private int id;// 日志ID
    private String applyId; // 用户申请ID
    private int agreementId; // 协议ID
    private Timestamp creationDate; // 同意日期

    public UserAgreeLog() {}

    public UserAgreeLog(String applyId, int aggrementId, Timestamp creationDate) {
        this.applyId = applyId;
        this.agreementId = aggrementId;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

  
  

    public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public int getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "UserAgreeLog [id=" + id + ", applyId=" + applyId + ", agreementId=" + agreementId + ", creationDate="
            + creationDate + "]";
    }

}
