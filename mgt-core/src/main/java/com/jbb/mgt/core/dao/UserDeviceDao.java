package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.UserDevice;

import java.util.List;

/**
 * 设备信息Dao接口
 *
 * @author wyq
 * @date 2018/8/22 15:56
 */
public interface UserDeviceDao {

    void saveUserDevice(UserDevice userDevice);

    List<UserDevice> getUserDevices(int userId);

    UserDevice getUserLastDevice(int userId);
}
