package com.jbb.mgt.core.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jbb.mgt.core.dao.LoanPlatFormStatisticDao;
import com.jbb.mgt.core.dao.LoanPlatformDetailStatisticDao;
import com.jbb.mgt.core.domain.LoanPlatformDetailStatistic;
import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import com.jbb.mgt.core.domain.PlatformExpense;
import com.jbb.mgt.core.service.LoanPlatformReportService;
import com.jbb.server.common.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Service("LoanPlatformReportService")
@Slf4j
public class LoanPlatformReportServiceImpl implements LoanPlatformReportService {

    /**
     * 跑批每日数据，分为分为两类 1.跑批贷超总表 2.跑批贷超分类详细表
     */

    @Autowired
    private LoanPlatFormStatisticDao loanPlatFormStatisticDao;

    @Autowired
    private LoanPlatformDetailStatisticDao loanPlatformDetailStatisticDao;

    @Override
    public List<LoanPlatformStatistic> selectPlatformByDate(String statisticDate) {
        return loanPlatFormStatisticDao.selectPlatformByDate(statisticDate);
    }

    @Override
    public List<LoanPlatformStatistic> selectPlatformById(int PlatformId, Timestamp startDate, Timestamp endDate) {
        return loanPlatFormStatisticDao.selectPlatformById(PlatformId, startDate, endDate);
    }

    @Override
    public LoanPlatformStatistic selectPlatformByStaDate(int PlatformId, String statisticDate, Integer day) {
        return loanPlatFormStatisticDao.selectPlatformByStaDate(PlatformId, statisticDate, day);
    }

    @Override
    public void updatePlatform(LoanPlatformStatistic loanPlatformStatistic) {
        loanPlatFormStatisticDao.updatePlatform(loanPlatformStatistic);
    }

    @Override
    public void insertPlatform(LoanPlatformStatistic loanPlatformStatistic) {
        loanPlatFormStatisticDao.insertPlatform(loanPlatformStatistic);
    }

    @Override
    public void updatePlatformByBalance(int balance, int platformId, Date statisticDate) {
        loanPlatFormStatisticDao.updatePlatformByBalance(balance, platformId, statisticDate);
    }

    @Override
    public List<LoanPlatformStatistic> selectPlatformDaily(int[] PlatformIds, String startDate, String endDate) {
        return loanPlatFormStatisticDao.selectPlatformDaily(PlatformIds, startDate, endDate);
    }

    @Override
    public List<LoanPlatformStatistic> selectPlatformCompare(int[] PlatformIds, String startDate, String endDate) {
        return loanPlatFormStatisticDao.selectPlatformCompare(PlatformIds, startDate, endDate);
    }

    @Override
    public List<LoanPlatformStatistic> selectEventLogByEventValue(Timestamp startDate, Timestamp endDate) {
        return loanPlatFormStatisticDao.selectEventLogByEventValue(startDate, endDate);
    }

    @Override
    public void runLoanPlatFormStatistic(Timestamp startDate, Timestamp endDate, boolean calculateFlag) {
        List<LoanPlatformStatistic> list = loanPlatFormStatisticDao.selectEventLogByEventValue(startDate, endDate);
        if (list.size() != 0) {
            for (LoanPlatformStatistic loanPlatformStatistic : list) {
                if (loanPlatformStatistic != null && loanPlatformStatistic.getStatisticDate() != null
                    && loanPlatformStatistic.getPlatformId() > 0) {

                    LoanPlatformStatistic loanPlatformStatistic1
                        = loanPlatFormStatisticDao.selectPlatformByStaDate(loanPlatformStatistic.getPlatformId(),
                            new SimpleDateFormat("yyyy-MM-dd").format(loanPlatformStatistic.getStatisticDate()), null);

                    int balance = null != loanPlatformStatistic1 ? loanPlatformStatistic1.getBalance() : 0;
                    loanPlatformStatistic
                        .setBalance(balance + loanPlatformStatistic.getAmount() - loanPlatformStatistic.getExpense());
                    loanPlatFormStatisticDao.insertPlatform(loanPlatformStatistic);
                }
            }
        }
        //计算余额
        CalculatePlatFormExpense(startDate, endDate, calculateFlag);
        /*Thread asyncThread = new Thread("asyncDataHandlerThread") {
            public void run() {
                log.debug("异步计算消耗额...");
                try {
                    CalculatePlatFormExpense(startDate, endDate, calculateFlag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        
        };
        asyncThread.start();*/

    }

   // @Transactional(propagation = Propagation.REQUIRED)
    private void CalculatePlatFormExpense(Timestamp startDate, Timestamp endDate, boolean calculateFlag) {
        try {
            boolean flag = loanPlatFormStatisticDao.checkExecute(startDate);
            if (!flag) {
                List<PlatformExpense> list
                    = loanPlatFormStatisticDao.getCalculatePlatFormExpense(startDate, endDate, calculateFlag);
                for (PlatformExpense platformExpense : list) {
                    loanPlatFormStatisticDao.updatePlatFormExpense(platformExpense);
                }
                if (calculateFlag) {
                    loanPlatFormStatisticDao.saveExecuteLog(startDate, DateUtil.getCurrentTimeStamp());
                }
            }
        } catch (Exception e) {
            log.error("error in CalculatePlatFormExpense");
        }

    }

    @Override
    public void runLoanPlatFormDetailStatistic(Timestamp startDate, Timestamp endDate) {
        List<LoanPlatformDetailStatistic> loanPlatformDetailStatistics
            = loanPlatformDetailStatisticDao.selectLoanPlatformLDetailStatisticsByDate(startDate, endDate);
        if (loanPlatformDetailStatistics != null && loanPlatformDetailStatistics.size() != 0) {
            for (LoanPlatformDetailStatistic loanPlatformDetailStatistic : loanPlatformDetailStatistics) {
                loanPlatformDetailStatistic.setStatisticDate(new Date(startDate.getTime()));
            }
            loanPlatformDetailStatisticDao.insertLoanPlatformDetailStatistics(loanPlatformDetailStatistics);
        }
    }

    @Override
    public List<LoanPlatformStatistic> selectLoanPlatformDetailStatistic(Integer platformId, Date startDate,
        Date endDate, Integer salesId) {
        return loanPlatformDetailStatisticDao.selectLoanPlatformDetailStatistic(platformId, startDate, endDate,
            salesId);
    }

    @Override
    public List<LoanPlatformStatistic> selectLoanPlatformDetailStatisticByStatisticDate(String startDate,
        String endDate, Integer salesId) {
        return loanPlatformDetailStatisticDao.selectLoanPlatformDetailStatisticByStatisticDate(startDate, endDate,
            salesId);
    }

    @Override
    public void updateLoanPlatformStatistic(List<LoanPlatformStatistic> list) {
        loanPlatFormStatisticDao.updateLoanPlatformStatistic(list);
    }

    @Override
    public List<LoanPlatformStatistic> selectPlatformByDateAndGroup(String statisticDate, String groupName,
        Integer type, Integer salesId) {
        return loanPlatFormStatisticDao.selectPlatformByDateAndGroup(statisticDate, groupName, type, salesId);
    }

    @Override
    public List<LoanPlatformStatistic> selectPlatformGroupById(Integer platformId, Timestamp startDate,
        Timestamp endDate, String groupName, Integer type, Integer salesId) {
        return loanPlatFormStatisticDao.selectPlatformGroupById(platformId, startDate, endDate, groupName, type,
            salesId);
    }

}
