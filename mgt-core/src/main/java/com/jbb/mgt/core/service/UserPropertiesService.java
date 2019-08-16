package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.UserProperty;

public interface UserPropertiesService {
    /**
     * 通过userId和属性键查到用户属性信息
     * 
     * @param userId
     * @param name
     * @return
     */
    UserProperty selectUserPropertyByUserIdAndName(int userId, String name);

    /**
     * 通过userId和属性键删除用户属性信息
     * 
     * @param userId
     * @param name
     * @return int
     */
    boolean deleteUserPropertyByUserIdAndName(int userId, String name);

    /**
     * 修改用户属性信息
     * 
     * @param userId
     * @param name
     * @return
     */
    boolean updateUserPropertyByUserIdAndName(int userId, String name, String value);

    /**
     * 增加用户属性信息
     * 
     * @param userId
     * @param name
     * @param value
     * @return
     */
    boolean insertUserProperty(int userId, String name, String value, Boolean isHidden);

    void insertUserProperties(int userId, List<UserProperty> properties);

    List<UserProperty> selectUserPropertyList(int userId);

}
