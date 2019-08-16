package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.FinMerchantStatisticDailyDao;
import com.jbb.mgt.core.dao.mapper.FinMerchantStatisticDailyMapper;
import com.jbb.mgt.core.domain.FinMerchantStatisticDaily;

@Repository("FinMerchantStatisticDailyDao")
public class FinMerchantStatisticDailyDaoImpl implements FinMerchantStatisticDailyDao {

    @Autowired
    private FinMerchantStatisticDailyMapper mapper;

    @Override
    public List<FinMerchantStatisticDaily> selectMerchantByDate(String statisticDate) {
        return mapper.selectMerchantByDate(statisticDate);
    }

    @Override
    public List<FinMerchantStatisticDaily> selectMerchantById(int merchantId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectMerchantById(merchantId, startDate, endDate);
    }

    @Override
    public FinMerchantStatisticDaily selectMerchantByStaDate(int merchantId, String statisticDate, Integer day) {
        return mapper.selectMerchantByStaDate(merchantId, statisticDate, day);
    }

    @Override
    public void updateMerchant(FinMerchantStatisticDaily merchant) {
        mapper.updateMerchant(merchant);
    }

    @Override
    public void insertMerchant(FinMerchantStatisticDaily merchant) {
        mapper.insertMerchant(merchant);
    }

    @Override
    public void updateMerchantByBalance(int balance, int merchantId, Date statisticDate) {
        mapper.updateMerchantByBalance(balance, merchantId, statisticDate);
    }

    @Override
    public List<FinMerchantStatisticDaily> selectMerchantDaily(int[] merchantIds, String startDate, String endDate) {
        return mapper.selectMerchantDaily(merchantIds, startDate, endDate);
    }

    @Override
    public List<FinMerchantStatisticDaily> selectMerchantCompare(int[] merchantIds, String startDate, String endDate) {
        return mapper.selectMerchantCompare(merchantIds, startDate, endDate);
    }

    @Override
    public List<FinMerchantStatisticDaily> selectEventLogByEventValue(Timestamp startDate, Timestamp endDate) {
        return mapper.selectEventLogByEventValue(startDate, endDate);
    }

    @Override
    public void updateMerchantList(List<FinMerchantStatisticDaily> list) {
        mapper.updateMerchantList(list);
    }

}
