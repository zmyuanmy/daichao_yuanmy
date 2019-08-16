package com.jbb.mgt.core.service.impl;

import java.util.List;

import com.jbb.mgt.core.dao.ChannelAppFunnelDao;
import com.jbb.mgt.core.domain.ChannelAppFunnel;
import com.jbb.mgt.core.service.ChannelAppFunnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ChannelAppFunnelServiceImpl")
public class ChannelAppFunnelServiceImpl implements ChannelAppFunnelService {
    @Autowired
    private ChannelAppFunnelDao channelAppFunnelDao;

    @Override
    public List<ChannelAppFunnel> getChannelAppFunnelDaily(String[] channelCodes, String groupName, String startDate,
        String endDate, Integer salesId) {
        return channelAppFunnelDao.getChannelAppFunnelDaily(channelCodes, groupName, startDate, endDate, salesId);
    }

    @Override
    public List<ChannelAppFunnel> getChannelAppFunnelCompare(String startDate, String endDate, Integer salesId) {
        return channelAppFunnelDao.getChannelAppFunnelCompare(startDate, endDate, salesId);
    }

}
