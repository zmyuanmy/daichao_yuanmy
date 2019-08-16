package com.jbb.mgt.core.dao.mapper;

import org.apache.ibatis.annotations.Param;
import com.jbb.mgt.core.domain.UserFaceIdBizNo;

public interface UserFaceIdBizNoMapper {

    void insertUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);

    UserFaceIdBizNo selectUserFaceIdBizNoByRandom(@Param("randomNumber") String randomNumber,
        @Param("userId") int userId);

    int updateUserFaceIdBizNo(UserFaceIdBizNo userFaceIdBizNo);

}
