package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.ChannelCrawlerUser;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ChannelCrawlerUserDao {

    List<ChannelCrawlerUser> selectCrawlUsers(String channelCode, Timestamp startDate, Timestamp endDate);

    void insertChannelsStatisticDaily(List<ChannelCrawlerUser> lists);

    int countCrawlUsers(String channelCode, Timestamp startDate, Timestamp endDate);

    ChannelCrawlerUser getLastCrawlUser(String channelCode);

    boolean selectExistCrawlUser(String channelCode, String uId);
}
