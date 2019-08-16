package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserPropertiesDao;
import com.jbb.mgt.core.dao.mapper.UserPropertiesMapper;
import com.jbb.mgt.core.domain.UserProperty;

@Repository("UserPropertiesDao")
public class UserPropertiesDaoImpl implements UserPropertiesDao {
    @Autowired
    private UserPropertiesMapper mapper;

    @Override
    public UserProperty selectUserPropertyByUserIdAndName(int userId, String name) {
        return mapper.selectUserPropertyByUserIdAndName(userId, name);
    }

    @Override
    public boolean deleteUserPropertyByUserIdAndName(int userId, String name) {
        return mapper.deleteUserPropertyByUserIdAndName(userId, name);
    }

    @Override
    public boolean updateUserPropertyByUserIdAndName(int userId, String name, String value) {
        return mapper.updateUserPropertyByUserIdAndName(userId, name, value);
    }

    @Override
    public void insertUserProperties(int userId, List<UserProperty> properties) {
        if (properties == null || properties.size() == 0) {
            return;
        }
        for (UserProperty p : properties) {
            mapper.insertUserProperty(userId, p.getName(), p.getValue(), p.getIsHidden());
        }

    }

    @Override
    public boolean insertUserProperty(int userId, String name, String value, Boolean isHidden) {
        return mapper.insertUserProperty(userId, name, value, isHidden);
    }

    @Override
    public List<UserProperty> selectUserPropertyList(int userId) {
        return mapper.selectUserPropertyList(userId);
    }
}
