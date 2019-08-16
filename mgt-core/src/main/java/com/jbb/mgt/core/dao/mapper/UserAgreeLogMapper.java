package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.UserAgreeLog;

public interface UserAgreeLogMapper {

    // 记录立即申请协议
    void insertUserAgreeLog(UserAgreeLog userAgreeLog);

}
