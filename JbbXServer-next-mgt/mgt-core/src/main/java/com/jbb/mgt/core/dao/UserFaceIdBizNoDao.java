package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.UserFaceIdBizNo;

public interface UserFaceIdBizNoDao {

    void insertUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);

    UserFaceIdBizNo selectUserFaceIdBizNoByRandom(String randomNumber, int userId);

    boolean updateUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);

}
