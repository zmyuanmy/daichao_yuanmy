package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.UserFaceIdBizNoDao;
import com.jbb.mgt.core.domain.UserFaceIdBizNo;
import com.jbb.mgt.core.service.UserFaceIdBizNoService;

@Service("UserFaceIdBizNoService")
public class UserFaceIdBizNoServiceImpl implements UserFaceIdBizNoService {
    @Autowired
    private UserFaceIdBizNoDao userFaceIdBizNoDao;

    @Override
    public void insertUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo) {
        userFaceIdBizNoDao.insertUserFaceIdBizNo(userFaceIdBizNo);
    }

    @Override
    public UserFaceIdBizNo selectUserFaceIdBizNoByRandom(String randomNumber, int userId) {
        return userFaceIdBizNoDao.selectUserFaceIdBizNoByRandom(randomNumber, userId);
    }

    @Override
    public boolean updateUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo) {
        // TODO Auto-generated method stub
        return userFaceIdBizNoDao.updateUserFaceIdBizNo(userFaceIdBizNo);
    }

}
