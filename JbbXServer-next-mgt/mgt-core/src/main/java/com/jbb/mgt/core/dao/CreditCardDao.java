package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.City;
import com.jbb.mgt.core.domain.CreditCard;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */
public interface CreditCardDao {

    // 信用卡分类
    List<CreditCard> selectCreditCardByCategoryId(String cityName, Integer categoryId);

    // 全部卡片信息
    List<CreditCard> selectCreditCardByCity(String cityName, Integer categoryId, Boolean isDeleted, Timestamp startDate,
        Timestamp endDate);

    // 单个卡片信息查询
    CreditCard selectCreditCardById(Integer creditId);

    // 新增卡片信息
    void insertCreditCard(CreditCard creditCard);

    // 编辑卡片信息
    void updateCreditCard(CreditCard creditCard);

    // 新增分类关联
    void insertCreditCategorieCard(Integer categoryId, Integer creditId);

    // 删除分类关联
    void deleteCreditCategorieCard(Integer creditId);

    // 新增地区关联
    void insertCreditCardArea(Integer zoneId, Integer creditId);

    // 删除地区关联
    void deleteCreditCardArea(Integer creditId);

    // 全部城市
    List<City> selectCity();

    // 信用卡下架
    void deleteCreditCard(Integer creditId);

}
