package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.CreditCardCategorieDao;
import com.jbb.mgt.core.dao.mapper.CreditCardCategorieMapper;
import com.jbb.mgt.core.domain.CreditCardCategorie;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */

@Repository("CreditCardCategorieDao")
public class CreditCardCategorieDaoImpl implements CreditCardCategorieDao {

    @Autowired
    private CreditCardCategorieMapper mapper;

    @Override
    public List<CreditCardCategorie> selectCreditCardCategorie() {
         return mapper.selectCreditCardCategorie();
    }

    @Override
    public CreditCardCategorie selectCreditCardCategorieById(Integer categoryId) {
         return mapper.selectCreditCardCategorieById(categoryId);
    }

    @Override
    public void saveCreditCardCategorie(CreditCardCategorie cardCategorie) {
        mapper.saveCreditCardCategorie(cardCategorie);
    }

}
