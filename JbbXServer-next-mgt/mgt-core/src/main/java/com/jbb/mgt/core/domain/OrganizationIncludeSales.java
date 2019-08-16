package com.jbb.mgt.core.domain;

import java.util.List;

public class OrganizationIncludeSales {
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
    private DataFlowSetting dataFlowSetting;

    private List<UserCounts> dateCounts;
    private List<UserAdCounts> dateAdCounts;
    private OrgRecharges recharge;
    private Account account;

    // 注册导流默认价格
    private int defaultRegisterPrice;
    // 进件导流默认价格
    private int defaultEntryPrice;
    private int delegatePrice;

    public OrganizationIncludeSales() {
        super();
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

    public OrganizationIncludeSales(String name, String desc) {
        this.name = name;
        this.description = desc;
    }

    public OrganizationIncludeSales(int orgId, String name, String companyName) {
        this.orgId = orgId;
        this.name = name;
        this.companyName = companyName;
    }

    public OrganizationIncludeSales(String name, String description, String smsSignName, String smsTemplateId,
        int level) {
        this.name = name;
        this.description = description;
        this.smsSignName = smsSignName;
        this.smsTemplateId = smsTemplateId;
        this.level = level;
    }

    public OrganizationIncludeSales(int orgId, String name, String description, boolean deleted, Integer count,
        String smsSignName, String smsTemplateId, DataFlowSetting dataFlowSetting, OrgRecharges recharge,
        List<UserCounts> dateCounts, int level, int orgType, int cnt, int weight, boolean dataEnabled,
        String filterScript) {
        super();
        this.orgId = orgId;
        this.name = name;
        this.description = description;
        this.deleted = deleted;
        this.count = count;
        this.smsSignName = smsSignName;
        this.smsTemplateId = smsTemplateId;
        this.dataFlowSetting = dataFlowSetting;
        this.recharge = recharge;
        this.dateCounts = dateCounts;
        this.level = level;
        this.orgType = orgType;
        this.cnt = cnt;
        this.weight = weight;
        this.dataEnabled = dataEnabled;
        this.filterScript = filterScript;
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

    public DataFlowSetting getDataFlowSetting() {
        return dataFlowSetting;
    }

    public void setDataFlowSetting(DataFlowSetting dataFlowSetting) {
        this.dataFlowSetting = dataFlowSetting;
    }

    public List<UserCounts> getDateCounts() {
        return dateCounts;
    }

    public void setDateCounts(List<UserCounts> dateCounts) {
        this.dateCounts = dateCounts;
    }

    public OrgRecharges getRecharge() {
        return recharge;
    }

    public void setRecharge(OrgRecharges recharge) {
        this.recharge = recharge;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<UserAdCounts> getDateAdCounts() {
        return dateAdCounts;
    }

    public void setDateAdCounts(List<UserAdCounts> dateAdCounts) {
        this.dateAdCounts = dateAdCounts;
    }

    public int getDelegatePrice() {
        return delegatePrice;
    }

    public void setDelegatePrice(int delegatePrice) {
        this.delegatePrice = delegatePrice;
    }

    @Override
    public String toString() {
        return "OrganizationIncludeSales [orgId=" + orgId + ", name=" + name + ", description=" + description
            + ", deleted=" + deleted + ", count=" + count + ", smsSignName=" + smsSignName + ", smsTemplateId="
            + smsTemplateId + ", level=" + level + ", orgType=" + orgType + ", cnt=" + cnt + ", weight=" + weight
            + ", dataEnabled=" + dataEnabled + ", filterScript=" + filterScript + ", companyName=" + companyName
            + ", dataFlowSetting=" + dataFlowSetting + ", dateCounts=" + dateCounts + ", dateAdCounts=" + dateAdCounts
            + ", recharge=" + recharge + ", account=" + account + ", defaultRegisterPrice=" + defaultRegisterPrice
            + ", defaultEntryPrice=" + defaultEntryPrice + ", delegatePrice=" + delegatePrice + "]";
    }

}
