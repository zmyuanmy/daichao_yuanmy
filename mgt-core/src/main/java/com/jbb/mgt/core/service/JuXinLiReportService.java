package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.DataJuXinLiUrls;
import com.jbb.mgt.core.domain.JuXinLiNotify;
import com.jbb.mgt.core.domain.JuXinLiReport;
import com.jbb.mgt.core.domain.JuXinLiReportRsp;

public interface JuXinLiReportService {

    DataJuXinLiUrls generateH5Url(int userId, int applyId, String reportType);

    void saveReportInfo(JuXinLiReport report);

    JuXinLiReport getReport(Integer userId, Integer applyId, String token, String reportType);

    JuXinLiReportRsp getReportFormJuXinLi(String token);

    void juXinLiAuthorizeNotify(JuXinLiNotify notify);

    void deleteOssRepoert(String token);

}
