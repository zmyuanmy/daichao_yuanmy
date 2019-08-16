package com.jbb.mgt.core.service;

import java.sql.Timestamp;

public interface AccountLoginLogService {
	 // 插入登录用户日志
    void insertAccountLoginLog(int userId, String ipAddress, Timestamp loginDate);
}
