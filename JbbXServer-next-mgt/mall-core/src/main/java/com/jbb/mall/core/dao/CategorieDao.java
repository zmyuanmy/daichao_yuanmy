package com.jbb.mall.core.dao;

import com.jbb.mall.core.dao.domain.Classification;

import java.util.List;

/**
 * @author wyq
 * @date 2019/1/17 20:55
 */
public interface CategorieDao {
    List<Classification> selectClassificationsByclassification(String productType, String[] classification,
        boolean getDetail);
}
