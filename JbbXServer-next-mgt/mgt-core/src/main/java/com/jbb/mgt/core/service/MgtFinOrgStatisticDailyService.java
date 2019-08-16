package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.MgtFinOrgStatisticDaily;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface MgtFinOrgStatisticDailyService {

    /**
     * 跑批每日组织销售统计数据
     *
     * @param type 类型 1：进件，2注册 为null时表示两个都查
     * @param orgId 组织id 为null时表示查询所有组织
     * @param startDate
     * @param endDate
     */
    void insertMgtFinOrgStatisticDailyAll(Integer type, Integer orgId, Timestamp startDate, Timestamp endDate);

    void insertMgtFinOrgStatisticDailyAd(Integer type, Integer orgId, Timestamp startDate, Timestamp endDate);

    void checkAndInsertMgtFinOrgStatisticDaily(int orgId, int type, Date statisticDate);

    /**
     * 批量修改状态为确认 status = 1
     *
     * @param list
     */
    void updateMgtFinOrgStatisticDailyList(List<MgtFinOrgStatisticDaily> list);

}
