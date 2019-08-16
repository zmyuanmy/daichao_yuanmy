package com.jbb.mgt.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

/**
 * 组织进件导流特殊配置实体类
 *
 * @author wyq
 * @date 2018/7/27 19:26
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgDflowBase {
    private Integer orgId;
    private Integer dflowId;
    private Integer updateAccountId;
    private Timestamp updateDate;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getDflowId() {
        return dflowId;
    }

    public void setDflowId(Integer dflowId) {
        this.dflowId = dflowId;
    }

    public Integer getUpdateAccountId() {
        return updateAccountId;
    }

    public void setUpdateAccountId(Integer updateAccountId) {
        this.updateAccountId = updateAccountId;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public OrgDflowBase() {
        super();
    }

    public OrgDflowBase(Integer orgId, Integer dflowId, Integer updateAccountId, Timestamp updateDate) {
        this.orgId = orgId;
        this.dflowId = dflowId;
        this.updateAccountId = updateAccountId;
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "OrgDflowBase{" + "orgId=" + orgId + ", dflowId=" + dflowId + ", updateAccountId=" + updateAccountId
            + ", updateDate=" + updateDate + '}';
    }
}
