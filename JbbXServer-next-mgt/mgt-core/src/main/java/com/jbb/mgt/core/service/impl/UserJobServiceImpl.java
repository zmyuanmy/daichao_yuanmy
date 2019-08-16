package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.UserJobDao;
import com.jbb.mgt.core.domain.UserJob;
import com.jbb.mgt.core.service.UserJobService;

@Service("UserJobService")
public class UserJobServiceImpl implements UserJobService {

    @Autowired
    private UserJobDao userJobDao;

    @Override
    public void saveUserJobInfo(UserJob userJob) {
        userJobDao.saveUserJobInfo(userJob);
    }

    @Override
    public UserJob selectJobInfoByAddressId(int userId) {
        return userJobDao.selectJobInfoByAddressId(userId);
    }

}
