package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

public interface AccountLoginLogMapper {
    void insertAccountLoginLog(@Param(value = "userId") int userId, @Param(value = "ipAddress") String ipAddress,
        @Param(value = "loginDate") Timestamp loginDate);
}
