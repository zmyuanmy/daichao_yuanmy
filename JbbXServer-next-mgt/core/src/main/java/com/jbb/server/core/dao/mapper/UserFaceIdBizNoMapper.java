package com.jbb.server.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.jbb.server.core.domain.UserFaceIdBizNo;

public interface UserFaceIdBizNoMapper {

    int insertUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);

    int updateUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);

    UserFaceIdBizNo selectUserFaceIdBizNo(@Param("bizNo") String bizNo, @Param("userId") int userId);
    
    UserFaceIdBizNo selectUserFaceIdBizNoByRandom(@Param("randomNumber") String randomNumber, @Param("userId") int userId);

}
