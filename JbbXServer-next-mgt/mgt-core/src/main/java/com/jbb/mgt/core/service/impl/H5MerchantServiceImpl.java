package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.H5MerchantDao;
import com.jbb.mgt.core.domain.H5Merchant;
import com.jbb.mgt.core.service.H5MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商家相关service实现类
 *
 * @author wyq
 * @date 2018/7/20 19:13
 */
@Service("H5MerchantService")
public class H5MerchantServiceImpl implements H5MerchantService {

    @Autowired
    private H5MerchantDao dao;

    @Override
    public void insertH5Merchant(H5Merchant H5Merchant) {
        dao.insertH5Merchant(H5Merchant);
    }

    @Override
    public List<H5Merchant> selectH5Merchants() {
        return dao.selectH5Merchants();
    }

    @Override
    public void updateH5Merchant(H5Merchant H5Merchant) {
        dao.updateH5Merchant(H5Merchant);
    }

    @Override
    public H5Merchant selectH5merchantById(Integer merchantId) {
        return dao.selectH5merchantById(merchantId);
    }

    @Override
    public H5Merchant selectH5merchantByChannelCode(String channelCode) {
        return dao.selectH5merchantByChannelCode(channelCode);
    }
}
