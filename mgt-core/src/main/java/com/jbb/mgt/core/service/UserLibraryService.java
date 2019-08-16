package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.UserLibraryInfo;

public interface UserLibraryService {

    List<UserLibraryInfo> getUserLibraryInfo(String channelCode, String phoneNumber, String ipAddress,
        Timestamp startDate, Timestamp endDate);

}
