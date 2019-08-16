package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.H5Merchant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商家相关Mapper接口
 *
 * @author wyq
 * @date 2018/7/20 19:18
 */
public interface H5MerchantMapper {

    void insertH5Merchant(H5Merchant h5Merchant);

    List<H5Merchant> selectH5Merchants();

    void updateH5Merchant(H5Merchant h5Merchant);

    H5Merchant selectH5merchantById(@Param(value = "merchantId") Integer merchantId);

    H5Merchant selectH5merchantByChannelCode(@Param(value = "channelCode") String channelCode);
}
