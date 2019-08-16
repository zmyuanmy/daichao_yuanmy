package com.jbb.mgt.core.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
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

import com.jbb.mgt.core.dao.MgtFinOrgStatisticDailyDao;
import com.jbb.mgt.core.dao.OrgRechargeDetailDao;
import com.jbb.mgt.core.dao.OrganizationDao;
import com.jbb.mgt.core.domain.MgtFinOrgStatisticDaily;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.UserAdCounts;
import com.jbb.mgt.core.domain.UserCounts;
import com.jbb.mgt.core.service.MgtFinOrgStatisticDailyService;
import com.jbb.server.common.util.DateUtil;

@Service("MgtFinOrgStatisticDailyService")
public class MgtFinOrgStatisticDailyServiceImpl implements MgtFinOrgStatisticDailyService {

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
    private OrganizationDao organizationDao;

    @Override
    public void insertMgtFinOrgStatisticDailyAll(Integer type, Integer orgId, Timestamp startDate, Timestamp endDate) {
        // 1.区分是查询全部还是查询单个组织 2.判断类型是否存在 如果给定类型则只查询当前类型，不给定则查询进件 跟注册 两种类型。3.计算数据 插入历史表
        List<Organization> organizations
            = mgtFinOrgStatisticDailyDao.selectOrganizations(true, true, startDate, endDate, orgId);

        if (organizations != null && organizations.size() != 0) {
            // 只有时间等于今天的时候，才会把今天存在的历史数据提取出来
            Date dateFormat = new Date(DateUtil.getTodayStartTs());
            List<MgtFinOrgStatisticDaily> todayMgtFinOrgStatisticDailyList = new ArrayList<MgtFinOrgStatisticDaily>();
            if (new Date(endDate.getTime()).toString().equals(dateFormat.toString())) {
                todayMgtFinOrgStatisticDailyList = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyByDate(type,
                    new Date(startDate.getTime()));
            }
            for (Organization organization : organizations) {
                countAndInsert(organization, type, startDate, todayMgtFinOrgStatisticDailyList);
            }
        }
    }

    @Override
    public void insertMgtFinOrgStatisticDailyAd(Integer type, Integer orgId, Timestamp startDate, Timestamp endDate) {
        // 1.区分是查询全部还是查询单个组织 2.判断类型是否存在 如果给定类型则只查询当前类型，不给定则查询进件 跟注册 两种类型。3.计算数据 插入历史表
        List<Organization> organizations
            = organizationDao.selectOrganizations(false, false, true, startDate, endDate, true);

        if (organizations != null && organizations.size() != 0) {
            // 只有时间等于今天的时候，才会把今天存在的历史数据提取出来
            Date dateFormat = new Date(DateUtil.getTodayStartTs());
            List<MgtFinOrgStatisticDaily> todayMgtFinOrgStatisticDailyList = new ArrayList<MgtFinOrgStatisticDaily>();
            if (new Date(endDate.getTime()).toString().equals(dateFormat.toString())) {
                todayMgtFinOrgStatisticDailyList = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyByDate(type,
                    new Date(startDate.getTime()));
            }
            for (Organization organization : organizations) {
                countAndInsertAd(organization, type, startDate, todayMgtFinOrgStatisticDailyList);
            }
        }
    }

    @Override
    public void checkAndInsertMgtFinOrgStatisticDaily(int orgId, int type, Date statisticDate) {
        MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily
            = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyByUnionKey(orgId, type, statisticDate);
        if (mgtFinOrgStatisticDaily == null) {
            MgtFinOrgStatisticDaily mgtFinOrgStatisticDailyNew = new MgtFinOrgStatisticDaily();
            mgtFinOrgStatisticDailyNew.setAmount(0);
            // 余额要根据上条记录的历史值计算
            Date statisticDateYesterDay = new Date(statisticDate.getTime() - DateUtil.DAY_MILLSECONDES);
            MgtFinOrgStatisticDaily lastMgtFinOrgStatisticDaily = mgtFinOrgStatisticDailyDao
                .selectMgtFinOrgStatisticDailyByUnionKey(orgId, type, statisticDateYesterDay);
            int balance = 0;
            if (lastMgtFinOrgStatisticDaily != null) {
                balance = lastMgtFinOrgStatisticDaily.getBalance();
            }
            mgtFinOrgStatisticDailyNew.setBalance(balance);
            mgtFinOrgStatisticDailyNew.setCnt(0);
            mgtFinOrgStatisticDailyNew.setCreationDate(DateUtil.getCurrentTimeStamp());
            mgtFinOrgStatisticDailyNew.setExpense(0);
            mgtFinOrgStatisticDailyNew.setOrgId(orgId);
            mgtFinOrgStatisticDailyNew.setPrice(0);
            mgtFinOrgStatisticDailyNew.setStatisticDate(statisticDate);
            mgtFinOrgStatisticDailyNew.setStatus(0);
            mgtFinOrgStatisticDailyNew.setManulAmount(0);
            mgtFinOrgStatisticDailyNew.setType(type);
            mgtFinOrgStatisticDailyDao.insertMgtFinOrgStatisticDaily(mgtFinOrgStatisticDailyNew);
        }

    }

