package com.jbb.mall.core.service;

import java.util.List;

import com.jbb.mall.core.dao.domain.MallCategoriesVo;

public interface MallCategoriesService {
	
	List<MallCategoriesVo> selectMallCategoriesVoList(String type);

}
