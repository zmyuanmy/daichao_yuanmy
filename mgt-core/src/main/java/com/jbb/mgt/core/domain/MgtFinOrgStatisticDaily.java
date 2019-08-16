package com.jbb.mgt.core.domain;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class MgtFinOrgStatisticDaily {

    private Date statisticDate;
    private int orgId;
    private int type;
    private int cnt;
    private Integer price;
    private int expense;
    private Integer amount;
    private int balance;
    private Integer status;
    private Integer manulAmount;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private Timestamp confirmDate;
    private Integer confrimAccountId;
    private List<FinFile> files;

    public List<FinFile> getFiles() {
        return files;
    }

    public void setFiles(List<FinFile> files) {
        this.files = files;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getManulAmount() {
        return manulAmount;
    }

    public void setManulAmount(Integer manulAmount) {
        this.manulAmount = manulAmount;
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

    @Override
    public String toString() {
        return "MgtFinOrgStatisticDaily{" + "statisticDate=" + statisticDate + ", orgId=" + orgId + ", type=" + type
            + ", cnt=" + cnt + ", price=" + price + ", expense=" + expense + ", amount=" + amount + ", balance="
            + balance + ", status=" + status + ", manulAmount=" + manulAmount + ", creationDate=" + creationDate
            + ", updateDate=" + updateDate + ", confirmDate=" + confirmDate + ", confrimAccountId=" + confrimAccountId
            + '}';
    }
}
