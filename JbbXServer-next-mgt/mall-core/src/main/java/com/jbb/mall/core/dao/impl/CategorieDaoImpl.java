package com.jbb.mall.core.dao.impl;

import com.jbb.mall.core.dao.CategorieDao;
import com.jbb.mall.core.dao.domain.Classification;
import com.jbb.mall.core.dao.mapper.CategorieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wyq
 * @date 2019/1/18 10:04
 */
@Repository("CategorieDao")
public class CategorieDaoImpl implements CategorieDao {

    @Autowired
    private CategorieMapper mapper;

    @Override
    public List<Classification> selectClassificationsByclassification(String productType, String[] classification,
        boolean getDetail) {
        return mapper.selectClassificationsByclassification(productType, classification, getDetail);
    }
}
