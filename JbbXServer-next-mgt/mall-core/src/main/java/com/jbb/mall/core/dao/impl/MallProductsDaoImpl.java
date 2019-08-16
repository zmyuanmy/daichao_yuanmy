package com.jbb.mall.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mall.core.dao.MallProductsDao;
import com.jbb.mall.core.dao.domain.FlowerProduct;
import com.jbb.mall.core.dao.domain.ProductDetailVo;
import com.jbb.mall.core.dao.mapper.MallProductsMapper;

@Repository("MallProductsDao")
public class MallProductsDaoImpl implements MallProductsDao {

    @Autowired
    private MallProductsMapper mapper;

    @Override
    public List<FlowerProduct> selectFlowerProductList(String type, Integer userId) {
        return mapper.selectFlowerProductList(type, userId);
    }

    @Override
    public ProductDetailVo selectProductDetail(String type, Integer productId, Integer userId) {
        return mapper.selectProductDetail(type, productId, userId);
    }

    @Override
    public boolean checkProduct(String type, Integer productId) {
        return mapper.checkProduct(type, productId)>0;
    }

	
	@Override
	public List<String> selectProductImgList(Integer productId) {		
		return mapper.selectProductImgList(productId);
	}

}
