package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.ReportSummary;

public interface ReportSummaryMapper {

    List<ReportSummary> getSummaryByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
