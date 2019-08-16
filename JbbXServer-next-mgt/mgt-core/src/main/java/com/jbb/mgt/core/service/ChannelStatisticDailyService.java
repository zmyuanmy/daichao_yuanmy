package com.jbb.mgt.core.service;

import java.text.ParseException;
import java.util.List;

import com.jbb.mgt.core.domain.ChannelStatisticDaily;
import com.jbb.mgt.core.domain.ChannelStatisticGroupName;
import com.jbb.mgt.core.domain.Channels;

public interface ChannelStatisticDailyService {
    void saveChannelStatisticDaily(ChannelStatisticDaily csd);

    ChannelStatisticDaily getChannelStatisticDaily(int orgId, boolean detail);

    void updateChannelStatisticDaily(ChannelStatisticDaily csd, boolean updateFlag, int change);

    List<Channels> getChannelStatisticDailys(String statisticDate, String channelCode, String groupName,
        Integer salesId) throws ParseException;

    ChannelStatisticDaily getChannelStatisticDaily(String statisticDate, String channelCode, boolean detail,
        boolean status);

    void insertChannelsStatisticDaily(List<ChannelStatisticDaily> lists);

    List<Channels> getstatisticByDate(String statisticDate, String groupName, Integer salesId) throws ParseException;

    List<ChannelStatisticGroupName> getStatisticByChannelCode(String channelCode, String startDate, String endDate,
        String groupName, Integer salesId) throws ParseException;

    List<ChannelStatisticDaily> selectChannelStatistic(String channelCode, String startDate, String endDate);

    void updateChannelStatisticDailyList(List<ChannelStatisticDaily> list);
}
