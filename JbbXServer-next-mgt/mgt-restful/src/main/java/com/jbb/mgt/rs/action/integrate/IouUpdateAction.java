package com.jbb.mgt.rs.action.integrate;

import java.util.TimeZone;

import com.jbb.server.common.exception.ObjectNotFoundException;
import org.apache.commons.lang.StringUtils;
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
import com.jbb.mgt.core.domain.UserLoanRecord;
import com.jbb.mgt.core.domain.UserLoanRecordDetail;
import com.jbb.mgt.core.service.UserLoanRecordService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

@Service(IouUpdateAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IouUpdateAction extends BasicAction {

    public static final String ACTION_NAME = "IouUpdateAction";

    private static Logger logger = LoggerFactory.getLogger(IouUpdateAction.class);

    @Autowired
    private UserLoanRecordService userLoanRecordService;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

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

    public void updateIou(Request req) {
        logger.debug(">updateIou()");
        if (req == null) {
            logger.debug("<updateIou() missing request");
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "request");
        }
        logger.debug(">updateIou() iouCode:{} extentionDate:{} status:{}", req.iouCode,
                req.extentionDate, req.status);
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            if (req.status == null || req.status == 0) {
                logger.debug("<updateIou() missing status");
                throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "status");
            }
            if (StringUtils.isEmpty(req.iouCode)) {
                logger.debug("<updateIou() missing iouCode");
                throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "iouCode");
            }
            UserLoanRecord userLoanRecord = userLoanRecordService.selectUserLoanRecordByIouCode(req.iouCode);
            if (userLoanRecord == null) {
                logger.debug("<updateIou() wrong iouCode:{}",req.iouCode);
                throw new ObjectNotFoundException("jbb.mgt.exception.userloanrecord.notFound", "zh", "userLoanRecord");
            }
            int beforeIouStatus = userLoanRecord.getIouStatus() == null ? 0 : userLoanRecord.getIouStatus();
            userLoanRecord.setIouCode(req.iouCode);
            userLoanRecord.setExtentionDate(Util.parseTimestamp(req.extentionDate, TimeZone.getDefault()));
            userLoanRecord.setIouStatus(req.status);
            userLoanRecordService.updateUserLoanRecordByIouCode(userLoanRecord, beforeIouStatus);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction (release user lock)
            rollbackTransaction(txStatus);
        }
        logger.debug("<updateIou()");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String iouCode;
        public String extentionDate;
        public Integer status;
    }

}
