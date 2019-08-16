package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.ChannelAccountInfo;

/**
 * 渠道账号信息维护Service接口
 *
 * @author wyq
 * @date 2018/7/23 10:13
 */
public interface ChannelAccountInfoService {

    void insertChannelAccountInfo(ChannelAccountInfo ChannelAccountInfo);

    ChannelAccountInfo selectChannelAccountInfo(String channelCode);
}
