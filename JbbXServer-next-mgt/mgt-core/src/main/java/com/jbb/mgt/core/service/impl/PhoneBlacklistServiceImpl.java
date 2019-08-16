package com.jbb.mgt.core.service.impl;

import com.jbb.server.common.exception.WrongParameterValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.PhoneBlacklistDao;
import com.jbb.mgt.core.service.PhoneBlacklistService;

/**
 * Created by inspironsun on 2019/4/11
 */

@Service("PhoneBlacklistService")
public class PhoneBlacklistServiceImpl implements PhoneBlacklistService {

    @Autowired
    private PhoneBlacklistDao phoneBlacklistDao;

    @Override
    public void checkPhoneNumberExist(String phoneNumber) {
        boolean flag = phoneBlacklistDao.checkPhoneNumberExist(phoneNumber);
        if(flag){
            throw new WrongParameterValueException("jbb.mgt.exception.phone.blacklist","zh");
        }
    }
}
