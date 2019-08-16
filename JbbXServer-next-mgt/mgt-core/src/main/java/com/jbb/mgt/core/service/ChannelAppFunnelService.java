package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.ChannelAppFunnel;

public interface ChannelAppFunnelService {

    List<ChannelAppFunnel> getChannelAppFunnelDaily(String[] channelCodes, String groupName, String startDate,
        String endDate, Integer salesId);

    List<ChannelAppFunnel> getChannelAppFunnelCompare(String startDate, String endDate, Integer salesId);

}
