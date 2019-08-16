package com.jbb.mgt.rs.action.xjlApplyRecord;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbb.mgt.core.domain.LoanTotal;
import com.jbb.mgt.core.domain.StatusCnt;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.domain.XjlApplyRecordStatistic;
import com.jbb.mgt.core.service.XjlApplyRecordService;
import com.jbb.mgt.core.service.XjlUserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.AccessDeniedException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

/**
 * 申请列表Action
 *
 * @author wyq
 * @date 2018/8/27 10:32
 */
@Service(XjlApplyRecordAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlApplyRecordAction extends BasicAction {
    public static final String ACTION_NAME = "XjlApplyRecordAction";

    private static Logger logger = LoggerFactory.getLogger(XjlApplyRecordAction.class);
    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    private Response response;

    @Autowired
    private XjlApplyRecordService service;
    @Autowired
    private PlatformTransactionManager txManager;
    @Autowired
    private XjlUserService xjlUserService;

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

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getXjlApplyRecords(int pageNo, int pageSize, String startDate, String endDate, String[] status,
        String searchText, Integer userId, Integer dateType, String channelCode, Integer finalAccId,
        Integer collectorAccId, String listType, Integer loanType, Boolean getStatusCount, Boolean getLoanTotal) {
        logger.debug(">getXjlApplyRecords, pageNo= " + pageNo + ",pageSize= " + pageSize + ",startDate=" + startDate
            + ",endDate=" + endDate + ",status=" + status + ",searchText=" + searchText + ",userId=" + userId);
        if (pageNo <= 0) {
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "pageNo");
        }
        pageSize = pageSize <= 0 ? 10 : pageSize;

        Timestamp startS = Util.parseTimestamp(startDate, getTimezone());
        Timestamp endS = Util.parseTimestamp(endDate, getTimezone());
        status = status.length == 0 ? null : status;
        searchText = StringUtil.isEmpty(searchText) ? null : searchText;
        getStatusCount = getStatusCount == null ? false : getStatusCount;
        getLoanTotal = getLoanTotal == null ? false : getLoanTotal;
        List<XjlApplyRecord> list = new ArrayList<>();
        LoanTotal list2 = new LoanTotal();
        List<StatusCnt> statusCnts = new ArrayList<>();
        PageHelper.startPage(pageNo, pageSize);
        Integer accountId = (this.isXjlOrgAdmin() || this.isXjlSysAdmin())
            && (listType.equals("finalPull") || listType.equals("machineCheck")) ? null : this.account.getAccountId();
        if (null == userId) {
            list = service.selectXjlApplyRecords(false, userId, status, searchText, startS, endS,
                this.account.getOrgId(), dateType, accountId, channelCode, finalAccId, collectorAccId, listType,
                loanType);
            if (getStatusCount) {
                statusCnts = service.selectStatusCnts(userId, null, searchText, startS, endS, this.account.getOrgId(),
                    accountId, listType, dateType);
            } ;
        } else {
            list = service.selectXjlApplyRecords(false, userId, null, null, null, null, this.account.getOrgId(), null,
                null, null, null, null, null, null);
            if (getStatusCount) {
            	
                statusCnts = service.selectStatusCnts(userId, null, null, null, null, this.account.getOrgId(), null,
                    null, null);
            }
            if (getLoanTotal) {
                list2 = service.selectXjlApplyRecordsTotal(userId, null, null, null, null, this.account.getOrgId());
            }
            if (null != list2 && null != userId) {
                this.response.loanTotal = list2;
            }
        }
        PageInfo<XjlApplyRecord> pageInfo = new PageInfo<XjlApplyRecord>(list);
        this.response.pageInfo = pageInfo;
        this.response.statusCnts = statusCnts;
        PageHelper.clearPage();
        logger.debug("<getXjlApplyRecords");
    }

    public void getXjlApplyRecordsByUserId(Integer orgId, Boolean isToReturn) {
        logger.debug(">getXjlApplyRecords");
        if (orgId == null || orgId < 1) {
            throw new WrongParameterValueException("jbb.mgt.exception.orgNotFound");
        }
        Boolean isToReturnF = isToReturn == null ? false : isToReturn;
        Boolean detali = isToReturnF == true ? false : true;
        List<XjlApplyRecord> list
            = service.getXjlApplyRecordByUserId(this.user.getUserId(), orgId, detali, isToReturnF);

        this.response.applies = list;

        logger.debug("<getXjlApplyRecords");

    }

    public void getXjlPerformance(String startDate, String endDate, int pageNo, int pageSize) {
        logger.debug(">getXjlPerformance");
        startDate = StringUtil.isEmpty(startDate) ? null : startDate;
        endDate = StringUtil.isEmpty(endDate) ? null : endDate;
        List<XjlApplyRecordStatistic> list = service.getXjlPerformance(this.account.getOrgId(), startDate, endDate);
        this.response.list = list;
        logger.debug("<getXjlPerformance");

    }

    public void updateXjlApplyRecordStatus(String applyId, Integer status, Request req) {
        logger.debug(">updateXjlApplyRecordStatus");
        TransactionStatus txStatus = null;
        String comment = req == null || StringUtil.isEmpty(req.comment) ? null : req.comment;
        XjlApplyRecord apply = service.getXjlApplyRecordByApplyId(applyId, this.account.getOrgId(), false);
        if (null == apply) {
            throw new WrongParameterValueException("jbb.xjl.exception.apply.notFound");
        }
        if (apply.getStatus() != 0 && apply.getStatus() != 6) {
            throw new WrongParameterValueException("jbb.xjl.error.exception.applyStatus");
        }
        if (status == null || (status != 1 && status != 2 && status != 7)) {
            throw new WrongParameterValueException("jbb.xjl.error.exception.applyOtherStatus");
        }
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            service.updateXjlApplyRecordStatus(applyId, status, comment);
            if(status==7) {
                xjlUserService.saveUserStatus(apply.getUserId(), this.account.getOrgId(), 2);
            }
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        logger.debug(">updateXjlApplyRecordStatus");
    }

    public void applyPull(String listType, ApplyRequest req) {
        logger.debug(">applyPull()");
        if (!((listType.equals("toFinalPull") && this.isXjlFinalAcc())
            || (listType.equals("toCollectPull") && this.isXjlCollectAcc()))) {
            throw new AccessDeniedException("jbb.error.validateAdminAccess.accessDenied");
        }

        if (req.applyIds == null || req.applyIds.length == 0) {
            return;
        }
        int pullCnt = 0;
        int pullFailCnt = 0;

        for (int i = 0; i < req.applyIds.length; i++) {
            if (service.checkApplyPull(req.applyIds[i], this.account.getOrgId(), listType)) {
                service.saveApplyPull(req.applyIds[i], this.account.getAccountId(), listType);
                pullCnt = pullCnt + 1;
            } else {
                pullFailCnt = pullFailCnt + 1;
                continue;
            }
        }
        this.response.totalCnt = req.applyIds.length;
        this.response.successCnt = pullCnt;
        this.response.failCnt = pullFailCnt;
        logger.debug("<applyPull()");

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String comment;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ApplyRequest {
        public String [] applyIds;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private PageInfo<XjlApplyRecord> pageInfo;
        private LoanTotal loanTotal;
        private List<StatusCnt> statusCnts;
        private List<XjlApplyRecord> applies;
        public List<XjlApplyRecordStatistic> list;
        private Integer totalCnt;
        private Integer successCnt;
        private Integer failCnt;

        public PageInfo<XjlApplyRecord> getPageInfo() {
            return pageInfo;
        }

        public LoanTotal getLoanTotal() {
            return loanTotal;
        }

        public List<StatusCnt> getStatusCnts() {
            return statusCnts;
        }

        public List<XjlApplyRecord> getApplies() {
            return applies;
        }

        public List<XjlApplyRecordStatistic> getList() {
            return list;
        }

        public Integer getTotalCnt() {
            return totalCnt;
        }

        public Integer getSuccessCnt() {
            return successCnt;
        }

        public Integer getFailCnt() {
            return failCnt;
        }

    }
    
	public void queryXjlApplyRecords(int pageNo, int pageSize, String listType, String searchText,
			String applyStartDate, String applyEndDate, String loanStartDate, String loanEndDate,
			String repaymentStartDate, String repaymentEndDate, Integer loanType, String channelCode,
			Boolean isReceived, Boolean isRenewal, String[] status, Integer collectorAccId, Integer repaymentStatus) {

		logger.debug(">queryXjlApplyRecords");
		if (pageNo <= 0) {
			throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "pageNo");
		}
		pageSize = pageSize <= 0 ? 10 : pageSize;
		Timestamp applyStart = Util.parseTimestamp(applyStartDate, getTimezone());
		Timestamp applyEnd = Util.parseTimestamp(applyEndDate, getTimezone());
		Timestamp loanStart = Util.parseTimestamp(loanStartDate, getTimezone());
		Timestamp loanEnd = Util.parseTimestamp(loanEndDate, getTimezone());
		Timestamp repaymentStart = Util.parseTimestamp(repaymentStartDate, getTimezone());
		Timestamp repaymentEnd = Util.parseTimestamp(repaymentEndDate, getTimezone());

		status = status.length == 0 ? null : status;
		searchText = StringUtil.isEmpty(searchText) ? null : searchText;
		listType = StringUtil.isEmpty(listType) ? null : listType;
		channelCode = StringUtil.isEmpty(channelCode) ? null : channelCode;
		
		List<XjlApplyRecord> list = new ArrayList<>();
		
		List<StatusCnt> statusCnts = new ArrayList<>();
		PageHelper.startPage(pageNo, pageSize);
		Integer accountId = (this.isXjlOrgAdmin() || this.isXjlSysAdmin())
				&& (listType.equals("finalPull") || listType.equals("machineCheck")) ? null
						: this.account.getAccountId();

		list = service.queryXjlApplyRecordList(accountId, listType, searchText, applyStart, applyEnd, loanStart,
				loanEnd, repaymentStart, repaymentEnd, loanType, channelCode, isReceived, isRenewal, status,
				collectorAccId, repaymentStatus, this.account.getOrgId());

		statusCnts = service.queryStatusCnts(status, searchText, this.account.getOrgId(), accountId, listType,
				applyStart, applyEnd, loanStart, loanEnd, repaymentStart, repaymentEnd);		
		PageInfo<XjlApplyRecord> pageInfo = new PageInfo<XjlApplyRecord>(list);
		this.response.pageInfo = pageInfo;
		this.response.statusCnts = statusCnts;
		PageHelper.clearPage();
		logger.debug("<queryXjlApplyRecords");

	}

}
