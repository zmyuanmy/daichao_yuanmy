package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.XjlUser;

public interface XjlUserService {

    List<XjlUser> getUsers(int accountId, int orgId, String searchText, Timestamp startDate, Timestamp endDate,
        String ChannelCode, String flag, Boolean isAppLogin, Boolean isUserInfoVerified, Boolean isMobileVerified,
        Boolean isVideoVerified, Boolean isRealnameVerified, Boolean hasContacts, Boolean isTaobaoVerified,
        Integer userStatus, Timestamp times);

    boolean checkExistByUserId(int orgId, Integer userId, Timestamp times);

    void saveUserStatus(Integer userId, int orgId, Integer status);

    boolean checkUserReceive(int orgId, Integer userId);

    void saveReceive(Integer userId, int orgId, int accountId);

    void updateUserLoanCnt(int orgId, Integer userId);

    void saveLastApplyDate(Integer userId, Integer orgId, Timestamp lastApplyDate);

    void saveLastLoginDate(int orgId, int userId);

}
