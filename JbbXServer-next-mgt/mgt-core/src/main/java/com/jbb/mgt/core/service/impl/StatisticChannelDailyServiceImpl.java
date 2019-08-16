package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.StatisticChannelDailyDao;
import com.jbb.mgt.core.domain.StatisticChannelDaily;
import com.jbb.mgt.core.service.StatisticChannelDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Service("StatisticChannelDailyService")
public class StatisticChannelDailyServiceImpl implements StatisticChannelDailyService {
    @Autowired
    private StatisticChannelDailyDao dao;

    @Override
    public List<StatisticChannelDaily> getStatisticChannelDailyByChannelCode(String channelCode, Timestamp startDate,
        Timestamp endDate, Integer userType, Integer orgId) {

        Date dateFormat = new Date(startDate.getTime());

        List<StatisticChannelDaily> list
            = dao.getStatisticChannelDailyByChannelCode(channelCode, startDate, endDate, userType, orgId);
        for (StatisticChannelDaily statisticChannelDaily : list) {
            statisticChannelDaily.setUserType(userType);
            statisticChannelDaily.setStatisticDate(dateFormat);

        }
        return list;
    }

    @Override
    public void insertStatisticChannelDaily(List<StatisticChannelDaily> lists) {
        dao.insertStatisticChannelDaily(lists);

    }

    @Override
    public List<StatisticChannelDaily> getChannelDailysByChannelCode(String[] channelCodes, String startDate,
        String endDate, Integer userType) {

        return dao.getChannelDailysByChannelCode(channelCodes, startDate, endDate, userType);
    }

    @Override
    public List<StatisticChannelDaily> getChannelsCompare(String startDate, String endDate, Integer userType) {
        return dao.getChannelsCompare(startDate, endDate, userType);
    }

}
