package com.jbb.mall.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mall.core.dao.ProductDao;
import com.jbb.mall.core.dao.domain.Product;
import com.jbb.mall.core.dao.mapper.ProductMapper;

/**
 * @author ThinkPad
 * @date 2019/01/18
 */
@Repository("ProductDao")
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> selectProductList(String type, Integer[] categoryId, Integer userId) {
        return mapper.selectProductList(type, categoryId, userId);
    }

}
