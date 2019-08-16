package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.UserLoginPuv;

public interface ChannelUserMapper {

    List<UserLoginPuv> getChannelUserDaily(@Param(value = "channelCode") String channelCode,
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate);

    List<UserLoginPuv> getChannelUserCompare(@Param(value = "statisticDate") String statisticDate);

}
