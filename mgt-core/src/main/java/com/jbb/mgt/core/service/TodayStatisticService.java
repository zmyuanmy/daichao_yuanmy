package com.jbb.mgt.core.service;

import java.sql.Timestamp;

import com.jbb.mgt.core.domain.TodayStatistic;

public interface TodayStatisticService {

    TodayStatistic getTodayStatistic(Timestamp startDate, Timestamp endDate, int orgId);

}
