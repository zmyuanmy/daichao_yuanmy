package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.LoanTotal;
import com.jbb.mgt.core.domain.MachineTrialOrderVO;
import com.jbb.mgt.core.domain.StatusCnt;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.domain.XjlApplyRecordListVO;
import com.jbb.mgt.core.domain.XjlApplyRecordStatistic;

/**
 * 申请操作Dao接口
 *
 * @author wyq
 * @date 2018/8/21 10:47
 */
public interface XjlApplyRecordDao {
    void insertXjlApplyRecord(XjlApplyRecord xjlApplyRecord);

    void updateXjlApplyRecord(XjlApplyRecord xjlApplyRecord);

    XjlApplyRecord selectXjlApplyRecordByApplyId(String applyId, Integer orgId, Boolean preLoan);

    void deleteXjlApplyRecord(String applyId);

    List<XjlApplyRecord> getXjlApplyRecordByUserId(int userId, Integer orgId, boolean detail, Boolean isToReturn);

    List<XjlApplyRecord> selectXjlApplyRecords(boolean getStatusCnts, Integer userId, String[] status,
        String searchText, Timestamp startDate, Timestamp endDate, int orgId, Integer dateType, Integer accountId,
        String channelCode, Integer finalAccId, Integer collectorAccId, String listType, Integer loanType);

    LoanTotal selectXjlApplyRecordsTotal(Integer userId, String[] status, String searchText, Timestamp startDate,
        Timestamp endDate, int orgId);

    List<StatusCnt> selectStatusCnts(Integer userId, String[] status, String searchText, Timestamp startDate,
        Timestamp endDate, int orgId, Integer accountId, String listType, Integer dateType);

    XjlApplyRecord selectXjlApplyRecordByOpenId(String openId);

    boolean checkXjlApplyExist(String applyId, int orgId);

    /**
     * 获取跑批审核申请记录的数据源
     * 
     * @param num
     *
     * @param count
     * @return
     */
    List<XjlApplyRecord> selectXjlApplyRecordForCheck(int num);

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

    List<MachineTrialOrderVO> selectMachineTrialOrderList(String channelName, String claim, String startDate,
        String endDate);

    List<XjlApplyRecordStatistic> selectXjlPerformance(int orgId, String startS, String endS);

    /**
     * 查询明日到期 已到期 已逾期(1-7天)数据
     */
    List<XjlApplyRecord> selectXjlApplyRecordsByStatus(Integer status, String startDate, Integer startDay,
        Integer endDay);
    
    List<XjlApplyRecord> queryXjlApplyRecordList(int accountId,String listType,String searchText, Timestamp applyStartDate, Timestamp applyEndDate, Timestamp loanStartDate,Timestamp loanEndDate,
    		Timestamp repaymentStartDate,Timestamp repaymentEndDate,Integer loanType,String channelCode, Boolean isReceived,Boolean isRenewal,String [] status,Integer collectorAccId, Integer repaymentStatus,int orgId);
    
    List<StatusCnt> queryStatusCnts(String[] status, String searchText,int orgId, Integer accountId, String listType,
    		Timestamp applyStartDate, Timestamp applyEndDate, Timestamp loanStartDate,Timestamp loanEndDate,
    		Timestamp repaymentStartDate,Timestamp repaymentEndDate);

}
