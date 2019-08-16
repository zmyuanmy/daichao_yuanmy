package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.StatisticChannelDailyDao;
import com.jbb.mgt.core.dao.mapper.StatisticChannelDailyMapper;
import com.jbb.mgt.core.domain.StatisticChannelDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository("StatisticChannelDailyDao")
public class StatisticChannelDailyDaoImpl implements StatisticChannelDailyDao {
    @Autowired
    private StatisticChannelDailyMapper mapper;

    @Override
    public List<StatisticChannelDaily> getStatisticChannelDailyByChannelCode(String channelCode, Timestamp startDate,
        Timestamp endDate, Integer userType, Integer orgId) {

        return mapper.getStatisticChannelDailyByChannelCode(channelCode, startDate, endDate, userType, orgId);
    }

    @Override
    public void insertStatisticChannelDaily(List<StatisticChannelDaily> lists) {
        mapper.insertStatisticChannelDaily(lists);

    }

    @Override
    public List<StatisticChannelDaily> getChannelDailysByChannelCode(String[] channelCodes, String startDate,
        String endDate, Integer userType) {
        return mapper.getChannelDailysByChannelCode(channelCodes, startDate, endDate, userType);
    }

    @Override
    public List<StatisticChannelDaily> getChannelsCompare(String startDate, String endDate, Integer userType) {
        return mapper.getChannelsCompare(startDate, endDate, userType);
    }

}
