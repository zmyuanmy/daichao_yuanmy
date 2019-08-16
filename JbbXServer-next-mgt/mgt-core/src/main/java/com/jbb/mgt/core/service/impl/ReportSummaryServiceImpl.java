package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.ReportSummaryDao;
import com.jbb.mgt.core.domain.ReportSummary;
import com.jbb.mgt.core.service.ReportSummaryService;

@Service("ReportSummaryService")
public class ReportSummaryServiceImpl implements ReportSummaryService {
    @Autowired
    private ReportSummaryDao reportSummaryDao;

    @Override
    public List<ReportSummary> getSummaryByDate(String startDate, String endDate) {
        return reportSummaryDao.getSummaryByDate(startDate, endDate);
    }

}
