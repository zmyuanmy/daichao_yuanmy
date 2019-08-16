package com.jbb.mgt.core.dao;

import java.sql.Timestamp;

public interface OrgAppChannelUserDao {

    void insertOrgAppChannelUser(int orgId, int applicationId, String channelCode, int userId, Integer userType,
        boolean isHidden);

    boolean checkExist(int userId, int orgId, Integer applicationId, Timestamp startDate, Timestamp endDate);

}
