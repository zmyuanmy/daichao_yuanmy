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
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.FinFile;
import com.jbb.mgt.core.domain.MgtFinOrgStatisticDaily;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.OrganizationIncludeSales;
import com.jbb.mgt.core.domain.UserAdCounts;
import com.jbb.mgt.core.service.FinOrgStatisticServices;
import com.jbb.server.common.util.DateUtil;

@Service("FinOrgAdStatisticServices")
public class FinOrgAdStatisticServicesImpl implements FinOrgStatisticServices {
    // 代理3
    private static final int TYPE = 3;

    private static Logger logger = LoggerFactory.getLogger(FinOrgAdStatisticServicesImpl.class);

    @Autowired
    private MgtFinOrgStatisticDailyDao mgtFinOrgStatisticDailyDao;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private PlatformTransactionManager txManager;

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
            // 查询实时
            String time = statisticDate.toString() + " 00:00:00";
            List<OrganizationIncludeSales> organizationIncludeSales
                = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyNow(null, false, false,
                    DateUtil.parseStrnew(time), DateUtil.getCurrentTimeStamp(), accountId, true);

            if (organizationIncludeSales != null && organizationIncludeSales.size() != 0) {
                for (OrganizationIncludeSales organizationIncludeSales1 : organizationIncludeSales) {
                    OrgStatisticDailyInfo orgStatisticDailyInfo = new OrgStatisticDailyInfo();

                    // 拼接当天数据
                    MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily = new MgtFinOrgStatisticDaily();
                    mgtFinOrgStatisticDaily.setStatisticDate(statisticDate);
                    mgtFinOrgStatisticDaily.setOrgId(organizationIncludeSales1.getOrgId());
                    mgtFinOrgStatisticDaily.setType(TYPE);
                    List<UserAdCounts> userCounts = organizationIncludeSales1.getDateAdCounts();
                    int cnt = 0;
                    if (userCounts != null && userCounts.size() != 0) {
                        cnt = userCounts.get(0).getAdCnt();
                    }
                    mgtFinOrgStatisticDaily.setCnt(cnt);
                    int defaultPrice = organizationIncludeSales1.getDelegatePrice();

                    int[] array = this.getBalance(organizationIncludeSales1.getOrgId(), defaultPrice, cnt, TYPE,
                        lastMgtFinOrgStatisticDailyList, todayMgtFinOrgStatisticDailyList);
                    if (todayMgtFinOrgStatisticDailyList != null && todayMgtFinOrgStatisticDailyList.size() != 0) {
                        List<FinFile> files
                            = finFileDao.selectFinFile(organizationIncludeSales1.getOrgId(), statisticDate, TYPE);
                        if (files != null && files.size() != 0) {
                            mgtFinOrgStatisticDaily.setFiles(files);
                        }
                    }
                    mgtFinOrgStatisticDaily.setBalance(array[0]);
                    mgtFinOrgStatisticDaily.setAmount(array[1]);
                    mgtFinOrgStatisticDaily.setExpense(array[2]);
                    mgtFinOrgStatisticDaily.setStatus(array[3]);
                    mgtFinOrgStatisticDaily.setPrice(defaultPrice);

                    // 拼接账号
                    Account account = organizationIncludeSales1.getAccount();

                    // 拼接组织
                    Organization organization = new Organization();
                    organization.setName(organizationIncludeSales1.getName());
                    organization.setOrgId(organizationIncludeSales1.getOrgId());
                    organization.setDescription(organizationIncludeSales1.getDescription());

                    // 整合进封装类
                    orgStatisticDailyInfo.setAccount(account);
                    orgStatisticDailyInfo.setMgtFinOrgStatisticDaily(mgtFinOrgStatisticDaily);
                    orgStatisticDailyInfo.setOrganization(organization);

                    // 添加到返回类
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
                = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyNow(orgId, false, false,
                    DateUtil.parseStrnew(time), DateUtil.getCurrentTimeStamp(), null, true);
            if (organizationIncludeSales != null && organizationIncludeSales.size() != 0) {
                // 获取今天时间的源数据
                OrganizationIncludeSales organizationIncludeSales1 = organizationIncludeSales.get(0);

                // 拼接历史表吻合数据
                MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily = new MgtFinOrgStatisticDaily();
                mgtFinOrgStatisticDaily.setStatisticDate(endDate);
                mgtFinOrgStatisticDaily.setOrgId(organizationIncludeSales1.getOrgId());
                mgtFinOrgStatisticDaily.setType(TYPE);
                List<UserAdCounts> userCounts = organizationIncludeSales1.getDateAdCounts();
                int cnt = 0;
                if (userCounts != null && userCounts.size() != 0) {
                    cnt = userCounts.get(0).getAdCnt();
                }
                mgtFinOrgStatisticDaily.setCnt(cnt);
                int defaultPrice = organizationIncludeSales1.getDelegatePrice();
                int[] array = this.getBalance(orgId, defaultPrice, cnt, TYPE, lastMgtFinOrgStatisticDailyList,
                    todayMgtFinOrgStatisticDailyList);
                if (todayMgtFinOrgStatisticDailyList != null && todayMgtFinOrgStatisticDailyList.size() != 0) {
                    List<FinFile> files = finFileDao.selectFinFile(organizationIncludeSales1.getOrgId(), endDate, TYPE);
                    if (files != null && files.size() != 0) {
                        mgtFinOrgStatisticDaily.setFiles(files);
                    }
                }
                mgtFinOrgStatisticDaily.setBalance(array[0]);
                mgtFinOrgStatisticDaily.setAmount(array[1]);
                mgtFinOrgStatisticDaily.setExpense(array[2]);
                mgtFinOrgStatisticDaily.setStatus(array[3]);
                mgtFinOrgStatisticDaily.setPrice(defaultPrice);

                // 添加到返回结果中
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
            amount = mgtFinOrgStatisticDailyNow.getAmount();
        }
        if (price == null) {
            price = mgtFinOrgStatisticDailyNow.getPrice();
        }
        if (status == null) {
            status = mgtFinOrgStatisticDailyNow.getStatus();
        }
        // 总收款额也会变换
        int balance = mgtFinOrgStatisticDailyNow.getBalance();
        int expense = mgtFinOrgStatisticDailyNow.getExpense();
        int balanceNow = balance + amount - mgtFinOrgStatisticDailyNow.getAmount();
        if (price != mgtFinOrgStatisticDailyNow.getPrice()) {
            // 价格变动 重新计算消耗额
            expense = expense + mgtFinOrgStatisticDailyNow.getCnt() * (price - mgtFinOrgStatisticDailyNow.getPrice());
            balanceNow = balance + amount - mgtFinOrgStatisticDailyNow.getAmount()
                - mgtFinOrgStatisticDailyNow.getCnt() * (price - mgtFinOrgStatisticDailyNow.getPrice());
        }
        int balanceChange = balanceNow - balance;

        // 2.更新当前条目 根据余额变化值更新历史数据所有余额 添加事物
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            // 更新当天
            mgtFinOrgStatisticDailyDao.updateMgtFinOrgStatisticDailyAmount(orgId, TYPE, statisticDate, amount, price,
                expense, balanceNow, status, 0);

            // 更新今天之后的数据
            mgtFinOrgStatisticDailyDao.updateMgtFinOrgStatisticDailyAmountAll(orgId, TYPE, statisticDate,
                balanceChange);
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

    private int[] getBalance(int orgId, int price, int cnt, int type,
        List<MgtFinOrgStatisticDaily> lastMgtFinOrgStatisticDailyList,
        List<MgtFinOrgStatisticDaily> todayMgtFinOrgStatisticDailyList) {
        int balance = 0;
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
                    // 注册 减去消耗额
                    balance = balance + mgtFinOrgStatisticDaily.getAmount() - price * cnt;
                    amount = mgtFinOrgStatisticDaily.getAmount();
                    status = mgtFinOrgStatisticDaily.getStatus();
                    break;
                }
            }
        } else {
            balance = balance - price * cnt;
        }
        expense = price * cnt;
        int[] array = new int[4];
        array[0] = balance;
        array[1] = amount;
        array[2] = expense;
        array[3] = status;

        return array;
    }

}
