package com.jbb.mgt.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.UserJob;

public interface UserJobMapper {

    // 保存用户工作信息
    void saveUserJobInfo(UserJob userJob);

    UserJob selectJobInfoByAddressId(@Param("userId") int userId);
}
