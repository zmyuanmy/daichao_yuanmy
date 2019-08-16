package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.H5MerchantDao;
import com.jbb.mgt.core.dao.mapper.H5MerchantMapper;
import com.jbb.mgt.core.domain.H5Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商家相关Dao实现类
 *
 * @author wyq
 * @date 2018/7/20 19:17
 */
@Repository("H5MerchantDao")
public class H5MerchantDaoImpl implements H5MerchantDao {

    @Autowired
    private H5MerchantMapper mapper;

    @Override
    public void insertH5Merchant(H5Merchant h5Merchant) {
        mapper.insertH5Merchant(h5Merchant);
    }

    @Override
    public List<H5Merchant> selectH5Merchants() {
        return mapper.selectH5Merchants();
    }

    @Override
    public void updateH5Merchant(H5Merchant h5Merchant) {
        mapper.updateH5Merchant(h5Merchant);
    }

    @Override
    public H5Merchant selectH5merchantById(Integer merchantId) {
        return mapper.selectH5merchantById(merchantId);
    }

    @Override
    public H5Merchant selectH5merchantByChannelCode(String channelCode) {
        return mapper.selectH5merchantByChannelCode(channelCode);
    }
}
