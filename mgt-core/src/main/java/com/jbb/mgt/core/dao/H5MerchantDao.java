package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.H5Merchant;

import java.util.List;

/**
 * 商家相关Dao接口
 *
 * @author wyq
 * @date 2018/7/20 19:16
 */
public interface H5MerchantDao {
    void insertH5Merchant(H5Merchant h5Merchant);

    List<H5Merchant> selectH5Merchants();

    void updateH5Merchant(H5Merchant h5Merchant);

    H5Merchant selectH5merchantById(Integer merchantId);

    H5Merchant selectH5merchantByChannelCode(String channelCode);
}
