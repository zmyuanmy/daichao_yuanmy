package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.PreloanReport;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserApplyRecord;

public interface TdRiskService {

    PreloanReport getPreloanReportByUserId(User userId, UserApplyRecord record, boolean refreshReport, Account account);

}
