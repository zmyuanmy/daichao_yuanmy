package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.UserLcDeviceDao;
import com.jbb.mgt.core.domain.UserLcDevice;
import com.jbb.mgt.core.service.UserLcDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wyq
 * @date 2018/9/5 11:52
 */
@Service("UserDeviceLcService")
public class UserLcDeviceServiceImpl implements UserLcDeviceService {

    @Autowired
    private UserLcDeviceDao dao;

    @Override
    public boolean saveUserDevice(UserLcDevice userDevice) {
        return dao.insertUserLcDevice(userDevice);
    }

    @Override
    public List<UserLcDevice> getUserLcDeviceListByUserId(int userId, String appName) {
        return dao.selectUserLcDeviceListByUserId(userId, appName);
    }

    @Override
    public void logoutUserLcDevice(int userId, String objectId) {
        dao.updateUserLcDevice(userId, true, objectId);
    }

    @Override
    public UserLcDevice selectUserLcDeviceListByObjectId(String objectId) {
        return dao.selectUserLcDeviceListByObjectId(objectId);
    }
}
