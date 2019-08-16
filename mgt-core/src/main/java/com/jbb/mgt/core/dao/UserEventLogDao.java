package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.PlatformUv;

public interface UserEventLogDao {

    // 记录用户操作
    boolean insertEventLog(Integer userId, String sourceId, String cookieId, String eventName, String eventAction,
        String eventLabel, String eventValue, String remoteAddress, Timestamp creationDate);

    // 统计用户操作
    int countEventLogByParams(Integer userId, String sourceId, String cookieId, String eventName, String eventAction,
        String eventLabel, String eventValue, Timestamp start, Timestamp end);

    boolean insertEventLog(Integer userId, String sourceId, String cookieId, String eventName, String eventAction,
        String eventLabel, String eventValue, String eventValue2, String remoteAddress, Timestamp creationDate);

    int countEventLogByParams(Integer userId, String sourceId, String cookieId, String eventName, String eventAction,
        String eventLabel, String eventValue, String eventValue2, Timestamp start, Timestamp end);

    // 统计贷超的UV数据
    List<PlatformUv> selectPlatformUv(Integer salesId, String groupName, Integer platformId, String startDate,
        String endDate);

}
