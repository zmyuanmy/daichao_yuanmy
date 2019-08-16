package com.jbb.mgt.core.domain;

import java.util.List;

public class OrgUserCounts {
    private Integer orgId;
    private List<UserCounts> dateCounts;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public List<UserCounts> getDateCounts() {
        return dateCounts;
    }

    public void setDateCounts(List<UserCounts> dateCounts) {
        this.dateCounts = dateCounts;
    }

    @Override
    public String toString() {
        return "OrgUserCounts [orgId=" + orgId + ", dateCounts=" + dateCounts + "]";
    }

}
