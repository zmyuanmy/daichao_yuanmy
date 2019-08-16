package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.LoanTotal;
import com.jbb.mgt.core.domain.MachineTrialOrderVO;
import com.jbb.mgt.core.domain.StatusCnt;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.domain.XjlApplyRecordListVO;
import com.jbb.mgt.core.domain.XjlApplyRecordStatistic;

/**
 * 用户申请Service接口
 *
 * @author wyq
 * @date 2018/8/21 10:28
 */
public interface XjlApplyRecordService {

    void saveXjlApplyRecord(XjlApplyRecord xjlApplyRecord);

    void updateXjlApplyRecord(XjlApplyRecord xjlApplyRecord);

    XjlApplyRecord selectXjlApplyRecordByApplyId(String applyId, Integer orgId, Boolean preLoan);

    XjlApplyRecord getXjlApplyRecordByApplyId(String applyId, Integer orgId, Boolean preLoan);

    void deleteXjlApplyRecord(String applyId);

    List<XjlApplyRecord> getXjlApplyRecordByUserId(int userId, int orgId, boolean detail, Boolean isToReturn);

    List<XjlApplyRecord> selectXjlApplyRecords(boolean getStatusCnts, Integer userId, String[] status,
        String searchText, Timestamp startDate, Timestamp endDate, int orgId, Integer dateType, Integer accountId,
        String channelCode, Integer finalAccId, Integer collectorAccId, String listType, Integer loanType);

    LoanTotal selectXjlApplyRecordsTotal(Integer userId, String[] status, String searchText, Timestamp startDate,
        Timestamp endDate, int orgId);

    List<StatusCnt> selectStatusCnts(Integer userId, String[] status, String searchText, Timestamp startDate,
        Timestamp endDate, int orgId, Integer accountId, String listType, Integer dateType);

    XjlApplyRecord selectXjlApplyRecordByOpenId(String openId);

    boolean checkXjlApplyExist(String applyId, int orgId);

    boolean checkExistByUserId(int orgId, Integer userId);

    // 更改申请记录状态
    void updateXjlApplyRecordStatus(String applyId, Integer status, String comment);

    /**
     * add by 2018-9-19
     */
    List<XjlApplyRecordListVO> selectXjlApplyRecordList(String platform, String auditor, String collector,
        String status, String startDate, String endDate, String searchText);

    /**
     * 修改申请记录状态 （审核通过，拒绝）
     */
    void updateStatus(int status, String applyId);

    /**
     * 修改申请扩展表 客户归属（admin后台领取使用）
     * 
     * @param applyId
     * @param ascriptionUserId
     * @return
     */
    int updateAscriptionUserId(String applyId, long ascriptionUserId);

    boolean checkApplyPull(String applyId, int orgId, String listType);

    void saveApplyPull(String applyId, int accountId, String listType);

    int updateCollectorAccId(String applyId, int collectorAccId);

    List<MachineTrialOrderVO> getMachineTrialOrderList(String channelName, String claim, String startDate,
        String endDate);

    List<XjlApplyRecordStatistic> getXjlPerformance(int orgId, String startS, String endS);

    List<XjlApplyRecord> queryXjlApplyRecordList(int accountId,String listType,String searchText, Timestamp applyStartDate, Timestamp applyEndDate, Timestamp loanStartDate,Timestamp loanEndDate,
    		Timestamp repaymentStartDate,Timestamp repaymentEndDate,Integer loanType,String channelCode, Boolean  isReceived, Boolean isRenewal,String [] status,Integer collectorAccId, Integer repaymentStatus,int orgId);
    
    List<StatusCnt> queryStatusCnts(String[] status, String searchText,int orgId, Integer accountId, String listType,
    		Timestamp applyStartDate, Timestamp applyEndDate, Timestamp loanStartDate,Timestamp loanEndDate,
    		Timestamp repaymentStartDate,Timestamp repaymentEndDate);

   
}
