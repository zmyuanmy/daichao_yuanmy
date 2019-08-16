package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.PlatformUv;

public interface UserEventLogService {

    // 记录用户操作
    boolean insertEventLog(Integer userId, String sourceId, String cookieId, String eventName, String eventAction,
        String eventLabel, String eventValue, String remoteAddress, Timestamp creationDate);

    // 统计用户操作
    int countEventLogByParams(Integer userId, String sourceId, String cookieId, String eventName, String eventAction,
        String eventLabel, String eventValue, Timestamp start, Timestamp end);

    // 记录用户操作,带eventValue2
    boolean insertEventLog(Integer userId, String sourceId, String cookieId, String eventName, String eventAction,
        String eventLabel, String eventValue, String eventValue2, String remoteAddress, Timestamp creationDate);

    // 统计用户操作,带eventValue2
    int countEventLogByParams(Integer userId, String sourceId, String cookieId, String eventName, String eventAction,
        String eventLabel, String eventValue, String eventValue2, Timestamp start, Timestamp end);

    // 统计贷超的UV数据
    List<PlatformUv> getPlatformUv(Integer salesId, String groupName, Integer platformId, String startDate,
        String endDate);

}
