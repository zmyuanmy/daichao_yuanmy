package com.jbb.mall.core.dao;

import java.util.List;

import com.jbb.mall.core.dao.domain.MallBannersVo;


/**
 * @author ThinkPad
 * @date 2019/01/17
 */

public interface MallBannerDao {
    
    String getMallBanner();
    
    List <MallBannersVo> selectMallBannerVoList(String type);

}
