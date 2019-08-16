package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.UserDevice;

import java.util.List;

/**
 * 设备信息Mapper接口
 *
 * @author wyq
 * @date 2018/8/22 15:57
 */
public interface UserDeviceMapper {

    void saveUserDevice(UserDevice userDevice);

    List<UserDevice> getUserDevices(int userId);

    UserDevice getUserLastDevice(int userId);
}
