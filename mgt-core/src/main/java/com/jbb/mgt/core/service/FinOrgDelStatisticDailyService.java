package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.FinOrgDelStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;

/**
 * @author wyq
 * @date 2018/9/12 20:02
 */
public interface FinOrgDelStatisticDailyService {

    void saveFinOrgDelStatisticDaily(FinOrgDelStatisticDaily mfo);

    FinOrgDelStatisticDaily getFinOrgDelStatisticDaily(String date, int orgId, boolean last);

    List<OrgStatisticDailyInfo> selectOrgDelStatistics(String statisticDate, Integer salesId);

    List<FinOrgDelStatisticDaily> getFinOrgDelStatisticDailys(Integer orgId, String startDate, String endDate);

    void updateFinOrgDelStatisticDaily(FinOrgDelStatisticDaily orgDelStatisticDaily);

    void updateBalance(int balance, int orgId, String statisticDate);

    void selectOrgDelStatistic(String startDateTs, String endDateTs);

    List<OrgStatisticDailyInfo> selectOrgDelStatisticsDaily(String statisticDate, Integer salesId);
}
