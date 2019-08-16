package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.XjlApplyRecordDao;
import com.jbb.mgt.core.domain.LoanTotal;
import com.jbb.mgt.core.domain.MachineTrialOrderVO;
import com.jbb.mgt.core.domain.StatusCnt;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.domain.XjlApplyRecordListVO;
import com.jbb.mgt.core.domain.XjlApplyRecordStatistic;
import com.jbb.mgt.core.service.XjlApplyRecordService;

/**
 * 用户申请Service接口实现类
 *
 * @author wyq
 * @date 2018/8/21 10:29
 */
@Service("XjlApplyRecordService")
public class XjlApplyRecordServiceImpl implements XjlApplyRecordService {

    @Autowired
    private XjlApplyRecordDao dao;

    @Override
    public void saveXjlApplyRecord(XjlApplyRecord xjlApplyRecord) {
        dao.insertXjlApplyRecord(xjlApplyRecord);
    }

    @Override
    public void updateXjlApplyRecord(XjlApplyRecord xjlApplyRecord) {
        dao.updateXjlApplyRecord(xjlApplyRecord);
    }

    @Override
    public XjlApplyRecord selectXjlApplyRecordByApplyId(String applyId, Integer orgId, Boolean preLoan) {
        return dao.selectXjlApplyRecordByApplyId(applyId, orgId, preLoan);
    }

    @Override
    public XjlApplyRecord getXjlApplyRecordByApplyId(String applyId, Integer orgId, Boolean preLoan) {
        return dao.selectXjlApplyRecordByApplyId(applyId, orgId, preLoan);
    }

    @Override
    public void deleteXjlApplyRecord(String applyId) {
        dao.deleteXjlApplyRecord(applyId);
    }

    @Override
    public List<XjlApplyRecord> getXjlApplyRecordByUserId(int userId, int orgId, boolean detail, Boolean isToReturn) {
        return dao.getXjlApplyRecordByUserId(userId, orgId, detail, isToReturn);
    }

    @Override
    public List<XjlApplyRecord> selectXjlApplyRecords(boolean getStatusCnts, Integer userId, String[] status,
        String searchText, Timestamp startDate, Timestamp endDate, int orgId, Integer dateType, Integer accountId,
        String channelCode, Integer finalAccId, Integer collectorAccId, String listType, Integer loanType) {
        return dao.selectXjlApplyRecords(getStatusCnts, userId, status, searchText, startDate, endDate, orgId, dateType,
            accountId, channelCode, finalAccId, collectorAccId, listType, loanType);
    }

    @Override
    public LoanTotal selectXjlApplyRecordsTotal(Integer userId, String[] status, String searchText, Timestamp startDate,
        Timestamp endDate, int orgId) {
        return dao.selectXjlApplyRecordsTotal(userId, status, searchText, startDate, endDate, orgId);
    }

    @Override
    public List<StatusCnt> selectStatusCnts(Integer userId, String[] status, String searchText, Timestamp startDate,
        Timestamp endDate, int orgId, Integer accountId, String listType, Integer dateType) {
        return dao.selectStatusCnts(userId, status, searchText, startDate, endDate, orgId, accountId, listType,
            dateType);
    }

    @Override
    public XjlApplyRecord selectXjlApplyRecordByOpenId(String openId) {
        return dao.selectXjlApplyRecordByOpenId(openId);
    }

    @Override
    public boolean checkXjlApplyExist(String applyId, int orgId) {
        return dao.checkXjlApplyExist(applyId, orgId);
    }

    @Override
    public boolean checkExistByUserId(int orgId, Integer userId) {
        return dao.checkExistByUserId(orgId, userId);
    }

    @Override
    public List<XjlApplyRecordListVO> selectXjlApplyRecordList(String platform, String auditor, String collector,
        String status, String startDate, String endDate, String searchText) {
        return dao.selectXjlApplyRecordList(platform, auditor, collector, status, startDate, endDate, searchText);
    }

    @Override
    public void updateStatus(int status, String applyId) {
        dao.updateStatus(status, applyId);
    }

    @Override
    public int updateAscriptionUserId(String applyId, long ascriptionUserId) {
        return dao.updateAscriptionUserId(applyId, ascriptionUserId);
    }

    @Override
    public List<MachineTrialOrderVO> getMachineTrialOrderList(String channelName, String claim, String startDate,
        String endDate) {
        return dao.selectMachineTrialOrderList(channelName, claim, startDate, endDate);
    }

    @Override
    public List<XjlApplyRecordStatistic> getXjlPerformance(int orgId, String startS, String endS) {
        return dao.selectXjlPerformance(orgId, startS, endS);
    }

    @Override
    public void updateXjlApplyRecordStatus(String applyId, Integer status, String comment) {
        dao.updateXjlApplyRecordStatus(applyId, status, comment);
    }

    @Override
    public boolean checkApplyPull(String applyId, int orgId, String listType) {

        return dao.checkApplyPull(applyId, orgId, listType);
    }

    @Override
    public void saveApplyPull(String applyId, int accountId, String listType) {
        dao.saveApplyPull(applyId, accountId, listType);
    }

    @Override
    public int updateCollectorAccId(String applyId, int collectorAccId) {
        return dao.updateCollectorAccId(applyId, collectorAccId);
    }

	@Override
	public List<XjlApplyRecord> queryXjlApplyRecordList(int accountId, String listType, String searchText,
			Timestamp applyStartDate, Timestamp applyEndDate, Timestamp loanStartDate, Timestamp loanEndDate,
			Timestamp repaymentStartDate, Timestamp repaymentEndDate, Integer loanType, String channelCode,
			Boolean isReceived, Boolean isRenewal, String[] status, Integer collectorAccId, Integer repaymentStatus,
			int orgId) {
	
		return dao.queryXjlApplyRecordList(accountId, listType, searchText, 
				applyStartDate, applyEndDate, loanStartDate, loanEndDate, 
				repaymentStartDate, repaymentEndDate, loanType, channelCode, 
				isReceived, isRenewal, status, collectorAccId, repaymentStatus,orgId);	
	}

	@Override
	public List<StatusCnt> queryStatusCnts(String[] status, String searchText, int orgId, Integer accountId,
			String listType, Timestamp applyStartDate, Timestamp applyEndDate, Timestamp loanStartDate,
			Timestamp loanEndDate, Timestamp repaymentStartDate, Timestamp repaymentEndDate) {
		
		return dao.queryStatusCnts(status, searchText, orgId, accountId, listType,
				applyStartDate, applyEndDate, loanStartDate, loanEndDate, 
				repaymentStartDate, repaymentEndDate);
	}

	
}
