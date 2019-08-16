package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.UserAgreeLogDao;
import com.jbb.mgt.core.domain.UserAgreeLog;
import com.jbb.mgt.core.service.UserAgreeLogService;

@Service("UserAgreeLogService")
public class UserAgreeLogServiceImpl implements UserAgreeLogService {

    @Autowired
    private UserAgreeLogDao userAggreeLogDao;

    @Override
    public void insertUserAgreeLog(UserAgreeLog userAgreeLog) {
        userAggreeLogDao.insertUserAgreeLog(userAgreeLog);
    }

}
