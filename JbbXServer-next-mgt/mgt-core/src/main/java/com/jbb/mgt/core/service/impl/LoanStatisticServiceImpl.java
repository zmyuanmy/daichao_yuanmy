package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.LoanStatisticDao;
import com.jbb.mgt.core.domain.LoanStatistic;
import com.jbb.mgt.core.service.LoanStatisticService;

@Service("LoanStatisticService")
public class LoanStatisticServiceImpl implements LoanStatisticService {
    @Autowired
    private LoanStatisticDao loanStatisticDao;

    @Override
    public List<LoanStatistic> getLoanStatistics(Timestamp startDate, Timestamp endDate, Integer orgId) {
        return loanStatisticDao.getLoanStatistics(startDate, endDate, orgId);
    }

}
