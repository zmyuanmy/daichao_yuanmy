package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.AppInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author wyq
 * @date 2019/1/5 18:15
 */
public interface AppInfoMapper {
    AppInfoVo selectAppInfoByAppName(@Param("appName") String appName);
}
