package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.JuXinLiReportDao;
import com.jbb.mgt.core.dao.mapper.JuXinLiReportMapper;
import com.jbb.mgt.core.domain.JuXinLiReport;

@Repository("JuXinLiReportDao")
public class JuXinLiReportDaoImpl implements JuXinLiReportDao {
    @Autowired
    private JuXinLiReportMapper mapper;

    @Override
    public void saveReportInfo(JuXinLiReport report) {
        mapper.saveReportInfo(report);
    }

    @Override
    public JuXinLiReport selectReport(Integer userId, Integer applyId, String token, String reportType) {
        return mapper.selectReport(userId, applyId, token, reportType);
    }

}
