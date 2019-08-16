package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.UserFaceIdBizNo;

public interface UserFaceIdBizNoService {
    void insertUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);

    UserFaceIdBizNo selectUserFaceIdBizNoByRandom(String randomNumber, int userId);

    boolean updateUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);
}
