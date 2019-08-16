package com.jbb.mgt.core.dao;


import java.sql.Timestamp;

public interface AccountLoginLogDao {

    // 插入User登录日志
    void insertAccountLoginLog(int userId, String ipAddress, Timestamp loginDate);
}
