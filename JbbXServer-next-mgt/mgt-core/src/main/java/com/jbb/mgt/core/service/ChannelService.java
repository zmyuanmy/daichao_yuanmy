package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelPromotion;
import com.jbb.mgt.core.domain.ChannelSimpleInfo;

/**
 * 渠道操作service借口
 * 
 * @author wyq
 * @date 2018年4月20日 10:33:47
 *
 */
public interface ChannelService {

    /**
     * 创建渠道
     * 
     * @param channel
     * @return
     */
    void createChannal(Channel channel);

    /**
     * 通过渠道编号获取渠道信息
     * 
     * @param channelCode
     * @return
     */
    Channel getChannelByCode(String channelCode);

    /**
     * 通过账号获取渠道信息
     * 
     * @param sourcePhoneNumber
     * @return
     */
    Channel getChannelBySourcePhoneNumber(String sourcePhoneNumber);

    /**
     * 获取 渠道列表
     * 
     * @param searchText
     * @param orgId
     * @param startDate
     * @param endDate
     * @param zhima
     * @param userType
     * @param includeWool
     * @param includeEmptyMobile
     * @param getAdStatisticF
     * @param groupName
     * @param getReducePercent
     * @param isReduce
     * @param accountId
     * @return
     */
    List<Channel> getChannels(String searchText, int orgId, Timestamp startDate, Timestamp endDate, Integer zhima,
        Integer userType, Boolean includeWool, Boolean includeEmptyMobile, String channelCode,
        boolean includeHiddenChannel, boolean getAdStatisticF, String groupName, boolean getReducePercent,
        boolean isReduce, Integer accountId, Integer mode);

    /**
     * 更新渠道
     * 
     * @param channel
     */
    void updateChannle(Channel channel);

    /**
     * 删除渠道
     * 
     * @param channelCode
     */

    void deleteChannel(String channelCode);

    /**
     * 冻结渠道
     * 
     * @param channelCode
     */
    void frozeChannel(String channelCode);

    /**
     * 验证渠道是否存在
     * 
     * @param channelCode
     */
    void verifyChannel(String channelCode);

    Channel checkPhoneNumberAndPassword(String phoneNumber, String passWord);

    /**
     * 解冻渠道
     *
     * @param channelCode
     */
    void thawChannel(String channelCode);

    /**
     * 是否有委托链存在
     *
     * @param orgId
     */
    boolean selectExistsDelegate(int orgId);

    List<ChannelSimpleInfo> getChannelNamesAndCodes(int orgId, Boolean includeHiddenChannel, String groupName);

    void updateMsgTimes(String channelCode, int times);

    /**
     * 获取 小金条渠道列表
     *
     * @param searchText
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    List<Channel> getXjlChannels(String searchText, int orgId, Timestamp startDate, Timestamp endDate);

    /**
     * 获取所有渠道的groupname集合
     *
     * @return
     */
    List<String> getChannelGroupName();

    void updateChannelTest(String channelCode, String merchantId, String merchantId2, String redirectUrl);

    /**
     * 获取渠道推广页总数
     * 
     * @return
     */
    ChannelPromotion getChannelPromotion(String channelCode, Timestamp creationDate, Timestamp endDate, int orgId);
}
