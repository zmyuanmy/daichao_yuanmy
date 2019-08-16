package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.LoanChannelStatistic;
import com.jbb.mgt.core.domain.LoanChannelStatisticDaily;

public interface LoanChannelStatisticDailyMapper {

    void insertLoanChannelStatisticDailys(@Param(value = "lists") List<LoanChannelStatisticDaily> list);

    List<LoanChannelStatisticDaily> selectLoanChannelStatisticByDate(@Param(value = "startDate") Timestamp startDate,@Param(value = "endDate") Timestamp endDate);

    List<LoanChannelStatistic> selectLoanChannelStatisticCompare(@Param(value = "startDate") String startDate,@Param(value = "endDate") String endDate);

    List<LoanChannelStatistic> selectLoanChannelStatisticByChannelCode(@Param(value = "channelCodes") String[] channelCodes,@Param(value = "startDate") String startDate,@Param(value = "endDate") String endDate);



}
