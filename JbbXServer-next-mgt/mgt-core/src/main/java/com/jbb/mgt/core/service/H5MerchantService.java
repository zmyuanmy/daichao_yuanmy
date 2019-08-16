package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.H5Merchant;

import java.util.List;

/**
 * 商家相关service接口
 *
 * @author wyq
 * @date 2018/7/20 19:12
 */
public interface H5MerchantService {

    void insertH5Merchant(H5Merchant H5Merchant);

    List<H5Merchant> selectH5Merchants();

    void updateH5Merchant(H5Merchant H5Merchant);

    H5Merchant selectH5merchantById(Integer merchantId);

    H5Merchant selectH5merchantByChannelCode(String channelCode);
}
