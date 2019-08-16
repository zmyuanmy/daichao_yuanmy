package com.jbb.mgt.core.domain;

import java.sql.Timestamp;




public class XjlRenewalLog {

	private Integer logId;
	
	private String applyId;
	
	private Integer userId;
	
	private Integer accountId;
	
	private Integer opType;
	
	private Timestamp creationDate;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "XjlRenewalLog [logId=" + logId + ", applyId=" + applyId + ", userId=" + userId + ", accountId="
				+ accountId + ", opType=" + opType + ", creationDate=" + creationDate + "]";
	}

	public XjlRenewalLog(String applyId, Integer userId, Integer accountId, Integer opType,
			Timestamp creationDate) {
		super();
		
		this.applyId = applyId;
		this.userId = userId;
		this.accountId = accountId;
		this.opType = opType;
		this.creationDate = creationDate;
	}

	public XjlRenewalLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
