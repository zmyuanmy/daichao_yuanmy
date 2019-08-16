package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.LoanStatistic;

public interface LoanStatisticService {
    List<LoanStatistic> getLoanStatistics(Timestamp startDate, Timestamp endDate, Integer orgId);
}
