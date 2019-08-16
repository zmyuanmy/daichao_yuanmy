package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.LoanPlatFormStatisticDao;
import com.jbb.mgt.core.dao.mapper.LoanPlatformStatisticMapper;
import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import com.jbb.mgt.core.domain.LoanPlatformStatisticByDate;
import com.jbb.mgt.core.domain.LoanPlatformStatisticByGroup;
import com.jbb.mgt.core.domain.PlatformExpense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository("LoanPlatFormStatisticDao")
public class LoanPlatFormStatisticDaoImpl implements LoanPlatFormStatisticDao {

    @Autowired
    private LoanPlatformStatisticMapper loanPlatformStatisticMapper;

    @Override
    public List<LoanPlatformStatistic> selectPlatformByDate(String statisticDate) {
        return loanPlatformStatisticMapper.selectPlatformByDate(statisticDate);
    }

    @Override
    public List<LoanPlatformStatistic> selectPlatformById(int PlatformId, Timestamp startDate, Timestamp endDate) {
        return loanPlatformStatisticMapper.selectPlatformById(PlatformId, startDate, endDate);
    }

    @Override
    public LoanPlatformStatistic selectPlatformByStaDate(int PlatformId, String statisticDate, Integer day) {
        return loanPlatformStatisticMapper.selectPlatformByStaDate(PlatformId, statisticDate, day);
    }

    @Override
    public void updatePlatform(LoanPlatformStatistic loanPlatformStatistic) {
        loanPlatformStatisticMapper.updatePlatform(loanPlatformStatistic);
    }

    @Override
    public void insertPlatform(LoanPlatformStatistic loanPlatformStatistic) {
        loanPlatformStatisticMapper.insertPlatform(loanPlatformStatistic);
    }

    @Override
    public void updatePlatformByBalance(int balance, int platformId, Date statisticDate) {
        loanPlatformStatisticMapper.updatePlatformByBalance(balance, platformId, statisticDate);
    }

    @Override
    public List<LoanPlatformStatistic> selectPlatformDaily(int[] PlatformIds, String startDate, String endDate) {
        return loanPlatformStatisticMapper.selectPlatformDaily(PlatformIds, startDate, endDate);
    }

    @Override
    public List<LoanPlatformStatistic> selectPlatformCompare(int[] PlatformIds, String startDate, String endDate) {
        return loanPlatformStatisticMapper.selectPlatformCompare(PlatformIds, startDate, endDate);
    }

    @Override
    public List<LoanPlatformStatistic> selectEventLogByEventValue(Timestamp startDate, Timestamp endDate) {
        return loanPlatformStatisticMapper.selectEventLogByEventValue(startDate, endDate);
    }

    @Override
    public void updateLoanPlatformStatistic(List<LoanPlatformStatistic> list) {
        loanPlatformStatisticMapper.updateLoanPlatformStatistic(list);
    }

    @Override
    public List<LoanPlatformStatistic> selectPlatformByDateAndGroup(String statisticDate, String groupName,
        Integer type, Integer salesId) {
        return loanPlatformStatisticMapper.selectPlatformByDateAndGroup(statisticDate, null, null, null, groupName,
            type, salesId);
    }

    @Override
    public List<LoanPlatformStatistic> selectPlatformGroupById(Integer platformId, Timestamp startDate,
        Timestamp endDate, String groupName, Integer type, Integer salesId) {
        return loanPlatformStatisticMapper.selectPlatformGroupById(platformId, startDate, endDate, groupName, type,
            salesId);
    }

    @Override
    public List<PlatformExpense> getCalculatePlatFormExpense(Timestamp startDate, Timestamp endDate,
        boolean calculateFlag) {
        return loanPlatformStatisticMapper.getCalculatePlatFormExpense(startDate, endDate, calculateFlag);
    }

    @Override
    public void updatePlatFormExpense(PlatformExpense platformExpense) {
        loanPlatformStatisticMapper.updatePlatFormExpense(platformExpense);

    }

    @Override
    public boolean checkExecute(Timestamp startDate) {
        return loanPlatformStatisticMapper.checkExecute(startDate) > 0;
    }

    @Override
    public void saveExecuteLog(Timestamp startDate, Timestamp creationDate) {
        loanPlatformStatisticMapper.saveExecuteLog(startDate, creationDate);

    }
}
