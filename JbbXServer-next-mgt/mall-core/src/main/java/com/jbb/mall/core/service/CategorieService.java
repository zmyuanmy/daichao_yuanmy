package com.jbb.mall.core.service;

import com.jbb.mall.core.dao.domain.Classification;

import java.util.List;

/**
 * @author wyq
 * @date 2019/1/17 20:54
 */
public interface CategorieService {

    List<Classification> getClassificationsByclassification(String productType, String[] classification,
        boolean getDetail);
}
