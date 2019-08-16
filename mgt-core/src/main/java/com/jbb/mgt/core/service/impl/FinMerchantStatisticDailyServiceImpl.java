package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.FinMerchantStatisticDailyDao;
import com.jbb.mgt.core.domain.FinMerchantStatisticDaily;
import com.jbb.mgt.core.service.FinMerchantStatisticDailyService;

@Service("FinMerchantStatisticDailyService")
public class FinMerchantStatisticDailyServiceImpl implements FinMerchantStatisticDailyService {

    @Autowired
    private FinMerchantStatisticDailyDao finMerchantStatisticDailyDao;

    @Override
    public List<FinMerchantStatisticDaily> selectMerchantByDate(String statisticDate) {
        return finMerchantStatisticDailyDao.selectMerchantByDate(statisticDate);
    }

    @Override
    public List<FinMerchantStatisticDaily> selectMerchantById(int merchantId, Timestamp startDate, Timestamp endDate) {
        return finMerchantStatisticDailyDao.selectMerchantById(merchantId, startDate, endDate);
    }

    @Override
    public FinMerchantStatisticDaily selectMerchantByStaDate(int merchantId, String statisticDate, Integer day) {
        return finMerchantStatisticDailyDao.selectMerchantByStaDate(merchantId, statisticDate, day);
    }

    @Override
    public void updateMerchant(FinMerchantStatisticDaily merchant) {
        finMerchantStatisticDailyDao.updateMerchant(merchant);
    }

    @Override
    public void insertMerchant(FinMerchantStatisticDaily merchant) {
        finMerchantStatisticDailyDao.insertMerchant(merchant);
    }

    @Override
    public void updateMerchantByBalance(int balance, int merchantId, Date statisticDate) {
        finMerchantStatisticDailyDao.updateMerchantByBalance(balance, merchantId, statisticDate);
    }

    @Override
    public List<FinMerchantStatisticDaily> selectMerchantDaily(int[] merchantIds, String startDate, String endDate) {
        return finMerchantStatisticDailyDao.selectMerchantDaily(merchantIds, startDate, endDate);
    }

    @Override
    public List<FinMerchantStatisticDaily> selectMerchantCompare(int[] merchantIds, String startDate, String endDate) {
        return finMerchantStatisticDailyDao.selectMerchantCompare(merchantIds, startDate, endDate);
    }

    @Override
    public List<FinMerchantStatisticDaily> selectEventLogByEventValue(Timestamp startDate, Timestamp endDate) {
        return finMerchantStatisticDailyDao.selectEventLogByEventValue(startDate, endDate);
    }

    @Override
    public void updateMerchantList(List<FinMerchantStatisticDaily> list) {
        finMerchantStatisticDailyDao.updateMerchantList(list);
    }

}
