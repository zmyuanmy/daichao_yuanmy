package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.UserAgreeLog;

public interface UserAgreeLogService {

    // 记录立即申请协议
    void insertUserAgreeLog(UserAgreeLog userAgreeLog);

}
