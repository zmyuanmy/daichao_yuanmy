package com.jbb.mall.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mall.core.dao.domain.MallCategoriesVo;

public interface MallCategoriesMapper {

	List<MallCategoriesVo> selectMallCategoriesVoList(@Param("type")String type);
}
