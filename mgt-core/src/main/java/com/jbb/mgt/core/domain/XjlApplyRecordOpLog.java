package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

/**
 * 用户申请操作日志表
 *
 * @author wyq
 * @date 2018/8/21 09:49
 */
public class XjlApplyRecordOpLog {
    private Integer opId;
    private String applyId;
    private Integer opType;
    private Timestamp opDate;
    private Integer accountId;
    private String comment;

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    

    public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Timestamp getOpDate() {
        return opDate;
    }

    public void setOpDate(Timestamp opDate) {
        this.opDate = opDate;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public XjlApplyRecordOpLog() {
        super();
    }

    public XjlApplyRecordOpLog(Integer opId, String applyId, Integer opType, Timestamp opDate, Integer accountId,
                               String comment) {
        this.opId = opId;
        this.applyId = applyId;
        this.opType = opType;
        this.opDate = opDate;
        this.accountId = accountId;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "XjlApplyRecordOpLog{" + "opId=" + opId + ", applyId=" + applyId + ", opType=" + opType + ", opDate="
            + opDate + ", accountId=" + accountId + ", comment='" + comment + '\'' + '}';
    }
}
