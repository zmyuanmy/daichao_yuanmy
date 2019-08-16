package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.FinMerchantStatisticDaily;

public interface FinMerchantStatisticDailyMapper {
    // 获取统计数据
    List<FinMerchantStatisticDaily> selectMerchantByDate(@Param("statisticDate") String statisticDate);

    // 按h5商家和日期范围获取单个商家的统计数据列表
    List<FinMerchantStatisticDaily> selectMerchantById(@Param("merchantId") int merchantId,
        @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    // 根据商家和统计时间查询
    FinMerchantStatisticDaily selectMerchantByStaDate(@Param("merchantId") int merchantId,
        @Param("statisticDate") String statisticDate, @Param("day") Integer day);

    // 修改记录
    void updateMerchant(FinMerchantStatisticDaily merchant);

    // 新增记录
    void insertMerchant(FinMerchantStatisticDaily merchant);

    // 修改余额
    void updateMerchantByBalance(@Param("balance") int balance, @Param("merchantId") int merchantId,
        @Param("statisticDate") Date statisticDate);

    List<FinMerchantStatisticDaily> selectMerchantDaily(@Param("merchantIds") int[] merchantIds,
        @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<FinMerchantStatisticDaily> selectMerchantCompare(@Param("merchantIds") int[] merchantIds,
        @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<FinMerchantStatisticDaily> selectEventLogByEventValue(@Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);

    void updateMerchantList(@Param("list") List<FinMerchantStatisticDaily> list);
}
