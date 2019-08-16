package com.jbb.server.rs.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.core.domain.LoanPlatform;
import com.jbb.server.shared.rs.Util;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsLoanPlatform {

    private int platformId;// 平台id
    private String name;// 平台名称
    private String description;// 平台描述
    private String minAmount;// 最高借款
    private String maxAmount;// 最高借款
    private String limitTime;// 借款期限
    private String loanRate;// 借款利息
    private String fastestTime;// 最快放款时间
    private String path;// 主页地址
    private String priority;// 推荐优先级
    private String serviceTelephone;// 服务号码
    private Boolean popular;// 是否热门
    private Boolean latest;// 是否最新
    private Boolean matched;// 是否匹配
    private Boolean borrowed;// 是否借过
    private String creationDate;// 创建时间
    private Long creationDateMs;// 创建时间

    public RsLoanPlatform() {

    }

    public RsLoanPlatform(int id, String name, String desc) {
        this.platformId = id;
        this.name = name;
        this.description = desc;
    }

    public RsLoanPlatform(LoanPlatform loanPlatform) {
        this.platformId = loanPlatform.getPlatformId();
        this.name = loanPlatform.getName();
        this.description = loanPlatform.getDescription();
        this.minAmount = loanPlatform.getMinAmount();
        this.maxAmount = loanPlatform.getMaxAmount();
        this.limitTime = loanPlatform.getLimitTime();
        this.loanRate = loanPlatform.getLoanRate();
        this.fastestTime = loanPlatform.getFastestTime();
        this.path = loanPlatform.getPath();
        this.priority = loanPlatform.getPriority();
        this.serviceTelephone = loanPlatform.getServiceTelephone();
        this.popular = loanPlatform.isPopular();
        this.latest = loanPlatform.isLatest();
        this.matched = loanPlatform.isMatched();
        this.borrowed = loanPlatform.isBorrowed();
        Timestamp creationDate = loanPlatform.getCreationDate();
        if (creationDate != null) {
            this.creationDate = Util.printDateTime(creationDate);
            this.creationDateMs = creationDate.getTime();
        }
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public String getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(String loanRate) {
        this.loanRate = loanRate;
    }

    public String getFastestTime() {
        return fastestTime;
    }

    public void setFastestTime(String fastestTime) {
        this.fastestTime = fastestTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getServiceTelephone() {
        return serviceTelephone;
    }

    public void setServiceTelephone(String serviceTelephone) {
        this.serviceTelephone = serviceTelephone;
    }

    public Boolean isPopular() {
        return popular;
    }

    public void setPopular(Boolean popular) {
        this.popular = popular;
    }

    public Boolean isLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    public Boolean isMatched() {
        return matched;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
    }

    public Boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }


    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreationDateMs() {
        return creationDateMs;
    }

    public void setCreationDateMs(Long creationDateMs) {
        this.creationDateMs = creationDateMs;
    }

}
