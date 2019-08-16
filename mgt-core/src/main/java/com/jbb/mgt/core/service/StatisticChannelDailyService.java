package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.StatisticChannelDaily;

import java.sql.Timestamp;
import java.util.List;

public interface StatisticChannelDailyService {
    List<StatisticChannelDaily> getStatisticChannelDailyByChannelCode(String channelCode, Timestamp startDate,
        Timestamp endDate, Integer userType, Integer orgId);

    void insertStatisticChannelDaily(List<StatisticChannelDaily> lists);

    List<StatisticChannelDaily> getChannelDailysByChannelCode(String[] channelCodes, String startDate, String endDate,
        Integer userType);

    List<StatisticChannelDaily> getChannelsCompare(String startDate, String endDate, Integer userType);
}
