 package com.jbb.server.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.server.core.dao.UserFaceIdBizNoDao;
import com.jbb.server.core.dao.mapper.UserFaceIdBizNoMapper;
import com.jbb.server.core.domain.UserFaceIdBizNo;


@Repository("UserFaceIdBizNoDao")
public class UserFaceIdBizNoDaoImpl implements UserFaceIdBizNoDao{
    
    @Autowired
    private UserFaceIdBizNoMapper userFaceIdBizNoMapper; 

    @Override
    public boolean insertUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo) {
         return userFaceIdBizNoMapper.insertUserFaceIdBizNo(userFaceIdBizNo) > 0;
    }

    @Override
    public boolean updateUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo) {
         return userFaceIdBizNoMapper.updateUserFaceIdBizNo(userFaceIdBizNo) > 0;
    }

    @Override
    public UserFaceIdBizNo selectUserFaceIdBizNo(String bizNo, int userId) {
         return userFaceIdBizNoMapper.selectUserFaceIdBizNo(bizNo, userId);
    }

    @Override
    public UserFaceIdBizNo selectUserFaceIdBizNoByRandom(String randomNumber, int userId) {
        // TODO Auto-generated method stub
         return userFaceIdBizNoMapper.selectUserFaceIdBizNoByRandom(randomNumber, userId);
    }

}
