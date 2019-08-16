package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.ChannelPromotion;
import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelSimpleInfo;

/**
 * 渠道操作mapper类
 * 
 * @author wyq
 * @date 2018年4月20日 11:46:54
 *
 */
public interface ChannelMapper {

    void insertChannal(Channel channel);

    Channel selectChannelByCode(@Param(value = "channelCode") String channelCode);

    Channel selectChannelBySourcePhoneNumber(@Param(value = "sourcePhoneNumber") String sourcePhoneNumber);

    List<Channel> selectChannels(@Param(value = "searchText") String searchText, @Param(value = "orgId") int orgId,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate,
        @Param(value = "zhima") Integer zhima, @Param(value = "userType") Integer userType,
        @Param(value = "woolcheckResult") Boolean includeWool,
        @Param(value = "mobileCheckResult") Boolean includeEmptyMobile,
        @Param(value = "channelCode") String channelCode,
        @Param(value = "includeHiddenChannel") boolean includeHiddenChannel,
        @Param(value = "getAdStatistic") boolean getAdStatistic, @Param("groupName") String groupName,
        @Param(value = "getReducePercent") boolean getReducePercent, @Param(value = "isReduce") boolean isReduce,
        @Param(value = "accountId") Integer accountId, @Param(value = "mode") Integer mode);

    void updateChannel(Channel channel);

    int deleteChannel(@Param(value = "channelCode") String channelCode);

    void frozeChannel(@Param(value = "channelCode") String channelCode);

    List<Channel> selectChannelsByNameOrCreator(@Param(value = "channelName") String channelNameOrCreator);

    void thawChannel(@Param(value = "channelCode") String channelCode);

    Channel selectUserRegisterChannal(@Param(value = "userId") int userId, @Param(value = "orgId") int orgId);

    int selectExistsDelegate(@Param(value = "orgId") int orgId);

    Channel selectOrgDelegateChannelWithStatistic(@Param(value = "orgId") int orgId,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate);

    Channel selectOrgDelegateChannel(@Param(value = "orgId") int orgId);

    List<ChannelSimpleInfo> getChannelNamesAndCodes(@Param(value = "orgId") int orgId,
        @Param(value = "includeHiddenChannel") Boolean includeHiddenChannel, @Param("groupName") String groupName);

    void updateMsgTimes(@Param(value = "channelCode") String channelCode, @Param(value = "times") int times);

    List<Channel> selectXjlChannels(@Param(value = "searchText") String searchText, @Param(value = "orgId") int orgId,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate);

    List<String> selectChannelGroupName();

    void updateChannelTest(@Param(value = "channelCode") String channelCode,
        @Param(value = "merchantId") String merchantId, @Param(value = "merchantId2") String merchantId2,
        @Param(value = "redirectUrl") String redirectUrl);

    ChannelPromotion selectChannelPromotion(@Param("channelCode") String channelCode,
        @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate, @Param("orgId") int orgId);
}
