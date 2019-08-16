package com.jbb.mgt.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserVerifyDao;

@Repository("UserVerifyDao")
public class UserVerifyDaoImpl implements UserVerifyDao {

    @Override
    public void saveUserVerifyResult(int userId, String verifyType, int verifyStep, boolean verified) {
        // TODO Auto-generated method stub

    }

}
