package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.LoanChannelStatisticDailyDao;
import com.jbb.mgt.core.dao.mapper.LoanChannelStatisticDailyMapper;
import com.jbb.mgt.core.domain.LoanChannelStatistic;
import com.jbb.mgt.core.domain.LoanChannelStatisticDaily;

@Repository("LoanChannelStatisticDailyDao")
public class LoanChannelStatisticDailyDaoImpl implements LoanChannelStatisticDailyDao {

    @Autowired
    private LoanChannelStatisticDailyMapper loanChannelStatisticDailyMapper;

    @Override
    public void insertLoanChannelStatisticDailys(List<LoanChannelStatisticDaily> list) {
        loanChannelStatisticDailyMapper.insertLoanChannelStatisticDailys(list);
    }

    @Override
    public List<LoanChannelStatisticDaily> selectLoanChannelStatisticByDate(Timestamp startDate, Timestamp endDate) {
        return loanChannelStatisticDailyMapper.selectLoanChannelStatisticByDate(startDate, endDate);
    }

    @Override
    public List<LoanChannelStatistic> selectLoanChannelStatisticCompare(String startDate, String endDate) {
        return loanChannelStatisticDailyMapper.selectLoanChannelStatisticCompare(startDate, endDate);
    }

    @Override
    public List<LoanChannelStatistic> selectLoanChannelStatisticByChannelCode(String[] channelCodes, String startDate,
        String endDate) {
        return loanChannelStatisticDailyMapper.selectLoanChannelStatisticByChannelCode(channelCodes, startDate,
            endDate);
    }
}
