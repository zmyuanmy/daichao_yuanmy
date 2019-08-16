package com.jbb.mgt.core.dao.impl;

import java.util.List;

import com.jbb.mgt.core.domain.ChannelFunnelFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.ChannelFunnelDao;
import com.jbb.mgt.core.dao.mapper.ChannelFunnelMapper;
import com.jbb.mgt.core.domain.ChannelFunnel;

@Repository("ChannelFunnelDao")
public class ChannelFunnelDaoImpl implements ChannelFunnelDao {
    @Autowired
    private ChannelFunnelMapper mapper;

    @Override
    public List<ChannelFunnel> getChannelFunnelDaily(String[] channelCodes, Integer uType, Integer h5Type,
        Boolean isDelegate, String startDate, String endDate, Integer salesId, Integer mode, boolean flag) {
        return mapper.getChannelFunnelDaily(channelCodes, uType, h5Type, isDelegate, startDate, endDate, salesId, mode,
            flag);
    }

    @Override
    public List<ChannelFunnel> getChannelFunnelCompare(Integer uType, Integer h5Type, Boolean isDelegate,
        String startDate, String endDate, Integer salesId, Integer mode, boolean flag) {
        return mapper.getChannelFunnelCompare(uType, h5Type, isDelegate, startDate, endDate, salesId, mode, flag);
    }

    @Override
    public ChannelFunnel getChannelFunnelCompareTotal(Integer uType, String startDate, String endDate,
        Integer salesId, Integer mode, boolean flag) {
        return mapper.getChannelFunnelCompareTotal(uType, startDate, endDate, salesId, mode, flag);
    }

}
