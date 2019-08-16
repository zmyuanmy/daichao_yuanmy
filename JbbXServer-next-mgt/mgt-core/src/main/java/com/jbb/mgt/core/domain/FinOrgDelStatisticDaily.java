package com.jbb.mgt.core.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class FinOrgDelStatisticDaily {

    private Date statisticDate;
    private int orgId;
    private int cnt;
    private int price;
    private int expense;
    private int amount;
    private int balance;
    private int status;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private Timestamp confirmDate;
    private Integer confrimAccountId;
    private List<FinFile> files;

    public FinOrgDelStatisticDaily() {}

    public FinOrgDelStatisticDaily(Date statisticDate, int orgId, int cnt, int price, int expense, int amount,
        int balance, int status, Timestamp creationDate, Timestamp updateDate, Timestamp confirmDate,
        Integer confrimAccountId) {
        super();
        this.statisticDate = statisticDate;
        this.orgId = orgId;
        this.cnt = cnt;
        this.price = price;
        this.expense = expense;
        this.amount = amount;
        this.balance = balance;
        this.status = status;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.confirmDate = confirmDate;
        this.confrimAccountId = confrimAccountId;
    }

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public Timestamp getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Timestamp confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getConfrimAccountId() {
        return confrimAccountId;
    }

    public void setConfrimAccountId(Integer confrimAccountId) {
        this.confrimAccountId = confrimAccountId;
    }

    public List<FinFile> getFiles() {
        return files;
    }

    public void setFiles(List<FinFile> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "FinOrgDelStatisticDaily [statisticDate=" + statisticDate + ", orgId=" + orgId + ", cnt=" + cnt
            + ", price=" + price + ", expense=" + expense + ", amount=" + amount + ", balance=" + balance + ", status="
            + status + ", creationDate=" + creationDate + ", updateDate=" + updateDate + ", confirmDate=" + confirmDate
            + ", confrimAccountId=" + confrimAccountId + ", files=" + files + "]";
    }

}
