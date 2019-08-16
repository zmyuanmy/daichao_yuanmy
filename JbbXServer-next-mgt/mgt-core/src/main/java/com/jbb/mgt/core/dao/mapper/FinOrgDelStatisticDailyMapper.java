package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.FinOrgDelStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;

/**
 * @author wyq
 * @date 2018/9/12 20:31
 */
public interface FinOrgDelStatisticDailyMapper {

    void insertFinOrgDelStatisticDaily(FinOrgDelStatisticDaily mfo);

    FinOrgDelStatisticDaily selectFinOrgDelStatisticDaily(@Param("statisticDate") String statisticDate,
        @Param("orgId") int orgId, @Param("last") boolean last);

    List<OrgStatisticDailyInfo> selectOrgDelStatistics(@Param("statisticDate") String statisticDate,
        @Param("salesId") Integer salesId);

    List<FinOrgDelStatisticDaily> getFinOrgDelStatisticDailys(@Param("orgId") Integer orgId,
        @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("flag") boolean flag);

    void updateFinOrgDelStatisticDaily(FinOrgDelStatisticDaily orgDelStatisticDaily);

    void updateBalance(@Param("balance") int balance, @Param("orgId") int orgId,
        @Param("statisticDate") String statisticDate);

    void saveFinOrgDelStatisticDaily(FinOrgDelStatisticDaily mfo);

    List<OrgStatisticDailyInfo> selectOrgDelStatisticsDaily(@Param("orgId") Integer orgId,
        @Param("salesId") Integer salesId, @Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate);

}
