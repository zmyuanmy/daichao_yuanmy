package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.LoanPlatformDetailStatistic;
import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface LoanPlatformDetailStatisticDao {

    List<LoanPlatformDetailStatistic> selectLoanPlatformLDetailStatisticsByDate(Timestamp startDate, Timestamp endDate);

    void insertLoanPlatformDetailStatistics(List<LoanPlatformDetailStatistic> list);

    void insertLoanPlatformDetailStatistic(LoanPlatformDetailStatistic loanPlatformDetailStatistic);

    void updateLoanPlatformDetailStatistic(LoanPlatformDetailStatistic loanPlatformDetailStatistic);

    List<LoanPlatformStatistic> selectLoanPlatformDetailStatistic(Integer platformId, Date startDate, Date endDate,
        Integer salesId);

    List<LoanPlatformStatistic> selectLoanPlatformDetailStatisticByStatisticDate(String startDate, String endDate,
        Integer salesId);

}
