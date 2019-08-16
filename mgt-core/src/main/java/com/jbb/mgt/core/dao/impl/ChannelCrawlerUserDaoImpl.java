package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.ChannelCrawlerUserDao;
import com.jbb.mgt.core.dao.mapper.ChannelCrawlerUserMapper;
import com.jbb.mgt.core.domain.ChannelCrawlerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository("ChannelCrawlerUserDao")
public class ChannelCrawlerUserDaoImpl implements ChannelCrawlerUserDao {

    @Autowired
    private ChannelCrawlerUserMapper channelCrawlerUserMapper;

    @Override
    public List<ChannelCrawlerUser> selectCrawlUsers(String channelCode, Timestamp startDate, Timestamp endDate) {
        return channelCrawlerUserMapper.selectCrawlUsers(channelCode, startDate, endDate);
    }

    @Override
    public void insertChannelsStatisticDaily(List<ChannelCrawlerUser> lists) {
        channelCrawlerUserMapper.insertChannelsStatisticDaily(lists);
    }

    @Override
    public int countCrawlUsers(String channelCode, Timestamp startDate, Timestamp endDate) {
        return channelCrawlerUserMapper.countCrawlUsers(channelCode, startDate, endDate);
    }

    @Override
    public ChannelCrawlerUser getLastCrawlUser(String channelCode) {
        return channelCrawlerUserMapper.getLastCrawlUser(channelCode);
    }

    @Override
    public boolean selectExistCrawlUser(String channelCode, String uId) {
        return channelCrawlerUserMapper.selectExistCrawlUser(channelCode, uId) > 0;
    }
}
