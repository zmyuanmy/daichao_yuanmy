package com.jbb.server.core.service;

import com.jbb.server.core.domain.UserFaceIdBizNo;

public interface UserFaceIdBizNoService {

    boolean insertUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);

    boolean updateUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);

    UserFaceIdBizNo selectUserFaceIdBizNo(String bizNo, int userId);

    UserFaceIdBizNo selectUserFaceIdBizNoByRandom(String randomNumber, int userId);

}
