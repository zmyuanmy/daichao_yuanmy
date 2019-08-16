 package com.jbb.server.core.dao;

import com.jbb.server.core.domain.UserFaceIdBizNo;

public interface UserFaceIdBizNoDao {
     
     boolean insertUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);
     
     boolean updateUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);
     
     UserFaceIdBizNo selectUserFaceIdBizNo(String bizNo,int userId);
     
     UserFaceIdBizNo selectUserFaceIdBizNoByRandom(String randomNumber,int userId);
     

}
