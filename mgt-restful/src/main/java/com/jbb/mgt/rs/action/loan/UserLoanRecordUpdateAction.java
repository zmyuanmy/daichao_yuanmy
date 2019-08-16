
package com.jbb.mgt.rs.action.loan;

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
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.AccountOpLog;
import com.jbb.mgt.core.domain.IouStatus;
import com.jbb.mgt.core.domain.LoanOpLog;
import com.jbb.mgt.core.domain.LoanRecordUpdateRsp;
import com.jbb.mgt.core.domain.ReMgtIou;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.domain.UserLoanRecord;
import com.jbb.mgt.core.domain.UserLoanRecordDetail;
import com.jbb.mgt.core.service.AccountOpLogService;
import com.jbb.mgt.core.service.JbbService;
import com.jbb.mgt.core.service.LoanRecordOpLogService;
import com.jbb.mgt.core.service.UserApplyRecordService;
import com.jbb.mgt.core.service.UserLoanRecordService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;
import com.jbb.server.common.exception.JbbCallException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@Service(UserLoanRecordUpdateAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserLoanRecordUpdateAction extends BasicAction {

    public static final String ACTION_NAME = "UserRecordUpdateAction";

    private static Logger logger = LoggerFactory.getLogger(UserLoanRecordUpdateAction.class);

    private static DefaultTransactionDefinition NEW_TX_DEFINITION =
        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private UserLoanRecordService userLoanRecordService;

    @Autowired
    private UserApplyRecordService userAppyRecordService;

    @Autowired
    private LoanRecordOpLogService loanRecordOpLogService;

    @Autowired
    private AccountOpLogService accountOpLogService;

    @Autowired
    private JbbService jbbService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlatformTransactionManager txManager;

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

    public void saveUserLoanRecord(Request req) {
        logger.debug(">saveUserLoanRecord()");
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            if (req.loanId == null) {
                createUserLaonRecord(req);
                markIouSuccess(req.applyId);

            } else {
                updateUserLaonRecord(req);
            }
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction (release user lock)
            rollbackTransaction(txStatus);
        }
        logger.debug("<saveUserLoanRecord()");
    }

    // 标记打借条成功
    private void markIouSuccess(int applyId) {
        UserApplyRecord applyRecord =
            userAppyRecordService.getUserApplyRecordByApplyId(applyId, null, null, this.account.getOrgId());

        applyRecord.setStatus(11);
        userAppyRecordService.updateUserAppayRecord(applyRecord);
        // 记录操作日志

        AccountOpLog opLog = new AccountOpLog(applyId, 20, this.account.getAccountId(), null, null);
        accountOpLogService.createOpLog(opLog);

    }

    private void updateUserLaonRecord(Request req) {
        logger.debug("<updateUserLaonRecord(), loanId=" + req.loanId + ", req=" + req);

        UserLoanRecord record = userLoanRecordService.getUserLoanRecordByLoanId(req.loanId);
        boolean isIouStatusChanged = (record.getIouPlatformId() == Constants.PLATFORM_JBB && req.iouStatus != null
            && req.iouStatus != record.getIouStatus());
        boolean isChanged = convert2Update(req, record);
        if (isChanged) {
            if (isIouStatusChanged) {
                // 同步借条数据到借帮帮平台
                IouStatus iouStatus = new IouStatus(record.getIouCode(), req.iouStatus, req.extentionDate);
                LoanRecordUpdateRsp rsp = jbbService.updateIouStatus(iouStatus);
                if (rsp == null || !rsp.getResultCode().equals(LoanRecordUpdateRsp.SUCCES_CODE)) {
                    throw new JbbCallException("jbb.mgt.integrate.exception.jbbError",rsp.getResultCodeMessage());
                }
            }
            userLoanRecordService.updateUserLoanRecord(record);
        }
        // 记录操作日志
        recordOpLog(record.getLoanId(), req);

        // 记账明细 支出收入明细
        saveUserLoanRecordDetail(record.getLoanId(), req);

        logger.debug("<updateUserLaonRecord()");
    }

    private void createUserLaonRecord(Request req) {
        logger.debug("<createUserLaonRecord() req=" + req);

        UserLoanRecord record = convert2Create(req);
        userLoanRecordService.createUserLoanRecord(record);
        // 记录操作日志
        recordOpLog(record.getLoanId(), req);

        logger.debug("<createUserLaonRecord()");
    }

    private void recordOpLog(int loanId, Request req) {
        if (req.opType != null) {
            LoanOpLog opLog = new LoanOpLog(loanId, req.opType == null ? 0 : req.opType, this.account.getAccountId(),
                req.commet, req.reason);
            loanRecordOpLogService.insertOpLog(opLog);
        }
    }

    private void saveUserLoanRecordDetail(int loanId, Request req) {
        // 记账明细
        if (req.opType != null && req.opMoneyType != null && req.opMoney != null) {
            UserLoanRecordDetail loanRecordDetail =
                new UserLoanRecordDetail(loanId, req.opType, req.opMoneyType, req.opMoney, this.account.getAccountId());
            userLoanRecordService.saveUserLoanRecordDetail(loanRecordDetail);
        }
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

    }

    private String getJbbIouCode(Request req) {
        ReMgtIou iou = new ReMgtIou();
        // 组装iou对象
        // borrower为 借款人的jbbId
        User user = userService.selectUserByApplyId(req.applyId, this.account.getOrgId());
        if (user == null || user.getJbbUserId() == null) {
            throw new WrongParameterValueException("jbb.mgt.integrate.exception.notFoundBorrowerJbbUserId");
        }
        // lenderId 为 出借人的jbbId
        Account lender = this.coreAccountService.getAccountById(req.loanAccId, null, false);
        if (lender == null || lender.getJbbUserId() == null) {
            throw new WrongParameterValueException("jbb.mgt.integrate.exception.notFoundLenderJbbUserId");
        }
        // 利率 要除100
        // 金额要除100.
        iou.setAnnualRate(req.annualRate / 100);
        iou.setBorrowingAmount(req.borrowingAmount / 100);
        iou.setBorrowerId(user.getJbbUserId());
        iou.setLenderId(lender.getJbbUserId());
        iou.setRepaymentDate(req.repaymentDate);
        iou.setBorrowingDate(req.borrowingDate);
        String iouCode = jbbService.fillIou(iou);
        if (StringUtil.isEmpty(iouCode)) {
            // 抛出调用JBBServer异常
            throw new JbbCallException("jbb.mgt.integrate.exception.jbbError");
        }
        return iouCode;
    }

    private UserLoanRecord convert2Create(Request req) {
        UserApplyRecord apply = userAppyRecordService.selectUserApplyRecordInfoByApplyId(req.applyId);
        if (apply == null) {
            throw new WrongParameterValueException("jbb.mgt.exception.apply.notFound");
        }
        UserLoanRecord record = new UserLoanRecord();
        record.setApplyId(apply.getApplyId());
        record.setUserId(apply.getUserId());
        record.setAccountId(req.accountId);
        record.setBorrowingAmount(req.borrowingAmount);
        record.setBorrowingDate(Util.parseTimestamp(req.borrowingDate, getTimezone()));
        record.setRepaymentDate(Util.parseTimestamp(req.repaymentDate, getTimezone()));
        record.setAnnualRate(req.annualRate);
        record.setUpdateDate(DateUtil.getCurrentTimeStamp());
        record.setCreationDate(DateUtil.getCurrentTimeStamp());
        record.setLoanAccId(req.loanAccId);
        if (req.iouPlatformId != null) {
            record.setIouPlatformId(req.iouPlatformId);
            if (req.iouPlatformId == Constants.PLATFORM_JBB) {
                record.setIouCode(getJbbIouCode(req));
                record.setStatus(-1);
                record.setIouStatus(30);
            } else {
                record.setStatus(0);
                record.setIouStatus(0);
                record.setIouCode(StringUtil.generateIoUId());
            }

        }
        return record;
    }

    private boolean convert2Update(Request req, UserLoanRecord record) {
        boolean isChanged = false;
        if (req.status != null) {
            record.setStatus(req.status);
            if (req.status == Constants.STATUS_FINISH_8 || req.status == Constants.STATUS_FINISH_7) {
                //销账时，标识下实际还款时间
                record.setActualRepaymentDate(DateUtil.getCurrentTimeStamp());
            } else if (req.status == 9) {
                record.setLoanDate(DateUtil.getCurrentTimeStamp());
            } else if (req.status == 9) {
                record.setActualRepaymentDate(DateUtil.getCurrentTimeStamp());
            } 
            
            if (req.iouStatus != null && req.iouStatus == 6 && record.getIouPlatformId() == Constants.PLATFORM_JBB) {
                //如果是JBB 平台 将展期申请的时间设置为还款时间
                record.setRepaymentDate(record.getExtentionDate());
            }
            isChanged = true;
        }
        if (req.borrowingAmount != null) {
            record.setBorrowingAmount(req.borrowingAmount);
            isChanged = true;
        }
        if (req.annualRate != null) {
            record.setAnnualRate(req.annualRate);
            isChanged = true;
        }

        if (req.borrowingDate != null) {
            record.setBorrowingDate(Util.parseTimestamp(req.borrowingDate, getTimezone()));
            isChanged = true;
        }

        if (req.repaymentDate != null) {
            record.setRepaymentDate(Util.parseTimestamp(req.repaymentDate, getTimezone()));
            isChanged = true;
        }

        if (req.loanAccId != null) {
            record.setLoanAccId(req.loanAccId);
            isChanged = true;
        }

        if (req.loanDate != null) {
            record.setLoanDate(Util.parseTimestamp(req.loanDate, getTimezone()));
            isChanged = true;
        }

        if (req.loanAmount != null) {
            int[] ps = {Constants.MGT_P_LOAN_MGT, Constants.MGT_P_ORGADMIN};// 没有确定权限
            this.validateUserAccess(ps);
            record.setLoanAmount(req.loanAmount);
            isChanged = true;
        }
        if (req.collectorAccId != null) {
            record.setCollectorAccId(req.collectorAccId);
            isChanged = true;
        }

        if (req.applyId != null) {
            UserApplyRecord apply = userAppyRecordService.selectUserApplyRecordInfoByApplyId(req.applyId);
            if (apply == null) {
                throw new WrongParameterValueException("jbb.mgt.exception.apply.notFound");
            }
            record.setApplyId(apply.getApplyId());
            record.setUserId(apply.getUserId());
            isChanged = true;
        }
        if (req.repayAmount != null) {
            int amount = record.getRepayAmount() == null ? 0 : record.getRepayAmount();
            record.setRepayAmount(amount + req.repayAmount);
            isChanged = true;
        }
        if (req.iouStatus != null) {
            record.setIouStatus(req.iouStatus);
            isChanged = true;
        }
        record.setUpdateDate(DateUtil.getCurrentTimeStamp());
        return isChanged;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {

        public Integer loanId;
        public Integer applyId;
        public Integer iouPlatformId;
        public Integer status;
        public Integer iouStatus;
        public Integer accountId;
        public Integer borrowingAmount;
        public Integer annualRate;
        public String borrowingDate;
        public String repaymentDate; // 展期确认和展期记账后的新还款日期
        public Integer loanAccId;
        public Integer loanAmount;
        public Integer repayAmount; // 催回金额
        public String loanDate;
        public Integer collectorAccId; // 转催收，催收人accountId

        public String extentionDate; // JBB借条展期申请时间

        // 操作日志
        public Integer opType;
        public String reason;
        public String commet;

        // 展期、销账、还款确认、展期确认、展期记账、销账记账、
        public Integer opMoneyType; // 还入、借出 类型
        public Integer opMoney; // 还入金额或者利息记为正， 借出借为负

    }

}
