package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserAgreeLogDao;
import com.jbb.mgt.core.dao.mapper.UserAgreeLogMapper;
import com.jbb.mgt.core.domain.UserAgreeLog;

@Repository("UserAgreeLogDao")
public class UserAgreeLogDaoImpl implements UserAgreeLogDao {

    @Autowired
    private UserAgreeLogMapper mapper;

    @Override
    public void insertUserAgreeLog(UserAgreeLog userAgreeLog) {
        mapper.insertUserAgreeLog(userAgreeLog);
    }

}
