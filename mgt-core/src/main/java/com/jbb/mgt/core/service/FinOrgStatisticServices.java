package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.MgtFinOrgStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;

import java.sql.Date;
import java.util.List;

public interface FinOrgStatisticServices {

    /**
     * 获取指定日期的指定销售的所有注册/进件 统计信息
     * @param statisticDate
     * @param accountId
     * @return
     */
    List<OrgStatisticDailyInfo> getOrgStatistics( Date statisticDate, Integer accountId);

    /**
     * 获取指定组织指定日期范围的所有注册/进件统计信息
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    List<MgtFinOrgStatisticDaily> getOrgStatistics(Integer orgId, Date startDate, Date endDate);

    /**
     * 保存指定组织的 进件、注册信息
     * @param statisticDaily
     */
    void saveOrgStatistic(MgtFinOrgStatisticDaily statisticDaily);
}
