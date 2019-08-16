package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.LoanStatistic;

public interface LoanStatisticDao {

    List<LoanStatistic> getLoanStatistics(Timestamp startDate, Timestamp endDate, Integer orgId);

}
