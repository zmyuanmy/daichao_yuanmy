package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.JuXinLiReport;

public interface JuXinLiReportDao {

    void saveReportInfo(JuXinLiReport report);

    JuXinLiReport selectReport(Integer userId, Integer applyId, String token, String reportType);

}
