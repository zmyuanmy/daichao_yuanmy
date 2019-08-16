package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.XjlApplyRecordDao;
import com.jbb.mgt.core.dao.mapper.XjlApplyRecordMapper;
import com.jbb.mgt.core.domain.LoanTotal;
import com.jbb.mgt.core.domain.MachineTrialOrderVO;
import com.jbb.mgt.core.domain.StatusCnt;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.domain.XjlApplyRecordListVO;
import com.jbb.mgt.core.domain.XjlApplyRecordStatistic;

/**
 * 申请操作Dao实现类
 *
 * @author wyq
 * @date 2018/8/21 10:47
 */
@Repository("XjlApplyRecordDao")
public class XjlApplyRecordDaoImpl implements XjlApplyRecordDao {

    @Autowired
    private XjlApplyRecordMapper mapper;

    @Override
    public void insertXjlApplyRecord(XjlApplyRecord xjlApplyRecord) {
        mapper.insertXjlApplyRecord(xjlApplyRecord);
    }

    @Override
    public void updateXjlApplyRecord(XjlApplyRecord xjlApplyRecord) {
        mapper.updateXjlApplyRecord(xjlApplyRecord);
    }

    @Override
    public XjlApplyRecord selectXjlApplyRecordByApplyId(String applyId, Integer orgId, Boolean preLoan) {
        return mapper.selectXjlApplyRecordByApplyId(applyId, orgId, preLoan);
    }

    @Override
    public void deleteXjlApplyRecord(String applyId) {
        mapper.deleteXjlApplyRecordByapplyId(applyId);
    }

    @Override
    public List<XjlApplyRecord> getXjlApplyRecordByUserId(int userId, Integer orgId, boolean detail,
        Boolean isToReturn) {
        return mapper.getXjlApplyRecordByUserId(userId, orgId, detail, isToReturn);
    }

    @Override
    public List<XjlApplyRecord> selectXjlApplyRecords(boolean getStatusCnts, Integer userId, String[] status,
        String searchText, Timestamp startDate, Timestamp endDate, int orgId, Integer dateType, Integer accountId,
        String channelCode, Integer finalAccId, Integer collectorAccId, String listType, Integer loanType) {
        return mapper.selectXjlApplyRecords(getStatusCnts, userId, status, searchText, startDate, endDate, orgId,
            dateType, accountId, channelCode, finalAccId, collectorAccId, listType, loanType);
    }

    @Override
    public LoanTotal selectXjlApplyRecordsTotal(Integer userId, String[] status, String searchText, Timestamp startDate,
        Timestamp endDate, int orgId) {
        return mapper.selectXjlApplyRecordsTotal(userId, status, searchText, startDate, endDate, orgId);
    }

    @Override
    public List<StatusCnt> selectStatusCnts(Integer userId, String[] status, String searchText, Timestamp startDate,
        Timestamp endDate, int orgId, Integer accountId, String listType, Integer dateType) {
        return mapper.selectStatusCnts(userId, status, searchText, startDate, endDate, orgId, accountId, listType,
            dateType);
    }

    @Override
    public XjlApplyRecord selectXjlApplyRecordByOpenId(String openId) {
        return mapper.selectXjlApplyRecordByOpenId(openId);
    }

    @Override
    public boolean checkXjlApplyExist(String applyId, int orgId) {
        return mapper.checkXjlApplyExist(applyId, orgId) > 0;
    }

    @Override
    public List<XjlApplyRecord> selectXjlApplyRecordForCheck(int num) {
        return mapper.selectXjlApplyRecordForCheck(num);
    }

    @Override
    public boolean checkExistByUserId(int orgId, Integer userId) {
        return mapper.checkExistByUserId(orgId, userId) > 0;
    }

    @Override
    public List<XjlApplyRecordListVO> selectXjlApplyRecordList(String platform, String auditor, String collector,
        String status, String startDate, String endDate, String searchText) {
        return mapper.selectXjlApplyRecordList(platform, auditor, collector, status, startDate, endDate, searchText);
    }

    @Override
    public void updateStatus(int status, String applyId) {
        mapper.updateStatus(status, applyId);
    }

    @Override
    public int updateAscriptionUserId(String applyId, long ascriptionUserId) {
        return mapper.updateAscriptionUserId(applyId, ascriptionUserId);
    }

    @Override
    public List<MachineTrialOrderVO> selectMachineTrialOrderList(String channelName, String claim, String startDate,
        String endDate) {
        return mapper.selectMachineTrialOrderList(channelName, claim, startDate, endDate);
    }

    @Override
    public List<XjlApplyRecordStatistic> selectXjlPerformance(int orgId, String startS, String endS) {
        return mapper.selectXjlPerformance(orgId, startS, endS);
    }

    @Override
    public void updateXjlApplyRecordStatus(String applyId, Integer status, String comment) {
        mapper.updateXjlApplyRecordStatus(applyId, status, comment);
    }

    @Override
    public boolean checkApplyPull(String applyId, int orgId, String listType) {
        return mapper.checkApplyPull(applyId, orgId, listType) > 0;
    }

    @Override
    public void saveApplyPull(String applyId, int accountId, String listType) {
        mapper.saveApplyPull(applyId, accountId, listType);
    }

    @Override
    public int updateCollectorAccId(String applyId, int collectorAccId) {
        return mapper.updateCollectorAccId(applyId,collectorAccId);
    }

    public List<XjlApplyRecord> selectXjlApplyRecordsByStatus(Integer status, String startDate, Integer startDay,
        Integer endDay) {
        return mapper.selectXjlApplyRecordsByStatus(status, startDate, startDay, endDay);
    }

	@Override
	public List<XjlApplyRecord> queryXjlApplyRecordList(int accountId, String listType, String searchText,
			Timestamp applyStartDate, Timestamp applyEndDate, Timestamp loanStartDate, Timestamp loanEndDate,
			Timestamp repaymentStartDate, Timestamp repaymentEndDate, Integer loanType, String channelCode,
			Boolean isReceived, Boolean isRenewal, String[] status, Integer collectorAccId, Integer repaymentStatus,int orgId) {
		
		return mapper.queryXjlApplyRecordList(accountId, listType, searchText, applyStartDate, applyEndDate, 
				loanStartDate, loanEndDate, repaymentStartDate, repaymentEndDate, loanType, channelCode, isReceived,
				isRenewal, status, collectorAccId, repaymentStatus,orgId);
							
	}

	@Override
	public List<StatusCnt> queryStatusCnts(String[] status, String searchText, int orgId, Integer accountId,
			String listType, Timestamp applyStartDate, Timestamp applyEndDate, Timestamp loanStartDate,
			Timestamp loanEndDate, Timestamp repaymentStartDate, Timestamp repaymentEndDate) {
		
		return mapper.queryStatusCnts(status, searchText, orgId, accountId, listType, 
				applyStartDate, applyEndDate, loanStartDate, loanEndDate, 
				repaymentStartDate, repaymentEndDate);
	}
}
