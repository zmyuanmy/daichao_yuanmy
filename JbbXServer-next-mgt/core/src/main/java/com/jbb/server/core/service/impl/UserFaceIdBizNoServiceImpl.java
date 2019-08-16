 package com.jbb.server.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.server.core.dao.UserFaceIdBizNoDao;
import com.jbb.server.core.domain.UserFaceIdBizNo;
import com.jbb.server.core.service.UserFaceIdBizNoService;

@Service("UserFaceIdBizNoService")
 public class UserFaceIdBizNoServiceImpl implements UserFaceIdBizNoService{

    @Autowired
    private UserFaceIdBizNoDao userFaceIdBizNoDao;
    @Override
    public boolean insertUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo) {
         return userFaceIdBizNoDao.insertUserFaceIdBizNo(userFaceIdBizNo);
    }

    @Override
    public boolean updateUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo) {
         return userFaceIdBizNoDao.updateUserFaceIdBizNo(userFaceIdBizNo);
    }

    @Override
    public UserFaceIdBizNo selectUserFaceIdBizNo(String bizNo, int userId) {
         return userFaceIdBizNoDao.selectUserFaceIdBizNo(bizNo, userId);
    }

    @Override
    public UserFaceIdBizNo selectUserFaceIdBizNoByRandom(String randomNumber, int userId) {
         return userFaceIdBizNoDao.selectUserFaceIdBizNoByRandom(randomNumber, userId);
    }

}
