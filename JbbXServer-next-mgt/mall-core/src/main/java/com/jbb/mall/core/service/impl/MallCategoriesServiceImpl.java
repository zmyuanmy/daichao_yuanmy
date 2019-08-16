package com.jbb.mall.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mall.core.dao.MallCategoriesDao;
import com.jbb.mall.core.dao.domain.MallCategoriesVo;
import com.jbb.mall.core.service.MallCategoriesService;

@Service("MallCategoriesService")
public class MallCategoriesServiceImpl implements MallCategoriesService {
	@Autowired
	private MallCategoriesDao dao;
	@Override
	public List<MallCategoriesVo> selectMallCategoriesVoList(String type) {
		// TODO Auto-generated method stub
		return dao.selectMallCategoriesVoList(type);
	}

}
