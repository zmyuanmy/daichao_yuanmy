package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.PhoneBlacklistDao;
import com.jbb.mgt.core.dao.mapper.PhoneBlacklistMapper;

/**
 * Created by inspironsun on 2019/4/11
 */
@Repository("PhoneBlacklistDao")
public class PhoneBlacklistDaoImpl implements PhoneBlacklistDao {

    @Autowired
    private PhoneBlacklistMapper phoneBlacklistMapper;

    @Override
    public boolean checkPhoneNumberExist(String phoneNumber) {
        return phoneBlacklistMapper.checkPhoneNumberExist(phoneNumber) > 0;
    }
}
