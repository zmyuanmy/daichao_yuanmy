package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.UserJob;

public interface UserJobService {

    // 保存用户工作信息
    void saveUserJobInfo(UserJob userJob);
    
    UserJob selectJobInfoByAddressId(int userId);
}
