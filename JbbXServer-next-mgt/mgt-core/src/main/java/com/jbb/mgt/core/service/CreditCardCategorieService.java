package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.CreditCardCategorie;

/**
 * @author xiaoeach
 * @date 2018/12/27
 */
public interface CreditCardCategorieService {

    // 信用卡分类信息列表
    List<CreditCardCategorie> getCreditCardCategorie();

    // 获取单个信用卡分类
    CreditCardCategorie getCreditCardCategorieById(Integer categoryId);

    // 新增、编辑信用卡分类信息
    void saveCreditCardCategorie(CreditCardCategorie cardCategorie);

}
