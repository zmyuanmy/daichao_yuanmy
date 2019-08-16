package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.UserDeviceDao;
import com.jbb.mgt.core.dao.mapper.UserDeviceMapper;
import com.jbb.mgt.core.domain.UserDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户设备信息Dao实现类
 *
 * @author wyq
 * @date 2018/8/22 15:57
 */
@Repository("UserDeviceDao")
public class UserDeviceDaoImpl implements UserDeviceDao {

    @Autowired
    private UserDeviceMapper mapper;

    @Override
    public void saveUserDevice(UserDevice userDevice) {
        mapper.saveUserDevice(userDevice);
    }

    @Override
    public List<UserDevice> getUserDevices(int userId) {
        return mapper.getUserDevices(userId);
    }

    @Override
    public UserDevice getUserLastDevice(int userId) {
        return mapper.getUserLastDevice(userId);
    }
}
