package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.AppInfoVo;

/**
 * @author wyq
 * @date 2019/1/5 18:14
 */
public interface AppInfoService {
    AppInfoVo getAppInfoByAppName(String appName);
}
