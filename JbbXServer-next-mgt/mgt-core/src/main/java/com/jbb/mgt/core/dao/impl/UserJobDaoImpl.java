package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserJobDao;
import com.jbb.mgt.core.dao.mapper.UserJobMapper;
import com.jbb.mgt.core.domain.UserJob;

@Repository("UserJobDao")
public class UserJobDaoImpl implements UserJobDao {

    @Autowired
    private UserJobMapper mapper;

    @Override
    public void saveUserJobInfo(UserJob userJob) {
        mapper.saveUserJobInfo(userJob);
    }

    @Override
    public UserJob selectJobInfoByAddressId(int userId) {
        return mapper.selectJobInfoByAddressId(userId);
    }

}
