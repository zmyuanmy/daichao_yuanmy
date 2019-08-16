package com.jbb.mgt.rs.action.juLiXin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.DataJuXinLiUrls;
import com.jbb.mgt.core.domain.JuXinLiNotify;
import com.jbb.mgt.core.domain.JuXinLiReport;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.service.JuXinLiReportService;
import com.jbb.mgt.core.service.OrgRechargesService;
import com.jbb.mgt.core.service.OrganizationUserService;
import com.jbb.mgt.core.service.UserApplyRecordService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.Call3rdApiException;
import com.jbb.server.common.exception.ObjectNotFoundException;

@Service(JuXinLiAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JuXinLiAction extends BasicAction {
    public static final String ACTION_NAME = "JuXinLiAction";

    private static Logger logger = LoggerFactory.getLogger(JuXinLiAction.class);

    private Response response;

    @Autowired
    private JuXinLiReportService juXinLiReportService;

    @Autowired
    private OrgRechargesService orgRechargesService;
    @Autowired
    private UserApplyRecordService userApplyRecordService;
    @Autowired
    private OrganizationUserService organizationUserService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    // 生成h5链接
    public void generateH5Url(int userId, int applyId, String reportType) {
        logger.debug(">generateH5Url(), userId = " + userId + " ,applyId=" + applyId + " ,reportType=" + reportType);

        OrgRecharges o = orgRechargesService.selectOrgRechargesForUpdate(this.account.getOrgId());
        if (o.getSmsBudget() <= 0) {
            logger.info(" org's jbb budget < 0 , orgId = " + this.account.getOrgId());
            throw new Call3rdApiException("jbb.mgt.error.api.feeNotEnough");
        }
        if (!userApplyRecordService.checkExist(userId, this.account.getOrgId(), applyId)) {
            throw new ObjectNotFoundException("jbb.mgt.exception.userNotFound");
        }

        this.response.dataJuXinLiUrls = juXinLiReportService.generateH5Url(userId, applyId, reportType);
        logger.debug("<generateH5Url()");
    }

    public void juXinLiAuthorizeNotify(JuXinLiNotify notify) {
        logger.debug(">juXinLiAuthorizeNotify(), notify = " + notify);
        juXinLiReportService.juXinLiAuthorizeNotify(notify);
        logger.debug("<juXinLiAuthorizeNotify()");

    }

    public void getReport(Integer userId, Integer applyId, String token, String reportType) {
        // TODO Auto-generated method stub
        logger.debug(">getReport(), userId = " + userId + " ,applyId=" + applyId + " ,token=" + token + " ,reportType="
            + reportType);
        if (userId != null && !organizationUserService.checkExist(this.account.getOrgId(), userId)) {
            throw new ObjectNotFoundException("jbb.mgt.exception.userNotFound");
        }
        this.response.report = juXinLiReportService.getReport(userId, applyId, token, reportType);
        logger.debug("<getReport()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private DataJuXinLiUrls dataJuXinLiUrls;

        private JuXinLiReport report;

        public DataJuXinLiUrls getDataJuXinLiUrls() {
            return dataJuXinLiUrls;
        }

        public JuXinLiReport getReport() {
            return report;
        }

    }

}
