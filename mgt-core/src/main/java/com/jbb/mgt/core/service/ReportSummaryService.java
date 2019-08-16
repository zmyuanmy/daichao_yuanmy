package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.ReportSummary;

public interface ReportSummaryService {

    List<ReportSummary> getSummaryByDate(String startDate, String newDate);

}
