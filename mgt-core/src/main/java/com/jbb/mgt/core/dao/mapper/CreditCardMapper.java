package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.City;
import com.jbb.mgt.core.domain.CreditCard;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */
public interface CreditCardMapper {

    // 信用卡分类
    List<CreditCard> selectCreditCardByCategoryId(@Param("cityName") String cityName,
        @Param("categoryId") Integer categoryId);

    // 全部卡片信息
    List<CreditCard> selectCreditCardByCity(@Param("cityName") String cityName, @Param("categoryId") Integer categoryId,
        @Param("isDeleted") Boolean isDeleted, @Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate);

    // 单个卡片信息查询
    CreditCard selectCreditCardById(@Param("creditId") Integer creditId);

    // 新增卡片信息
    void insertCreditCard(CreditCard creditCard);

    // 编辑卡片信息
    void updateCreditCard(CreditCard creditCard);

    // 新增分类关联
    void insertCreditCategorieCard(@Param("categoryId") Integer categoryId, @Param("creditId") Integer creditId);

    // 删除分类关联
    void deleteCreditCategorieCard(@Param("creditId") Integer creditId);

    // 新增地区关联
    void insertCreditCardArea(@Param("zoneId") Integer zoneId, @Param("creditId") Integer creditId);

    // 删除地区关联
    void deleteCreditCardArea(@Param("creditId") Integer creditId);

    // 全部城市
    List<City> selectCity();

    // 信用卡下架
    void deleteCreditCard(@Param("creditId") Integer creditId);

}
