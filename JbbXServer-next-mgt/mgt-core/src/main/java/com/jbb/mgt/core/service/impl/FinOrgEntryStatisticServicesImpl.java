package com.jbb.mgt.core.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jbb.mgt.core.dao.FinFileDao;
import com.jbb.mgt.core.dao.MgtFinOrgStatisticDailyDao;
import com.jbb.mgt.core.dao.OrgRechargeDetailDao;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.FinFile;
import com.jbb.mgt.core.domain.MgtFinOrgStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.OrganizationIncludeSales;
import com.jbb.mgt.core.domain.UserCounts;
import com.jbb.mgt.core.service.FinOrgStatisticServices;
import com.jbb.server.common.util.DateUtil;

@Service("FinOrgEntryStatisticServices")
public class FinOrgEntryStatisticServicesImpl implements FinOrgStatisticServices {
    // 进件为1
    private static final int TYPE = 1;

    private static Logger logger = LoggerFactory.getLogger(MgtFinOrgStatisticDailyServiceImpl.class);

    @Autowired
    private MgtFinOrgStatisticDailyDao mgtFinOrgStatisticDailyDao;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private OrgRechargeDetailDao orgRechargeDetailDao;

    @Autowired
    private FinFileDao finFileDao;

    @Override
    public List<OrgStatisticDailyInfo> getOrgStatistics(Date statisticDate, Integer accountId) {
        // 如果时间是今天 那么查询实时值，如果时间是历史，查询历史记录
        List<OrgStatisticDailyInfo> orgStatisticDailyInfos = new ArrayList<OrgStatisticDailyInfo>();
        // 判断时间
        Date dateFormat = new Date(DateUtil.getTodayStartCurrentTime());
        if (statisticDate.toString().equals(dateFormat.toString())) {
            Date dateYesterday = new Date(DateUtil.getTodayStartCurrentTime() - DateUtil.DAY_MILLSECONDES);
            List<MgtFinOrgStatisticDaily> lastMgtFinOrgStatisticDailyList
                = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyByDate(TYPE, dateYesterday);
            List<MgtFinOrgStatisticDaily> todayMgtFinOrgStatisticDailyList
                = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyByDate(TYPE, statisticDate);

            // 查询当天的实时数据
            String time = statisticDate.toString() + " 00:00:00";
            List<OrganizationIncludeSales> organizationIncludeSales
                = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyNow(null, true, true,
                    DateUtil.parseStrnew(time), DateUtil.getCurrentTimeStamp(), accountId, false);

            if (organizationIncludeSales != null && organizationIncludeSales.size() != 0) {
                for (OrganizationIncludeSales organizationIncludeSales1 : organizationIncludeSales) {
                    OrgStatisticDailyInfo orgStatisticDailyInfo = new OrgStatisticDailyInfo();

                    // 拼接表格数据
                    MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily = new MgtFinOrgStatisticDaily();

                    mgtFinOrgStatisticDaily.setStatisticDate(statisticDate);
                    mgtFinOrgStatisticDaily.setOrgId(organizationIncludeSales1.getOrgId());
                    mgtFinOrgStatisticDaily.setType(TYPE);
                    List<UserCounts> userCounts = organizationIncludeSales1.getDateCounts();
                    int cnt = 0;
                    if (userCounts != null && userCounts.size() != 0) {
                        cnt = userCounts.get(0).getEntryCnt();
                    }
                    mgtFinOrgStatisticDaily.setCnt(cnt);
                    int defaultPrice = organizationIncludeSales1.getDefaultEntryPrice();
                    int[] array = this.getBalance(organizationIncludeSales1.getOrgId(), lastMgtFinOrgStatisticDailyList,
                        todayMgtFinOrgStatisticDailyList);
                    if (todayMgtFinOrgStatisticDailyList != null && todayMgtFinOrgStatisticDailyList.size() != 0) {
                        List<FinFile> files
                            = finFileDao.selectFinFile(organizationIncludeSales1.getOrgId(), statisticDate, TYPE);
                        if (files != null && files.size() != 0) {
                            mgtFinOrgStatisticDaily.setFiles(files);
                        }
                    }
                    mgtFinOrgStatisticDaily.setPrice(defaultPrice);
                    mgtFinOrgStatisticDaily.setBalance(array[0]);
                    mgtFinOrgStatisticDaily.setManulAmount(array[1]);
                    mgtFinOrgStatisticDaily.setAmount(array[2]);
                    mgtFinOrgStatisticDaily.setExpense(array[3]);
                    mgtFinOrgStatisticDaily.setStatus(array[4]);

                    // 拼接组织跟账号
                    Organization organization = new Organization();
                    Account account = organizationIncludeSales1.getAccount();
                    organization.setName(organizationIncludeSales1.getName());
                    organization.setOrgId(organizationIncludeSales1.getOrgId());
                    organization.setDescription(organizationIncludeSales1.getDescription());
                    orgStatisticDailyInfo.setAccount(account);
                    orgStatisticDailyInfo.setMgtFinOrgStatisticDaily(mgtFinOrgStatisticDaily);
                    orgStatisticDailyInfo.setOrganization(organization);
                    orgStatisticDailyInfos.add(orgStatisticDailyInfo);
                }
            }
        } else {
            // 查询历史
            Date endDate = new Date(statisticDate.getTime() + DateUtil.DAY_MILLSECONDES);
            orgStatisticDailyInfos = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDaily(null, TYPE, statisticDate,
                endDate, accountId);
        }
        return orgStatisticDailyInfos;
    }

