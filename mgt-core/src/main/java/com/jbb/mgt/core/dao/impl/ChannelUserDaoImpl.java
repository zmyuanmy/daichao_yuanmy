package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.ChannelUserDao;
import com.jbb.mgt.core.dao.mapper.ChannelUserMapper;
import com.jbb.mgt.core.domain.UserLoginPuv;

@Repository("ChannelUserDao")
public class ChannelUserDaoImpl implements ChannelUserDao {
    @Autowired
    private ChannelUserMapper mapper;

    @Override
    public List<UserLoginPuv> getChannelUserDaily(String channelCode, String startDate, String endDate) {
        return mapper.getChannelUserDaily(channelCode, startDate, endDate);
    }

    @Override
    public List<UserLoginPuv> getChannelUserCompare(String statisticDate) {
        return mapper.getChannelUserCompare(statisticDate);
    }

}
