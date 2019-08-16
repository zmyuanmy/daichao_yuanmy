package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.ChannelFunnelCondition;
import com.jbb.mgt.core.domain.ChannelFunnelFilter;
import org.apache.ibatis.annotations.Param;

public interface ChannelFunnelFilterDao {

    // 查询规则
    List<ChannelFunnelFilter> selectChannelFunnelFilter(Integer filterId);

    // 新增修改规则
    void saveChannelFunnelFilter(ChannelFunnelFilter channelFunnelFilter);

    // 删除规则
    void deleteChannelFunnelFilter(Integer filterId);

    // 新增条件
    void insertChannelFunnelCondition(Integer filterId, List<ChannelFunnelCondition> conditions);

    // 删除条件
    void deleteChannelFunnelCondition(Integer filterId);

}
