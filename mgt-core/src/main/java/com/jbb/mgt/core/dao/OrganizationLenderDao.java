package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.OrganizationLender;

import java.util.List;

public interface OrganizationLenderDao {

    int deleteByPrimaryKey(Integer orgId);

    int insert(OrganizationLender record);

    int insertSelective(OrganizationLender record);

    OrganizationLender selectByPrimaryKey(Integer orgId);

    int updateByPrimaryKeySelective(OrganizationLender record);

    int updateByPrimaryKey(OrganizationLender record);

    List<OrganizationLender> selectOrgLenderList();
}
