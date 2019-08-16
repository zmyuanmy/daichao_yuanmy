package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class XjlMessageDetail {
    private String msgId; // 消息ID
    private String applyId; // 申请id
    private Integer accountId;// 操作Id
    private String mobile; // 手机号
    private String msgDesc; // 消息内容
    private String status; // 状态
    private String statusDesc; // 状态说明
    private Timestamp notifyDate; // 回调时间
    private Timestamp creationDate; // 创建时间

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

   

    public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsgDesc() {
        return msgDesc;
    }

    public void setMsgDesc(String msgDesc) {
        this.msgDesc = msgDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Timestamp getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Timestamp notifyDate) {
        this.notifyDate = notifyDate;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "XjlMessageDetail [msgId=" + msgId + ", applyId=" + applyId + ", accountId=" + accountId + ", mobile="
            + mobile + ", msgDesc=" + msgDesc + ", status=" + status + ", statusDesc=" + statusDesc + ", notifyDate="
            + notifyDate + ", creationDate=" + creationDate + "]";
    }

}
