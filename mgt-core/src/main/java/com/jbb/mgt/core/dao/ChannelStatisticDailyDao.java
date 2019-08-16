package com.jbb.mgt.core.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelStatisticDaily;
import com.jbb.mgt.core.domain.ChannelStatisticGroupName;
import com.jbb.mgt.core.domain.Channels;

public interface ChannelStatisticDailyDao {

    void insertChannelsStatisticDaily(List<ChannelStatisticDaily> lists);

    List<Channels> getstatisticByDate(Date date, String groupName, Integer salesId);

    List<ChannelStatisticGroupName> getStatisticByChannelCode(String channelCode, String startDate, String endDate,
        boolean flag, String groupName, Integer salesId);

    ChannelStatisticDaily getChannelStatisticDaily(String statisticDate, String channelCode, boolean detail,
        boolean status);
    
    List<ChannelStatisticDaily> getChannelStatisticDaily(String statisticDate,  boolean detail,
        boolean status);

    List<Channel> selectChannelStatisticToday(Integer orgId, Timestamp timestamp, Timestamp timestamp2,
        String channelCode, String groupName, Integer salesId);

    void updateChannelStatisticDaily(ChannelStatisticDaily csd);

    void updateAllChannelStatisticDaily(String channelCode, int change, java.util.Date statisticDate);

    void updateAllChannelStatisticDaily(String channelCode, int balance, Date statisticDate);

    void saveChannelStatisticDaily(ChannelStatisticDaily csd);

    List<ChannelStatisticDaily> selectChannelStatistic(String channelCode, String startDate, String endDate);

    void updateChannelStatisticDailyList(List<ChannelStatisticDaily> list);
}
