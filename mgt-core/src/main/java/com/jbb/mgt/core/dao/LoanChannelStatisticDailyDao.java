package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.LoanChannelStatistic;
import com.jbb.mgt.core.domain.LoanChannelStatisticDaily;

public interface LoanChannelStatisticDailyDao {

    void insertLoanChannelStatisticDailys(List<LoanChannelStatisticDaily> list);

    List<LoanChannelStatisticDaily> selectLoanChannelStatisticByDate(Timestamp startDate, Timestamp endDate);

    List<LoanChannelStatistic> selectLoanChannelStatisticCompare(String startDate, String endDate);

    List<LoanChannelStatistic> selectLoanChannelStatisticByChannelCode(String[] channelCodes, String startDate,
        String endDate);

}
