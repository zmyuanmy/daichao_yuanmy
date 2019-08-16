package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.ClubReport;

public interface ClubReportMapper {

    void insertReport(ClubReport report);

    ClubReport selectReport(@Param(value = "userId") Integer userId, @Param(value = "identityCode") String identityCode,
        @Param(value = "realName") String realName, @Param(value = "channelType") String channelType,
        @Param(value = "channelCode") String channelCode, @Param(value = "username") String username,
        @Param(value = "taskId") String taskId, @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate);

    void updateReport(ClubReport report);

    List<ClubReport> selectClubReportStatus(@Param(value = "userId") int userId,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate,
        @Param(value = "channelTypes") String[] channelTypes);
    
    List<ClubReport> selectClubReports(
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate,
        @Param(value = "channelTypes") String[] channelTypes);

}
