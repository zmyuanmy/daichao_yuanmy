package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.jbb.mgt.core.domain.FinMerchantStatisticDaily;

public interface FinMerchantStatisticDailyDao {

    // 获取统计数据
    List<FinMerchantStatisticDaily> selectMerchantByDate(String statisticDate);

    // 按h5商家和日期范围获取单个商家的统计数据列表
    List<FinMerchantStatisticDaily> selectMerchantById(int merchantId, Timestamp startDate, Timestamp endDate);

    // 根据商家和统计时间查询
    FinMerchantStatisticDaily selectMerchantByStaDate(int merchantId, String statisticDate, Integer day);

    // 修改记录
    void updateMerchant(FinMerchantStatisticDaily merchant);

    // 新增记录
    void insertMerchant(FinMerchantStatisticDaily merchant);

    // 修改余额
    void updateMerchantByBalance(int balance, int merchantId, Date statisticDate);

    List<FinMerchantStatisticDaily> selectMerchantDaily(int[] merchantIds, String startDate, String endDate);

    List<FinMerchantStatisticDaily> selectMerchantCompare(int[] merchantIds, String startDate, String endDate);

    List<FinMerchantStatisticDaily> selectEventLogByEventValue(Timestamp startDate, Timestamp endDate);

    void updateMerchantList(List<FinMerchantStatisticDaily> list);
}
