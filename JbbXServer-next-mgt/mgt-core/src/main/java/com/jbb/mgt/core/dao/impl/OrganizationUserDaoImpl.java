package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.OrganizationUserDao;
import com.jbb.mgt.core.dao.mapper.OrganizationUserMapper;
import com.jbb.mgt.core.domain.OrganizationUser;

@Repository("OrganizationUserDao")
public class OrganizationUserDaoImpl implements OrganizationUserDao {

    @Autowired
    private OrganizationUserMapper mapper;

    @Override
    public void insertOrganizationUser(OrganizationUser orgUser) {
        mapper.insertOrganizationUser(orgUser);
    }

    @Override
    public boolean checkExist(int orgId, int userId) {
        return mapper.checkExist(orgId, userId) > 0;
    }

    @Override
    public OrganizationUser selectOrganizationUser(int userId, int orgId, Boolean entryStatus) {
        return mapper.selectOrganizationUser(userId, orgId, entryStatus);
    }

    @Override
    public void updateOrganizationUser(OrganizationUser orgUser) {
        mapper.updateOrganizationUser(orgUser);
    }

    @Override
    public List<OrganizationUser> selectOrganizationUsers(int userId, Integer orgId, Boolean entryStatus) {
        return mapper.selectOrganizationUsers(userId, orgId, entryStatus);
    }

}
