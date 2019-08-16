package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.UserPropertiesDao;
import com.jbb.mgt.core.domain.UserProperty;
import com.jbb.mgt.core.service.UserPropertiesService;

@Service("UserPropertiesService")
public class UserPropertiesServiceImpl implements UserPropertiesService {

    @Autowired
    private UserPropertiesDao userPropertiesDao;

    @Override
    public UserProperty selectUserPropertyByUserIdAndName(int userId, String name) {
        return userPropertiesDao.selectUserPropertyByUserIdAndName(userId, name);
    }

    @Override
    public boolean deleteUserPropertyByUserIdAndName(int userId, String name) {
        return userPropertiesDao.deleteUserPropertyByUserIdAndName(userId, name);
    }

    @Override
    public boolean updateUserPropertyByUserIdAndName(int userId, String name, String value) {
        return userPropertiesDao.updateUserPropertyByUserIdAndName(userId, name, value);
    }

    @Override
    public boolean insertUserProperty(int userId, String name, String value, Boolean isHidden) {
        return userPropertiesDao.insertUserProperty(userId, name, value, isHidden);
    }

    @Override
    public void insertUserProperties(int userId, List<UserProperty> properties) {
        userPropertiesDao.insertUserProperties(userId, properties);
    }

    @Override
    public List<UserProperty> selectUserPropertyList(int userId) {
        return userPropertiesDao.selectUserPropertyList(userId);
    }

}
