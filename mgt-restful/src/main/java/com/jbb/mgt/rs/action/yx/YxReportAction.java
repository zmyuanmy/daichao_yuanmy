package com.jbb.mgt.rs.action.yx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.DataYxUrls;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.YxNotify;
import com.jbb.mgt.core.domain.YxReport;
import com.jbb.mgt.core.service.OrgRechargesService;
import com.jbb.mgt.core.service.OrganizationUserService;
import com.jbb.mgt.core.service.YxReportService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.Call3rdApiException;
import com.jbb.server.common.exception.ObjectNotFoundException;

@Service(YxReportAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class YxReportAction extends BasicAction {
    public static final String ACTION_NAME = "YxReportAction";

    private static Logger logger = LoggerFactory.getLogger(YxReportAction.class);

    private Response response;

    @Autowired
    private YxReportService yxReportService;

    @Autowired
    private OrgRechargesService orgRechargesService;

    @Autowired
    private OrganizationUserService organizationUserService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void generateH5Url(int userId, int applyId, String reportType) {
        logger.debug(">generateH5Url(), userId = " + userId + " ,applyId=" + applyId + " ,reportType=" + reportType);

        OrgRecharges o = orgRechargesService.selectOrgRechargesForUpdate(this.account.getOrgId());
        if (o.getSmsBudget() <= 0) {
            logger.info(" org's jbb budget < 0 , orgId = " + this.account.getOrgId());
            throw new Call3rdApiException("jbb.mgt.error.api.feeNotEnough");
        }

        this.response.dataYxUrls = yxReportService.generateH5Url(userId, applyId, reportType);
        logger.debug("<generateH5Url()");
    }

    public void notify(YxNotify notify) {
        logger.debug(">notify(), notify = " + notify);
        yxReportService.notify(notify);
        logger.debug("<notify()");

    }

    public void getReport(Integer userId, Integer applyId, String taskId, String reportType) {
        logger.debug(">getReport(), userId = " + userId + " ,applyId=" + applyId + " ,taskId=" + taskId
            + " ,reportType=" + reportType);

        if (userId != null && !organizationUserService.checkExist(this.account.getOrgId(), userId)) {
            throw new ObjectNotFoundException("jbb.mgt.exception.userNotFound");
        }

        this.response.report = yxReportService.getReport(userId, applyId, taskId, reportType);
        logger.debug("<getReport()");

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private DataYxUrls dataYxUrls;

        private YxReport report;

        public DataYxUrls getDataYxUrls() {
            return dataYxUrls;
        }

        public YxReport getReport() {
            return report;
        }

    }

}
