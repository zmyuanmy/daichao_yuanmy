package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.ChannelFunnelCondition;
import com.jbb.mgt.core.domain.ChannelFunnelFilter;

public interface ChannelFunnelFilterService {

    // 查询规则
    List<ChannelFunnelFilter> getChannelFunnelFilter(Integer filterId);

    // 新增修改规则
    void saveChannelFunnelFilter(ChannelFunnelFilter channelFunnelFilter);

    // 删除规则
    void deleteChannelFunnelFilter(Integer filterId);

    // 新增条件
    void insertChannelFunnelCondition(Integer filterId, List<ChannelFunnelCondition> conditions);

    // 删除条件
    void deleteChannelFunnelCondition(Integer filterId);

}
