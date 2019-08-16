package com.jbb.mgt.core.dao;

public interface UserVerifyDao {
    void saveUserVerifyResult(int userId, String verifyType, int verifyStep, boolean verified);

}
