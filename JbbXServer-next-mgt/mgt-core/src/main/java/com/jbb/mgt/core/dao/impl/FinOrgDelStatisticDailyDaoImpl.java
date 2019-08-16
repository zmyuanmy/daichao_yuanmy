package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.FinOrgDelStatisticDailyDao;
import com.jbb.mgt.core.dao.mapper.FinOrgDelStatisticDailyMapper;
import com.jbb.mgt.core.domain.FinOrgDelStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;

/**
 * @author wyq
 * @date 2018/9/12 20:31
 */
@Repository("FinOrgDelStatisticDailyDao")
public class FinOrgDelStatisticDailyDaoImpl implements FinOrgDelStatisticDailyDao {

    @Autowired
    private FinOrgDelStatisticDailyMapper mapper;

    @Override
    public void insertFinOrgDelStatisticDaily(FinOrgDelStatisticDaily mfo) {
        mapper.insertFinOrgDelStatisticDaily(mfo);
    }

    @Override
    public FinOrgDelStatisticDaily selectFinOrgDelStatisticDaily(String statisticDate, int orgId, boolean last) {
        return mapper.selectFinOrgDelStatisticDaily(statisticDate, orgId, last);
    }

    @Override
    public List<OrgStatisticDailyInfo> selectOrgDelStatistics(String statisticDate, Integer salesId) {
        return mapper.selectOrgDelStatistics(statisticDate, salesId);
    }

    @Override
    public List<FinOrgDelStatisticDaily> getFinOrgDelStatisticDailys(Integer orgId, String startDate, String endDate,
        boolean flag) {
        return mapper.getFinOrgDelStatisticDailys(orgId, startDate, endDate, flag);
    }

    @Override
    public void updateFinOrgDelStatisticDaily(FinOrgDelStatisticDaily orgDelStatisticDaily) {
        mapper.updateFinOrgDelStatisticDaily(orgDelStatisticDaily);
    }

    @Override
    public void updateBalance(int balance, int orgId, String statisticDate) {
        mapper.updateBalance(balance, orgId, statisticDate);
    }

    @Override
    public void saveFinOrgDelStatisticDaily(FinOrgDelStatisticDaily mfo) {
        mapper.saveFinOrgDelStatisticDaily(mfo);

    }

    @Override
    public List<OrgStatisticDailyInfo> selectOrgDelStatisticsDaily(Integer orgId, Integer salesId, Timestamp startDate,
        Timestamp endDate) {
        return mapper.selectOrgDelStatisticsDaily(orgId, salesId, startDate, endDate);
    }
}
