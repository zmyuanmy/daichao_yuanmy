package com.jbb.mall.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mall.core.dao.MallCategoriesDao;
import com.jbb.mall.core.dao.domain.MallCategoriesVo;
import com.jbb.mall.core.dao.mapper.MallCategoriesMapper;
@Repository("MallCategoriesDao")
public class MallCategoriesDaoImpl implements MallCategoriesDao {

	@Autowired
	private MallCategoriesMapper mapper;
	
	@Override
	public List<MallCategoriesVo> selectMallCategoriesVoList(String type) {
		
		return mapper.selectMallCategoriesVoList(type);
	}

}
