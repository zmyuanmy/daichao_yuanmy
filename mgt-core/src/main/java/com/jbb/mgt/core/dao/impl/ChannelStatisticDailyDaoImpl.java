package com.jbb.mgt.core.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import com.jbb.mgt.core.domain.ChannelStatisticGroupName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.ChannelStatisticDailyDao;
import com.jbb.mgt.core.dao.mapper.ChannelStatisticDailyMapper;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelStatisticDaily;
import com.jbb.mgt.core.domain.Channels;

@Repository("ChannelStatisticDailyDao")
public class ChannelStatisticDailyDaoImpl implements ChannelStatisticDailyDao {
    @Autowired
    private ChannelStatisticDailyMapper mapper;

    @Override
    public void insertChannelsStatisticDaily(List<ChannelStatisticDaily> lists) {
        mapper.insertChannelsStatisticDaily(lists);
    }

    @Override
    public List<Channels> getstatisticByDate(Date date, String groupName, Integer salesId) {
        return mapper.getstatisticByDate(date, groupName, salesId);
    }

    @Override
    public List<ChannelStatisticGroupName> getStatisticByChannelCode(String channelCode, String startDate,
        String endDate, boolean flag, String groupName, Integer salesId) {
        return mapper.getStatisticByChannelCode(channelCode, startDate, endDate, flag, groupName, salesId);
    }

    @Override
    public ChannelStatisticDaily getChannelStatisticDaily(String statisticDate, String channelCode, boolean detail,
        boolean status) {
        List<ChannelStatisticDaily> r = mapper.getChannelStatisticDaily(statisticDate, channelCode, detail, status);
        return r.isEmpty() ? null : r.get(0);
    }

    @Override
    public List<ChannelStatisticDaily> getChannelStatisticDaily(String statisticDate, boolean detail, boolean status) {
        return mapper.getChannelStatisticDaily(statisticDate, null, detail, status);
    }

    @Override
    public List<Channel> selectChannelStatisticToday(Integer orgId, Timestamp startDate, Timestamp endDate,
        String channelCode, String groupName, Integer salesId) {
        return mapper.selectChannelStatisticToday(orgId, startDate, endDate, channelCode, groupName, salesId);
    }

    @Override
    public void updateChannelStatisticDaily(ChannelStatisticDaily csd) {
        mapper.updateChannelStatisticDaily(csd);

    }

    @Override
    public void updateAllChannelStatisticDaily(String channelCode, int balance, Date statisticDate) {
        mapper.updateAllChannelStatisticDaily(channelCode, balance, statisticDate);

    }

    @Override
    public void updateAllChannelStatisticDaily(String channelCode, int change, java.util.Date statisticDate) {
        mapper.updateAllChannelStatisticDaily(channelCode, change, statisticDate);

    }

    @Override
    public void saveChannelStatisticDaily(ChannelStatisticDaily csd) {
        mapper.saveChannelStatisticDaily(csd);

    }

    @Override
    public List<ChannelStatisticDaily> selectChannelStatistic(String channelCode, String startDate, String endDate) {
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        return mapper.selectChannelStatistic(channelCode, startDate, endDate, now);
    }

    @Override
    public void updateChannelStatisticDailyList(List<ChannelStatisticDaily> list) {
        mapper.updateChannelStatisticDailyList(list);
    }
}
