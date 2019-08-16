package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.LargeLoanOrg;
import com.jbb.mgt.core.domain.UserEventLog;

public interface LargeLoanApplyDao {

    List<LargeLoanOrg> getAllLargeLoanOrg();

    void updateUserInfo(int userId, String username, String idcard, String occupation);

    UserEventLog selectLargeLoanApplyLog(int userId);

}
