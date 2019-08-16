package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.ChannelUserDao;
import com.jbb.mgt.core.domain.UserLoginPuv;
import com.jbb.mgt.core.service.ChannelUserService;

@Service("ChannelUserService")
public class ChannelUserServiceImpl implements ChannelUserService {
    @Autowired
    private ChannelUserDao channelUserDao;

    @Override
    public List<UserLoginPuv> getChannelUserDaily(String channelCode, String startDate, String endDate) {
        return channelUserDao.getChannelUserDaily(channelCode, startDate, endDate);
    }

    @Override
    public List<UserLoginPuv> getChannelUserCompare(String statisticDate) {
        return channelUserDao.getChannelUserCompare(statisticDate);
    }

}
