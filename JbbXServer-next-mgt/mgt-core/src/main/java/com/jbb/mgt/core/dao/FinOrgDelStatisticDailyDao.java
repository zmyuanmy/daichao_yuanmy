package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.FinOrgDelStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;

/**
 * @author wyq
 * @date 2018/9/12 20:26
 */
public interface FinOrgDelStatisticDailyDao {

    void insertFinOrgDelStatisticDaily(FinOrgDelStatisticDaily mfo);

    FinOrgDelStatisticDaily selectFinOrgDelStatisticDaily(String statisticDate, int orgId, boolean last);

    List<OrgStatisticDailyInfo> selectOrgDelStatistics(String statisticDate, Integer salesId);

    List<FinOrgDelStatisticDaily> getFinOrgDelStatisticDailys(Integer orgId, String startDate, String endDate,
        boolean flag);

    void updateFinOrgDelStatisticDaily(FinOrgDelStatisticDaily orgDelStatisticDaily);

    void updateBalance(int balance, int orgId, String statisticDate);

    void saveFinOrgDelStatisticDaily(FinOrgDelStatisticDaily mfo);

    public List<OrgStatisticDailyInfo> selectOrgDelStatisticsDaily(Integer orgId, Integer salesId, Timestamp startDate,
        Timestamp endDate);

}
