package com.jbb.mgt.core.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class LoanPlatformStatistic {

    private Date statisticDate; // 统计时间
    private int platformId; // 渠道ID
    private int cnt; // 统计数
    private int puvCnt;// 产品上报Uv
    private int price; // 单价， 单位为分
    private int expense; // 消耗额
    private int amount; // 收款额
    private int balance; // 余额
    private int status; // 状态：0-待确认， 1- 已确认
    private Timestamp creationDate; // 创建时间
    private Timestamp updateDate; // 修改时间
    private Timestamp confirmDate; // 确认时间
    private int confrimAccountId; // 确认人
    private int uvCnt;// UV数
    private double uvPrice;// UV单价
    private int totalRegisterCnt;// 注册总数
    private double uvRates;// UV注册率
    private double registerRates;// 用户注册率
    private double contributedValue;// 单用户贡献价值
    private double money;// 金额
    private String platformName;
    private Platform platform;
    private int clickCnt;
    private List<FinFile> files;
    List<LoanPlatformDetailStatistic> loanPlatformDetailStatistics;
    private Account salesAccount;

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
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

    public int getConfrimAccountId() {
        return confrimAccountId;
    }

    public void setConfrimAccountId(int confrimAccountId) {
        this.confrimAccountId = confrimAccountId;
    }

    public int getUvCnt() {
        return uvCnt;
    }

    public void setUvCnt(int uvCnt) {
        this.uvCnt = uvCnt;
    }

    public double getUvPrice() {
        return uvPrice;
    }

    public void setUvPrice(double uvPrice) {
        this.uvPrice = uvPrice;
    }

    public int getTotalRegisterCnt() {
        return totalRegisterCnt;
    }

    public void setTotalRegisterCnt(int totalRegisterCnt) {
        this.totalRegisterCnt = totalRegisterCnt;
    }

    public double getUvRates() {
        return uvRates;
    }

    public void setUvRates(double uvRates) {
        this.uvRates = uvRates;
    }

    public double getRegisterRates() {
        return registerRates;
    }

    public void setRegisterRates(double registerRates) {
        this.registerRates = registerRates;
    }

    public double getContributedValue() {
        return contributedValue;
    }

    public void setContributedValue(double contributedValue) {
        this.contributedValue = contributedValue;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public List<FinFile> getFiles() {
        return files;
    }

    public void setFiles(List<FinFile> files) {
        this.files = files;
    }

    public List<LoanPlatformDetailStatistic> getLoanPlatformDetailStatistics() {
        return loanPlatformDetailStatistics;
    }

    public void setLoanPlatformDetailStatistics(List<LoanPlatformDetailStatistic> loanPlatformDetailStatistics) {
        this.loanPlatformDetailStatistics = loanPlatformDetailStatistics;
    }

    public int getClickCnt() {
        return clickCnt;
    }

    public void setClickCnt(int clickCnt) {
        this.clickCnt = clickCnt;
    }

    public int getPuvCnt() {
        return puvCnt;
    }

    public void setPuvCnt(int puvCnt) {
        this.puvCnt = puvCnt;
    }

    public Account getSalesAccount() {
        return salesAccount;
    }

    public void setSalesAccount(Account salesAccount) {
        this.salesAccount = salesAccount;
    }

    @Override
    public String toString() {
        return "LoanPlatformStatistic [statisticDate=" + statisticDate + ", platformId=" + platformId + ", cnt=" + cnt
            + ", puvCnt=" + puvCnt + ", price=" + price + ", expense=" + expense + ", amount=" + amount + ", balance="
            + balance + ", status=" + status + ", creationDate=" + creationDate + ", updateDate=" + updateDate
            + ", confirmDate=" + confirmDate + ", confrimAccountId=" + confrimAccountId + ", uvCnt=" + uvCnt
            + ", uvPrice=" + uvPrice + ", totalRegisterCnt=" + totalRegisterCnt + ", uvRates=" + uvRates
            + ", registerRates=" + registerRates + ", contributedValue=" + contributedValue + ", money=" + money
            + ", platformName=" + platformName + ", platform=" + platform + ", clickCnt=" + clickCnt + ", files="
            + files + ", loanPlatformDetailStatistics=" + loanPlatformDetailStatistics + "]";
    }
}
