package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.UserLcDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyq
 * @date 2018/9/5 11:54
 */
public interface UserLcDeviceDao {

    boolean insertUserLcDevice(UserLcDevice userDevice);

    /**
     * @param userId 根据userId 获取用户的设备信息，存在多平台的情况 ios/android 等
     * @return
     */
    List<UserLcDevice> selectUserLcDeviceListByUserId(int userId, String appName);

    void updateUserLcDevice(int userId, boolean status, String objectId);

    UserLcDevice selectUserLcDeviceListByObjectId(String objectId);
}
