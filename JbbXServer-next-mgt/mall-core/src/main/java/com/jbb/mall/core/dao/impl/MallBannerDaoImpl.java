package com.jbb.mall.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mall.core.dao.MallBannerDao;
import com.jbb.mall.core.dao.domain.MallBannersVo;
import com.jbb.mall.core.dao.domain.MallCategoriesVo;
import com.jbb.mall.core.dao.mapper.MallBannerMapper;

/**
 * @author ThinkPad
 * @date 2019/01/17
 */
@Repository("MallBannerDao")
public class MallBannerDaoImpl implements MallBannerDao {

    @Autowired
    private MallBannerMapper mallBannerMapper;

    @Override
    public String getMallBanner() {
        return mallBannerMapper.getMallBanner();
    }

	

	@Override
	public List<MallBannersVo> selectMallBannerVoList(String type) {
		// TODO Auto-generated method stub
		return mallBannerMapper.selectMallBannerVoList(type);
	}

}
