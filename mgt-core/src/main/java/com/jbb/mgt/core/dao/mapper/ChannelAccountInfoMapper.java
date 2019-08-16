package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.ChannelAccountInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 渠道账号信息维护Mapper接口
 *
 * @author wyq
 * @date 2018/7/23 10:20
 */
public interface ChannelAccountInfoMapper {

    void insertChannelAccountInfo(ChannelAccountInfo ChannelAccountInfo);

    ChannelAccountInfo selectChannelAccountInfo(@Param(value = "channelCode") String channelCode);
}
