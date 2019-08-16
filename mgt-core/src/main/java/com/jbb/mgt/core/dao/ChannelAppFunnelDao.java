package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.ChannelAppFunnel;

public interface ChannelAppFunnelDao {

    List<ChannelAppFunnel> getChannelAppFunnelDaily(String[] channelCodes, String groupName, String startDate,
        String endDate, Integer salesId);

    List<ChannelAppFunnel> getChannelAppFunnelCompare(String startDate, String endDate, Integer salesId);

}
