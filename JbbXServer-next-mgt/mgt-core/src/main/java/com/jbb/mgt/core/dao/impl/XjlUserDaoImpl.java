package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.XjlUserDao;
import com.jbb.mgt.core.dao.mapper.XjlUserMapper;
import com.jbb.mgt.core.domain.XjlUser;

@Repository("XjlUserDao")
public class XjlUserDaoImpl implements XjlUserDao {
    @Autowired
    private XjlUserMapper mapper;

    @Override
    public List<XjlUser> getUsers(int accountId, int orgId, String searchText, Timestamp startDate, Timestamp endDate,
        String ChannelCode, String flag, Boolean isAppLogin, Boolean isUserInfoVerified, Boolean isMobileVerified,
        Boolean isVideoVerified, Boolean isRealnameVerified, Boolean hasContacts, Boolean isTaobaoVerified,
        Integer userStatus, Timestamp times) {
        return mapper.getUsers(accountId, orgId, searchText, startDate, endDate, ChannelCode, flag, isAppLogin,
            isUserInfoVerified, isMobileVerified, isVideoVerified, isRealnameVerified, hasContacts, isTaobaoVerified,
            userStatus, times);
    }

    @Override
    public boolean checkExistByUserId(int orgId, Integer userId, Timestamp times) {
        return mapper.checkExistByUserId(orgId, userId, times) > 0;
    }

    @Override
    public void saveUserStatus(Integer userId, int orgId, Integer status) {
        mapper.saveUserStatus(userId, orgId, status);

    }

    @Override
    public boolean checkUserReceive(int orgId, Integer userId) {
        return mapper.checkUserReceive(orgId, userId) > 0;
    }

    @Override
    public void saveReceive(Integer userId, int orgId, int accountId) {
        mapper.saveReceive(userId, orgId, accountId);

    }

    @Override
    public void updateUserLoanCnt(int orgId, Integer userId) {
        mapper.updateUserLoanCnt(orgId, userId);
    }

    @Override
    public void saveLastApplyDate(Integer userId, Integer orgId, Timestamp lastApplyDate) {
        mapper.saveLastApplyDate(userId, orgId, lastApplyDate);
    }

    @Override
    public void saveLastLoginDate(int orgId, int userId) {
        mapper.saveLastLoginDate(orgId, userId);

    }

}
