
package com.jbb.mgt.rs.action.loan;

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
import com.github.pagehelper.util.StringUtil;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.domain.UserLoanRecord;
import com.jbb.mgt.core.service.UserLoanRecordService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Service(LoanRecordListAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoanRecordListAction extends BasicAction {

    public static final String ACTION_NAME = "LoanRecordListAction";

    private static Logger logger = LoggerFactory.getLogger(LoanRecordListAction.class);

    private Response response;

    @Autowired
    private UserLoanRecordService userLoanRecordService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getUserApplyRecords(int pageNo, int pageSize, String orderBy, Boolean desc, String op, int[] statuses,
        int[] iouStatuses, Integer iouDateType, String phoneNumberSearch, String channelSearch, String usernameSearch,
        String idcardSearch, String jbbIdSearch) {
        logger.debug(">getUserApplyRecords(), pageNo=" + pageNo + "pageSize=" + pageSize + "statuses=" + statuses
            + "phoneNumberSearch=" + phoneNumberSearch + "channelSearch=" + channelSearch + "usernameSearch="
            + usernameSearch + "op=" + op + "op=" + "idcardSearch=" + idcardSearch + "jbbIdSearch=" + jbbIdSearch
            + "iouDateType=" + iouDateType);

        PageHelper.startPage(pageNo, pageSize);
        
        if (!StringUtil.isEmpty(orderBy)) {
            String order = (desc != null && desc == true) ? "desc" : "asc";
            PageHelper.orderBy("c." + orderBy + " " + order);
        }
        Integer accountId = null;

        if (!this.isOrgAdmin()) {
            accountId = this.account.getAccountId();
        }

        List<UserLoanRecord> list =
            userLoanRecordService.getUserLoanRecords(null, accountId, op, this.account.getOrgId(), statuses,
                iouStatuses, iouDateType, phoneNumberSearch, channelSearch, usernameSearch, idcardSearch, jbbIdSearch);
        PageInfo<UserLoanRecord> pageInfo = new PageInfo<UserLoanRecord>(list);
        
        this.response.pageInfo = pageInfo;
        PageHelper.clearPage();
        logger.debug("<getUserApplyRecords()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        
        private PageInfo<UserLoanRecord> pageInfo;

        public PageInfo<UserLoanRecord> getPageInfo() {
            return pageInfo;
        }
        

    }

}
