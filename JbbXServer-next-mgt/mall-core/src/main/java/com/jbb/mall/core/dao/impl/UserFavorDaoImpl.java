package com.jbb.mall.core.dao.impl;

import com.jbb.mall.core.dao.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mall.core.dao.UserFavorDao;
import com.jbb.mall.core.dao.domain.UserFavor;
import com.jbb.mall.core.dao.mapper.UserFavorMapper;

import java.util.List;

/**
 * @author ThinkPad
 * @date 2019/01/21
 */
@Repository("UserFavorDao")
public class UserFavorDaoImpl implements UserFavorDao {

    @Autowired
    private UserFavorMapper mapper;

    @Override
    public void saveUserFavor(UserFavor userFavor) {
        mapper.saveUserFavor(userFavor);
    }

    @Override
    public void updateUserFavor(Integer userId, Integer productId) {
        mapper.updateUserFavor(userId, productId);
    }

    @Override
    public boolean checkUserFavor(Integer userId, Integer productId) {
        return mapper.checkUserFavor(userId, productId) > 0;
    }

    @Override
    public List<Product> selectUserFavor(int userId, String type) {
        return mapper.selectUserFavor(userId, type);
    }

}
