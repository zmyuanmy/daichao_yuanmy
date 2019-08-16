package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.ChannelCrawlerUser;
import com.jbb.mgt.core.domain.ChannelStatisticDaily;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ChannelCrawlerUserMapper {

    List<ChannelCrawlerUser> selectCrawlUsers(@Param(value = "channelCode") String channelCode,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate);

    void insertChannelsStatisticDaily(@Param(value = "lists") List<ChannelCrawlerUser> lists);

    int countCrawlUsers(@Param(value = "channelCode") String channelCode,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate);

    ChannelCrawlerUser getLastCrawlUser(@Param(value = "channelCode") String channelCode);

    int selectExistCrawlUser(@Param(value = "channelCode") String channelCode, @Param(value = "uId") String uId);
}
