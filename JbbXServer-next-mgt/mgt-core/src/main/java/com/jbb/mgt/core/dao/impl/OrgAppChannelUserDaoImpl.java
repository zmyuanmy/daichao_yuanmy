 package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.OrgAppChannelUserDao;
import com.jbb.mgt.core.dao.mapper.OrgAppChannelUserMapper;

@Repository("OrgAppChannelUserDao")
public class OrgAppChannelUserDaoImpl implements OrgAppChannelUserDao {
    
    @Autowired
    private OrgAppChannelUserMapper mapper;

    @Override
    public void insertOrgAppChannelUser(int orgId, int applicationId, String channelCode, int userId, Integer userType,
        boolean isHidden) {
        mapper.insertOrgAppChannelUser(orgId, applicationId, channelCode, userId, userType, isHidden);

    }

    @Override
    public boolean checkExist(int userId, int orgId, Integer applicationId, Timestamp startDate, Timestamp endDate) {
        return mapper.checkExist(userId, orgId, applicationId, startDate, endDate) > 0;
    }

}
