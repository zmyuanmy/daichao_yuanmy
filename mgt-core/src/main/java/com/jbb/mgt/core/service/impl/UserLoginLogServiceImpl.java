package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.UserLoginLogDao;
import com.jbb.mgt.core.domain.UserLoginLog;
import com.jbb.mgt.core.service.UserLoginLogService;

@Service("UserLoginLogService")
public class UserLoginLogServiceImpl implements UserLoginLogService {

    @Autowired
    private UserLoginLogDao userLoginLogDao;

    @Override
    public void insertUserLoginLog(UserLoginLog log) {

        userLoginLogDao.insertUserLoginLog(log);
    }

}
