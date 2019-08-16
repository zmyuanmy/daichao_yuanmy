package com.jbb.mall.core.dao;

import java.util.List;

import com.jbb.mall.core.dao.domain.Product;

/**
 * @author ThinkPad
 * @date 2019/01/18
 */
public interface ProductDao {

    // 获取商品列表
    List<Product> selectProductList(String type, Integer[] categoryId, Integer userId);
    
}
