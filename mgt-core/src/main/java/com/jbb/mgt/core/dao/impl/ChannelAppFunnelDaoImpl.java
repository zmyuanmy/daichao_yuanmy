package com.jbb.mgt.core.dao.impl;

import java.util.List;

import com.jbb.mgt.core.dao.ChannelAppFunnelDao;
import com.jbb.mgt.core.dao.mapper.ChannelAppFunnelMapper;
import com.jbb.mgt.core.domain.ChannelAppFunnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("ChannelAppFunnelDaoImpl")
public class ChannelAppFunnelDaoImpl implements ChannelAppFunnelDao {
    @Autowired
    private ChannelAppFunnelMapper mapper;

    @Override
    public List<ChannelAppFunnel> getChannelAppFunnelDaily(String[] channelCodes, String groupName, String startDate,
        String endDate, Integer salesId) {
        return mapper.getChannelAppFunnelDaily(channelCodes, groupName, startDate, endDate, salesId);
    }

    @Override
    public List<ChannelAppFunnel> getChannelAppFunnelCompare(String startDate, String endDate, Integer salesId) {
        return mapper.getChannelAppFunnelCompare(startDate, endDate, salesId);
    }

}
