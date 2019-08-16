package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.OrganizationUser;

public interface OrganizationUserService {

    void createOrganizationUser(OrganizationUser orgUser);

    boolean checkExist(int orgId, int userId);

    OrganizationUser getOrganizationUser(int userId, int orgId, Boolean isEntry);
}
