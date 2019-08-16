package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.LargeLoanOrg;
import com.jbb.mgt.core.domain.UserEventLog;

public interface LargeLoanApplyService {

    List<LargeLoanOrg> getAllLargeLoanOrg();

    void updateUserInfo(int userId, String username, String idcard, String profession);

    UserEventLog selectLargeLoanApplyLog(int userId);

}
