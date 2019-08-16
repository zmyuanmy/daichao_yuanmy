package com.jbb.mall.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mall.core.dao.domain.MallBannersVo;


/**
 * @author ThinkPad
 * @date 2019/01/17
 */
public interface MallBannerMapper {

    String getMallBanner();
    
    List <MallBannersVo> selectMallBannerVoList(@Param("type")String type);
}
