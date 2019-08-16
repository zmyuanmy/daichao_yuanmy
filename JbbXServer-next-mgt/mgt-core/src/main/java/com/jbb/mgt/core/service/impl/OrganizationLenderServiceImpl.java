package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.OrganizationLenderDao;
import com.jbb.mgt.core.domain.OrganizationLender;
import com.jbb.mgt.core.service.OrganizationLenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OrganizationLenderService")
public class OrganizationLenderServiceImpl implements OrganizationLenderService {

    @Autowired
    OrganizationLenderDao dao;

    @Override
    public int deleteByPrimaryKey(Integer orgId) {
        return dao.deleteByPrimaryKey(orgId);
    }

    @Override
    public int insert(OrganizationLender record) {
        return dao.insert(record);
    }

    @Override
    public int insertSelective(OrganizationLender record) {
        return dao.insertSelective(record);
    }

    @Override
    public OrganizationLender selectByPrimaryKey(Integer orgId) {
        return dao.selectByPrimaryKey(orgId);
    }

    @Override
    public int updateByPrimaryKeySelective(OrganizationLender record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrganizationLender record) {
        return dao.updateByPrimaryKey(record);
    }

    @Override
    public List<OrganizationLender> selectOrgLenderList() {
        return dao.selectOrgLenderList();
    }
}
