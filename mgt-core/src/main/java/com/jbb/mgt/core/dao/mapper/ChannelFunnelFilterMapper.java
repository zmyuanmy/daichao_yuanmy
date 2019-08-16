package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.ChannelFunnelCondition;
import com.jbb.mgt.core.domain.ChannelFunnelFilter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChannelFunnelFilterMapper {

    // 查询规则
    List<ChannelFunnelFilter> selectChannelFunnelFilter(@Param("filterId") Integer filterId);

    // 新增修改规则
    void saveChannelFunnelFilter(ChannelFunnelFilter channelFunnelFilter);

    // 删除规则
    void deleteChannelFunnelFilter(@Param("filterId") Integer filterId);

    // 新增条件
    void insertChannelFunnelCondition(@Param("filterId") Integer filterId,
        @Param("conditions") List<ChannelFunnelCondition> conditions);

    // 删除条件
    void deleteChannelFunnelCondition(@Param("filterId") Integer filterId);

}
