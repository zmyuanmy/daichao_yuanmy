package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.DataWhtianbeiReport;

public interface DataWhtianbeiReportDao {

    DataWhtianbeiReport getDataWhtianbeiReportById(Integer userId);

    void insetDataWhtianbeiReport(DataWhtianbeiReport report);

    DataWhtianbeiReport getTbReportByIdAndorgId(Integer userId, int orgId);

}
