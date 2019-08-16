package com.jbb.mall.core.dao.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization {

    private int orgId;
    private String name;
    private String description;
    private boolean deleted;
    private Integer count;
    private String smsSignName;
    private String smsTemplateId;
    private Integer level;
    private Integer orgType;// 组织类型
    private Integer cnt;// 导流数量
    private Integer weight;// 导流权重
    private Boolean dataEnabled;// 是否导流
    private String filterScript;// 过滤条件
    private String companyName;// 公司名称
    private boolean delegateEnabled;
    private boolean delegateEnabled2;
    private int delegateWeight; // 权重
    private int delegateH5Type; // H5版本
    private Integer balance;
    private Account sales;

    // 注册导流默认价格
    private int defaultRegisterPrice;
    // 进件导流默认价格
    private int defaultEntryPrice;
    // 默认代理价格
    private int delegatePrice;

    private int recommandUserType;

    public Organization() {
        super();
    }

    public Organization(String name, String desc) {
        this.name = name;
        this.description = desc;
    }

    public Organization(int orgId, String name, String companyName) {
        this.orgId = orgId;
        this.name = name;
        this.companyName = companyName;
    }

    public Organization(int orgId, String name, String companyName, int recommandUserType) {
        this.orgId = orgId;
        this.name = name;
        this.companyName = companyName;
        this.recommandUserType = recommandUserType;
    }

    public Organization(String name, String description, String smsSignName, String smsTemplateId, int level) {
        this.name = name;
        this.description = description;
        this.smsSignName = smsSignName;
        this.smsTemplateId = smsTemplateId;
        this.level = level;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSmsSignName() {
        return smsSignName;
    }

    public void setSmsSignName(String smsSignName) {
        this.smsSignName = smsSignName;
    }

    public String getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(String smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getDataEnabled() {
        return dataEnabled;
    }

    public void setDataEnabled(Boolean dataEnabled) {
        this.dataEnabled = dataEnabled;
    }

    public String getFilterScript() {
        return filterScript;
    }

    public void setFilterScript(String filterScript) {
        this.filterScript = filterScript;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Account getSales() {
        return sales;
    }

    public void setSales(Account sales) {
        this.sales = sales;
    }

    public int getDefaultRegisterPrice() {
        return defaultRegisterPrice;
    }

    public void setDefaultRegisterPrice(int defaultRegisterPrice) {
        this.defaultRegisterPrice = defaultRegisterPrice;
    }

    public int getDefaultEntryPrice() {
        return defaultEntryPrice;
    }

    public void setDefaultEntryPrice(int defaultEntryPrice) {
        this.defaultEntryPrice = defaultEntryPrice;
    }

    public int getRecommandUserType() {
        return recommandUserType;
    }

    public void setRecommandUserType(int recommandUserType) {
        this.recommandUserType = recommandUserType;
    }

    public boolean isDelegateEnabled() {
        return delegateEnabled;
    }

    public void setDelegateEnabled(boolean delegateEnabled) {
        this.delegateEnabled = delegateEnabled;
    }

    public int getDelegateWeight() {
        return delegateWeight;
    }

    public void setDelegateWeight(int delegateWeight) {
        this.delegateWeight = delegateWeight;
    }

    public int getDelegatePrice() {
        return delegatePrice;
    }

    public void setDelegatePrice(int delegatePrice) {
        this.delegatePrice = delegatePrice;
    }

    public int getDelegateH5Type() {
        return delegateH5Type;
    }

    public void setDelegateH5Type(int delegateH5Type) {
        this.delegateH5Type = delegateH5Type;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public boolean isDelegateEnabled2() {
        return delegateEnabled2;
    }

    public void setDelegateEnabled2(boolean delegateEnabled2) {
        this.delegateEnabled2 = delegateEnabled2;
    }

}
