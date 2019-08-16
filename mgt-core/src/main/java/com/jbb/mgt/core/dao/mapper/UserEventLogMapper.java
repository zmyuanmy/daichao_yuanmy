package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.PlatformUv;

public interface UserEventLogMapper {

    // 记录用户操作
    int insertEventLog(@Param(value = "userId") Integer userId, @Param(value = "sourceId") String sourceId,
        @Param(value = "cookieId") String cookieId, @Param(value = "eventName") String eventName,
        @Param(value = "eventAction") String eventAction, @Param(value = "eventLabel") String eventLabel,
        @Param(value = "eventValue") String eventValue, @Param(value = "remoteAddress") String remoteAddress,
        @Param(value = "creationDate") Timestamp creationDate);

    // 统计用户操作
    int countEventLogByParams(@Param(value = "userId") Integer userId, @Param(value = "sourceId") String sourceId,
        @Param(value = "cookieId") String cookieId, @Param(value = "eventName") String eventName,
        @Param(value = "eventAction") String eventAction, @Param(value = "eventLabel") String eventLabel,
        @Param(value = "eventValue") String eventValue, @Param(value = "start") Timestamp start,
        @Param(value = "end") Timestamp end);

    int insertEventLog2(@Param(value = "userId") Integer userId, @Param(value = "sourceId") String sourceId,
        @Param(value = "cookieId") String cookieId, @Param(value = "eventName") String eventName,
        @Param(value = "eventAction") String eventAction, @Param(value = "eventLabel") String eventLabel,
        @Param(value = "eventValue") String eventValue, @Param(value = "eventValue2") String eventValue2,
        @Param(value = "remoteAddress") String remoteAddress, @Param(value = "creationDate") Timestamp creationDate);

    int countEventLog2ByParams(@Param(value = "userId") Integer userId, @Param(value = "sourceId") String sourceId,
        @Param(value = "cookieId") String cookieId, @Param(value = "eventName") String eventName,
        @Param(value = "eventAction") String eventAction, @Param(value = "eventLabel") String eventLabel,
        @Param(value = "eventValue") String eventValue, @Param(value = "eventValue2") String eventValue2,
        @Param(value = "start") Timestamp start, @Param(value = "end") Timestamp end);

    // 统计贷超的UV数据
    List<PlatformUv> selectPlatformUv(@Param(value = "salesId") Integer salesId,
        @Param(value = "groupName") String groupName, @Param(value = "platformId") Integer platformId,
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate);

}
