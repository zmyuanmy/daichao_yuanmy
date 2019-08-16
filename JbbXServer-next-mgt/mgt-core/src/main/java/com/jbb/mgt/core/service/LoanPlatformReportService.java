package com.jbb.mgt.core.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import com.jbb.mgt.core.domain.LoanPlatformStatisticByDate;
import com.jbb.mgt.core.domain.LoanPlatformStatisticByGroup;

public interface LoanPlatformReportService {

    void runLoanPlatFormStatistic(Timestamp startDate, Timestamp endDate, boolean calculateFlag);

    // 获取统计数据
    List<LoanPlatformStatistic> selectPlatformByDate(String statisticDate);

    // 按h5商家和日期范围获取单个商家的统计数据列表
    List<LoanPlatformStatistic> selectPlatformById(int PlatformId, Timestamp startDate, Timestamp endDate);

    // 根据商家和统计时间查询
    LoanPlatformStatistic selectPlatformByStaDate(int PlatformId, String statisticDate, Integer day);

    // 修改记录
    void updatePlatform(LoanPlatformStatistic loanPlatformStatistic);

    // 新增记录
    void insertPlatform(LoanPlatformStatistic loanPlatformStatistic);

    // 修改余额
    void updatePlatformByBalance(int balance, int platformId, Date statisticDate);

    List<LoanPlatformStatistic> selectPlatformDaily(int[] PlatformIds, String startDate, String endDate);

    List<LoanPlatformStatistic> selectPlatformCompare(int[] PlatformIds, String startDate, String endDate);

    List<LoanPlatformStatistic> selectEventLogByEventValue(Timestamp startDate, Timestamp endDate);

    // 以下为详细报表的内容
    void runLoanPlatFormDetailStatistic(Timestamp startDate, Timestamp endDate);

    List<LoanPlatformStatistic> selectLoanPlatformDetailStatistic(Integer platformId, Date startDate, Date endDate,
        Integer salesId);

    List<LoanPlatformStatistic> selectLoanPlatformDetailStatisticByStatisticDate(String startDate, String endDate,
        Integer salesId);

    /**
     * 一键确认 mgt_loan_platform_statistic表的status变为1
     *
     * @param list
     */
    void updateLoanPlatformStatistic(List<LoanPlatformStatistic> list);

    List<LoanPlatformStatistic> selectPlatformByDateAndGroup(String statisticDate, String groupName,
        Integer type, Integer salesId);

    List<LoanPlatformStatistic> selectPlatformGroupById(Integer platformId, Timestamp startTs, Timestamp endTs,
        String groupName, Integer type, Integer salesId);

}
