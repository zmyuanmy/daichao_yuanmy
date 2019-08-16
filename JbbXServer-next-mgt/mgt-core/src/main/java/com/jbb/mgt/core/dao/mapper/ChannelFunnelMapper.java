package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.ChannelFunnel;

public interface ChannelFunnelMapper {

    List<ChannelFunnel> getChannelFunnelDaily(@Param(value = "channelCodes") String[] channelCodes,
        @Param(value = "uType") Integer uType, @Param(value = "h5Type") Integer h5Type,
        @Param(value = "isDelegate") Boolean isDelegate, @Param(value = "startDate") String startDate,
        @Param(value = "endDate") String endDate, @Param(value = "salesId") Integer salesId,
        @Param("mode") Integer mode, @Param("flag") boolean flag);

    List<ChannelFunnel> getChannelFunnelCompare(@Param(value = "uType") Integer uType,
        @Param(value = "h5Type") Integer h5Type, @Param(value = "isDelegate") Boolean isDelegate,
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
        @Param(value = "salesId") Integer salesId, @Param("mode") Integer mode, @Param("flag") boolean flag);

    ChannelFunnel getChannelFunnelCompareTotal(@Param(value = "uType") Integer uType,
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
        @Param(value = "salesId") Integer salesId, @Param("mode") Integer mode, @Param("flag") boolean flag);

}
