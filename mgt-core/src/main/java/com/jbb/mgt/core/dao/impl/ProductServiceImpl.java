package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.ProductDao;
import com.jbb.mgt.core.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public int getProductCount(int userId, String productName) {
        return productDao.selectUserProductCount(userId, productName);
    }

    @Override
    public int getProductCountForUpdate(int userId, String productName) {
        return productDao.selectUserProductCountForUpdate(userId, productName);
    }

    @Override
    public void reduceProductCount(int userId, String productName) {
        int cnt = productDao.selectUserProductCount(userId, productName);
        if (cnt == 0) {
            return;
        }
        productDao.reduceUserProductCount(userId, productName);

    }

    @Override
    public void increaseUserProductCount(int userId, String productName) {
        productDao.increaseUserProductCount(userId, productName);

    }

}
