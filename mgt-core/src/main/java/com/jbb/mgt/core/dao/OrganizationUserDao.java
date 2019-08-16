package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.OrganizationUser;

public interface OrganizationUserDao {

    void insertOrganizationUser(OrganizationUser orgUser);

    boolean checkExist(int orgId, int userId);

    OrganizationUser selectOrganizationUser(int userId, int orgId, Boolean entryStatus);

    void updateOrganizationUser(OrganizationUser orgUser);

    List<OrganizationUser> selectOrganizationUsers(int userId, Integer orgId, Boolean entryStatus);
}
