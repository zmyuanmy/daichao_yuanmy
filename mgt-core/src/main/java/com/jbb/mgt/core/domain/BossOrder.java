package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class BossOrder {

    private String orderId;
    private int userId;
    private int applyId;
    private String status;
    private String notifyStatus;
    private String decision;
    private String result;
    private Timestamp creationDate;
    private Timestamp updateDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    @Override
    public String toString() {
        return "BossOrder{" + "orderId='" + orderId + '\'' + ", userId=" + userId + ", applyId=" + applyId
            + ", status='" + status + '\'' + ", notifyStatus='" + notifyStatus + '\'' + ", decision='" + decision + '\''
            + ", result='" + result + '\'' + ", creationDate=" + creationDate + ", updateDate=" + updateDate + '}';
    }
}
