package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.TodayStatisticDao;
import com.jbb.mgt.core.dao.mapper.TodayStatisticMapper;
import com.jbb.mgt.core.domain.TodayStatistic;

@Repository("TodayStatisticDao")
public class TodayStatisticDaoImpl implements TodayStatisticDao {

    @Autowired
    private TodayStatisticMapper mapper;

    @Override
    public TodayStatistic getTodayStatistic(Timestamp startDate, Timestamp endDate, int orgId) {
        return mapper.getTodayStatistic(startDate, endDate,orgId);
    }

}
