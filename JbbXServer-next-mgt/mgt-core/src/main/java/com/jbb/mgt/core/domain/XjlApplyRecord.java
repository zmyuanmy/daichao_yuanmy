package com.jbb.mgt.core.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 用户申请表实体类
 *
 * @author wyq
 * @date 2018/8/21 09:40
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XjlApplyRecord {
    private String applyId;
    private Integer userId;
    private Integer orgId;
    private Integer status;
    private Integer amount;
    private Integer serviceFee;
    private Timestamp creationDate;
    private String purpose;
    private Timestamp loanDate;
    private Integer loanAmount;
    private Integer days;
    private Timestamp repaymentDate;
    private Timestamp actualRepaymentDate;
    private Long amountTotal;
    private Long repaymentTotal;
    private Long toRepaymentTotal;
    private Long serviceFeeTotal;
    private String address;
    private User user;
    private String openId;
    private String notifyStatus;
    private String applyStatus;
    private String applyMsg;
    private Integer finalAccountId;
    private Timestamp finalEntryDate;
    private Timestamp finalApprovalDate;
    private Integer collectorAccId;
    private Timestamp collectorEntryDate;
    private int repaymentCnt;

    private String overDay;
    private XjlUserOrder xjlUserOrder;
    private PayRecord payRecord;
    private Channel channel;

    private Account finalAccount;
    private Account collectAccount;

    private Integer userStatus;

    private Integer renewalCnt; //续期次数
    
    private Integer interest; //利息
    
    private Integer loanAgain; //是否复借:0首借,1复借

    private Integer lateFee; //滞纳金 默认为0
    
    
    public Integer getLateFee() {
		return lateFee;
	}

	public void setLateFee(Integer lateFee) {
		this.lateFee = lateFee;
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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Integer serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Timestamp getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Timestamp loanDate) {
        this.loanDate = loanDate;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Timestamp getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Timestamp repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Timestamp getActualRepaymentDate() {
        return actualRepaymentDate;
    }

    public void setActualRepaymentDate(Timestamp actualRepaymentDate) {
        this.actualRepaymentDate = actualRepaymentDate;
    }

    public Long getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Long amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Long getRepaymentTotal() {
        return repaymentTotal;
    }

    public void setRepaymentTotal(Long repaymentTotal) {
        this.repaymentTotal = repaymentTotal;
    }

    public Long getToRepaymentTotal() {
        return toRepaymentTotal;
    }

    public void setToRepaymentTotal(Long toRepaymentTotal) {
        this.toRepaymentTotal = toRepaymentTotal;
    }

    public Long getServiceFeeTotal() {
        return serviceFeeTotal;
    }

    public void setServiceFeeTotal(Long serviceFeeTotal) {
        this.serviceFeeTotal = serviceFeeTotal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyMsg() {
        return applyMsg;
    }

    public void setApplyMsg(String applyMsg) {
        this.applyMsg = applyMsg;
    }

    public Integer getFinalAccountId() {
        return finalAccountId;
    }

    public void setFinalAccountId(Integer finalAccountId) {
        this.finalAccountId = finalAccountId;
    }

    public Timestamp getFinalEntryDate() {
        return finalEntryDate;
    }

    public void setFinalEntryDate(Timestamp finalEntryDate) {
        this.finalEntryDate = finalEntryDate;
    }

    public Timestamp getFinalApprovalDate() {
        return finalApprovalDate;
    }

    public void setFinalApprovalDate(Timestamp finalApprovalDate) {
        this.finalApprovalDate = finalApprovalDate;
    }

    public Integer getCollectorAccId() {
        return collectorAccId;
    }

    public void setCollectorAccId(Integer collectorAccId) {
        this.collectorAccId = collectorAccId;
    }

    public Timestamp getCollectorEntryDate() {
        return collectorEntryDate;
    }

    public void setCollectorEntryDate(Timestamp collectorEntryDate) {
        this.collectorEntryDate = collectorEntryDate;
    }

    public int getRepaymentCnt() {
        return repaymentCnt;
    }

    public void setRepaymentCnt(int repaymentCnt) {
        this.repaymentCnt = repaymentCnt;
    }

    public String getOverDay() {
        return overDay;
    }

    public void setOverDay(String overDay) {
        this.overDay = overDay;
    }

    public XjlUserOrder getXjlUserOrder() {
        return xjlUserOrder;
    }

    public void setXjlUserOrder(XjlUserOrder xjlUserOrder) {
        this.xjlUserOrder = xjlUserOrder;
    }

    public PayRecord getPayRecord() {
        return payRecord;
    }

    public void setPayRecord(PayRecord payRecord) {
        this.payRecord = payRecord;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public XjlApplyRecord() {
        super();
    }

    public Account getFinalAccount() {
        return finalAccount;
    }

    public void setFinalAccount(Account finalAccount) {
        this.finalAccount = finalAccount;
    }

    public Account getCollectAccount() {
        return collectAccount;
    }

    public void setCollectAccount(Account collectAccount) {
        this.collectAccount = collectAccount;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
    

	public Integer getRenewalCnt() {
		return renewalCnt;
	}

	public void setRenewalCnt(Integer renewalCnt) {
		this.renewalCnt = renewalCnt;
	}

	public Integer getInterest() {
		return interest;
	}

	public void setInterest(Integer interest) {
		this.interest = interest;
	}

	public Integer getLoanAgain() {
		return loanAgain;
	}

	public void setLoanAgain(Integer loanAgain) {
		this.loanAgain = loanAgain;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public XjlApplyRecord(String applyId, Integer userId, Integer orgId, Integer status, Integer amount,
        Integer serviceFee, Timestamp creationDate, String purpose, Timestamp loanDate, Integer loanAmount,
        Integer days, Timestamp repaymentDate, Timestamp actualRepaymentDate) {
        this.applyId = applyId;
        this.userId = userId;
        this.orgId = orgId;
        this.status = status;
        this.amount = amount;
        this.serviceFee = serviceFee;
        this.creationDate = creationDate;
        this.purpose = purpose;
        this.loanDate = loanDate;
        this.loanAmount = loanAmount;
        this.days = days;
        this.repaymentDate = repaymentDate;
        this.actualRepaymentDate = actualRepaymentDate;
    }

	@Override
	public String toString() {
		return "XjlApplyRecord [applyId=" + applyId + ", userId=" + userId + ", orgId=" + orgId + ", status=" + status
				+ ", amount=" + amount + ", serviceFee=" + serviceFee + ", creationDate=" + creationDate + ", purpose="
				+ purpose + ", loanDate=" + loanDate + ", loanAmount=" + loanAmount + ", days=" + days
				+ ", repaymentDate=" + repaymentDate + ", actualRepaymentDate=" + actualRepaymentDate + ", amountTotal="
				+ amountTotal + ", repaymentTotal=" + repaymentTotal + ", toRepaymentTotal=" + toRepaymentTotal
				+ ", serviceFeeTotal=" + serviceFeeTotal + ", address=" + address + ", user=" + user + ", openId="
				+ openId + ", notifyStatus=" + notifyStatus + ", applyStatus=" + applyStatus + ", applyMsg=" + applyMsg
				+ ", finalAccountId=" + finalAccountId + ", finalEntryDate=" + finalEntryDate + ", finalApprovalDate="
				+ finalApprovalDate + ", collectorAccId=" + collectorAccId + ", collectorEntryDate="
				+ collectorEntryDate + ", repaymentCnt=" + repaymentCnt + ", overDay=" + overDay + ", xjlUserOrder="
				+ xjlUserOrder + ", payRecord=" + payRecord + ", channel=" + channel + ", finalAccount=" + finalAccount
				+ ", collectAccount=" + collectAccount + ", userStatus=" + userStatus + ", renewalCnt=" + renewalCnt
				+ ", interest=" + interest + ", loanAgain=" + loanAgain + ", lateFee=" + lateFee + "]";
	}

    
}
