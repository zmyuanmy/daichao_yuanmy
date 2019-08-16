package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserEventLogDao;
import com.jbb.mgt.core.dao.mapper.UserEventLogMapper;
import com.jbb.mgt.core.domain.PlatformUv;

@Repository("UserEventLogDao")
public class UserEventLogDaoImpl implements UserEventLogDao {

    @Autowired
    private UserEventLogMapper mapper;

    // 记录用户操作
    @Override
    public boolean insertEventLog(Integer userId, String sourceId, String cookieId, String eventName,
        String eventAction, String eventLabel, String eventValue, String remoteAddress, Timestamp creationDate) {
        return mapper.insertEventLog(userId, sourceId, cookieId, eventName, eventAction, eventLabel, eventValue,
            remoteAddress, creationDate) > 0;
    }

    // 统计用户操作
    @Override
    public int countEventLogByParams(Integer userId, String sourceId, String cookieId, String eventName,
        String eventAction, String eventLabel, String eventValue, Timestamp start, Timestamp end) {
        return mapper.countEventLogByParams(userId, sourceId, cookieId, eventName, eventAction, eventLabel, eventValue,
            start, end);
    }

    @Override
    public boolean insertEventLog(Integer userId, String sourceId, String cookieId, String eventName,
        String eventAction, String eventLabel, String eventValue, String eventValue2, String remoteAddress,
        Timestamp creationDate) {
        return mapper.insertEventLog2(userId, sourceId, cookieId, eventName, eventAction, eventLabel, eventValue,
            eventValue2, remoteAddress, creationDate) > 0;
    }

    @Override
    public int countEventLogByParams(Integer userId, String sourceId, String cookieId, String eventName,
        String eventAction, String eventLabel, String eventValue, String eventValue2, Timestamp start, Timestamp end) {
        return mapper.countEventLog2ByParams(userId, sourceId, cookieId, eventName, eventAction, eventLabel, eventValue,
            eventValue2, start, end);
    }

    @Override
    public List<PlatformUv> selectPlatformUv(Integer salesId, String groupName, Integer platformId, String startDate,
        String endDate) {
        return mapper.selectPlatformUv(salesId, groupName, platformId, startDate, endDate);
    }

}
