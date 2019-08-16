package com.jbb.mgt.rs.action.organization;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.util.StringUtil;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.AccessDeniedException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.rs.Util;

/**
 * 组织管理Action类
 *
 * @author wyq
 * @date 2018/6/6 17:16
 */
@Service(OrganizationAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrganizationAction extends BasicAction {

    public static final String ACTION_NAME = "OrganizationAction";

    private static Logger logger = LoggerFactory.getLogger(OrganizationAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new OrganizationAction.Response();
    }

    @Autowired
    OrganizationService organizationService;

    public void getOrganizations(String startDate, String endDate, Boolean getStatistic, Boolean getAdStatistic,
        Integer userType, Boolean simple, Integer[] salesId, Boolean includeDelete) {
        logger.debug(">getOrganizations");
        if (this.account.getOrgId() != 1) {
            throw new AccessDeniedException("jbb.error.validateAdminAccess.accessDenied");
        }

        Timestamp startTs = Util.parseTimestamp(startDate, getTimezone());
        Timestamp endTs = Util.parseTimestamp(endDate, getTimezone());

        // endDate为空，则使用当前系统时间
        if (endTs == null) {
            endTs = DateUtil.getCurrentTimeStamp();
        }

        // startDte为空，则使用当前系统时间向前两天的开始时间
        if (startTs == null) {
            startTs = new Timestamp(DateUtil.getTodayStartTs() - 2 * DateUtil.DAY_MILLSECONDES);
        }

        // endDate 和 startDate时间超过3天，再使用结束时间的前两天的开始时间作为开始时间
        if ((endTs.getTime() - startTs.getTime() > 3 * DateUtil.DAY_MILLSECONDES)) {
            startTs = new Timestamp(DateUtil.getStartTsOfDay(endTs.getTime()) - 2 * DateUtil.DAY_MILLSECONDES);
        }

        Boolean isGetStatistic = getStatistic == null ? false : getStatistic;
        Boolean isGetAdStatistic = getAdStatistic == null ? false : getStatistic;
        Boolean isIncludeDelete = includeDelete == null ? false : includeDelete;
        List<Organization> list = null;
        if (null != simple && simple) {
            salesId = salesId.length == 0 ? null : salesId;
            list = organizationService.selectOrganizationSimper(salesId);
        } else {
            list = organizationService.getOrganizations(true, isGetStatistic, isGetAdStatistic, startTs, endTs,
                isIncludeDelete);
        }

        this.response.organizations = list;
        /*for (Organization o : list){
            this.response.organizations.add(o);
        }*/
        logger.debug(">getOrganizations");
    }

    public void getOrganizationById() {
        logger.debug(">getOrganizationById");
        Organization org = organizationService.getOrganizationById(this.account.getOrgId(), false);
        this.response.organization = org;
        logger.debug("<getOrganizationById");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private List<Organization> organizations;

        private Organization organization;

        public Organization getOrganization() {
            return organization;
        }

        public List<Organization> getOrganizations() {
            return organizations;
        }
    }

}
