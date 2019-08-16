package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.UserLoginPuv;

public interface ChannelUserService {

    List<UserLoginPuv> getChannelUserCompare(String statisticDate);

    List<UserLoginPuv> getChannelUserDaily(String channelCode, String startDate, String endDate);

}
