package com.jbb.mall.core.dao.mapper;

import com.jbb.mall.core.dao.domain.Product;
import org.apache.ibatis.annotations.Param;

import com.jbb.mall.core.dao.domain.UserFavor;

import java.util.List;

/**
 * @author ThinkPad
 * @date 2019/01/21
 */
public interface UserFavorMapper {

    // 用户收藏产品
    void saveUserFavor(UserFavor userFavor);

    // 用户解除收藏产品
    void updateUserFavor(@Param("userId") Integer userId, @Param("productId") Integer productId);

    // 用户收藏是否存在
    int checkUserFavor(@Param("userId") Integer userId, @Param("productId") Integer productId);

    //获取收藏列表
    List<Product> selectUserFavor(@Param("userId")int userId, @Param("type")String type);
}
