package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jbb.mgt.core.domain.ChannelPromotion;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.ChannelDao;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelSimpleInfo;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.server.core.util.PasswordUtil;
import com.jbb.server.common.exception.AccessDeniedException;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;

/**
 * 渠道操作Service实现类
 *
 * @author wyq
 * @dete 2018年4月20日 10:38:16
 *
 */
@Service("ChannelService")
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelDao channelDao;

    @Override
    public void createChannal(Channel channel) {
        channelDao.insertChannal(channel);
    }

    @Override
    public Channel getChannelByCode(String channelCode) {

        return channelDao.selectChannelByCode(channelCode);
    }

    @Override
    public List<Channel> getChannels(String searchText, int orgId, Timestamp startDate, Timestamp endDate,
        Integer zhima, Integer userType, Boolean includeWool, Boolean includeEmptyMobile, String channelCode,
        boolean includeHiddenChannel, boolean getAdStatistic, String groupName, boolean getReducePercent,
        boolean isReduce, Integer accountId, Integer mode) {
        List<Channel> channels = new ArrayList<>();
        Channel channel = channelDao.selectOrgDelegateChannelWithStatistic(orgId, startDate, endDate);
        if (null != channel) {
            channels.add(channel);
        }
        channels.addAll(channelDao.selectChannels(searchText, orgId, startDate, endDate, zhima, userType, includeWool,
            includeEmptyMobile, channelCode, includeHiddenChannel, getAdStatistic, groupName, getReducePercent,
            isReduce, accountId, mode));
        return channels;
    }

    @Override
    public void updateChannle(Channel channel) {
        channelDao.updateChannle(channel);

    }

    @Override
    public void deleteChannel(String channelCode) {
        channelDao.deleteChannel(channelCode);

    }

    @Override
    public void frozeChannel(String channelCode) {
        channelDao.frozeChannel(channelCode);
    }

    @Override
    public void verifyChannel(String channelCode) {
        if (StringUtils.isNotBlank(channelCode)) {
            Channel channel = getChannelByCode(channelCode);
            if (channel != null) {
                return;
            }
        }
        throw new WrongParameterValueException("jbb.mgt.error.exception.channelNotFound", "zh", channelCode);
    }

    @Override
    public Channel checkPhoneNumberAndPassword(String phoneNumber, String passWord) {
        if (StringUtil.isEmpty(phoneNumber)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "sourcePhoneNumber");
        }
        if (StringUtil.isEmpty(passWord)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "sourcePassword");
        }

        Channel channel = channelDao.selectChannelBySourcePhoneNumber(phoneNumber);
        if (channel == null) {
            throw new AccessDeniedException("jbb.mgt.exception.channel.userNameOrPwError");
        }

        if (!phoneNumber.equals(channel.getSourcePhoneNumber())
            || !PasswordUtil.verifyPassword(passWord, channel.getSourcePassword())) {
            throw new AccessDeniedException("jbb.mgt.exception.channel.userNameOrPwError");
        }
        return channel;
    }

    @Override
    public void thawChannel(String channelCode) {
        channelDao.thawChannel(channelCode);
    }

    @Override
    public Channel getChannelBySourcePhoneNumber(String sourcePhoneNumber) {
        return channelDao.selectChannelBySourcePhoneNumber(sourcePhoneNumber);
    }

    @Override
    public boolean selectExistsDelegate(int orgId) {
        return channelDao.selectExistsDelegate(orgId);
    }

    @Override
    public List<ChannelSimpleInfo> getChannelNamesAndCodes(int orgId, Boolean includeHiddenChannel, String groupName) {
        return channelDao.getChannelNamesAndCodes(orgId, includeHiddenChannel, groupName);

    }

    @Override
    public void updateMsgTimes(String channelCode, int times) {
        channelDao.updateMsgTimes(channelCode, times);

    }

    @Override
    public List<Channel> getXjlChannels(String searchText, int orgId, Timestamp startDate, Timestamp endDate) {
        List<Channel> channels = new ArrayList<>();
        Channel channel = channelDao.selectOrgDelegateChannelWithStatistic(orgId, startDate, endDate);
        if (null != channel) {
            channels.add(channel);
        }
        channels.addAll(channelDao.selectXjlChannels(searchText, orgId, startDate, endDate));
        return channels;
    }

    @Override
    public List<String> getChannelGroupName() {
        return channelDao.selectChannelGroupName();
    }

    @Override
    public void updateChannelTest(String channelCode, String merchantId, String merchantId2, String redirectUrl) {
        channelDao.updateChannelTest(channelCode, merchantId, merchantId2, redirectUrl);
    }

    @Override
    public ChannelPromotion getChannelPromotion(String channelCode, Timestamp creationDate, Timestamp endDate,
        int orgId) {
        return channelDao.selectChannelPromotion(channelCode, creationDate, endDate, orgId);
    }

}
