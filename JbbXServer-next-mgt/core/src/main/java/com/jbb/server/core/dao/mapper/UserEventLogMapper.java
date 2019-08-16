 package com.jbb.server.core.dao.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

public interface UserEventLogMapper {
     int insertEventLog(
         @Param(value = "userId") Integer userId, 
         @Param(value = "sourceId") String sourceId, 
         @Param(value = "eventName") String eventName, 
         @Param(value = "eventAction") String eventAction, 
         @Param(value = "eventLabel") String eventLabel,
         @Param(value = "eventValue") String eventValue, 
         @Param(value = "remoteAddress") String remoteAddress, 
         @Param(value = "creationDate") Timestamp creationDate);

    int countEventLogByParams(@Param(value = "userId") Integer userId, 
        @Param(value = "sourceId") String sourceId, 
        @Param(value = "eventName") String eventName, 
        @Param(value = "eventAction") String eventAction, 
        @Param(value = "eventLabel") String eventLabel,
        @Param(value = "eventValue") String eventValue, 
        @Param(value = "start") Timestamp start,
        @Param(value = "end") Timestamp end
       );

}
