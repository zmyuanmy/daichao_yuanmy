package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.ChannelPromotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.ChannelDao;
import com.jbb.mgt.core.dao.mapper.ChannelMapper;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelSimpleInfo;

/**
 * 渠道操作dao实现类
 * 
 * @author wyq
 * @date 2018/04/25
 */
@Repository("ChannelDao")
public class ChannelDaoImpl implements ChannelDao {

    @Autowired
    private ChannelMapper mapper;

    @Override
    public void insertChannal(Channel channel) {
        mapper.insertChannal(channel);
    }

    @Override
    public Channel selectChannelByCode(String channelCode) {
        return mapper.selectChannelByCode(channelCode);
    }

    @Override
    public void updateChannle(Channel channel) {
        mapper.updateChannel(channel);

    }

    @Override
    public boolean deleteChannel(String channelCode) {
        return mapper.deleteChannel(channelCode) > 0;
    }

    @Override
    public void frozeChannel(String channelCode) {
        mapper.frozeChannel(channelCode);
    }

    @Override
    public void thawChannel(String channelCode) {
        mapper.thawChannel(channelCode);
    }

    @Override
    public Channel selectChannelBySourcePhoneNumber(String sourcePhoneNumber) {
        return mapper.selectChannelBySourcePhoneNumber(sourcePhoneNumber);
    }

    @Override
    public List<Channel> selectChannels(String searchText, int orgId, Timestamp startDate, Timestamp endDate,
        Integer zhima, Integer userType, Boolean includeWool, Boolean includeEmptyMobile, String channelCode,
        boolean includeHiddenChannel, boolean getAdStatistic, String groupName, boolean getReducePercent,
        boolean isReduce, Integer accountId, Integer mode) {
        return mapper.selectChannels(searchText, orgId, startDate, endDate, zhima, userType, includeWool,
            includeEmptyMobile, channelCode, includeHiddenChannel, getAdStatistic, groupName, getReducePercent,
            isReduce, accountId, mode);
    }

    @Override
    public Channel selectUserRegisterChannal(int userId, int orgId) {
        return mapper.selectUserRegisterChannal(userId, orgId);
    }

    @Override
    public boolean selectExistsDelegate(int orgId) {
        return mapper.selectExistsDelegate(orgId) > 0;
    }

    public Channel selectOrgDelegateChannelWithStatistic(int orgId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectOrgDelegateChannelWithStatistic(orgId, startDate, endDate);
    }

    public Channel selectOrgDelegateChannel(int orgId) {
        return mapper.selectOrgDelegateChannel(orgId);
    }

    @Override
    public List<ChannelSimpleInfo> getChannelNamesAndCodes(int orgId, Boolean includeHiddenChannel, String groupName) {
        return mapper.getChannelNamesAndCodes(orgId, includeHiddenChannel, groupName);
    }

    @Override
    public void updateMsgTimes(String channelCode, int times) {
        mapper.updateMsgTimes(channelCode, times);

    }

    @Override
    public List<String> selectChannelGroupName() {
        return mapper.selectChannelGroupName();
    }

    @Override
    public void updateChannelTest(String channelCode, String merchantId, String merchantId2, String redirectUrl) {
        mapper.updateChannelTest(channelCode, merchantId, merchantId2, redirectUrl);
    }

    @Override
    public List<Channel> selectXjlChannels(String searchText, int orgId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectXjlChannels(searchText, orgId, startDate, endDate);
    }

    @Override
    public ChannelPromotion selectChannelPromotion(String channelCode, Timestamp creationDate, Timestamp endDate,
        int orgId) {
        return mapper.selectChannelPromotion(channelCode, creationDate, endDate, orgId);
    }

}
