package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.CreditCardDao;
import com.jbb.mgt.core.domain.City;
import com.jbb.mgt.core.domain.CreditCard;
import com.jbb.mgt.core.service.CreditCardService;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */

@Service("CreditCardService")
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardDao creditCardDao;

    @Override
    public List<CreditCard> getCreditCardByCategoryId(String cityName, Integer categoryId) {
        return creditCardDao.selectCreditCardByCategoryId(cityName, categoryId);
    }

    @Override
    public List<CreditCard> getCreditCardByCity(String cityName, Integer categoryId, Boolean isDeleted,
        Timestamp startDate, Timestamp endDate) {
        return creditCardDao.selectCreditCardByCity(cityName, categoryId, isDeleted, startDate, endDate);
    }

    @Override
    public CreditCard getCreditCardById(Integer creditId) {
        return creditCardDao.selectCreditCardById(creditId);
    }

    @Override
    public void insertCreditCard(CreditCard creditCard) {
        creditCardDao.insertCreditCard(creditCard);
    }

    @Override
    public void updateCreditCard(CreditCard creditCard) {
        creditCardDao.updateCreditCard(creditCard);
    }

    @Override
    public void insertCreditCategorieCard(Integer categoryId, Integer creditId) {
        creditCardDao.insertCreditCategorieCard(categoryId, creditId);
    }

    @Override
    public void deleteCreditCategorieCard(Integer creditId) {
        creditCardDao.deleteCreditCategorieCard(creditId);
    }

    @Override
    public void insertCreditCardArea(Integer zoneId, Integer creditId) {
        creditCardDao.insertCreditCardArea(zoneId, creditId);
    }

    @Override
    public void deleteCreditCardArea(Integer creditId) {
        creditCardDao.deleteCreditCardArea(creditId);
    }

    @Override
    public List<City> selectCity() {
        return creditCardDao.selectCity();
    }

    @Override
    public void deleteCreditCard(Integer creditId) {
        creditCardDao.deleteCreditCard(creditId);
        creditCardDao.deleteCreditCategorieCard(creditId);
    }

}
