package com.jbb.mall.core.dao;

import java.util.List;

import com.jbb.mall.core.dao.domain.FlowerProduct;
import com.jbb.mall.core.dao.domain.ProductDetailVo;

public interface MallProductsDao {

	List<FlowerProduct> selectFlowerProductList(String type,Integer userId);
	
	ProductDetailVo selectProductDetail(String type, Integer productId,Integer userId);

	boolean checkProduct(String type, Integer productId);
	
	List<String> selectProductImgList(Integer productId);
}
