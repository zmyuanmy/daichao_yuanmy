package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.AppInfoVo;

/**
 * @author wyq
 * @date 2019/1/5 18:15
 */
public interface AppInfoDao {
    AppInfoVo selectAppInfoByAppName(String appName);
}