    @Override
    public List<MgtFinOrgStatisticDaily> getOrgStatistics(Integer orgId, Date startDate, Date endDate) {
        List<MgtFinOrgStatisticDaily> mgtFinOrgStatisticDailies = new ArrayList<MgtFinOrgStatisticDaily>();

        // 如果时间范围包含今天，那么在集合的第一条先加入今天的拼接数据
        Date dateFormat = new Date(DateUtil.getTodayStartCurrentTime());
        boolean includeEndDate = false;
        if (endDate.toString().equals(dateFormat.toString())) {
            Date dateYesterday = new Date(DateUtil.getTodayStartCurrentTime() - DateUtil.DAY_MILLSECONDES);
            List<MgtFinOrgStatisticDaily> lastMgtFinOrgStatisticDailyList
                = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyByDate(TYPE, dateYesterday);
            List<MgtFinOrgStatisticDaily> todayMgtFinOrgStatisticDailyList
                = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyByDate(TYPE, endDate);

            // 查询实时
            String time = endDate.toString() + " 00:00:00";
            List<OrganizationIncludeSales> organizationIncludeSales
                = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyNow(orgId, true, true,
                    DateUtil.parseStrnew(time), DateUtil.getCurrentTimeStamp(), null, false);

            if (organizationIncludeSales != null && organizationIncludeSales.size() != 0) {
                OrganizationIncludeSales organizationIncludeSales1 = organizationIncludeSales.get(0);

                MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily = new MgtFinOrgStatisticDaily();
                mgtFinOrgStatisticDaily.setStatisticDate(endDate);
                mgtFinOrgStatisticDaily.setOrgId(organizationIncludeSales1.getOrgId());
                mgtFinOrgStatisticDaily.setType(TYPE);
                List<UserCounts> userCounts = organizationIncludeSales1.getDateCounts();
                int cnt = 0;
                if (userCounts != null && userCounts.size() != 0) {
                    cnt = userCounts.get(0).getEntryCnt();
                }
                mgtFinOrgStatisticDaily.setCnt(cnt);
                int defaultPrice = organizationIncludeSales1.getDefaultEntryPrice();
                int[] array = this.getBalance(orgId, lastMgtFinOrgStatisticDailyList, todayMgtFinOrgStatisticDailyList);
                if (todayMgtFinOrgStatisticDailyList != null && todayMgtFinOrgStatisticDailyList.size() != 0) {
                    List<FinFile> files = finFileDao.selectFinFile(organizationIncludeSales1.getOrgId(), endDate, TYPE);
                    if (files != null && files.size() != 0) {
                        mgtFinOrgStatisticDaily.setFiles(files);
                    }
                }
                mgtFinOrgStatisticDaily.setPrice(defaultPrice);
                mgtFinOrgStatisticDaily.setBalance(array[0]);
                mgtFinOrgStatisticDaily.setManulAmount(array[1]);
                mgtFinOrgStatisticDaily.setAmount(array[2]);
                mgtFinOrgStatisticDaily.setExpense(array[3]);
                mgtFinOrgStatisticDaily.setStatus(array[4]);

                mgtFinOrgStatisticDailies.add(mgtFinOrgStatisticDaily);
            }
        } else {
            includeEndDate = true;
        }

        List<MgtFinOrgStatisticDaily> mgtFinOrgStatisticDailieHistory = mgtFinOrgStatisticDailyDao
            .selectMgtFinOrgStatisticDailyByOrgId(orgId, TYPE, startDate, endDate, includeEndDate);
        mgtFinOrgStatisticDailies.addAll(mgtFinOrgStatisticDailieHistory);
        return mgtFinOrgStatisticDailies;
    }

