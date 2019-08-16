package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserFaceIdBizNoDao;
import com.jbb.mgt.core.dao.mapper.UserFaceIdBizNoMapper;
import com.jbb.mgt.core.domain.UserFaceIdBizNo;

@Repository("UserFaceIdBizNoDao")
public class UserFaceIdBizNoDaoImpl implements UserFaceIdBizNoDao {
    @Autowired
    private UserFaceIdBizNoMapper userFaceIdBizNoMapper;

    @Override
    public void insertUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo) {
        userFaceIdBizNoMapper.insertUserFaceIdBizNo(userFaceIdBizNo);
    }

    @Override
    public UserFaceIdBizNo selectUserFaceIdBizNoByRandom(String randomNumber, int userId) {
        return userFaceIdBizNoMapper.selectUserFaceIdBizNoByRandom(randomNumber, userId);
    }

    @Override
    public boolean updateUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo) {
        return userFaceIdBizNoMapper.updateUserFaceIdBizNo(userFaceIdBizNo) > 0;
    }

}
