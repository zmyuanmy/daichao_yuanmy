package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.StatisticChannelDaily;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface StatisticChannelDailyMapper {

    List<StatisticChannelDaily> getStatisticChannelDailyByChannelCode(@Param(value = "channelCode") String channelCode,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate,
        @Param(value = "userType") Integer userType, @Param(value = "orgId") Integer orgId);

    void insertStatisticChannelDaily(@Param(value = "lists") List<StatisticChannelDaily> lists);

    List<StatisticChannelDaily> getChannelDailysByChannelCode(@Param(value = "channelCodes") String[] channelCodes,
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
        @Param(value = "userType") Integer userType);

    List<StatisticChannelDaily> getChannelsCompare(@Param(value = "startDate") String startDate,
        @Param(value = "endDate") String endDate, @Param(value = "userType") Integer userType);

}
