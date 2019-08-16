package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.LoanPlatformDetailStatistic;
import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface LoanPlatformDetailStatisticMapper {

    void insertLoanPlatformDetailStatistics(@Param(value = "lists") List<LoanPlatformDetailStatistic> list);

    void insertLoanPlatformDetailStatistic(LoanPlatformDetailStatistic loanPlatformDetailStatistic);

    void updateLoanPlatformDetailStatistic(LoanPlatformDetailStatistic loanPlatformDetailStatistic);

    List<LoanPlatformDetailStatistic> selectLoanPlatformLDetailStatisticsByDate(
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate);

    List<LoanPlatformStatistic> selectLoanPlatformDetailStatistic(@Param(value = "platformId") Integer platformId,
        @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate,
        @Param(value = "salesId") Integer salesId);

    List<LoanPlatformStatistic> selectLoanPlatformDetailStatisticByStatisticDate(
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
        @Param(value = "salesId") Integer salesId);

}
