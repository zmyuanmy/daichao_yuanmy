package com.jbb.mgt.core.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.MgtFinOrgStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.OrganizationIncludeSales;

public interface MgtFinOrgStatisticDailyDao {

    /**
     * insert
     *
     * @param mgtFinOrgStatisticDaily
     * @return
     */
    void insertMgtFinOrgStatisticDaily(MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily);

    /**
     * 更改状态
     *
     * @param statisticDate
     * @param orgId
     * @param status
     * @return
     */
    boolean updateMgtFinOrgStatisticDaily(Date statisticDate, int orgId, int status, int type);

    List<Organization> selectOrganizations(boolean detail, boolean getStatistic, Timestamp startDate, Timestamp endDate,
        Integer orgId);

    /**
     * 获取当前组织的最新一条历史记录
     *
     * @param orgId
     * @param type
     * @return
     */
    MgtFinOrgStatisticDaily selectLastMgtFinOrgStatisticDaily(int orgId, int type);

    List<OrgStatisticDailyInfo> selectMgtFinOrgStatisticDaily(Integer orgId, Integer type, Date startDate, Date endDate,
        Integer accountId);

    List<MgtFinOrgStatisticDaily> selectMgtFinOrgStatisticDailyByOrgId(Integer orgId, Integer type, Date startDate,
        Date endDate, boolean includeEndDate);

    void updateMgtFinOrgStatisticDailyAmount(int orgId, int type, Date statisticDate, int amount, int price,
        int expense, int balance, int status, int manulAmount);

    void updateMgtFinOrgStatisticDailyAmountAll(int orgId, int type, Date statisticDate, int changeBalance);

    MgtFinOrgStatisticDaily selectMgtFinOrgStatisticDailyByUnionKey(int orgId, int type, Date statisticDate);

    List<OrganizationIncludeSales> selectMgtFinOrgStatisticDailyNow(Integer orgId, boolean detail, boolean getStatistic,
        Timestamp startDate, Timestamp endDate, Integer accountId, boolean getAdStatistic);

    List<MgtFinOrgStatisticDaily> selectMgtFinOrgStatisticDailyByDate(Integer type, Date statisticDate);

    void updateMgtFinOrgStatisticDailyList(List<MgtFinOrgStatisticDaily> list);
}
