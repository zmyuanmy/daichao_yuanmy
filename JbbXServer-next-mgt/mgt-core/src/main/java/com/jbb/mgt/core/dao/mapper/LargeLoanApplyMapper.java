package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.LargeLoanOrg;
import com.jbb.mgt.core.domain.UserEventLog;

public interface LargeLoanApplyMapper {

    List<LargeLoanOrg> getAllLargeLoanOrg();

    void updateUserInfo(@Param("userId") int userId, @Param("username") String username, @Param("idcard") String idcard,
        @Param("occupation") String occupation);

    UserEventLog selectLargeLoanApplyLog(@Param("userId") int userId);

}
