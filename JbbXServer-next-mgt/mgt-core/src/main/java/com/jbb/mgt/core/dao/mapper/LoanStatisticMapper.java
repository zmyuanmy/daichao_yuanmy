package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.LoanStatistic;

public interface LoanStatisticMapper {

    List<LoanStatistic> getLoanStatistics(@Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate, @Param(value = "orgId") Integer orgId);

}
