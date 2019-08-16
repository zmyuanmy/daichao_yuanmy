package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.UserDevice;

import java.util.List;

/**
 * 设备信息Service接口
 *
 * @author wyq
 * @date 2018/8/22 15:54
 */
public interface UserDeviceService {
    /**
     * 如果存在再更新。 inser into .... on duplicate key update ,
     */
    void saveUserDevice(UserDevice userDevice);

    List<UserDevice> getUserDevices(int userId);

    UserDevice getUserLastDevice(int userId);
}
