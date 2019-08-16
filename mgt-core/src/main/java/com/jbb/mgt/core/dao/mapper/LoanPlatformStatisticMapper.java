package com.jbb.mgt.core.dao.mapper;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import com.jbb.mgt.core.domain.LoanPlatformStatisticByGroup;
import com.jbb.mgt.core.domain.PlatformExpense;

public interface LoanPlatformStatisticMapper {

    // 获取统计数据
    List<LoanPlatformStatistic> selectPlatformByDate(@Param("statisticDate") String statisticDate);

    // 按h5商家和日期范围获取单个商家的统计数据列表
    List<LoanPlatformStatistic> selectPlatformById(@Param("platformId") int platformId,
        @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    // 根据商家和统计时间查询
    LoanPlatformStatistic selectPlatformByStaDate(@Param("platformId") int platformId,
        @Param("statisticDate") String statisticDate, @Param("day") Integer day);

    // 修改记录
    void updatePlatform(LoanPlatformStatistic loanPlatformStatistic);

    // 新增记录
    void insertPlatform(LoanPlatformStatistic loanPlatformStatistic);

    // 修改余额
    void updatePlatformByBalance(@Param("balance") int balance, @Param("platformId") int platformId,
        @Param("statisticDate") Date statisticDate);

    List<LoanPlatformStatistic> selectPlatformDaily(@Param("platformIds") int[] platformIds,
        @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<LoanPlatformStatistic> selectPlatformCompare(@Param("platformIds") int[] merchantIds,
        @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<LoanPlatformStatistic> selectEventLogByEventValue(@Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);

    void updateLoanPlatformStatistic(@Param("list") List<LoanPlatformStatistic> list);

    List<LoanPlatformStatistic> selectPlatformByDateAndGroup(@Param("statisticDate") String statisticDate,
        @Param("platformId") Integer platformId, @Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate, @Param("groupName") String groupName, @Param("type") Integer type,
        @Param("salesId") Integer salesId);

    List<LoanPlatformStatistic> selectPlatformGroupById(@Param("platformId") Integer platformId,
        @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate,
        @Param("groupName") String groupName, @Param("type") Integer type, @Param("salesId") Integer salesId);

    List<PlatformExpense> getCalculatePlatFormExpense(@Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate, @Param("calculateFlag") boolean calculateFlag);

    void updatePlatFormExpense(PlatformExpense platformExpense);

    int checkExecute(@Param("startDate") Timestamp startDate);

    void saveExecuteLog(@Param("startDate") Timestamp startDate, @Param("creationDate") Timestamp creationDate);
}
