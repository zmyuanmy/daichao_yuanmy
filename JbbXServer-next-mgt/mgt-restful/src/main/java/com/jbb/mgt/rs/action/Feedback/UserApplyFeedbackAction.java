package com.jbb.mgt.rs.action.Feedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.UserApplyFeedback;
import com.jbb.mgt.core.domain.UserApplyRecord;

import com.jbb.mgt.core.service.UserApplyFeedbackService;
import com.jbb.mgt.core.service.UserApplyRecordService;

import com.jbb.mgt.rs.action.h5Merchant.H5MerchantAction;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;


@Service(UserApplyFeedbackAction.ACTION_NAME)
@Scope()
public class UserApplyFeedbackAction extends BasicAction {

    public static final String ACTION_NAME = "UserApplyFeedbackAction";

    private static Logger logger = LoggerFactory.getLogger(H5MerchantAction.class);

    @Autowired
    private UserApplyFeedbackService userApplyFeedbackService;

    @Autowired
    private UserApplyRecordService userApplyRecordService;

   

    public void saveUserApplyFeedback(Integer applyId, Request req) {
        logger.debug(">saveUserApplyFeedback");
        if (null == req) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "req");
        }
        UserApplyRecord applyRecord = userApplyRecordService.selectUserApplyRecordInfoByApplyId(applyId);
        if (null == applyRecord || (null != applyRecord && applyRecord.getOrgId() != this.account.getOrgId())) {
            throw new WrongParameterValueException("jbb.mgt.exception.feedback.notFound");
        }
        UserApplyFeedback feedback = generateFeedback(req);
        feedback.setApplyId(applyId);
        userApplyFeedbackService.saveUserApplyFeedback(feedback);
        logger.debug("<saveUserApplyFeedback");
    }

    private UserApplyFeedback generateFeedback(Request req) {
        UserApplyFeedback feedback = new UserApplyFeedback();
        feedback.setReason(req.reason);
        feedback.setReasonDesc(req.reasonDesc);
        feedback.setPoint(req.point = req.point == null ? 0 : req.point);
        feedback.setZjFlag(req.zjFlag = req.zjFlag == null ? false : req.zjFlag);
        return feedback;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String reason;
        public String reasonDesc;
        public Integer point;
        public Boolean zjFlag;
    }

    
    
   
}
