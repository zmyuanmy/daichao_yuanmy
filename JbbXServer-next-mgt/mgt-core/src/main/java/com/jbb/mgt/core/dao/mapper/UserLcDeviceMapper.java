package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.UserLcDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyq
 * @date 2018/9/5 14:02
 */
public interface UserLcDeviceMapper {

    boolean insertUserLcDevice(UserLcDevice userDevice);

    /**
     * @param userId 根据userId 获取用户的设备信息，存在多平台的情况 ios/android 等
     * @return
     */
    List<UserLcDevice> selectUserLcDeviceListByUserId(@Param(value = "userId") int userId,
                                                      @Param(value = "appName") String appName);

    void updateUserLcDevice(@Param(value = "userId") int userId, @Param(value = "status") boolean status,
                            @Param(value = "objectId") String objectId);

    UserLcDevice selectUserLcDeviceListByObjectId(@Param(value = "objectId") String objectId);
}
