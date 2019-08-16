package com.jbb.mgt.core.dao.impl;

import java.util.List;

import com.jbb.mgt.core.domain.ChannelFunnelCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.ChannelFunnelFilterDao;
import com.jbb.mgt.core.dao.mapper.ChannelFunnelFilterMapper;
import com.jbb.mgt.core.domain.ChannelFunnelFilter;

@Repository("ChannelFunnelFilterDao")
public class ChannelFunnelFilterDaoImpl implements ChannelFunnelFilterDao {

    @Autowired
    private ChannelFunnelFilterMapper mapper;

    @Override
    public List<ChannelFunnelFilter> selectChannelFunnelFilter(Integer filterId) {
        return mapper.selectChannelFunnelFilter(filterId);
    }

    @Override
    public void saveChannelFunnelFilter(ChannelFunnelFilter channelFunnelFilter) {
        mapper.saveChannelFunnelFilter(channelFunnelFilter);
    }

    @Override
    public void deleteChannelFunnelFilter(Integer filterId) {
        mapper.deleteChannelFunnelFilter(filterId);
    }

    @Override
    public void insertChannelFunnelCondition(Integer filterId, List<ChannelFunnelCondition> conditions) {
        mapper.insertChannelFunnelCondition(filterId, conditions);
    }

    @Override
    public void deleteChannelFunnelCondition(Integer filterId) {
        mapper.deleteChannelFunnelCondition(filterId);
    }
}
