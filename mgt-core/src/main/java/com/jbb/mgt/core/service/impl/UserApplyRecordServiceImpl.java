package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.StatisticDao;
import com.jbb.mgt.core.dao.UserApplyRecordDao;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.StatisticsMoney;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.service.AccountOpLogService;
import com.jbb.mgt.core.service.AccountService;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.core.service.UserApplyRecordService;

/**
 * UserApplyRecordService接口实现类
 * 
 * @author wyq
 * @date 2018/04/24
 */
@Service("UserApplyRecordService")
public class UserApplyRecordServiceImpl implements UserApplyRecordService {

    private static Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Autowired
    @Qualifier("AccountOpLogService")
    private AccountOpLogService accountOpLogService;

    @Autowired
    @Qualifier("AccountService")
    private AccountService accountService;

    @Autowired
    @Qualifier("ChannelService")
    private ChannelService channelService;

    @Autowired
    private UserApplyRecordDao userApplyRecordDao;

    @Autowired
    private StatisticDao statisticDao;

    @Override
    public int countUserApplyRecords(int orgId, String channelCode, Timestamp startDate, Timestamp endDate) {
        Channel channel = channelService.getChannelByCode(channelCode);
        boolean isJbbChannel = true;
        boolean includeHidden = true;
        if (channel != null) {
            Account account = accountService.getAccountById(channel.getCreator(), orgId, false);
            if (account != null) {
                isJbbChannel = false;
                includeHidden = false;
            }
        }
        return userApplyRecordDao.countUserApplyRecords(orgId, startDate, endDate, isJbbChannel, includeHidden);
    }

    @Override
    public StatisticsMoney selectIndexCountData(Timestamp startDate, Timestamp endDate, Integer orgId) {
        StatisticsMoney sm = new StatisticsMoney();
        sm.setAuditingNum(statisticDao.selectAuditAmount(orgId, startDate, endDate));
        sm.setEntryNum(statisticDao.selectIntoAmount(orgId, startDate, endDate));
        sm.setLoanNum(statisticDao.selectLoanNumber(orgId, startDate, endDate));
        sm.setLoanMoney(statisticDao.selectLoanAmount(orgId, startDate, endDate));
        sm.setReturnMoney(statisticDao.selectReturnAmount(orgId, startDate, endDate));
        sm.setDueMoney(
            statisticDao.selectDueAmount(orgId, null, null) - statisticDao.selectReturnAmount(orgId, null, null));
        sm.setDueToMoney(statisticDao.selectDueAmount(orgId, null, endDate));
        return sm;
    }

    @Override
    public void createUserApplyRecord(UserApplyRecord userApplyRecord) {
        userApplyRecordDao.insertUserApplyRecord(userApplyRecord);
    }

    @Override
    public void updateUserAppayRecord(UserApplyRecord userApplyRecord) {
        userApplyRecordDao.updateUserAppayRecord(userApplyRecord);
    }

    @Override
    public List<UserApplyRecord> getUserApplyRecords(Integer applyId, String op, Integer accountId, Integer orgId,
        int[] statuses, String phoneNumberSearch, String channelSearch, String usernameSearch, String assignNameSearch,
        String initNameSearch, String finalNameSearch, String loandNameSearch, String idcardSearch, String jbbIdSearch,
        boolean feedback, Integer sOrgId, Integer sUserType, Timestamp startDate, Timestamp endDate,
        Integer initAccountId, Integer finalAccountId, Integer loanAccountId, Integer currentAccountId,
        String channelCode, Boolean verified) {
        return userApplyRecordDao.selectUserApplyRecords(applyId, op, accountId, orgId, statuses, phoneNumberSearch,
            channelSearch, usernameSearch, assignNameSearch, initNameSearch, finalNameSearch, loandNameSearch,
            idcardSearch, jbbIdSearch, feedback, sOrgId, sUserType, startDate, endDate, initAccountId, finalAccountId,
            loanAccountId, currentAccountId, channelCode, verified);
    }

    @Override
    public UserApplyRecord getUserApplyRecordByApplyId(Integer applyId, String op, Integer accountId, Integer orgId) {
        return userApplyRecordDao.selectUserApplyRecordByApplyId(applyId, op, accountId, orgId);
    }

    @Override
    public UserApplyRecord getUserLastApplyRecordInOrg(int userId, int orgId) {
        return userApplyRecordDao.selectUserLastApplyRecordInOrg(userId, orgId);
    }

    @Override
    public List<UserApplyRecord> getUnassignUserApplyRecords(Integer limit, Integer orgId) {
        return userApplyRecordDao.selectUnassignUserApplyRecords(limit, orgId);
    }

    @Override
    public void updateUserAppayRecordAssignAccIdInBatch(int[] applyIds, int assignAccId, Timestamp assignDate,
        int initAccId) {

        userApplyRecordDao.updateUserAppayRecordAssignAccIdInBatch(applyIds, assignAccId, assignDate, initAccId);

    }

    @Override
    public UserApplyRecord selectUserApplyRecordInfoByApplyId(Integer applyId) {
        return userApplyRecordDao.selectUserApplyRecordInfoByApplyId(applyId);
    }

    @Override
    public int getStatisticsNumber(Integer[] statuses, Timestamp startDate, Timestamp endDate) {
        return userApplyRecordDao.getStatisticsNumber(statuses, startDate, endDate);
    }

    @Override
    public Integer getDiversionCount(Integer orgId, Integer type) {
        return userApplyRecordDao.getDiversionCount(orgId, type);
    }

    @Override
    public int countUserApply(Integer orgId, Integer userType, Timestamp startDate, Timestamp endDate) {
        return userApplyRecordDao.countUserApply(orgId, userType, startDate, endDate);
    }

    @Override
    public List<UserApplyRecord> selectUserApplyRecordsByOrgId(Integer orgId, Integer userType, Timestamp startDate,
        Timestamp endDate) {
        return userApplyRecordDao.selectUserApplyRecordsByOrgId(orgId, userType, startDate, endDate);
    }

    @Override
    public boolean checkExist(Integer userId, Integer orgId, Integer applyId) {
        return userApplyRecordDao.checkExist(userId, orgId, applyId);
    }

    @Override
    public void deleteUserApplys(int orgId, Timestamp startDateTs, Timestamp endDateTs) {
        userApplyRecordDao.deleteUserApplys(orgId, startDateTs, endDateTs);

    }

    @Override
    public int countDeleteUserApplys(int orgId, Timestamp startDate, Timestamp endDate) {
        return userApplyRecordDao.countDeleteUserApplys(orgId, startDate, endDate);
    }

}
