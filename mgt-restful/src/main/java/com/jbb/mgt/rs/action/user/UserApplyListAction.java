
package com.jbb.mgt.rs.action.user;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.service.UserApplyRecordService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.shared.rs.Util;

@Service(UserApplyListAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserApplyListAction extends BasicAction {

    public static final String ACTION_NAME = "UserApplyListAction";

    private static Logger logger = LoggerFactory.getLogger(UserApplyListAction.class);

    private Response response;

    @Autowired
    private UserApplyRecordService userApplyRecordService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getUserApplyRecords(int pageNo, int pageSize, Boolean getTotal, String orderBy, Boolean desc, String op,
        int[] statuses, String phoneNumberSearch, String channelSearch, String usernameSearch, String assignNameSearch,
        String initNameSearch, String finalNameSearch, String loanNameSearch, String idcardSearch, String jbbIdSearch,
        Boolean feedback, Integer sOrgId, Integer sUserType, String startDate, String endDate, Integer initAccountId,
        Integer finalAccountId, Integer loanAccountId, Integer currentAccountId, String channelCode, Boolean verified) {
        logger.debug(">getUserApplyRecords(), pageNo=" + pageNo + "pageSize=" + pageSize + "statuses=" + statuses
            + "phoneNumberSearch=" + phoneNumberSearch + "channelSearch=" + channelSearch + "usernameSearch="
            + usernameSearch + "op=" + op + "op=" + initNameSearch + "initNameSearch=" + initNameSearch
            + "assignNameSearch=" + assignNameSearch + "finalNameSearch=" + finalNameSearch + "loandNameSearch="
            + loanNameSearch + "idcardSearch=" + idcardSearch + "jbbIdSearch=" + jbbIdSearch + "feedback=" + feedback
            + "sOrgId=" + sOrgId + "sUserType=" + sUserType + "startDate=" + startDate + "endDate=" + endDate
            + "initAccountId=" + initAccountId + "finalAccountId=" + finalAccountId + "loanAccountId=" + loanAccountId
            + "currentAccountId=" + currentAccountId + "channelCode=" + channelCode);

        PageHelper.startPage(pageNo, pageSize);
        if (!StringUtil.isEmpty(orderBy)) {
            String order = (desc != null && desc == true) ? "desc" : "asc";
            PageHelper.orderBy("c." + orderBy + " " + order);
        }
        Integer accountId = this.account.getAccountId();
        boolean getTotalF = (getTotal != null && getTotal == true);
        if (this.isOrgAdmin() && getTotalF) {
            accountId = null;
        }
        boolean feedbackF = (feedback != null && feedback == true);

        Timestamp startDateT = Util.parseTimestamp(startDate, TimeZone.getDefault());

        Timestamp endDateT = Util.parseTimestamp(endDate, TimeZone.getDefault());

        List<UserApplyRecord> list
            = userApplyRecordService.getUserApplyRecords(null, op, accountId, this.account.getOrgId(), statuses,
                phoneNumberSearch, channelSearch, usernameSearch, assignNameSearch, initNameSearch, finalNameSearch,
                loanNameSearch, idcardSearch, jbbIdSearch, feedbackF, sOrgId, sUserType, startDateT, endDateT,
                initAccountId, finalAccountId, loanAccountId, currentAccountId, channelCode, verified);
        PageInfo<UserApplyRecord> pageInfo = new PageInfo<UserApplyRecord>(list);
        // this.response.userApplyRecoreds = list;
        this.response.pageInfo = pageInfo;

        PageHelper.clearPage();
        logger.debug("<getUserApplyRecords()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private PageInfo<UserApplyRecord> pageInfo;

        public PageInfo<UserApplyRecord> getPageInfo() {
            return pageInfo;
        }

    }

}
