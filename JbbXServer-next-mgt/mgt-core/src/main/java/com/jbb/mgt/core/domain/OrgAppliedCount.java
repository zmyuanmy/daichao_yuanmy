package com.jbb.mgt.core.domain;

public class OrgAppliedCount {
    private int orgId;
    private int cnt;

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

    @Override
    public String toString() {
        return "OrgAppliedCount [orgId=" + orgId + ", cnt=" + cnt + "]";
    }

}
