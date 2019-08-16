package com.jbb.mall.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mall.core.dao.MallProductsDao;
import com.jbb.mall.core.dao.domain.FlowerProduct;
import com.jbb.mall.core.dao.domain.ProductDetailVo;
import com.jbb.mall.core.service.MallProductsService;
@Service("MallProductsService")
public class MallProductsServiceImpl implements MallProductsService {

	@Autowired
	private MallProductsDao dao;
	
	@Override
	public List<FlowerProduct> selectFlowerProductList(String type,Integer userId) {
		
		return dao.selectFlowerProductList(type,userId);
	}

	@Override
	public ProductDetailVo selectProductDetail(String type, Integer productId,Integer userId) {
		
		return dao.selectProductDetail(type, productId,userId);
	}

    @Override
    public boolean checkProduct(String type, Integer productId) {
         return dao.checkProduct(type, productId);
    }

	
	@Override
	public List<String> selectProductImgList(Integer productId) {
		return dao.selectProductImgList(productId);
	}

}
