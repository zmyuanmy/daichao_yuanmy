package com.jbb.mall.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mall.core.dao.UserDao;
import com.jbb.mall.core.dao.domain.User;
import com.jbb.mall.core.dao.mapper.UserMapper;
import com.jbb.server.common.util.DateUtil;

@Repository("UserDao")
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByUserKey(String userKey) {
        return userMapper.selectUserByUserKey(userKey, DateUtil.getCurrentTimeStamp());
    }

}
