package com.jbb.mgt.core.dao.mapper;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.ChannelStatisticGroupName;
import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelStatisticDaily;
import com.jbb.mgt.core.domain.Channels;

public interface ChannelStatisticDailyMapper {

    void insertChannelsStatisticDaily(@Param(value = "lists") List<ChannelStatisticDaily> lists);

    List<Channels> getstatisticByDate(@Param(value = "date") Date date, @Param("groupName") String groupName,
        @Param("salesId") Integer salesId);

    List<ChannelStatisticGroupName> getStatisticByChannelCode(@Param(value = "channelCode") String channelCode,
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
        @Param(value = "flag") boolean flag, @Param(value = "groupName") String groupName,
        @Param("salesId") Integer salesId);

    List<ChannelStatisticDaily> getChannelStatisticDaily(@Param(value = "statisticDate") String statisticDate,
        @Param(value = "channelCode") String channelCode, @Param(value = "detail") boolean detail,
        @Param(value = "status") boolean status);

    List<Channel> selectChannelStatisticToday(@Param(value = "orgId") Integer orgId,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate,
        @Param(value = "channelCode") String channelCode, @Param(value = "groupName") String groupName,
        @Param("salesId") Integer salesId);

    void updateChannelStatisticDaily(ChannelStatisticDaily csd);

    void updateAllChannelStatisticDaily(@Param(value = "channelCode") String channelCode,
        @Param(value = "change") int change, @Param(value = "statisticDate") java.util.Date statisticDate);

    void saveChannelStatisticDaily(ChannelStatisticDaily csd);

    List<ChannelStatisticDaily> selectChannelStatistic(@Param(value = "channelCode") String channelCode,
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
        @Param(value = "now") String now);

    void updateChannelStatisticDailyList(@Param("list") List<ChannelStatisticDaily> list);
}
