package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.ChannelAccountInfoDao;
import com.jbb.mgt.core.dao.mapper.ChannelAccountInfoMapper;
import com.jbb.mgt.core.domain.ChannelAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 渠道账号信息维护Dao接口实现类
 * 
 * @author wyq
 * @date 2018/7/23 10:19
 */
@Repository("ChannelAccountInfoDao")
public class ChannelAccountInfoDaoImpl implements ChannelAccountInfoDao {

    @Autowired
    private ChannelAccountInfoMapper mapper;

    @Override
    public void insertChannelAccountInfo(ChannelAccountInfo ChannelAccountInfo) {
        mapper.insertChannelAccountInfo(ChannelAccountInfo);
    }

    @Override
    public ChannelAccountInfo selectChannelAccountInfo(String channelCode) {
        return mapper.selectChannelAccountInfo(channelCode);
    }
}
