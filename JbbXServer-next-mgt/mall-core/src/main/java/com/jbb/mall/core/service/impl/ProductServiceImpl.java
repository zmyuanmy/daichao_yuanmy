package com.jbb.mall.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mall.core.dao.ProductDao;
import com.jbb.mall.core.dao.domain.Product;
import com.jbb.mall.core.service.ProductService;

/**
 * @author ThinkPad
 * @date 2019/01/18
 */

@Service("ProductService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProductList(String type, Integer[] categoryId, Integer userId) {
        return productDao.selectProductList(type, categoryId, userId);
    }

}
