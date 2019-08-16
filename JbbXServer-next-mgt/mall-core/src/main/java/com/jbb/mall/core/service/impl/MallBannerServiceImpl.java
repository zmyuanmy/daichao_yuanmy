package com.jbb.mall.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mall.core.dao.MallBannerDao;
import com.jbb.mall.core.dao.domain.MallBannersVo;
import com.jbb.mall.core.dao.domain.MallCategoriesVo;
import com.jbb.mall.core.service.MallBannerService;

/**
 * @author ThinkPad
 * @date 2019/01/17
 */
@Service("MallBannerService")
public class MallBannerServiceImpl implements MallBannerService {

    @Autowired
    private MallBannerDao mallBannerDao;

    @Override
    public String getMallBanner() {

        return mallBannerDao.getMallBanner();
    }

	
	@Override
	public List<MallBannersVo> selectMallBannerVoList(String type) {
		// TODO Auto-generated method stub
		return mallBannerDao.selectMallBannerVoList(type);
	}

}
