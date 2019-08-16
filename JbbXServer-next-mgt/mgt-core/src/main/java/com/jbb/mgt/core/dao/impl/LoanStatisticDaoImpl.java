package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.LoanStatisticDao;
import com.jbb.mgt.core.dao.mapper.LoanStatisticMapper;
import com.jbb.mgt.core.domain.LoanStatistic;

@Repository("LoanStatisticDao")
public class LoanStatisticDaoImpl implements LoanStatisticDao {
    @Autowired
    private LoanStatisticMapper mapper;

    @Override
    public List<LoanStatistic> getLoanStatistics(Timestamp startDate, Timestamp endDate, Integer orgId) {
        return mapper.getLoanStatistics(startDate, endDate, orgId);
    }
}
