package com.jbb.mgt.core.domain;

import java.sql.Timestamp;
import java.util.List;

public class PlatformVo {
    private int platformId;
    private String name;
    private String shortName;
    private String url;
    private String logo;
    private String adImgUrl;
    private String desc1;
    private String desc2;
    private String desc3;
    private int minAmount;
    private int maxAmount;
    private String loanPeriod;
    private String interestRate;
    private String approvalTime;
    private boolean isNew;
    private boolean isHot;
    private int creator;
    private boolean isDeleted;
    private int cpaPrice;
    private Timestamp creationTime;
    private Timestamp updateTime;
    private String groupName;
    private Integer type;
    private Integer salesId;
    public boolean isSupportIos;
    public boolean isSupportAndroid;
    public boolean isHomePagePush;
    private Account salesAccount;
    private Integer applyCnt;// 申请人数
    private Integer priceMode;// 计价方式
    private Integer uvPrice;// UV价格
    private Integer estimatedUvCnt;// 每天估算UV量
    private Integer minBalance;// 最低待收余额

    private List<LoanTag> areaTags;

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAdImgUrl() {
        return adImgUrl;
    }

    public void setAdImgUrl(String adImgUrl) {
        this.adImgUrl = adImgUrl;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean isHot) {
        this.isHot = isHot;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getCpaPrice() {
        return cpaPrice;
    }

    public void setCpaPrice(int cpaPrice) {
        this.cpaPrice = cpaPrice;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSalesId() {
        return salesId;
    }

    public void setSalesId(Integer salesId) {
        this.salesId = salesId;
    }

    public Account getSalesAccount() {
        return salesAccount;
    }

    public void setSalesAccount(Account salesAccount) {
        this.salesAccount = salesAccount;
    }

    public Integer getApplyCnt() {
        return applyCnt;
    }

    public void setApplyCnt(Integer applyCnt) {
        this.applyCnt = applyCnt;
    }

    public List<LoanTag> getAreaTags() {
        return areaTags;
    }

    public void setAreaTags(List<LoanTag> areaTags) {
        this.areaTags = areaTags;
    }

    public boolean isSupportIos() {
        return isSupportIos;
    }

    public void setSupportIos(boolean isSupportIos) {
        this.isSupportIos = isSupportIos;
    }

    public boolean isSupportAndroid() {
        return isSupportAndroid;
    }

    public void setSupportAndroid(boolean isSupportAndroid) {
        this.isSupportAndroid = isSupportAndroid;
    }

    public boolean isHomePagePush() {
        return isHomePagePush;
    }

    public void setHomePagePush(boolean homePagePush) {
        isHomePagePush = homePagePush;
    }

    public Integer getPriceMode() {
        return priceMode;
    }

    public void setPriceMode(Integer priceMode) {
        this.priceMode = priceMode;
    }

    public Integer getUvPrice() {
        return uvPrice;
    }

    public void setUvPrice(Integer uvPrice) {
        this.uvPrice = uvPrice;
    }

    public Integer getEstimatedUvCnt() {
        return estimatedUvCnt;
    }

    public void setEstimatedUvCnt(Integer estimatedUvCnt) {
        this.estimatedUvCnt = estimatedUvCnt;
    }

    public Integer getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(Integer minBalance) {
        this.minBalance = minBalance;
    }

    @Override
    public String toString() {
        return "PlatformVo [platformId=" + platformId + ", name=" + name + ", shortName=" + shortName + ", url=" + url
            + ", logo=" + logo + ", adImgUrl=" + adImgUrl + ", desc1=" + desc1 + ", desc2=" + desc2 + ", desc3=" + desc3
            + ", minAmount=" + minAmount + ", maxAmount=" + maxAmount + ", loanPeriod=" + loanPeriod + ", interestRate="
            + interestRate + ", approvalTime=" + approvalTime + ", isNew=" + isNew + ", isHot=" + isHot + ", creator="
            + creator + ", isDeleted=" + isDeleted + ", cpaPrice=" + cpaPrice + ", creationTime=" + creationTime
            + ", updateTime=" + updateTime + ", groupName=" + groupName + ", type=" + type + ", salesId=" + salesId
            + ", isSupportIos=" + isSupportIos + ", isSupportAndroid=" + isSupportAndroid + ", salesAccount="
            + salesAccount + ", applyCnt=" + applyCnt + ", priceMode=" + priceMode + ", uvPrice=" + uvPrice
            + ", estimatedUvCnt=" + estimatedUvCnt + ", minBalance=" + minBalance + ", areaTags=" + areaTags + "]";
    }

}
