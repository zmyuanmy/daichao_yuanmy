package com.jbb.mgt.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.DataWhtianbeiReport;

public interface DataWhtianbeiReportMapper {

    DataWhtianbeiReport getDataWhtianbeiReportById(@Param("userId") Integer userId);

    void insetDataWhtianbeiReport(DataWhtianbeiReport report);

    DataWhtianbeiReport getTbReportByIdAndorgId(@Param("userId") Integer userId, @Param("orgId") int orgId);

}
