package com.jbb.mall.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jbb.mall.core.dao.UserDao;
import com.jbb.mall.core.dao.domain.User;
import com.jbb.mall.core.service.UserService;
import com.jbb.server.common.exception.WrongUserKeyException;

@Service("UserService")
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User login(String userKey) throws WrongUserKeyException {
        if (userKey == null)
            throw new WrongUserKeyException();

        User user = userDao.selectUserByUserKey(userKey);

        if (user == null) {
            throw new WrongUserKeyException();
        }

        return user;
    }

}
