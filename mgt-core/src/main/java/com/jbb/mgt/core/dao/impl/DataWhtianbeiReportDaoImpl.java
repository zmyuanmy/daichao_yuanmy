package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.DataWhtianbeiReportDao;
import com.jbb.mgt.core.dao.mapper.DataWhtianbeiReportMapper;
import com.jbb.mgt.core.domain.DataWhtianbeiReport;

@Repository("DataWhtianbeiReportDao")
public class DataWhtianbeiReportDaoImpl implements DataWhtianbeiReportDao {
    @Autowired
    private DataWhtianbeiReportMapper mapper;

    @Override
    public DataWhtianbeiReport getDataWhtianbeiReportById(Integer userId) {
        return mapper.getDataWhtianbeiReportById(userId);
    }

    @Override
    public void insetDataWhtianbeiReport(DataWhtianbeiReport report) {
        mapper.insetDataWhtianbeiReport(report);
    }

    @Override
    public DataWhtianbeiReport getTbReportByIdAndorgId(Integer userId, int orgId) {
        return mapper.getTbReportByIdAndorgId(userId, orgId);
    }
}
