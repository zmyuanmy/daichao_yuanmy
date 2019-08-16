package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelPromotion;
import com.jbb.mgt.core.domain.ChannelSimpleInfo;

/**
 * 渠道操作dao借口
 * 
 * @author wyq
 * @date 2018年4月20日 10:55:59
 *
 */
public interface ChannelDao {

    void insertChannal(Channel channel);

    Channel selectChannelByCode(String channelCode);

    Channel selectChannelBySourcePhoneNumber(String sourcePhoneNumber);

    List<Channel> selectChannels(String searchText, int orgId, Timestamp startDate, Timestamp endDate, Integer zhima,
        Integer userType, Boolean includeWool, Boolean includeEmptyMobile, String channelCode,
        boolean includeHiddenChannel, boolean getAdStatistic, String groupName, boolean getReducePercent,
        boolean isReduce, Integer accountId, Integer mode);

    void updateChannle(Channel channel);

    boolean deleteChannel(String channelCode);

    public void frozeChannel(String channelCode);

    void thawChannel(String channelCode);

    Channel selectUserRegisterChannal(int userId, int orgId);

    boolean selectExistsDelegate(int orgId);

    Channel selectOrgDelegateChannelWithStatistic(int orgId, Timestamp startDate, Timestamp endDate);

    Channel selectOrgDelegateChannel(int orgId);

    List<ChannelSimpleInfo> getChannelNamesAndCodes(int orgId, Boolean includeHiddenChannel, String groupName);

    void updateMsgTimes(String channelCode, int times);

    List<String> selectChannelGroupName();

    List<Channel> selectXjlChannels(String searchText, int orgId, Timestamp startDate, Timestamp endDate);

    void updateChannelTest(String channelCode, String merchantId, String merchantId2, String redirectUrl);

    ChannelPromotion selectChannelPromotion(String channelCode, Timestamp creationDate, Timestamp endDate, int orgId);
}
