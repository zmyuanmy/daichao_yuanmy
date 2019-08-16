package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.CreditCardCategorieDao;
import com.jbb.mgt.core.domain.CreditCardCategorie;
import com.jbb.mgt.core.service.CreditCardCategorieService;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */
@Service("CreditCardCategorieService")
public class CreditCardCategorieServiceImpl implements CreditCardCategorieService {

    @Autowired
    private CreditCardCategorieDao creditCardCategorieDao;

    @Override
    public List<CreditCardCategorie> getCreditCardCategorie() {
        return creditCardCategorieDao.selectCreditCardCategorie();
    }

    @Override
    public CreditCardCategorie getCreditCardCategorieById(Integer categoryId) {
        return creditCardCategorieDao.selectCreditCardCategorieById(categoryId);
    }

    @Override
    public void saveCreditCardCategorie(CreditCardCategorie cardCategorie) {
        creditCardCategorieDao.saveCreditCardCategorie(cardCategorie);
    }

}
