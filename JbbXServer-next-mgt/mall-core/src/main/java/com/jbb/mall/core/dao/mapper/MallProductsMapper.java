package com.jbb.mall.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mall.core.dao.domain.FlowerProduct;
import com.jbb.mall.core.dao.domain.ProductDetailVo;

public interface MallProductsMapper {

    List<FlowerProduct> selectFlowerProductList(@Param("type") String type, @Param("userId") Integer userId);

    ProductDetailVo selectProductDetail(@Param("type") String type, @Param("productId") Integer productId,
        @Param("userId") Integer userId);

    int checkProduct(@Param("type") String type, @Param("productId") Integer productId);
    
    List<String> selectProductImgList(@Param("productId") Integer productId);
}
