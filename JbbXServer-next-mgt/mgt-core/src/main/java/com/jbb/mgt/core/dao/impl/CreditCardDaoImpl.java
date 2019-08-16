package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.CreditCardDao;
import com.jbb.mgt.core.dao.mapper.CreditCardMapper;
import com.jbb.mgt.core.domain.City;
import com.jbb.mgt.core.domain.CreditCard;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */

@Repository("CreditCardDao")
public class CreditCardDaoImpl implements CreditCardDao {

    @Autowired
    private CreditCardMapper mapper;

    @Override
    public List<CreditCard> selectCreditCardByCategoryId(String cityName, Integer categoryId) {
        return mapper.selectCreditCardByCategoryId(cityName, categoryId);
    }

    @Override
    public List<CreditCard> selectCreditCardByCity(String cityName, Integer categoryId, Boolean isDeleted,
        Timestamp startDate, Timestamp endDate) {
        return mapper.selectCreditCardByCity(cityName, categoryId, isDeleted, startDate, endDate);
    }

    @Override
    public CreditCard selectCreditCardById(Integer creditId) {
        return mapper.selectCreditCardById(creditId);
    }

    @Override
    public void insertCreditCard(CreditCard creditCard) {
        mapper.insertCreditCard(creditCard);
    }

    @Override
    public void updateCreditCard(CreditCard creditCard) {
        mapper.updateCreditCard(creditCard);
    }

    @Override
    public void insertCreditCategorieCard(Integer categoryId, Integer creditId) {
        mapper.insertCreditCategorieCard(categoryId, creditId);
    }

    @Override
    public void deleteCreditCategorieCard(Integer creditId) {
        mapper.deleteCreditCategorieCard(creditId);
    }

    @Override
    public void insertCreditCardArea(Integer zoneId, Integer creditId) {
        mapper.insertCreditCardArea(zoneId, creditId);
    }

    @Override
    public void deleteCreditCardArea(Integer creditId) {
        mapper.deleteCreditCardArea(creditId);
    }

    @Override
    public List<City> selectCity() {
        return mapper.selectCity();
    }

    @Override
    public void deleteCreditCard(Integer creditId) {
        mapper.deleteCreditCard(creditId);
    }

}
