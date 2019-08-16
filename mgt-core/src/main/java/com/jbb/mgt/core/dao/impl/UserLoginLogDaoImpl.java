package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserLoginLogDao;
import com.jbb.mgt.core.dao.mapper.UserLoginLogMapper;
import com.jbb.mgt.core.domain.UserLoginLog;

@Repository("UserLoginLogDao")
public class UserLoginLogDaoImpl implements UserLoginLogDao {

    @Autowired
    private UserLoginLogMapper mapper;

    @Override
    public void insertUserLoginLog(UserLoginLog log) {
        mapper.insertUserLoginLog(log);
    }

}
