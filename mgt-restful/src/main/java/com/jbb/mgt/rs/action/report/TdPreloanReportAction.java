
package com.jbb.mgt.rs.action.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.PreloanReport;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.service.TdRiskService;
import com.jbb.mgt.core.service.UserApplyRecordService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ObjectNotFoundException;

@Service(TdPreloanReportAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TdPreloanReportAction extends BasicAction {
    public static final String ACTION_NAME = "TdPreloanReportAction";

    private static Logger logger = LoggerFactory.getLogger(TdPreloanReportAction.class);

    private Response response;

    @Autowired
    private TdRiskService tdRiskService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserApplyRecordService userApplyRecordService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getPreloanReport(int applyId, int userId, Boolean refreshData) {
        logger.debug(">getPreloanReport() applyId ={},userId={},  refreshData={}", applyId, userId, refreshData);

        User user = userService.selectUserById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.userNotFound");
        }
        UserApplyRecord userApplyRecord =
            userApplyRecordService.getUserApplyRecordByApplyId(applyId, null, null, account.getOrgId());

        if (userApplyRecord == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.applyNotFound");
        }

        boolean refreshReportF = (refreshData != null && refreshData == true);
        this.response.report =
            tdRiskService.getPreloanReportByUserId(user, userApplyRecord, refreshReportF, this.account);

        // 设置用户类型
        if (userApplyRecord.getsOrgId() == Constants.JBB_ORG) {
            // 借帮帮导流类型
            this.response.userType = userApplyRecord.getsUserType();
        } else {
            this.response.userType = 0; // 自有渠道
        }
        // 设置费用
        this.response.fee = PropertyManager.getIntProperty("jbb.mgt.td.price", 40);

        logger.debug("<getPreloanReport");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        PreloanReport report;
        int fee;
        int userType;

        public PreloanReport getReport() {
            return report;
        }

        public int getFee() {
            return fee;
        }

        public int getUserType() {
            return userType;
        }

    }

}
