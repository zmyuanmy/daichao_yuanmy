package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserLibraryDao;
import com.jbb.mgt.core.dao.mapper.UserLibraryMapper;
import com.jbb.mgt.core.domain.UserLibraryInfo;

@Repository("UserLibraryDao")
public class UserLibraryDaoImpl implements UserLibraryDao {
    @Autowired
    private UserLibraryMapper userLibraryDao;

    @Override
    public List<UserLibraryInfo> getUserLibraryInfo(String channelCode, String phoneNumber, String ipAddress,
        Timestamp startDate, Timestamp endDate) {
        return userLibraryDao.getUserLibraryInfo(channelCode, phoneNumber, ipAddress, startDate, endDate);
    }

}
