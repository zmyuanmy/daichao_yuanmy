package com.jbb.server.core.dao;

import com.jbb.server.core.domain.UserLoginLog;

public interface UserLoginLogsDao {
    int insert(UserLoginLog record);

    int insertSelective(UserLoginLog record);
}
