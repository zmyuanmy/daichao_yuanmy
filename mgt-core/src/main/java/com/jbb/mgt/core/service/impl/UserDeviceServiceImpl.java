package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.UserDeviceDao;
import com.jbb.mgt.core.domain.UserDevice;
import com.jbb.mgt.core.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备信息Service接口实现类
 *
 * @author wyq
 * @date 2018/8/22 15:55
 */
@Service("UserDeviceService")
public class UserDeviceServiceImpl implements UserDeviceService {

    @Autowired
    private UserDeviceDao dao;

    @Override
    public void saveUserDevice(UserDevice userDevice) {
        dao.saveUserDevice(userDevice);
    }

    @Override
    public List<UserDevice> getUserDevices(int userId) {
        return dao.getUserDevices(userId);
    }

    @Override
    public UserDevice getUserLastDevice(int userId) {
        return dao.getUserLastDevice(userId);
    }
}
