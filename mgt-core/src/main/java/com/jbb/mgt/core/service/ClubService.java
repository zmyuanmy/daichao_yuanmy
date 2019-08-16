package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.ClubReport;

public interface ClubService {

    boolean saveNotify(String event, String type, String time, String data, String sign, String params);

    void saveReport(ClubReport report);

    void saveReport(Integer userId, String taskId);

    ClubReport getReport(int userId, String channelType, String channelCode,Timestamp startDate,Timestamp endDate);

    ClubReport getReport(String taskId);

    ClubReport getReport(String identityCode, String realName, String channelType, String channelCode, String username);

    List<ClubReport> getClubReportStatus(int userId, Timestamp startTs, Timestamp endTs, String[] channelTypes);

}
