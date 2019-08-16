package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

public interface OrgAppChannelUserMapper {

    void insertOrgAppChannelUser(@Param(value = "orgId") int orgId, @Param(value = "applicationId") int applicationId,
        @Param(value = "channelCode") String channelCode, @Param(value = "userId") int userId,
        @Param(value = "userType") Integer userType, @Param(value = "isHidden") boolean isHidden);

    int checkExist(@Param(value = "userId") int userId, @Param(value = "orgId") int orgId,
        @Param(value = "applicationId") Integer applicationId, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);

}
