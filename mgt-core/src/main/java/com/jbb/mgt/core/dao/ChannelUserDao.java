package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.UserLoginPuv;

public interface ChannelUserDao {

    List<UserLoginPuv> getChannelUserDaily(String channelCode, String startDate, String endDate);

    List<UserLoginPuv> getChannelUserCompare(String statisticDate);

}
