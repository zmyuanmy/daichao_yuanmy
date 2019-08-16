package com.jbb.mall.core.service.impl;

import com.jbb.mall.core.dao.UserDao;
import com.jbb.mall.core.dao.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mall.core.dao.UserFavorDao;
import com.jbb.mall.core.dao.domain.UserFavor;
import com.jbb.mall.core.service.UserFavorService;

import java.util.List;

/**
 * @author ThinkPad
 * @date 2019/01/21
 */

@Service("UserFavorService")
public class UserFavorServiceImpl implements UserFavorService {

    @Autowired
    private UserFavorDao userFavorDao;

    @Override
    public void saveUserFavor(UserFavor userFavor) {
        userFavorDao.saveUserFavor(userFavor);
    }

    @Override
    public void updateUserFavor(Integer userId, Integer productId) {
        userFavorDao.updateUserFavor(userId, productId);
    }

    @Override
    public boolean checkUserFavor(Integer userId, Integer productId) {
        return userFavorDao.checkUserFavor(userId, productId);
    }

    @Override
    public List<Product> getUserFavor(int userId, String type) {
        return userFavorDao.selectUserFavor(userId, type);
    }

}
