package com.jbb.mgt.rs.action.loanRecord;

import java.util.List;

import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.LoanOpLog;
import com.jbb.mgt.core.service.LoanRecordOpLogService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Service(LoanOpLogAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoanOpLogAction extends BasicAction {

    public static final String ACTION_NAME = "LoanOpLogAction";

    private static Logger logger = LoggerFactory.getLogger(LoanOpLogAction.class);

    private Response response;

    @Autowired
    LoanRecordOpLogService loanRecordOpLogService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new LoanOpLogAction.Response();
    }

    /**
     * 新增操作记录
     * 
     * @param loanId
     * @param req
     */
    public void insertLoanOpLog(Integer loanId, Request req) {
        logger.debug(">insertLoanOpLog()");
        if (req == null) {
            logger.debug("<insertLoanOpLog() missing request");
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "request");
        }
        logger.debug(">insertLoanOpLog() loanId:{} opType:{} reason:{} comment:{}", loanId, req.opType, req.reason,
            req.comment);
        if (null == loanId || loanId == 0) {
            logger.debug("<insertLoanOpLog() missing loanId");
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "loanId");
        }
        // 记录操作日志
        LoanOpLog opLog = new LoanOpLog(loanId, req.opType == null ? 0 : req.opType, this.account.getAccountId(),
            req.comment, req.reason);
        loanRecordOpLogService.insertOpLog(opLog);
        logger.debug("<insertLoanOpLog()");
    }

    /**
     * 查询操作记录
     * 
     * @param loanId
     */
    public void getLoanOpLogs(Integer loanId, Integer[] opTypes) {
        logger.debug(">getLoanOpLogs() loanId:{} opTypes:{}",loanId,opTypes);
        if (null == loanId || loanId == 0) {
            logger.debug(">getLoanOpLogs() missing loanId");
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "loanId");
        }
        this.response.opLogs = loanRecordOpLogService.selectLoanRecordOpLogByLoanId(loanId, opTypes);
        logger.debug("<getLoanOpLogs()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<LoanOpLog> opLogs;

        public List<LoanOpLog> getOpLogs() {
            return opLogs;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer opType;
        public String reason;
        public String comment;
    }

}
