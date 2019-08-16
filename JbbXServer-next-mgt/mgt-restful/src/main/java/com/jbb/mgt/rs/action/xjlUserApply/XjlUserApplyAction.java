package com.jbb.mgt.rs.action.xjlUserApply;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Agreement;
import com.jbb.mgt.core.domain.Credit;
import com.jbb.mgt.core.domain.UserAgreeLog;
import com.jbb.mgt.core.domain.UserCard;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.service.UserAgreeLogService;
import com.jbb.mgt.core.service.UserCardService;
import com.jbb.mgt.core.service.XjlApplyRecordService;
import com.jbb.mgt.core.service.XjlUserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;

@Service(XjlUserApplyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlUserApplyAction extends BasicAction {
    public static final String ACTION_NAME = "XjlUserApplyAction";

    private static Logger logger = LoggerFactory.getLogger(XjlUserApplyAction.class);
    // 完成状态
    private static int FINAL_STATUS = 99;
    // 拒绝
    private static int REJECT_STATUS = 2;

    private static int REJECT_STATUS_2 = 7;
    // 待审核
    private static int PRE_STATUS = 0;

    private static int USER_WHITE = 1;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private UserCardService userCardService;
    @Autowired
    private XjlApplyRecordService xjlApplyRecordService;
    @Autowired
    private UserAgreeLogService userAgreeLogService;
    @Autowired
    private XjlUserService xjlUserService;

    public void checkUserApply() {

    }

    public void xjlUserApply(Integer orgId, Request req) {
        logger.debug("> xjlUserApply()");
        int days = PropertyManager.getIntProperty("jbb.mgt.xjl.loanDays", 7);
        String payProductId = PropertyManager.getProperty("jbb.xjl.payProduct.id", "hlb");
        if (orgId == null || orgId < 1) {
            throw new WrongParameterValueException("jbb.mgt.exception.orgNotFound");
        }
        boolean loginStatus = this.user == null ? false : true;
        boolean userInfoStatus = loginStatus && this.user.isOcrIdcardVerified() && this.user.isXjlBasicInfoVerified()
            && this.user.isMobileVerified() && this.user.isVideoVerified() && this.user.isIdcardVerified() ? true
                : false;
        boolean cardStatus = false;
        XjlApplyRecord userApplyRecord = null;
        boolean applyStatus = false;
        if (userInfoStatus) {
            List<UserCard> list = userCardService.selectUserCards(this.user.getUserId(), payProductId);
            cardStatus = list == null || list.size() == 0 ? false : true;
        }

        if (cardStatus) {
            List<XjlApplyRecord> list
                = xjlApplyRecordService.getXjlApplyRecordByUserId(this.user.getUserId(), orgId, false, false);
            if (list != null && list.size() != 0 && list.get(0).getStatus() != FINAL_STATUS) {
                if (list.get(0).getStatus() == REJECT_STATUS || list.get(0).getStatus() == REJECT_STATUS_2) {
                    if ((DateUtil.getCurrentTime() - list.get(0).getCreationDate().getTime() <= days
                        * DateUtil.DAY_MILLSECONDES) && list.get(0).getUserStatus() != USER_WHITE) {
                        applyStatus = true;
                        userApplyRecord = list.get(0);
                    }

                } else {
                    applyStatus = true;
                    userApplyRecord = list.get(0);
                }

            }
        }
        if (loginStatus && userInfoStatus && cardStatus && !applyStatus) {
            XjlApplyRecord xjlApplyRecord = generateXjlApplyRecord(req, orgId);
            Timestamp creationDate = DateUtil.getCurrentTimeStamp();
            xjlApplyRecord.setCreationDate(creationDate);
            xjlApplyRecordService.saveXjlApplyRecord(xjlApplyRecord);
            xjlUserService.saveLastApplyDate(xjlApplyRecord.getUserId(), xjlApplyRecord.getOrgId(), creationDate);
            if (req.agreements != null && req.agreements.size() != 0) {
                for (int i = 0; i < req.agreements.size(); i++) {
                    UserAgreeLog userAgreeLog = new UserAgreeLog(xjlApplyRecord.getApplyId(),
                        req.agreements.get(i).getAgreementId(), DateUtil.getCurrentTimeStamp());
                    userAgreeLogService.insertUserAgreeLog(userAgreeLog);
                }
            }

        }
        this.response.loginStatus = loginStatus;
        this.response.userInfoStatus = userInfoStatus;
        this.response.cardStatus = cardStatus;
        this.response.applyStatus = applyStatus;
        this.response.userApplyRecord = userApplyRecord;
        logger.debug(">xjlUserApply()");
    }

    private XjlApplyRecord generateXjlApplyRecord(Request req, int orgId) {
        XjlApplyRecord xjlApplyRecord = new XjlApplyRecord();
        if (req == null || req.credit == null) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "credit");
        }
        if (req.credit.getAmount() > req.credit.getMaxAmount()
            || req.credit.getAmount() < req.credit.getMinAmount() - req.credit.getServiceFee()) {
            throw new WrongParameterValueException("jbb.xjl.exception.error.wrongAmount");
        }
        xjlApplyRecord.setAmount(req.credit.getAmount());
        xjlApplyRecord.setUserId(this.user.getUserId());
        xjlApplyRecord.setOrgId(orgId);
        if (req.credit.getPurpose() == null) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "purpose");
        }
        xjlApplyRecord.setPurpose(req.credit.getPurpose());
        xjlApplyRecord.setStatus(PRE_STATUS);
        xjlApplyRecord.setServiceFee(req.credit.getServiceFee());
        xjlApplyRecord.setDays(req.credit.getDays());
        if (req.agreements == null || req.agreements.size() == 0) {
            throw new MissingParameterException("jbb.xjl.error.agreement.empty");
        }

        return xjlApplyRecord;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Credit credit;
        public List<Agreement> agreements;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private boolean loginStatus;
        private boolean userInfoStatus;
        private boolean cardStatus;
        private boolean applyStatus;
        private XjlApplyRecord userApplyRecord;

        public boolean isLoginStatus() {
            return loginStatus;
        }

        public boolean isUserInfoStatus() {
            return userInfoStatus;
        }

        public boolean isCardStatus() {
            return cardStatus;
        }

        public boolean isApplyStatus() {
            return applyStatus;
        }

        public XjlApplyRecord getUserApplyRecord() {
            return userApplyRecord;
        }

    }

}
