package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.LoanTotal;
import com.jbb.mgt.core.domain.MachineTrialOrderVO;
import com.jbb.mgt.core.domain.StatusCnt;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.domain.XjlApplyRecordListVO;
import com.jbb.mgt.core.domain.XjlApplyRecordStatistic;

/**
 * 申请操作Mapper接口
 *
 * @author wyq
 * @date 2018/8/21 10:50
 */
public interface XjlApplyRecordMapper {

    void insertXjlApplyRecord(XjlApplyRecord xjlApplyRecordService);

    void updateXjlApplyRecord(XjlApplyRecord xjlApplyRecordService);

    XjlApplyRecord selectXjlApplyRecordByApplyId(@Param("applyId") String applyId, @Param("orgId") Integer orgId,
        @Param("preLoan") Boolean preLoan);

    List<XjlApplyRecord> selectXjlApplyRecords(@Param("getStatusCnts") boolean getStatusCnts,
        @Param("userId") Integer userId, @Param("status") String[] status, @Param("searchText") String searchText,
        @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate, @Param("orgId") int orgId,
        @Param("dateType") Integer dateType, @Param("accountId") Integer accountId,
        @Param("channelCode") String channelCode, @Param("finalAccId") Integer finalAccId,
        @Param("collectorAccId") Integer collectorAccId, @Param("listType") String listType,
        @Param("loanType") Integer loanType);

    LoanTotal selectXjlApplyRecordsTotal(@Param("userId") Integer userId, @Param("status") String[] status,
        @Param("searchText") String searchText, @Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate, @Param("orgId") int orgId);

    void deleteXjlApplyRecordByapplyId(@Param("applyId") String applyId);

    List<XjlApplyRecord> getXjlApplyRecordByUserId(@Param("userId") int userId, @Param("orgId") Integer orgId,
        @Param("detail") boolean detail, @Param("isToReturn") Boolean isToReturn);

    List<StatusCnt> selectStatusCnts(@Param("userId") Integer userId, @Param("status") String[] status,
        @Param("searchText") String searchText, @Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate, @Param("orgId") int orgId, @Param("accountId") Integer accountId,
        @Param("listType") String listType, @Param("dateType") Integer dateType);

    XjlApplyRecord selectXjlApplyRecordByOpenId(@Param("openId") String openId);

    int checkXjlApplyExist(@Param("applyId") String applyId, @Param("orgId") int orgId);

    List<XjlApplyRecord> selectXjlApplyRecordForCheck(@Param("num") int num);

    int checkExistByUserId(@Param("orgId") int orgId, @Param("userId") Integer userId);

    void updateXjlApplyRecordStatus(@Param("applyId") String applyId, @Param("status") Integer status,
        @Param("comment") String comment);

    List<XjlApplyRecordListVO> selectXjlApplyRecordList(@Param("platform") String platform,
        @Param("auditor") String auditor, @Param("collector") String collector, @Param("status") String status,
        @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("searchText") String searchText);

    /**
     * 操作：通过并放款 拒绝
     * 
     * @param status
     * @param applyId
     */
    void updateStatus(@Param("status") int status, @Param("applyId") String applyId);

    int updateAscriptionUserId(@Param("applyId") String applyId, @Param("ascriptionUserId") long ascriptionUserId);

    List<MachineTrialOrderVO> selectMachineTrialOrderList(@Param("channelName") String channelName,
        @Param("claim") String claim, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<XjlApplyRecordStatistic> selectXjlPerformance(@Param("orgId") int orgId, @Param("startDate") String startDate,
        @Param("endDate") String endDate);

    int checkApplyPull(@Param("applyId") String applyId, @Param("orgId") int orgId, @Param("listType") String listType);

    void saveApplyPull(@Param("applyId") String applyId, @Param("accountId") int accountId,
        @Param("listType") String listType);

    int updateCollectorAccId(@Param("applyId") String applyId, @Param("collectorAccId") int collectorAccId);

    List<XjlApplyRecord> selectXjlApplyRecordsByStatus(@Param("status") Integer status,
        @Param("startDate") String startDate, @Param("startDay") Integer startDay, @Param("endDay") Integer endDay);
    
    List<XjlApplyRecord> queryXjlApplyRecordList(@Param("accountId") Integer accountId,@Param("listType")String listType,
    		@Param("searchText")String searchText, @Param("applyStartDate")Timestamp applyStartDate, 
    		@Param("applyEndDate")Timestamp applyEndDate, 
    		@Param("loanStartDate")Timestamp loanStartDate,@Param("loanEndDate")Timestamp loanEndDate,
    		@Param("repaymentStartDate")Timestamp repaymentStartDate,@Param("repaymentEndDate")Timestamp repaymentEndDate,
    		@Param("loanType")Integer loanType,@Param("channelCode")String channelCode, @Param("isReceived")Boolean isReceived, 
    		@Param("isRenewal")Boolean isRenewal,@Param("status")String [] status,@Param("collectorAccId")Integer collectorAccId, 
    		@Param("repaymentStatus")Integer repaymentStatus,@Param("orgId") int orgId);
    
    List<StatusCnt> queryStatusCnts(@Param("status")String[] status, 
    		@Param("searchText")String searchText,@Param("orgId")int orgId, @Param("accountId")Integer accountId,
    		@Param("listType")String listType,
    		@Param("applyStartDate")Timestamp applyStartDate, 
    		@Param("applyEndDate")Timestamp applyEndDate, 
    		@Param("loanStartDate")Timestamp loanStartDate,@Param("loanEndDate")Timestamp loanEndDate,
    		@Param("repaymentStartDate")Timestamp repaymentStartDate,@Param("repaymentEndDate")Timestamp repaymentEndDate);

}
