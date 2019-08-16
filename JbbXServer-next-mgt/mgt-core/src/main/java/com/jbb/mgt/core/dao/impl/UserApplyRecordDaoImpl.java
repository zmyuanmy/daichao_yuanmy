package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserApplyRecordDao;
import com.jbb.mgt.core.dao.mapper.UserApplyRecordMapper;
import com.jbb.mgt.core.domain.Statistics;
import com.jbb.mgt.core.domain.UserApplyRecord;

/**
 * UserApplyRecordDao实现类
 * 
 * @author wyq
 * @date 2018/04/24
 */
@Repository("UserApplyRecordDao")
public class UserApplyRecordDaoImpl implements UserApplyRecordDao {

    @Autowired
    private UserApplyRecordMapper mapper;

    @Override
    public Statistics selectUserAppayRecordsCountByApplyId(Integer orgId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectUserAppayRecordsCountByApplyId(orgId, startDate, endDate);
    }

    @Override
    public UserApplyRecord selectUserApplyRecordInfoByApplyId(Integer applyId) {
        return mapper.selectUserApplyRecordInfoByApplyId(applyId);
    }

    @Override
    public int getStatisticsNumber(Integer[] statuses, Timestamp startDate, Timestamp endDate) {
        return mapper.getStatisticsNumber(statuses, startDate, endDate);
    }

    @Override
    public int countUserApplyRecords(int orgId, Timestamp startDate, Timestamp endDate, boolean isJbbChannel,
        boolean includeHidden) {
        return mapper.countUserApplyRecords(orgId, startDate, endDate, isJbbChannel, includeHidden);
    }

    @Override
    public void insertUserApplyRecord(UserApplyRecord userApplyRecord) {
        mapper.insertUserApplyRecord(userApplyRecord);

    }

    @Override
    public void updateUserAppayRecord(UserApplyRecord userApplyRecord) {
        mapper.updateUserAppayRecord(userApplyRecord);

    }

    @Override
    public void updateUserAppayRecordAssignAccIdInBatch(int[] applyIds, int assignAccId, Timestamp assignDate,
        int initAccId) {
        mapper.updateUserAppayRecordAssignAccIdInBatch(applyIds, assignAccId, assignDate, initAccId);
    }

    @Override
    public UserApplyRecord selectUserApplyRecordByApplyId(Integer applyId, String op, Integer accountId,
        Integer orgId) {
        List<UserApplyRecord> list
            = mapper.selectUserApplyRecords(applyId, op, accountId, orgId, null, null, null, null, null, null, null,
                null, null, null, null, false, null, null, null, null, null, null, null, null, null, null);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;

    }

    @Override
    public List<UserApplyRecord> selectUserApplyRecords(Integer applyId, String op, Integer accountId, Integer orgId,
        int[] statuses, String phoneNumberSearch, String channelSearch, String usernameSearch, String assignNameSearch,
        String initNameSearch, String finalNameSearch, String loandNameSearch, String idcardSearch, String jbbIdSearch,
        boolean feedback, Integer sOrgId, Integer sUserType, Timestamp startDate, Timestamp endDate,
        Integer initAccountId, Integer finalAccountId, Integer loanAccountId, Integer currentAccountId,
        String channelCode, Boolean verified) {
        return mapper.selectUserApplyRecords(applyId, op, accountId, orgId, statuses, phoneNumberSearch, channelSearch,
            usernameSearch, assignNameSearch, initNameSearch, finalNameSearch, loandNameSearch, idcardSearch,
            jbbIdSearch, null, feedback, sOrgId, sUserType, startDate, endDate, initAccountId, finalAccountId,
            loanAccountId, currentAccountId, channelCode, verified);
    }

    @Override
    public UserApplyRecord selectUserLastApplyRecordInOrg(int userId, int orgId) {
        List<UserApplyRecord> list
            = mapper.selectUserApplyRecords(null, null, null, orgId, null, null, null, null, null, null, null, null,
                null, null, userId, false, null, null, null, null, null, null, null, null, null, null);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<UserApplyRecord> selectUnassignUserApplyRecords(Integer limit, Integer orgId) {
        return mapper.selectUnassignUserApplyRecords(limit, orgId);
    }

    @Override
    public Integer getDiversionCount(Integer orgId, Integer type) {
        return mapper.getDiversionCount(orgId, type);
    }

    @Override
    public int countUserApply(Integer orgId, Integer userType, Timestamp startDate, Timestamp endDate) {
        return mapper.countUserApply(orgId, userType, startDate, endDate);
    }

    @Override
    public List<UserApplyRecord> selectUserApplyRecordsByOrgId(Integer orgId, Integer userType, Timestamp startDate,
        Timestamp endDate) {
        return mapper.selectUserApplyRecordsByOrgId(orgId, userType, startDate, endDate);
    }

    @Override
    public boolean checkExist(Integer userId, Integer orgId, Integer applyId) {
        return mapper.checkExist(userId, orgId, applyId) > 0;
    }

    @Override
    public List<UserApplyRecord> getCountUserApply(Integer userType, Timestamp startDate, Timestamp endDate) {
        return mapper.getCountUserApply(userType, startDate, endDate);

    }

    @Override
    public void deleteUserApplys(int orgId, Timestamp startDateTs, Timestamp endDateTs) {
        mapper.deleteUserApplys(orgId, startDateTs, endDateTs);

    }

    @Override
    public int countDeleteUserApplys(int orgId, Timestamp startDate, Timestamp endDate) {
        return mapper.countDeleteUserApplys(orgId, startDate, endDate);
    }

}
