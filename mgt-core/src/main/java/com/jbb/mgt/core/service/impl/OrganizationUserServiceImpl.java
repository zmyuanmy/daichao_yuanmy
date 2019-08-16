package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.OrganizationUserDao;
import com.jbb.mgt.core.domain.OrganizationUser;
import com.jbb.mgt.core.service.OrganizationUserService;

@Service("OrganizationUserService")
public class OrganizationUserServiceImpl implements OrganizationUserService {

    @Autowired
    private OrganizationUserDao orgaizationUserDao;

    @Override
    public void createOrganizationUser(OrganizationUser orgUser) {
        orgaizationUserDao.insertOrganizationUser(orgUser);
    }

    @Override
    public boolean checkExist(int orgId, int userId) {
        return orgaizationUserDao.checkExist(orgId, userId);
    }

    @Override
    public OrganizationUser getOrganizationUser(int userId, int orgId, Boolean entryStatus) {
        return orgaizationUserDao.selectOrganizationUser(userId, orgId, entryStatus);
    }

}
