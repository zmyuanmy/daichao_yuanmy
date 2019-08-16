package com.jbb.mgt.rs.action.whtianbeiReport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.DataWhtianbeiReport;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.service.DataWhtianbeiReportService;
import com.jbb.mgt.core.service.UserApplyRecordService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ObjectNotFoundException;

@Service(WhtianbeiReportAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WhtianbeiReportAction extends BasicAction {

 
    public static final String ACTION_NAME = "WhtianbeiReportAction";

    private static Logger logger = LoggerFactory.getLogger(WhtianbeiReportAction.class);

    int updatePric = PropertyManager.getIntProperty("jbb.mgt.report.api.updatePric", 60);

    @Autowired
    private DataWhtianbeiReportService dataWhtianbeiReportService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserApplyRecordService userApplyRecordService;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getWhtianbeiReport(int userId, int applyId, Boolean refreshReport) {
        logger.debug(
            ">getWhtianbeiReport(), userId=" + userId + " , applyId=" + applyId + " , refreshReport=" + refreshReport);
        User user = userService.selectUserById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.userNotFound");
        }
        UserApplyRecord userApplyRecord =
            userApplyRecordService.getUserApplyRecordByApplyId(applyId, null, null, account.getOrgId());

        if (userApplyRecord == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.applyNotFound");
        }

        boolean refreshReportF = (refreshReport != null && refreshReport == true);
        this.response.report =
            dataWhtianbeiReportService.getDataWhtianbeiReportById(user, userApplyRecord, account, refreshReportF);

        // 设置用户类型
        if (userApplyRecord.getsOrgId() == Constants.JBB_ORG) {
            // 借帮帮导流类型
            this.response.userType = userApplyRecord.getsUserType();
        } else {
            this.response.userType = 0; // 自有渠道
        }
        // 设置费用
        this.response.fee = PropertyManager.getIntProperty("jbb.mgt.tb.price", 60);

        logger.debug("<getWhtianbeiReport()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private DataWhtianbeiReport report;

        int fee;
        int userType; // 0 自有渠道，1进件， 2 注册流量

        public DataWhtianbeiReport getReport() {
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
