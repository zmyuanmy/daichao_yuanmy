package com.jbb.mgt.rs.action.msgCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.service.ChuangLanService;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.core.service.XjlApplyRecordService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;

@Component(XjlRemindMsgCodeAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlRemindMsgCodeAction extends BasicAction {

    public static final String ACTION_NAME = "XjlRemindMsgCodeAction";

    private static Logger logger = LoggerFactory.getLogger(XjlRemindMsgCodeAction.class);
    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private XjlApplyRecordService xjlApplyRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ChuangLanService chuangLanService;

    public void remindSendCode(String applyId, Integer userId, Integer status) {
        logger.debug(">remindSendCode()");
        verifyParam(applyId, userId, status);
        int orgId = this.account.getOrgId();
        XjlApplyRecord applyRecord = xjlApplyRecordService.getXjlApplyRecordByApplyId(applyId, orgId, false);
        if (applyRecord == null || (applyRecord != null && !applyRecord.getUserId().equals(userId))) {
            throw new ObjectNotFoundException("jbb.mgt.exception.applyNotFound");
        }
        User user = userService.selectUserById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.user.notFound");
        }

        Organization org = organizationService.getOrganizationById(orgId, false);
        String date = "";
        String day = "";
        if (applyRecord.getLoanDate() != null) {
            date = applyRecord.getLoanDate().toString().substring(0, 11);
        }

        if (status == 3) {
            // 判断是否是明天到期
            long t1 = applyRecord.getRepaymentDate().getTime();
            t1 = t1 - DateUtil.DAY_MILLSECONDES;
            long toDay = DateUtil.getTodayStartTs();
            if (t1 < toDay && t1 >= toDay + DateUtil.DAY_MILLSECONDES) {
                throw new WrongParameterValueException("jbb.xjl.error.apply.dueTomorrow");
            }
        } else if (status == 4) {
            day = String.valueOf(
                DateUtil.calDays(applyRecord.getRepaymentDate().getTime(), DateUtil.getCurrentTimeStamp().getTime()));
        }
        chuangLanService.sendMsgCodeXjlRemind(applyId, this.account.getAccountId(), user.getPhoneNumber(),
            org.getSmsSignName(), status, user.getUserName(), String.valueOf((applyRecord.getAmount() / 100)), date,
            day);
        logger.debug("<remindSendCode()");
    }

    private void verifyParam(String applyId, Integer userId, Integer status) {
        if (applyId == null) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "applyId");
        }
        if (userId == null || userId <= 0) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "userId");
        }
        if (status == null || status < 0) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "status");
        }
    }

}
