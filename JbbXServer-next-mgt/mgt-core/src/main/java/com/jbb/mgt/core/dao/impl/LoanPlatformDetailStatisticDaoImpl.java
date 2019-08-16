package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.LoanPlatformDetailStatisticDao;
import com.jbb.mgt.core.dao.mapper.LoanPlatformDetailStatisticMapper;
import com.jbb.mgt.core.domain.LoanPlatformDetailStatistic;
import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository("LoanPlatformDetailStatisticDao")
public class LoanPlatformDetailStatisticDaoImpl implements LoanPlatformDetailStatisticDao {

    @Autowired
    private LoanPlatformDetailStatisticMapper loanPlatformDetailStatisticMapper;

    @Override
    public List<LoanPlatformDetailStatistic> selectLoanPlatformLDetailStatisticsByDate(Timestamp startDate,
        Timestamp endDate) {
        return loanPlatformDetailStatisticMapper.selectLoanPlatformLDetailStatisticsByDate(startDate, endDate);
    }

    @Override
    public void insertLoanPlatformDetailStatistics(List<LoanPlatformDetailStatistic> list) {
        loanPlatformDetailStatisticMapper.insertLoanPlatformDetailStatistics(list);
    }

    @Override
    public void insertLoanPlatformDetailStatistic(LoanPlatformDetailStatistic loanPlatformDetailStatistic) {
        loanPlatformDetailStatisticMapper.insertLoanPlatformDetailStatistic(loanPlatformDetailStatistic);
    }

    @Override
    public void updateLoanPlatformDetailStatistic(LoanPlatformDetailStatistic loanPlatformDetailStatistic) {
        loanPlatformDetailStatisticMapper.updateLoanPlatformDetailStatistic(loanPlatformDetailStatistic);
    }

    @Override
    public List<LoanPlatformStatistic> selectLoanPlatformDetailStatistic(Integer platformId, Date startDate,
        Date endDate, Integer salesId) {
        return loanPlatformDetailStatisticMapper.selectLoanPlatformDetailStatistic(platformId, startDate, endDate,
            salesId);
    }

    @Override
    public List<LoanPlatformStatistic> selectLoanPlatformDetailStatisticByStatisticDate(String startDate,
        String endDate, Integer salesId) {
        return loanPlatformDetailStatisticMapper.selectLoanPlatformDetailStatisticByStatisticDate(startDate, endDate,
            salesId);
    }
}
