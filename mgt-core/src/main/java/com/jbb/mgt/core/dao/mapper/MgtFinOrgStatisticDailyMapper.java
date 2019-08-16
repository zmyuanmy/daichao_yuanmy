package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.MgtFinOrgStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;
import com.jbb.mgt.core.domain.OrganizationIncludeSales;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface MgtFinOrgStatisticDailyMapper {

    /**
     * insert
     *
     * @param mgtFinOrgStatisticDaily
     * @return
     */
    int insertMgtFinOrgStatisticDaily(MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily);

    /**
     * 更改状态
     *
     * @param statisticDate
     * @param orgId
     * @param status
     * @return
     */
    int updateMgtFinOrgStatisticDaily(@Param("statisticDate") Date statisticDate, @Param("orgId") int orgId,
        @Param("status") int status, @Param("type") int type);

    MgtFinOrgStatisticDaily selectLastMgtFinOrgStatisticDaily(@Param("orgId") int orgId, @Param("type") int type);

    List<OrgStatisticDailyInfo> selectMgtFinOrgStatisticDaily(@Param("orgId") Integer orgId,
        @Param("type") Integer type, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
        @Param(value = "accountId") Integer accountId);

    List<MgtFinOrgStatisticDaily> selectMgtFinOrgStatisticDailyByOrgId(@Param("orgId") Integer orgId,
        @Param("type") Integer type, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
        @Param("includeEndDate") boolean includeEndDate);

    void updateMgtFinOrgStatisticDaily(@Param("orgId") int orgId, @Param("type") int type, @Param("date") Date date,
        @Param("status") int status);

    void updateMgtFinOrgStatisticDailyAmount(@Param("orgId") int orgId, @Param("type") int type,
        @Param("statisticDate") Date statisticDate, @Param("amount") int amount, @Param("price") int price,
        @Param("expense") int expense, @Param("balance") int balance, @Param("status") int status,
        @Param("manulAmount") int manulAmount);

    void updateMgtFinOrgStatisticDailyAmountAll(@Param("orgId") int orgId, @Param("type") int type,
        @Param("statisticDate") Date statisticDate, @Param("changeBalance") int changeBalance);

    MgtFinOrgStatisticDaily selectMgtFinOrgStatisticDailyByUnionKey(@Param("orgId") Integer orgId,
        @Param("type") Integer type, @Param("statisticDate") Date statisticDate);

    List<OrganizationIncludeSales> selectMgtFinOrgStatisticDailyNow(@Param(value = "orgId") Integer orgId,
        @Param(value = "detail") boolean detail, @Param(value = "getStatistic") boolean getStatistic,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate,
        @Param(value = "accountId") Integer accountId, @Param(value = "getAdStatistic") boolean getAdStatistic);

    List<MgtFinOrgStatisticDaily> selectMgtFinOrgStatisticDailyByDate(@Param("type") Integer type,
        @Param("statisticDate") Date statisticDate);

    void updateMgtFinOrgStatisticDailyList(@Param("list") List<MgtFinOrgStatisticDaily> list);
}
