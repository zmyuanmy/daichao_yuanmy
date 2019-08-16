package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.AppInfoDao;
import com.jbb.mgt.core.domain.AppInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.service.AppInfoService;

/**
 * @author wyq
 * @date 2019/1/5 18:14
 */
@Service("AppInfoService")
public class AppInfoServiceImpl implements AppInfoService {

    @Autowired
    private AppInfoDao dao;

    @Override
    public AppInfoVo getAppInfoByAppName(String appName) {
        return dao.selectAppInfoByAppName(appName);
    }
}
