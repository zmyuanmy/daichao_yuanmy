package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.OrganizationLenderDao;
import com.jbb.mgt.core.dao.mapper.OrganizationLenderMapper;
import com.jbb.mgt.core.domain.OrganizationLender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OrganizationLenderDao")
public class OrganizationLenderDaoImpl implements OrganizationLenderDao{

    @Autowired
    OrganizationLenderMapper mapper;

    @Override
    public int deleteByPrimaryKey(Integer orgId) {
        return mapper.deleteByPrimaryKey(orgId);
    }

    @Override
    public int insert(OrganizationLender record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(OrganizationLender record) {
        return mapper.insertSelective(record);
    }

    @Override
    public OrganizationLender selectByPrimaryKey(Integer orgId) {
        return mapper.selectByPrimaryKey(orgId);
    }

    @Override
    public int updateByPrimaryKeySelective(OrganizationLender record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrganizationLender record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public List<OrganizationLender> selectOrgLenderList() {
        return mapper.selectOrgLenderList();
    }
}
