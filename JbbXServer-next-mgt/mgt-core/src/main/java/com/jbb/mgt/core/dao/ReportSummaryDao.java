package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.ReportSummary;

public interface ReportSummaryDao {

    List<ReportSummary> getSummaryByDate(String startDate, String endDate);

}