    @Override
    public void updateMgtFinOrgStatisticDailyList(List<MgtFinOrgStatisticDaily> list) {
        mgtFinOrgStatisticDailyDao.updateMgtFinOrgStatisticDailyList(list);
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

    private void countAndInsert(Organization organization, Integer type, Timestamp startTime,
        List<MgtFinOrgStatisticDaily> todayMgtFinOrgStatisticDailyList) {
        List<UserCounts> dateCounts = organization.getDateCounts();
        int entryCnt = 0;
        int regCnt = 0;
        if (dateCounts != null && dateCounts.size() != 0) {
            UserCounts userCounts = dateCounts.get(0);
            entryCnt = userCounts.getEntryCnt(); // 进件cnt
            regCnt = userCounts.getRegCnt();// 注册cnt
        }

        int registerDefalultPrice = organization.getDefaultRegisterPrice();
        int entryDefaultPrice = organization.getDefaultEntryPrice();
        int registAmout = 0;
        int entryManulAmout = 0;
        int entryStatus = 0;
        int regStatus = 0;

        MgtFinOrgStatisticDaily MgtFinOrgStatisticDailyentry
            = createMgtFinOrgStatisticDaily(1, entryCnt, organization.getOrgId(), entryDefaultPrice, startTime);
        MgtFinOrgStatisticDaily MgtFinOrgStatisticDailyreg
            = createMgtFinOrgStatisticDaily(2, regCnt, organization.getOrgId(), registerDefalultPrice, startTime);

        if (todayMgtFinOrgStatisticDailyList != null && todayMgtFinOrgStatisticDailyList.size() != 0) {
            for (MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily : todayMgtFinOrgStatisticDailyList) {
                if (mgtFinOrgStatisticDaily.getOrgId() == organization.getOrgId()) {
                    if (mgtFinOrgStatisticDaily.getType() == 1) {
                        entryManulAmout = mgtFinOrgStatisticDaily.getManulAmount();
                        entryStatus = mgtFinOrgStatisticDaily.getStatus();
                    }
                    if (mgtFinOrgStatisticDaily.getType() == 2) {
                        // System.out.println("orgId " + organization.getOrgId() + " registAmout " + registAmout);
                        registAmout = mgtFinOrgStatisticDaily.getAmount();
                        regStatus = mgtFinOrgStatisticDaily.getStatus();
                    }
                }
            }
        }

        // 如果今天有数据的，需要把今天的数据处理一下
        MgtFinOrgStatisticDailyentry.setManulAmount(entryManulAmout);// 进件的要把今天已有的手工收款保留
        MgtFinOrgStatisticDailyentry.setStatus(entryStatus);

        MgtFinOrgStatisticDailyreg.setAmount(registAmout);// 注册的要把今天的收款额的余额加到总余额里面
        MgtFinOrgStatisticDailyreg.setBalance(MgtFinOrgStatisticDailyreg.getBalance() + registAmout);
        MgtFinOrgStatisticDailyreg.setStatus(regStatus);
        if (type == null) {
            // 计算 进件、注册
            mgtFinOrgStatisticDailyDao.insertMgtFinOrgStatisticDaily(MgtFinOrgStatisticDailyentry);
            mgtFinOrgStatisticDailyDao.insertMgtFinOrgStatisticDaily(MgtFinOrgStatisticDailyreg);
            return;
        } else {
            if (type == 1) {
                mgtFinOrgStatisticDailyDao.insertMgtFinOrgStatisticDaily(MgtFinOrgStatisticDailyentry);
                return;
            } else {
                mgtFinOrgStatisticDailyDao.insertMgtFinOrgStatisticDaily(MgtFinOrgStatisticDailyreg);
                return;
            }
        }
    }

    private void countAndInsertAd(Organization organization, Integer type, Timestamp startTime,
        List<MgtFinOrgStatisticDaily> todayMgtFinOrgStatisticDailyList) {
        List<UserAdCounts> dateCounts = organization.getDateAdCounts();
        int adCnt = 0;

        if (dateCounts != null && dateCounts.size() != 0) {
            UserAdCounts userCounts = dateCounts.get(0);
            adCnt = userCounts.getAdCnt(); // 进件cnt
        }

        int registerDefalultPrice = organization.getDelegatePrice();
        int registAmout = 0;
        int regStatus = 0;

        MgtFinOrgStatisticDaily MgtFinOrgStatisticDailyAd
            = createMgtFinOrgStatisticDaily(3, adCnt, organization.getOrgId(), registerDefalultPrice, startTime);

        if (todayMgtFinOrgStatisticDailyList != null && todayMgtFinOrgStatisticDailyList.size() != 0) {
            for (MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily : todayMgtFinOrgStatisticDailyList) {
                if (mgtFinOrgStatisticDaily.getOrgId() == organization.getOrgId()) {

                    if (mgtFinOrgStatisticDaily.getType() == 3) {
                        // System.out.println("orgId " + organization.getOrgId() + " registAmout " + registAmout);
                        registAmout = mgtFinOrgStatisticDaily.getAmount();
                        regStatus = mgtFinOrgStatisticDaily.getStatus();
                    }
                }
            }
        }
        MgtFinOrgStatisticDailyAd.setAmount(registAmout);// 注册的要把今天的收款额的余额加到总余额里面
        MgtFinOrgStatisticDailyAd.setBalance(MgtFinOrgStatisticDailyAd.getBalance() + registAmout);
        MgtFinOrgStatisticDailyAd.setStatus(regStatus);
        mgtFinOrgStatisticDailyDao.insertMgtFinOrgStatisticDaily(MgtFinOrgStatisticDailyAd);
    }

    private MgtFinOrgStatisticDaily createMgtFinOrgStatisticDaily(int type, int cnt, int orgId, int price,
        Timestamp startTime) {
        int expense = 0;
        int amount = 0;
        int balance = 0;
        MgtFinOrgStatisticDaily last = mgtFinOrgStatisticDailyDao.selectMgtFinOrgStatisticDailyByUnionKey(orgId, type,
            new Date(startTime.getTime() - DateUtil.DAY_MILLSECONDES));
        // 如果是进件类型 单价为给定price，如果是进件用户，单价为 昨天的消费额/昨天的数量 余额为，前一条数据的余额+前一天充值总额-前一条消费的余额
        if (type == 1) {
            // 进件 计算price balance
            if (last != null) {
                balance = last.getBalance();
            }
            Integer countAdd = orgRechargeDetailDao.selectAllOrgRechargeCountByDate(orgId, 12, startTime,
                DateUtil.calTimestamp(startTime.getTime(), DateUtil.DAY_MILLSECONDES));
            Integer countConsume = orgRechargeDetailDao.selectAllOrgRechargeCountByDate(orgId, 21, startTime,
                DateUtil.calTimestamp(startTime.getTime(), DateUtil.DAY_MILLSECONDES));
            if (countAdd == null) {
                countAdd = 0;
            }

            if (countConsume == null) {
                countConsume = 0;
            }
            balance = balance + countAdd - countConsume;
            // 进件模式 单价 收款 消耗 都是反向计算
            if (cnt != 0) {
                price = countConsume / cnt;
            }
            expense = countConsume;
            amount = countAdd;
        } else {
            // 注册
            expense = cnt * price;
            // 获取上一条历史记录，获取历史balance值
            if (last != null) {
                balance = last.getBalance() - expense;
            } else {
                balance = -expense;
            }
        }
        MgtFinOrgStatisticDaily mgtFinOrgStatisticDaily = new MgtFinOrgStatisticDaily();
        mgtFinOrgStatisticDaily.setAmount(amount);
        mgtFinOrgStatisticDaily.setBalance(balance);
        mgtFinOrgStatisticDaily.setCnt(cnt);
        mgtFinOrgStatisticDaily.setCreationDate(DateUtil.getCurrentTimeStamp());
        mgtFinOrgStatisticDaily.setExpense(expense);
        mgtFinOrgStatisticDaily.setOrgId(orgId);
        mgtFinOrgStatisticDaily.setPrice(price);
        mgtFinOrgStatisticDaily.setStatisticDate(new Date(startTime.getTime()));
        mgtFinOrgStatisticDaily.setStatus(0);
        mgtFinOrgStatisticDaily.setManulAmount(0);
        mgtFinOrgStatisticDaily.setType(type);
        return mgtFinOrgStatisticDaily;
    }
}
