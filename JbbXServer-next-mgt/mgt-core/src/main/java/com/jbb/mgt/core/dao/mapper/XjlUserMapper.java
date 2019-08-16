package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.XjlUser;

public interface XjlUserMapper {

    List<XjlUser> getUsers(@Param(value = "accountId") int accountId, @Param(value = "orgId") int orgId,
        @Param(value = "searchText") String searchText, @Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate, @Param(value = "ChannelCode") String ChannelCode,
        @Param(value = "flag") String flag, @Param(value = "isAppLogin") Boolean isAppLogin,
        @Param(value = "isUserInfoVerified") Boolean isUserInfoVerified,
        @Param(value = "isMobileVerified") Boolean isMobileVerified,
        @Param(value = "isVideoVerified") Boolean isVideoVerified,
        @Param(value = "isRealnameVerified") Boolean isRealnameVerified,
        @Param(value = "hasContacts") Boolean hasContacts, @Param(value = "isTaobaoVerified") Boolean isTaobaoVerified,
        @Param(value = "userStatus") Integer userStatus, @Param(value = "times") Timestamp times);

    int checkExistByUserId(@Param(value = "orgId") int orgId, @Param(value = "userId") Integer userId,
        @Param(value = "times") Timestamp times);

    void saveUserStatus(@Param(value = "userId") Integer userId, @Param(value = "orgId") int orgId,
        @Param(value = "status") Integer status);

    int checkUserReceive(@Param(value = "orgId") int orgId, @Param(value = "userId") Integer userId);

    void saveReceive(@Param(value = "userId") Integer userId, @Param(value = "orgId") int orgId,
        @Param(value = "accountId") int accountId);

    void updateUserLoanCnt(@Param(value = "orgId") int orgId, @Param(value = "userId") Integer userId);

    void saveLastApplyDate(@Param(value = "userId") Integer userId, @Param(value = "orgId") Integer orgId,
        @Param(value = "lastApplyDate") Timestamp lastApplyDate);

    void saveLastLoginDate(@Param(value = "orgId") int orgId, @Param(value = "userId") int userId);

}
