package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.OrganizationUser;

public interface OrganizationUserMapper {

    void insertOrganizationUser(OrganizationUser orgUser);

    int checkExist(@Param(value = "orgId") int orgId, @Param(value = "userId") int userId);

    OrganizationUser selectOrganizationUser(@Param(value = "userId") int userId, @Param(value = "orgId") int orgId,
        @Param(value = "entryStatus") Boolean entryStatus);

    void updateOrganizationUser(OrganizationUser orgUser);

    List<OrganizationUser> selectOrganizationUsers(@Param(value = "userId") int userId,
        @Param(value = "orgId") Integer orgId, @Param(value = "entryStatus") Boolean entryStatus);
}
