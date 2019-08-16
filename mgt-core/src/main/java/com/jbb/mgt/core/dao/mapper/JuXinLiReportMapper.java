package com.jbb.mgt.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.JuXinLiReport;

public interface JuXinLiReportMapper {

    void saveReportInfo(JuXinLiReport report);

    JuXinLiReport selectReport(@Param(value = "userId") Integer userId, @Param(value = "applyId") Integer applyId,
        @Param(value = "token") String token, @Param(value = "reportType") String reportType);

}
