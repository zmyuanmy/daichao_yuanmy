package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.ChannelFunnelFilterDao;
import com.jbb.mgt.core.domain.ChannelFunnelCondition;
import com.jbb.mgt.core.domain.ChannelFunnelFilter;
import com.jbb.mgt.core.service.ChannelFunnelFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service("ChannelFunnelFilterService")
public class ChannelFunnelFilterServiceImpl implements ChannelFunnelFilterService {

    @Autowired
    private ChannelFunnelFilterDao channelFunnelFilterDao;

    @Override
    public List<ChannelFunnelFilter> getChannelFunnelFilter(Integer filterId) {
        return channelFunnelFilterDao.selectChannelFunnelFilter(filterId);
    }

    @Override
    public void saveChannelFunnelFilter(ChannelFunnelFilter channelFunnelFilter) {
        channelFunnelFilterDao.saveChannelFunnelFilter(channelFunnelFilter);
    }

    @Override
    public void deleteChannelFunnelFilter(Integer filterId) {
        channelFunnelFilterDao.deleteChannelFunnelFilter(filterId);
    }

    @Override
    public void insertChannelFunnelCondition(Integer filterId, List<ChannelFunnelCondition> conditions) {
        channelFunnelFilterDao.insertChannelFunnelCondition(filterId, conditions);
    }

    @Override
    public void deleteChannelFunnelCondition(Integer filterId) {
        channelFunnelFilterDao.deleteChannelFunnelCondition(filterId);
    }
}
