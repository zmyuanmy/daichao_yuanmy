package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

public class PayRecord {
    public static String FAIL_STATUS = "FAIL";
    public static String SUCCESS_STATUS = "SUCCESS";
    public static String REFUND_STATUS = "REFUND";
    public static String RECEIVE_STATUS = "RECEIVE";
    public static String INIT_STATUS = "INIT";
    public static String DOING_STATUS = "DOING";

    private String orderId;// 订单号
    private String customNumber;// 商户号
    private int amount;// 金额, 单位分
    private String bankCode;// 银行编号 如ICBC
    private String bankAccountNumber;// 银行账号
    private String bankAccountName;// 银行账户名
    private String biz;// 业务类型 B2B对公,B2C 对私
    private String bankUnionCode;// 银行联行号。
    private String feeType;// 手续费收取方式
    private boolean urgency;// 是否加急
    private String summary;// 打款备注
    private String serialNumber;// 支付系统返回的唯一流水号
    private String orderStatus;// 打款状态
    private Timestamp creationDate;// 创建时间
    private Timestamp updateDate;// 更新时间
    private int accountId;// 执行账号ID
    private String applyId;// 申请借款applyId
    private String retReason;// 订单失败或成功原因

    public PayRecord() {}

    public PayRecord(String orderId, String customNumber, int amount, String bankCode, String bankAccountNumber,
        String bankAccountName, String biz, String bankUnionCode, String feeType, boolean urgency, String summary,
        String serialNumber, String orderStatus, Timestamp creationDate, Timestamp updateDate, int accountId,
        String applyId, String retReason) {
        super();
        this.orderId = orderId;
        this.customNumber = customNumber;
        this.amount = amount;
        this.bankCode = bankCode;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountName = bankAccountName;
        this.biz = biz;
        this.bankUnionCode = bankUnionCode;
        this.feeType = feeType;
        this.urgency = urgency;
        this.summary = summary;
        this.serialNumber = serialNumber;
        this.orderStatus = orderStatus;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.accountId = accountId;
        this.applyId = applyId;
        this.retReason = retReason;
    }

    public PayRecord(String orderId, String customNumber, int amount, String bankCode, String bankAccountNumber,
        String bankAccountName, String biz, String bankUnionCode, String feeType, boolean urgency, String summary,
        String serialNumber, String orderStatus, Timestamp creationDate, int accountId, String applyId, String retReason) {
        this.orderId = orderId;
        this.customNumber = customNumber;
        this.amount = amount;
        this.bankCode = bankCode;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountName = bankAccountName;
        this.biz = biz;
        this.bankUnionCode = bankUnionCode;
        this.feeType = feeType;
        this.urgency = urgency;
        this.summary = summary;
        this.serialNumber = serialNumber;
        this.orderStatus = orderStatus;
        this.creationDate = creationDate;
        this.accountId = accountId;
        this.applyId = applyId;
        this.retReason = retReason;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomNumber() {
        return customNumber;
    }

    public void setCustomNumber(String customNumber) {
        this.customNumber = customNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getBankUnionCode() {
        return bankUnionCode;
    }

    public void setBankUnionCode(String bankUnionCode) {
        this.bankUnionCode = bankUnionCode;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public boolean isUrgency() {
        return urgency;
    }

    public void setUrgency(boolean urgency) {
        this.urgency = urgency;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

   

    public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getRetReason() {
        return retReason;
    }

    public void setRetReason(String retReason) {
        this.retReason = retReason;
    }

    @Override
    public String toString() {
        return "PayRecord [orderId=" + orderId + ", customNumber=" + customNumber + ", amount=" + amount + ", bankCode="
            + bankCode + ", bankAccountNumber=" + bankAccountNumber + ", bankAccountName=" + bankAccountName + ", biz="
            + biz + ", bankUnionCode=" + bankUnionCode + ", feeType=" + feeType + ", urgency=" + urgency + ", summary="
            + summary + ", serialNumber=" + serialNumber + ", orderStatus=" + orderStatus + ", creationDate="
            + creationDate + ", updateDate=" + updateDate + ", accountId=" + accountId + ", applyId=" + applyId
            + ", retReason=" + retReason + "]";
    }

}
