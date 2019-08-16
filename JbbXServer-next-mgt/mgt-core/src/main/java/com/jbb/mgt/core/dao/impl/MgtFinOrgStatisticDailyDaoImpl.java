package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.MgtFinOrgStatisticDailyDao;
import com.jbb.mgt.core.dao.mapper.MgtFinOrgStatisticDailyMapper;
import com.jbb.mgt.core.dao.mapper.OrganizationMapper;
import com.jbb.mgt.core.domain.MgtFinOrgStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.OrganizationIncludeSales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository("MgtFinOrgStatisticDailyDao")
public class MgtFinOrgStatisticDailyDaoImpl implements MgtFinOrgStatisticDailyDao {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private MgtFinOrgStatisticDailyMapper mgtFinOrgStatisticDailyMapper;

    @Override
    public void insertMgtFinOrgStatisticDaily(MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily) {
        mgtFinOrgStatisticDailyMapper.insertMgtFinOrgStatisticDaily(mgtFinOrgStatisticDaily);
    }

    @Override
    public boolean updateMgtFinOrgStatisticDaily(Date statisticDate, int orgId, int status, int type) {
        return mgtFinOrgStatisticDailyMapper.updateMgtFinOrgStatisticDaily(statisticDate, orgId, status, type) > 0;
    }

    @Override
    public List<Organization> selectOrganizations(boolean detail, boolean getStatistic, Timestamp startDate,
        Timestamp endDate, Integer orgId) {
        return organizationMapper.selectOrganizations(orgId, detail, getStatistic, false, startDate, endDate, true);
    }

    @Override
    public MgtFinOrgStatisticDaily selectLastMgtFinOrgStatisticDaily(int org_id, int type) {
        return mgtFinOrgStatisticDailyMapper.selectLastMgtFinOrgStatisticDaily(org_id, type);
    }

    @Override
    public List<OrgStatisticDailyInfo> selectMgtFinOrgStatisticDaily(Integer orgId, Integer type, Date startDate,
        Date endDate, Integer accountId) {
        return mgtFinOrgStatisticDailyMapper.selectMgtFinOrgStatisticDaily(orgId, type, startDate, endDate, accountId);
    }

    @Override
    public List<MgtFinOrgStatisticDaily> selectMgtFinOrgStatisticDailyByOrgId(Integer orgId, Integer type,
        Date startDate, Date endDate, boolean includeEndDate) {
        return mgtFinOrgStatisticDailyMapper.selectMgtFinOrgStatisticDailyByOrgId(orgId, type, startDate, endDate,
            includeEndDate);
    }

    @Override
    public void updateMgtFinOrgStatisticDailyAmount(int orgId, int type, Date statisticDate, int amount, int price,
        int expense, int balance, int status, int manulAmount) {
        mgtFinOrgStatisticDailyMapper.updateMgtFinOrgStatisticDailyAmount(orgId, type, statisticDate, amount, price,
            expense, balance, status, manulAmount);
    }

    @Override
    public void updateMgtFinOrgStatisticDailyAmountAll(int orgId, int type, Date statisticDate, int changeBalance) {
        mgtFinOrgStatisticDailyMapper.updateMgtFinOrgStatisticDailyAmountAll(orgId, type, statisticDate, changeBalance);
    }

    @Override
    public MgtFinOrgStatisticDaily selectMgtFinOrgStatisticDailyByUnionKey(int orgId, int type, Date statisticDate) {
        return mgtFinOrgStatisticDailyMapper.selectMgtFinOrgStatisticDailyByUnionKey(orgId, type, statisticDate);
    }

    @Override
    public List<OrganizationIncludeSales> selectMgtFinOrgStatisticDailyNow(Integer orgId, boolean detail,
        boolean getStatistic, Timestamp startDate, Timestamp endDate, Integer accountId, boolean getAdStatistic) {
        return mgtFinOrgStatisticDailyMapper.selectMgtFinOrgStatisticDailyNow(orgId, detail, getStatistic, startDate,
            endDate, accountId, getAdStatistic);
    }

    @Override
    public List<MgtFinOrgStatisticDaily> selectMgtFinOrgStatisticDailyByDate(Integer type, Date statisticDate) {
        return mgtFinOrgStatisticDailyMapper.selectMgtFinOrgStatisticDailyByDate(type, statisticDate);
    }

    @Override
    public void updateMgtFinOrgStatisticDailyList(List<MgtFinOrgStatisticDaily> list) {
        mgtFinOrgStatisticDailyMapper.updateMgtFinOrgStatisticDailyList(list);
    }
}
