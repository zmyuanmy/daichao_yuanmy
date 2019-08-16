package com.jbb.mgt.rs.action.spc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbb.mgt.core.domain.LoginLog;
import com.jbb.mgt.core.domain.UserApplyRecordsSpc;
import com.jbb.mgt.core.service.UserApplyRecordsSpcService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@Service(UserApplyRecordsSpcAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserApplyRecordsSpcAction extends BasicAction {
    public static final String ACTION_NAME = "UserApplyRecordsSpcAction";
    private static Logger logger = LoggerFactory.getLogger(UserApplyRecordsSpcAction.class);
    private Response response;
    @Autowired
    private UserApplyRecordsSpcService userApplyRecordsSpcService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getApplies(int pageNo, int pageSize, Integer accountId, String startDate, String endDate) {
        logger.debug(">getApplies()");
        if (StringUtil.isEmpty(startDate)) {
            startDate = null;
        }
        if (StringUtil.isEmpty(endDate)) {
            endDate = null;
        }

        PageHelper.startPage(pageNo, pageSize);

        Integer newAccountId = 0;
        if (accountId != null && this.account.getOrgId() == 1) {
            newAccountId = accountId;
        } else if (this.account.getOrgId() != 1) {
            newAccountId = this.account.getAccountId();
        } else if (accountId == null && this.account.getOrgId() == 1) {
            newAccountId = null;
        }
        List<UserApplyRecordsSpc> userSpcs = userApplyRecordsSpcService.selectAppliesByAccountId(newAccountId,
            Util.parseTimestamp(startDate, getTimezone()), Util.parseTimestamp(endDate, getTimezone()));

        PageInfo<UserApplyRecordsSpc> pageInfo = new PageInfo<UserApplyRecordsSpc>(userSpcs);

        this.response.pageInfo = pageInfo;

        PageHelper.clearPage();

        logger.debug(">getApplies()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        
        public PageInfo<UserApplyRecordsSpc> pageInfo;

        public PageInfo<UserApplyRecordsSpc> getPageInfo() {
            return pageInfo;
        }
    }

}
