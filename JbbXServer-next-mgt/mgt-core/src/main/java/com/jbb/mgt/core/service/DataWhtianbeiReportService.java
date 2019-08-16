package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.DataWhtianbeiReport;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserApplyRecord;

public interface DataWhtianbeiReportService {

    DataWhtianbeiReport getDataWhtianbeiReportById(User user, UserApplyRecord userApplyRecord, Account account,
        boolean refreshReport);

    String getDataWhtianbeiReport(String phone, String name, String idcard);

    String getTianBeiReportstatic(String phone, String name, String idcard);

    String getTianBeiMfReport(String phone, String name, String idcard);

}
