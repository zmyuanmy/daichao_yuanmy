package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.ProductDao;
import com.jbb.mgt.core.dao.mapper.ProductMapper;

@Repository("ProductDao")
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean insertUserProductCount(int userId, String productName, int count) {
        return productMapper.insertUserProductCount(userId, productName, count) > 0;
    }

    @Override
    public int selectUserProductCountForUpdate(int userId, String productName) {
        Integer cnt = productMapper.selectUserProductCountForUpdate(userId, productName);
        return cnt == null ? 0 : cnt;
    }

    @Override
    public int selectUserProductCount(int userId, String productName) {
        Integer cnt = productMapper.selectUserProductCount(userId, productName);
        return cnt == null ? 0 : cnt;
    }

    @Override
    public boolean increaseUserProductCount(int userId, String productName) {
        return productMapper.increaseUserProductCount(userId, productName) > 0;
    }

    @Override
    public boolean reduceUserProductCount(int userId, String productName) {
        return productMapper.reduceUserProductCount(userId, productName) > 0;
    }

}
