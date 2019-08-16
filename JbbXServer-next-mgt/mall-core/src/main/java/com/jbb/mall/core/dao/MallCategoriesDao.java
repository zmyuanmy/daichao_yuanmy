package com.jbb.mall.core.dao;

import java.util.List;

import com.jbb.mall.core.dao.domain.MallCategoriesVo;

public interface MallCategoriesDao {

	List<MallCategoriesVo> selectMallCategoriesVoList(String type);
}
