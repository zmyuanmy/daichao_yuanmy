package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.FinFileDao;
import com.jbb.mgt.core.dao.FinOrgDelStatisticDailyDao;
import com.jbb.mgt.core.dao.OrganizationDao;
import com.jbb.mgt.core.domain.FinOrgDelStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.UserAdCounts;
import com.jbb.mgt.core.service.FinOrgDelStatisticDailyService;
import com.jbb.mgt.core.service.MgtFinOrgStatisticDailyService;
import com.jbb.server.common.util.DateFormatUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

/**
 * @author wyq
 * @date 2018/9/12 20:25
 */
@Service("FinOrgDelStatisticDailyService")
public class FinOrgDelStatisticDailyServiceImpl implements FinOrgDelStatisticDailyService {

    // 代理为3
    private static final int TYPE = 3;

    @Autowired
    private FinOrgDelStatisticDailyDao dao;
    @Autowired
    private MgtFinOrgStatisticDailyService mgtFinOrgStatisticDailyService;

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private FinFileDao finFileDao;

    @Override
    public void saveFinOrgDelStatisticDaily(FinOrgDelStatisticDaily mfo) {
        dao.saveFinOrgDelStatisticDaily(mfo);
    }

    @Override
    public FinOrgDelStatisticDaily getFinOrgDelStatisticDaily(String date, int orgId, boolean last) {
        return dao.selectFinOrgDelStatisticDaily(date, orgId, last);
    }

    @Override
    public List<OrgStatisticDailyInfo> selectOrgDelStatistics(String statisticDate, Integer salesId) {
        return dao.selectOrgDelStatistics(statisticDate, salesId);
    }

    @Override
    public List<FinOrgDelStatisticDaily> getFinOrgDelStatisticDailys(Integer orgId, String startDate, String endDate) {
        long today = DateUtil.getTodayStartTs();
        Timestamp date1 = Util.parseTimestamp(startDate, TimeZone.getDefault());
        long l1 = date1.getTime();
        long l2 = DateUtil.getCurrentTime();
        Date date2 = new Date();
        String todayString = new SimpleDateFormat("yyyy-MM-dd").format(date2);
        if (endDate != null) {
            Timestamp date3 = Util.parseTimestamp(endDate, TimeZone.getDefault());
            l2 = date3.getTime();
        }
        boolean flag = true;
        if (l1 <= today && l2 >= today) {
            endDate = todayString;
            flag = false;
        }
        List<FinOrgDelStatisticDaily> list = dao.getFinOrgDelStatisticDailys(orgId, startDate, endDate, flag);
        if (l1 <= today && l2 >= today) {
            List<Organization> organizations = organizationDao.selectOrgAdByOrgId(orgId, false, false, true,
                new Timestamp(today), new Timestamp(l2));
            FinOrgDelStatisticDaily ods = new FinOrgDelStatisticDaily();
            ods.setOrgId(orgId);
            int price = organizations.get(0).getDelegatePrice();

            FinOrgDelStatisticDaily orgDelStatistic = dao.selectFinOrgDelStatisticDaily(todayString, orgId, false);
            if (orgDelStatistic != null) {
                ods.setAmount(orgDelStatistic.getAmount());
                ods.setStatus(orgDelStatistic.getStatus());
            }

            UserAdCounts userAdCounts = organizations.get(0).getDateAdCounts().size() != 0
                ? organizations.get(0).getDateAdCounts().get(0) : new UserAdCounts();
            int adCnt = userAdCounts.getAdCnt();
            ods = generateOrgDel(todayString, adCnt, price, ods);
            ods.setFiles(finFileDao.selectFinFile(orgId, new java.sql.Date(today), TYPE));
            list.add(ods);
        }

        return list;

    }

    private FinOrgDelStatisticDaily generateOrgDel(String startDate, int adCnt, int price,
        FinOrgDelStatisticDaily ods) {
        FinOrgDelStatisticDaily daily = dao.selectFinOrgDelStatisticDaily(startDate, ods.getOrgId(), true);
        ods.setCnt(adCnt);
        ods.setStatisticDate(DateFormatUtil.stringToDate(startDate));
        ods.setPrice(price);
        ods.setExpense(adCnt * price);
        int balance = null != daily ? daily.getBalance() : 0;
        ods.setBalance(balance + ods.getAmount() - adCnt * price);
        return ods;
    }

    @Override
    public void updateFinOrgDelStatisticDaily(FinOrgDelStatisticDaily orgDelStatisticDaily) {
        dao.updateFinOrgDelStatisticDaily(orgDelStatisticDaily);
    }

    @Override
    public void updateBalance(int balance, int orgId, String statisticDate) {
        dao.updateBalance(balance, orgId, statisticDate);
    }

    @Override
    public void selectOrgDelStatistic(String startDate, String endDate) {
        Timestamp startDateTs = Util.parseTimestamp(startDate, TimeZone.getDefault());
        Timestamp endDateTs = Util.parseTimestamp(endDate, TimeZone.getDefault());
        List<Organization> organizations
            = organizationDao.selectOrganizations(false, false, true, startDateTs, endDateTs, true);
        if (organizations.size() > 0) {
            for (Organization organization : organizations) {
                if (organization != null && organization.getOrgId() != 0) {
                    FinOrgDelStatisticDaily orgDelStatistic
                        = dao.selectFinOrgDelStatisticDaily(startDate, organization.getOrgId(), false);
                    orgDelStatistic = orgDelStatistic != null ? orgDelStatistic : new FinOrgDelStatisticDaily();
                    UserAdCounts userAdCounts = organization.getDateAdCounts().size() != 0
                        ? organization.getDateAdCounts().get(0) : new UserAdCounts();
                    int adCnt = userAdCounts.getAdCnt();
                    int price = organization.getDelegatePrice();
                    orgDelStatistic.setOrgId(organization.getOrgId());
                    orgDelStatistic = generateOrgDel(startDate, adCnt, price, orgDelStatistic);
                    dao.insertFinOrgDelStatisticDaily(orgDelStatistic);
                }
            }
        }
    }

    @Override
    public List<OrgStatisticDailyInfo> selectOrgDelStatisticsDaily(String statisticDate, Integer salesId) {
        Timestamp startDateTs = Util.parseTimestamp(statisticDate, TimeZone.getDefault());
        List<OrgStatisticDailyInfo> OrgStatisticDailyInfo
            = dao.selectOrgDelStatisticsDaily(null, salesId, startDateTs, DateUtil.getCurrentTimeStamp());
        for (OrgStatisticDailyInfo orgStaInfo : OrgStatisticDailyInfo) {
            FinOrgDelStatisticDaily orgDel = orgStaInfo.getOrgDelStatisticDaily() != null
                ? orgStaInfo.getOrgDelStatisticDaily() : new FinOrgDelStatisticDaily();
            int adCnt = orgDel.getCnt();
            int price = orgDel.getPrice();
            generateOrgDel(statisticDate, adCnt, price, orgDel);
        }
        return OrgStatisticDailyInfo;
    }

}
