package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.OrganizationLender;

import java.util.List;

public interface OrganizationLenderService {

    int deleteByPrimaryKey(Integer orgId);

    int insert(OrganizationLender record);

    int insertSelective(OrganizationLender record);

    OrganizationLender selectByPrimaryKey(Integer orgId);

    int updateByPrimaryKeySelective(OrganizationLender record);

    int updateByPrimaryKey(OrganizationLender record);

    List<OrganizationLender> selectOrgLenderList();
}
