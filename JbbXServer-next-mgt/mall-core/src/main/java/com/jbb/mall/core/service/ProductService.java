package com.jbb.mall.core.service;

import java.util.List;

import com.jbb.mall.core.dao.domain.Product;

/**
 * @author ThinkPad
 * @date 2019/01/18
 */
public interface ProductService {

    // 获取商品列表
    List<Product> getProductList(String type, Integer[] categoryId, Integer userId);

}
