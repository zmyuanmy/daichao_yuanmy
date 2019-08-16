package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.TodayStatisticDao;
import com.jbb.mgt.core.domain.TodayStatistic;
import com.jbb.mgt.core.service.TodayStatisticService;

@Service("TodayStatisticService")
public class TodayStatisticServiceImpl implements TodayStatisticService {

    @Autowired
    private TodayStatisticDao todayStatisticDao;

    @Override
    public TodayStatistic getTodayStatistic(Timestamp startDate, Timestamp endDate, int orgId) {
        return todayStatisticDao.getTodayStatistic(startDate, endDate, orgId);

    }

}
