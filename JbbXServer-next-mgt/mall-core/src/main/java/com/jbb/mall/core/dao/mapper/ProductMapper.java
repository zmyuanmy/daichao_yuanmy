package com.jbb.mall.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mall.core.dao.domain.Product;

/**
 * @author ThinkPad
 * @date 2019/01/18
 */
public interface ProductMapper {

    // 获取商品列表
    List<Product> selectProductList(@Param("type") String type, @Param("categoryIds") Integer[] categoryIds,
        @Param("userId") Integer userId);

}
