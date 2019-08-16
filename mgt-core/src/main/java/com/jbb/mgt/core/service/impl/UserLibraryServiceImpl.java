package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.UserLibraryDao;
import com.jbb.mgt.core.domain.UserLibraryInfo;
import com.jbb.mgt.core.service.UserLibraryService;

@Service("UserLibraryService")
public class UserLibraryServiceImpl implements UserLibraryService {
    @Autowired
    private UserLibraryDao userLibraryDao;

    @Override
    public List<UserLibraryInfo> getUserLibraryInfo(String channelCode, String phoneNumber, String ipAddress,
        Timestamp startDate, Timestamp endDate) {
        return userLibraryDao.getUserLibraryInfo(channelCode, phoneNumber, ipAddress, startDate, endDate);
    }

}
