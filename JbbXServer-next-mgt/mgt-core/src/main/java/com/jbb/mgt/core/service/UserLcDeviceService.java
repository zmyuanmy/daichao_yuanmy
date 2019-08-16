package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.UserLcDevice;

import java.util.List;

/**
 * @author wyq
 * @date 2018/9/5 11:46
 */
public interface UserLcDeviceService {

    boolean saveUserDevice(UserLcDevice userDevice);

    /**
     * @param userId 根据userId 获取用户的设备信息，存在多平台的情况 ios/android 等
     * @return
     */
    List<UserLcDevice> getUserLcDeviceListByUserId(int userId, String appName);

    void logoutUserLcDevice(int userId, String objectId);

    UserLcDevice selectUserLcDeviceListByObjectId(String objectId);
}
