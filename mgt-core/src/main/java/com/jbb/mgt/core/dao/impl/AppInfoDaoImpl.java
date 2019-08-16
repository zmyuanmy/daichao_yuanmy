package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.mapper.AppInfoMapper;
import com.jbb.mgt.core.domain.AppInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.AppInfoDao;

/**
 * @author wyq
 * @date 2019/1/5 18:15
 */
@Repository("AppInfoDao")
public class AppInfoDaoImpl implements AppInfoDao {
    @Autowired
    private AppInfoMapper mapper;

    @Override
    public AppInfoVo selectAppInfoByAppName(String appName) {
        return mapper.selectAppInfoByAppName(appName);
    }
}
