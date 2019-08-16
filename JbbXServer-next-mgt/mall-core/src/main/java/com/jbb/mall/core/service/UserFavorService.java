package com.jbb.mall.core.service;

import com.jbb.mall.core.dao.domain.Product;
import com.jbb.mall.core.dao.domain.UserFavor;

import java.util.List;

/**
 * @author ThinkPad
 * @date 2019/01/21
 */
public interface UserFavorService {

    // 用户收藏产品
    void saveUserFavor(UserFavor userFavor);

    // 用户解除收藏产品
    void updateUserFavor(Integer userId, Integer productId);

    // 用户收藏是否存在
    boolean checkUserFavor(Integer userId, Integer productId);

    //获取收藏列表
    List<Product> getUserFavor(int userId, String type);
}
