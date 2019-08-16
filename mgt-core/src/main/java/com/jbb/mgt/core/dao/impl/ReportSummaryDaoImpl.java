package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.ReportSummaryDao;
import com.jbb.mgt.core.dao.mapper.ReportSummaryMapper;
import com.jbb.mgt.core.domain.ReportSummary;

@Repository("ReportSummaryDao")
public class ReportSummaryDaoImpl implements ReportSummaryDao {
    @Autowired
    private ReportSummaryMapper mapper;

    @Override
    public List<ReportSummary> getSummaryByDate(String startDate, String endDate) {
        return mapper.getSummaryByDate(startDate, endDate);
    }

}
