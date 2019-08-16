package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.LoanChannelStatisticDailyDao;
import com.jbb.mgt.core.domain.LoanChannelStatistic;
import com.jbb.mgt.core.domain.LoanChannelStatisticDaily;
import com.jbb.mgt.core.service.LoanChannelStatisticDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Service("LoanChannelStatisticDailyService")
public class LoanChannelStatisticDailyServiceImpl implements LoanChannelStatisticDailyService {

    @Autowired
    private LoanChannelStatisticDailyDao loanChannelStatisticDailyDao;

    @Override
    public void runLoanChannelStatisticDaily(Timestamp startDate, Timestamp endDate) {
        List<LoanChannelStatisticDaily> loanChannelStatisticDailies
            = loanChannelStatisticDailyDao.selectLoanChannelStatisticByDate(startDate, endDate);
        if (loanChannelStatisticDailies != null && loanChannelStatisticDailies.size() != 0) {
            for (LoanChannelStatisticDaily loanChannelStatisticDaily : loanChannelStatisticDailies) {
                loanChannelStatisticDaily.setStatisticDate(new Date(startDate.getTime()));
            }
            loanChannelStatisticDailyDao.insertLoanChannelStatisticDailys(loanChannelStatisticDailies);
        }
    }

    @Override
    public List<LoanChannelStatistic> selectLoanChannelStatisticCompare(String startDate, String endDate) {
        return loanChannelStatisticDailyDao.selectLoanChannelStatisticCompare(startDate, endDate);
    }

    @Override
    public List<LoanChannelStatistic> selectLoanChannelStatisticByChannelCode(String[] channelCodes, String startDate,
        String endDate) {
        return loanChannelStatisticDailyDao.selectLoanChannelStatisticByChannelCode(channelCodes, startDate, endDate);
    }

}
