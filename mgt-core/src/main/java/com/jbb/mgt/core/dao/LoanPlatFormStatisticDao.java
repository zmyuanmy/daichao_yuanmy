package com.jbb.mgt.core.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import com.jbb.mgt.core.domain.LoanPlatformStatisticByDate;
import com.jbb.mgt.core.domain.LoanPlatformStatisticByGroup;
import com.jbb.mgt.core.domain.PlatformExpense;

public interface LoanPlatFormStatisticDao {

    /*    void insertLoanPlatformStatistics(List<LoanPlatformStatistic> list);
    
    void insertLoanPlatformStatistic(LoanPlatformStatistic loanPlatformStatistic);
    
    void updateLoanPlatformStatistic(LoanPlatformStatistic loanPlatformStatistic);
    
    
    List<LoanPlatformStatistic> selectLoanPlatformStatisticsHistory(Date startDate, Date endDate, Integer platformId);
    
    
    List<LoanPlatformStatistic> selectLoanPlatformStatisticsByDate(Timestamp startDate, Timestamp endDate,Integer platformId);*/

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

    void updateLoanPlatformStatistic(List<LoanPlatformStatistic> list);

    List<LoanPlatformStatistic> selectPlatformByDateAndGroup(String statisticDate, String groupName,
        Integer type, Integer salesId);

    List<LoanPlatformStatistic> selectPlatformGroupById(Integer platformId, Timestamp startDate,
        Timestamp endDate, String groupName, Integer type, Integer salesId);

    List<PlatformExpense> getCalculatePlatFormExpense(Timestamp startDate, Timestamp endDate, boolean calculateFlag);

    void updatePlatFormExpense(PlatformExpense platformExpense);

    boolean checkExecute(Timestamp startDate);

    void saveExecuteLog(Timestamp startDate, Timestamp currentTimeStamp);
}
