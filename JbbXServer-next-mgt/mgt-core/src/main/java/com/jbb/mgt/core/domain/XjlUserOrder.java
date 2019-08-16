package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class XjlUserOrder {

    private String orderId;
    private int userId;
    private String applyId;
    private String type;
    private String status;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private Timestamp expiryDate;
    private String ipAddress;
    private String terminalType;
    private String terminalId;
    private String cardNo;
    private String payProductId;

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
 

    public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPayProductId() {
        return payProductId;
    }

    public void setPayProductId(String payProductId) {
        this.payProductId = payProductId;
    }

    @Override
    public String toString() {
        return "XjlUserOrder{" + "orderId='" + orderId + '\'' + ", userId=" + userId + ", applyId=" + applyId
            + ", type='" + type + '\'' + ", status='" + status + '\'' + ", creationDate=" + creationDate
            + ", updateDate=" + updateDate + ", expiryDate=" + expiryDate + ", ipAddress='" + ipAddress + '\''
            + ", terminalType='" + terminalType + '\'' + ", terminalId='" + terminalId + '\'' + ", cardNo='" + cardNo
            + '\'' + ", payProductId='" + payProductId + '\'' + '}';
    }
}
