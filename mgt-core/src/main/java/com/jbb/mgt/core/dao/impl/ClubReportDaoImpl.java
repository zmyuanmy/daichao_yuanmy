package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.ClubReportDao;
import com.jbb.mgt.core.dao.mapper.ClubReportMapper;
import com.jbb.mgt.core.domain.ClubReport;

@Repository("ClubReportDao")
public class ClubReportDaoImpl implements ClubReportDao {
    @Autowired
    private ClubReportMapper mapper;

    @Override
    public void insertReport(ClubReport report) {
        mapper.insertReport(report);
    }

    @Override
    public ClubReport selectReport(Integer userId, String identityCode, String realName, String channelType,
        String channelCode, String username, String taskId,Timestamp startDate,Timestamp endDate) {
        return mapper.selectReport(userId, identityCode, realName, channelType, channelCode, username, taskId,startDate,endDate);
    }

    @Override
    public void saveReport(ClubReport report) {
        try {
            mapper.insertReport(report);
        } catch (DuplicateKeyException e) {
            mapper.updateReport(report);
        }

    }

    @Override
    public List<ClubReport> selectClubReportStatus(int userId, Timestamp startDate, Timestamp endDate,
        String[] channelTypes) {
        return mapper.selectClubReportStatus(userId, startDate, endDate, channelTypes);
    }

    @Override
    public List<ClubReport> selectClubReports(
       Timestamp startDate,  Timestamp endDate,
         String[] channelTypes){
        return mapper.selectClubReports(startDate, endDate, channelTypes);
    }
}
