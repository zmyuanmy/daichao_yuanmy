package com.jbb.mgt.rs.action.club;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.ClubReport;
import com.jbb.mgt.core.service.ClubService;
import com.jbb.mgt.core.service.OrganizationUserService;
import com.jbb.mgt.core.service.XjlApplyRecordService;
import com.jbb.mgt.core.service.XjlUserService;
import com.jbb.mgt.rs.action.channel.ChannelAction;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@Service(ClubReportAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ClubReportAction extends BasicAction {

    public static final String ACTION_NAME = "ClubReportAction";

    private static Logger logger = LoggerFactory.getLogger(ChannelAction.class);

    @Autowired
    private ClubService clubService;

    @Autowired
    private OrganizationUserService organizationUserService;
    @Autowired
    private XjlApplyRecordService xjlApplyRecordService;
    @Autowired
    private XjlUserService xjlUserService;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void notifyToGetAndSaveReport(Integer userId, String taskId) {

        logger.debug(">notifyToGetAndSaveReport,userId =" + userId + ", taskId =" + taskId);

        if (StringUtil.isEmpty(taskId) || userId == null) {
            logger.info("notifyToGetAndSaveReport, taskId or userId is empty");
            return;
        }

        clubService.saveReport(userId, taskId);
        logger.debug("<notifyToGetAndSaveReport");
    }

    private boolean checkUserExistInOrg(int userId) {
        return organizationUserService.checkExist(this.account.getOrgId(), userId);
    }

    private boolean checkUserExistInXjl(Integer userId) {
        return xjlApplyRecordService.checkExistByUserId(this.account.getOrgId(), userId);
    }

    public void getClubReport(int userId, String channelType, String channelCode) {

        logger.debug(
            ">getClubReport,userId =" + userId + ", channelType =" + channelType + ", channelCode =" + channelCode);
        // 检查用户是否和此机构有申请

        if (!checkUserExistInOrg(userId)) {
            throw new ObjectNotFoundException("jbb.mgt.exception.userNotFound");
        }
        this.response.report = clubService.getReport(userId, channelType, channelCode, null, null);
        logger.debug("<getClubReport");
    }

    public void getXjlClubReport(Integer userId, String channelType, String channelCode) {
        logger.debug(
            ">getClubReport,userId =" + userId + ", channelType =" + channelType + ", channelCode =" + channelCode);
        // 检查用户是否和此机构有申请
        if (!checkUserExistInXjl(userId) && !checkUserExist(userId)) {
            throw new ObjectNotFoundException("jbb.mgt.exception.userNotFound");
        }
        this.response.report = clubService.getReport(userId, channelType, channelCode, null, null);
        logger.debug("<getClubReport");
    }

    private boolean checkUserExist(Integer userId) {
        return xjlUserService.checkExistByUserId(this.account.getOrgId(), userId, null);
    }

    public void getClubReport(String taskId) {

        logger.debug(">getClubReport,taskId =" + taskId);
        this.response.report = clubService.getReport(taskId);
        logger.debug("<getClubReport");
    }

    public void getClubReportStatus(String startDate, String endDate, String[] channelTypes) {
        logger.debug(">getClubReportStatus");
        String[] ChannelTypesF = channelTypes == null || channelTypes.length == 0 ? null : channelTypes;
        Timestamp startTs = Util.parseTimestamp(startDate, this.getTimezone());
        Timestamp endTs = Util.parseTimestamp(endDate, this.getTimezone());
        this.response.reports = clubService.getClubReportStatus(this.user.getUserId(), startTs, endTs, ChannelTypesF);
        logger.debug("<getClubReportStatus");

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        ClubReport report;
        private List<ClubReport> reports;

        public ClubReport getReport() {
            return report;
        }

        public List<ClubReport> getReports() {
            return reports;
        }

    }

}