    @Override
    public void saveOrgStatistic(MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily) {

        Date statisticDate = mgtFinOrgStatisticDaily.getStatisticDate();
        int orgId = mgtFinOrgStatisticDaily.getOrgId();
        Integer amount = mgtFinOrgStatisticDaily.getAmount();
        Integer price = mgtFinOrgStatisticDaily.getPrice();
        Integer status = mgtFinOrgStatisticDaily.getStatus();
        // 1计算当前余额。
        MgtFinOrgStatisticDaily mgtFinOrgStatisticDailyNow
            = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyByUnionKey(orgId, TYPE, statisticDate);

        if (amount == null) {
            amount = mgtFinOrgStatisticDailyNow.getManulAmount();
        }
        if (price == null) {
            price = mgtFinOrgStatisticDailyNow.getPrice();
        }
        if (status == null) {
            status = mgtFinOrgStatisticDailyNow.getStatus();
        }
        // 进件 传入的amount相当于是 手工收款额
        int manulAmount = amount;// 手工收款 = 手工上账总额
        amount = mgtFinOrgStatisticDailyNow.getAmount();// 收款额不随手动上账变化
        int expense = mgtFinOrgStatisticDailyNow.getExpense();
        int balance = mgtFinOrgStatisticDailyNow.getBalance();

        // 2.更新当前条目 进件模式余额不变化 添加事物
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            mgtFinOrgStatisticDailyDao.updateMgtFinOrgStatisticDailyAmount(orgId, TYPE, statisticDate, amount, price,
                expense, balance, status, manulAmount);
            txManager.commit(txStatus);
            txStatus = null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("update MgtFinOrgStatisticDaily false", e);
        } finally {
            // roll back not committed transaction
            rollbackTransaction(txStatus);
        }

    }

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            logger.warn("Cannot rollback transaction", er);
        }
    }

    private int[] getBalance(int orgId, List<MgtFinOrgStatisticDaily> lastMgtFinOrgStatisticDailyList,
        List<MgtFinOrgStatisticDaily> todayMgtFinOrgStatisticDailyList) {
        int balance = 0;
        int manulAmount = 0;
        int amount = 0;
        int expense = 0;
        int status = 0;
        if (lastMgtFinOrgStatisticDailyList != null && lastMgtFinOrgStatisticDailyList.size() != 0) {
            for (MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily : lastMgtFinOrgStatisticDailyList) {
                if (mgtFinOrgStatisticDaily.getOrgId() == orgId) {
                    balance = mgtFinOrgStatisticDaily.getBalance();
                    break;
                }
            }
        }

        if (todayMgtFinOrgStatisticDailyList != null && todayMgtFinOrgStatisticDailyList.size() != 0) {
            for (MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily : todayMgtFinOrgStatisticDailyList) {
                if (mgtFinOrgStatisticDaily.getOrgId() == orgId) {
                    manulAmount = mgtFinOrgStatisticDaily.getManulAmount();
                    status = mgtFinOrgStatisticDaily.getStatus();
                    break;
                }
            }
        }
        // 进件 减去明细表中的消耗额
        Integer countAdd = orgRechargeDetailDao.selectAllOrgRechargeCountByDate(orgId, 12,
            DateUtil.calTimestamp(DateUtil.getTodayStartCurrentTime(), 0),
            DateUtil.calTimestamp(DateUtil.getTodayStartCurrentTime(), DateUtil.DAY_MILLSECONDES));
        Integer countConsume = orgRechargeDetailDao.selectAllOrgRechargeCountByDate(orgId, 21,
            DateUtil.calTimestamp(DateUtil.getTodayStartCurrentTime(), 0),
            DateUtil.calTimestamp(DateUtil.getTodayStartCurrentTime(), DateUtil.DAY_MILLSECONDES));
        if (countAdd == null) {
            countAdd = 0;
        }
        if (countConsume == null) {
            countConsume = 0;
        }
        balance = balance + countAdd - countConsume;
        amount = countAdd;
        expense = countConsume;
        int[] array = new int[5];
        array[0] = balance;
        array[1] = manulAmount;
        array[2] = amount;
        array[3] = expense;
        array[4] = status;
        return array;
    }
}
