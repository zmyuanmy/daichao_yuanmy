package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.ChannelAccountInfo;

/**
 * 渠道账号信息维护Dao接口
 * 
 * @author wyq
 * @date 2018/7/23 10:18
 */
public interface ChannelAccountInfoDao {

    void insertChannelAccountInfo(ChannelAccountInfo ChannelAccountInfo);

    ChannelAccountInfo selectChannelAccountInfo(String channelCode);
}
