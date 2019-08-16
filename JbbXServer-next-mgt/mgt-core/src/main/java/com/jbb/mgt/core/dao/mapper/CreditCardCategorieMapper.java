package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.CreditCardCategorie;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */
public interface CreditCardCategorieMapper {

    // 信用卡分类信息列表
    List<CreditCardCategorie> selectCreditCardCategorie();

    // 获取单个信用卡分类
    CreditCardCategorie selectCreditCardCategorieById(@Param("categoryId") Integer categoryId);

    // 新增、编辑信用卡分类信息
    void saveCreditCardCategorie(CreditCardCategorie cardCategorie);

}
