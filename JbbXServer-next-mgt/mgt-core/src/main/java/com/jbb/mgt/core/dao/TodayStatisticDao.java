package com.jbb.mgt.core.dao;

import java.sql.Timestamp;

import com.jbb.mgt.core.domain.TodayStatistic;

public interface TodayStatisticDao {

    TodayStatistic getTodayStatistic(Timestamp startDate, Timestamp endDate, int orgId);

}
