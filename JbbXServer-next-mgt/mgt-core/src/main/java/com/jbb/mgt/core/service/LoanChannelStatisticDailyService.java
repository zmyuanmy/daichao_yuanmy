package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.LoanChannelStatistic;

import java.sql.Timestamp;
import java.util.List;

public interface LoanChannelStatisticDailyService {

    void runLoanChannelStatisticDaily(Timestamp startDate, Timestamp endDate);

    List<LoanChannelStatistic> selectLoanChannelStatisticCompare(String startDate, String endDate);

    List<LoanChannelStatistic> selectLoanChannelStatisticByChannelCode(String[] channelCodes, String startDate,
        String endDate);

}
