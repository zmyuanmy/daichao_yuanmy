package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.Organization;

public interface OrganizationDao {

    // 插入组织
    void insertOrganization(Organization org);

    // 通过ID获取组织信息
    Organization selectOrganizationById(int orgId, boolean detail);

    // 删除组织信息
    boolean deleteOrganization(int orgId);

    List<Organization> selectOrganizations(boolean detail, boolean getStatistic, boolean getAdStatistic,
        Timestamp startDate, Timestamp endDate, Boolean includeDelete);

    void updateOrganization(Organization org);

    List<Organization> selectOrganizationSimper(Integer[] salesId);

    List<Organization> selectOrgAdByOrgId(Integer orgId, boolean detail, boolean getStatistic, boolean getAdStatistic,
        Timestamp startDate, Timestamp endDate);

}
