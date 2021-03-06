package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.XjlUserDao;
import com.jbb.mgt.core.domain.XjlUser;
import com.jbb.mgt.core.service.XjlUserService;

@Service("XjlUserService")
public class XjlUserServiceImpl implements XjlUserService {
    @Autowired
    private XjlUserDao xjlUserDao;

    @Override
    public List<XjlUser> getUsers(int accountId, int orgId, String searchText, Timestamp startDate, Timestamp endDate,
        String ChannelCode, String flag, Boolean isAppLogin, Boolean isUserInfoVerified, Boolean isMobileVerified,
        Boolean isVideoVerified, Boolean isRealnameVerified, Boolean hasContacts, Boolean isTaobaoVerified,
        Integer userStatus, Timestamp times) {
        return xjlUserDao.getUsers(accountId, orgId, searchText, startDate, endDate, ChannelCode, flag, isAppLogin,
            isUserInfoVerified, isMobileVerified, isVideoVerified, isRealnameVerified, hasContacts, isTaobaoVerified,
            userStatus, times);
    }

    @Override
    public boolean checkExistByUserId(int orgId, Integer userId, Timestamp times) {
        return xjlUserDao.checkExistByUserId(orgId, userId, times);
    }

    @Override
    public void saveUserStatus(Integer userId, int orgId, Integer status) {
        xjlUserDao.saveUserStatus(userId, orgId, status);

    }

    @Override
    public boolean checkUserReceive(int orgId, Integer userId) {
        return xjlUserDao.checkUserReceive(orgId, userId);
    }

    @Override
    public void saveReceive(Integer userId, int orgId, int accountId) {
        xjlUserDao.saveReceive(userId, orgId, accountId);

    }

    @Override
    public void updateUserLoanCnt(int orgId, Integer userId) {
        xjlUserDao.updateUserLoanCnt(orgId, userId);
    }

    @Override
    public void saveLastApplyDate(Integer userId, Integer orgId, Timestamp lastApplyDate) {
        xjlUserDao.saveLastApplyDate(userId, orgId, lastApplyDate);
    }

    @Override
    public void saveLastLoginDate(int orgId, int userId) {
        xjlUserDao.saveLastLoginDate(orgId, userId);

    }

}
