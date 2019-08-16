package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.ChannelAccountInfoDao;
import com.jbb.mgt.core.domain.ChannelAccountInfo;
import com.jbb.mgt.core.service.ChannelAccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 渠道账号信息维护Service接口实现类
 *
 * @author wyq
 * @date 2018/7/23 10:16
 */
@Service("ChannelAccountInfoService")
public class ChannelAccountInfoServiceImpl implements ChannelAccountInfoService {

    @Autowired
    private ChannelAccountInfoDao dao;

    @Override
    public void insertChannelAccountInfo(ChannelAccountInfo ChannelAccountInfo) {
        dao.insertChannelAccountInfo(ChannelAccountInfo);
    }

    @Override
    public ChannelAccountInfo selectChannelAccountInfo(String channelCode) {
        return dao.selectChannelAccountInfo(channelCode);
    }
}
