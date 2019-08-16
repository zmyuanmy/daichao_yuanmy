package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.ClubReport;

public interface ClubReportDao {

    void insertReport(ClubReport report);

    void saveReport(ClubReport report);

    ClubReport selectReport(Integer userId, String identityCode, String realName, String channelType,
                            String channelCode, String username, String taskId, Timestamp startDate,Timestamp endDate);

    List<ClubReport> selectClubReportStatus(int userId, Timestamp startDate, Timestamp endDate, String[] channelTypes);

    List<ClubReport> selectClubReports(Timestamp startDate, Timestamp endDate, String[] channelTypes);
}
