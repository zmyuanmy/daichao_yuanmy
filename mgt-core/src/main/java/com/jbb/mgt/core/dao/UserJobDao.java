package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.UserJob;

public interface UserJobDao {

    // 保存用户工作信息
    void saveUserJobInfo(UserJob userJob);

    UserJob selectJobInfoByAddressId(int userId);
}
