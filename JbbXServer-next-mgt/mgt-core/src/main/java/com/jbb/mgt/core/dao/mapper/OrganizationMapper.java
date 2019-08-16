package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.Organization;

public interface OrganizationMapper {

    // 插入组织
    void insertOrganization(Organization org);

    // 获取组织列表
    List<Organization> selectOrganizations(@Param(value = "orgId") Integer orgId,
        @Param(value = "detail") boolean detail, @Param(value = "getStatistic") boolean getStatistic,
        @Param(value = "getAdStatistic") boolean getAdStatistic, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate, @Param(value = "includeDelete") Boolean includeDelete);

    // 更新组织信息
    void updateOrganization(Organization org);

    // 删除组织信息
    int deleteOrganization(@Param(value = "orgId") int orgId);

    // 获取组织列表(仅包含简单信息)
    List<Organization> selectOrganizationSimper(@Param(value = "salesId") Integer[] salesId);
}
