package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import com.jbb.mgt.core.domain.ChannelAppFunnel;
import org.apache.ibatis.annotations.Param;

public interface ChannelAppFunnelMapper {

    List<ChannelAppFunnel> getChannelAppFunnelDaily(@Param(value = "channelCodes") String[] channelCodes,
        @Param(value = "groupName") String groupName, @Param(value = "startDate") String startDate,
        @Param(value = "endDate") String endDate, @Param(value = "salesId") Integer salesId);

    List<ChannelAppFunnel> getChannelAppFunnelCompare(@Param(value = "startDate") String startDate,
        @Param(value = "endDate") String endDate, @Param(value = "salesId") Integer salesId);

}
