package com.jbb.mall.core.dao.mapper;

import com.jbb.mall.core.dao.domain.Classification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyq
 * @date 2019/1/18 10:05
 */
public interface CategorieMapper {
    List<Classification> selectClassificationsByclassification(@Param("productType") String productType,
        @Param("classification") String[] classification, @Param("getDetail") boolean getDetail);
}