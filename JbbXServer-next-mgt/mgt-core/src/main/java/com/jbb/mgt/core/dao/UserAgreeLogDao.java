package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.UserAgreeLog;

public interface UserAgreeLogDao {

    // 记录立即申请协议
    void insertUserAgreeLog(UserAgreeLog userAgreeLog);

}
