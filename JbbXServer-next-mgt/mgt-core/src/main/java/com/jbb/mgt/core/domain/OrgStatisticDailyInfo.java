package com.jbb.mgt.core.domain;

public class OrgStatisticDailyInfo {

    private MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily;

    private FinOrgDelStatisticDaily OrgDelStatisticDaily;

    private Organization organization;

    private Account account;

    public FinOrgDelStatisticDaily getOrgDelStatisticDaily() {
        return OrgDelStatisticDaily;
    }

    public void setOrgDelStatisticDaily(FinOrgDelStatisticDaily orgDelStatisticDaily) {
        OrgDelStatisticDaily = orgDelStatisticDaily;
    }

    public MgtFinOrgStatisticDaily getMgtFinOrgStatisticDaily() {
        return mgtFinOrgStatisticDaily;
    }

    public void setMgtFinOrgStatisticDaily(MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily) {
        this.mgtFinOrgStatisticDaily = mgtFinOrgStatisticDaily;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
