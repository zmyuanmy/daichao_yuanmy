package com.jbb.mall.core.service.impl;

import com.jbb.mall.core.dao.CategorieDao;
import com.jbb.mall.core.dao.domain.Classification;
import com.jbb.mall.core.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wyq
 * @date 2019/1/17 20:54
 */
@Service("CategorieService")
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    private CategorieDao dao;

    @Override
    public List<Classification> getClassificationsByclassification(String productType, String[] classification, boolean getDetail) {
        return dao.selectClassificationsByclassification(productType, classification, getDetail);
    }
}
