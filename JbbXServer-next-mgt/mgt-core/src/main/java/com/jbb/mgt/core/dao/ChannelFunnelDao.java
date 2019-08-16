package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.ChannelFunnel;

public interface ChannelFunnelDao {

    List<ChannelFunnel> getChannelFunnelDaily(String[] channelCodes, Integer uType, Integer h5Type, Boolean isDelegate,
        String startDate, String endDate, Integer salesId, Integer mode, boolean flag);

    List<ChannelFunnel> getChannelFunnelCompare(Integer uType, Integer h5Type, Boolean isDelegate, String startDate,
        String endDate, Integer salesId, Integer mode, boolean flag);

    ChannelFunnel getChannelFunnelCompareTotal(Integer uType, String startDate, String endDate, Integer salesId,
        Integer mode, boolean flag);

}
