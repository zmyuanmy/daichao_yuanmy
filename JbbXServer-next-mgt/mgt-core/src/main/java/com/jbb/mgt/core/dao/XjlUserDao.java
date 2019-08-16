package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.XjlUser;
import org.apache.ibatis.annotations.Param;

public interface XjlUserDao {

    List<XjlUser> getUsers(int accountId, int orgId, String searchText, Timestamp startDate, Timestamp endDate,
        String ChannelCode, String flag, Boolean isAppLogin, Boolean isUserInfoVerified, Boolean isMobileVerified,
        Boolean isVideoVerified, Boolean isRealnameVerified, Boolean hasContacts, Boolean isTaobaoVerified,
        Integer userStatus, Timestamp times);

    boolean checkExistByUserId(int orgId, Integer userId, Timestamp times);

    void saveUserStatus(Integer userId, int orgId, Integer status);

    boolean checkUserReceive(int orgId, Integer userId);

    void saveReceive(Integer userId, int orgId, int accountId);

    void updateUserLoanCnt(int orgId, Integer userId);

    void saveLastApplyDate(Integer userId, Integer orgId, Timestamp creationDate);

    void saveLastLoginDate(int orgId, int userId);

}
