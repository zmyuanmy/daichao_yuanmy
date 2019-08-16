package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.UserLcDeviceDao;
import com.jbb.mgt.core.dao.mapper.UserLcDeviceMapper;
import com.jbb.mgt.core.domain.UserLcDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wyq
 * @date 2018/9/5 11:55
 */
@Repository("UserDeviceLcDao")
public class UserLcDeviceDaoImpl implements UserLcDeviceDao {

    @Autowired
    private UserLcDeviceMapper mapper;

    @Override
    public boolean insertUserLcDevice(UserLcDevice userDevice) {
        return mapper.insertUserLcDevice(userDevice);
    }

    @Override
    public List<UserLcDevice> selectUserLcDeviceListByUserId(int userId, String appName) {
        return mapper.selectUserLcDeviceListByUserId(userId, appName);
    }

    @Override
    public void updateUserLcDevice(int userId, boolean status, String objectId) {
        mapper.updateUserLcDevice(userId, status, objectId);
    }

    @Override
    public UserLcDevice selectUserLcDeviceListByObjectId(String objectId) {
        return mapper.selectUserLcDeviceListByObjectId(objectId);
    }
}
