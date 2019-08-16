package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.ChannelFunnelDao;
import com.jbb.mgt.core.domain.ChannelFunnel;
import com.jbb.mgt.core.service.ChannelFunnelService;

@Service("ChannelFunnelService")
public class ChannelFunnelServiceImpl implements ChannelFunnelService {
    @Autowired
    private ChannelFunnelDao channelFunnelDao;

    @Override
    public List<ChannelFunnel> getChannelFunnelDaily(String[] channelCodes, Integer uType, Integer h5Type,
        Boolean isDelegate, String startDate, String endDate, Integer salesId, Integer mode) {
        boolean flag = isGetToDayChannelFunnel(startDate, endDate);
        return channelFunnelDao.getChannelFunnelDaily(channelCodes, uType, h5Type, isDelegate, startDate, endDate,
            salesId, mode, flag);
    }

    @Override
    public List<ChannelFunnel> getChannelFunnelCompare(Integer uType, Integer h5Type, Boolean isDelegate,
        String startDate, String endDate, Integer salesId, Integer mode) {
        boolean flag = isGetToDayChannelFunnel(startDate, endDate);
        return channelFunnelDao.getChannelFunnelCompare(uType, h5Type, isDelegate, startDate, endDate, salesId, mode,
            flag);
    }

    @Override
    public ChannelFunnel getChannelFunnelCompareTotal(Integer uType, String startDate, String endDate,
        Integer salesId, Integer mode) {
        boolean flag = isGetToDayChannelFunnel(startDate, endDate);
        return channelFunnelDao.getChannelFunnelCompareTotal(uType, startDate, endDate, salesId, mode, flag);
    }

    public boolean isGetToDayChannelFunnel(String startDate, String endDate) {
        Timestamp date1 = Util.parseTimestamp(startDate, TimeZone.getDefault());
        Timestamp date2 = StringUtil.isEmpty(endDate) ? DateUtil.getCurrentTimeStamp()
            : Util.parseTimestamp(endDate, TimeZone.getDefault());
        long newTime = DateUtil.getTodayStartTs();
        boolean result = StringUtil.isEmpty(endDate) ? false : true;
        boolean flag = false;
        if (newTime >= date1.getTime()
            && (!result && newTime <= date2.getTime() || result && newTime < date2.getTime())) {
            flag = true;
        }
        return flag;
    }

}
