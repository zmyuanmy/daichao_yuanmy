package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.ChannelFunnel;

public interface ChannelFunnelService {

    List<ChannelFunnel> getChannelFunnelDaily(String[] channelCodes, Integer uType, Integer h5Type, Boolean isDelegate,
        String startDate, String endDate, Integer salesId, Integer mode);

    List<ChannelFunnel> getChannelFunnelCompare(Integer uType, Integer h5Type, Boolean isDelegate, String startDate,
        String endDate, Integer salesId, Integer mode);

    ChannelFunnel getChannelFunnelCompareTotal(Integer uType, String startDate, String endDate, Integer salesId,
        Integer mode);

}
