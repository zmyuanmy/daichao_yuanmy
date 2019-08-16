package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.TodayStatistic;

public interface TodayStatisticMapper {

    TodayStatistic getTodayStatistic(@Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate, @Param(value = "orgId") int orgId);

}
