package com.jbb.mgt.rs.action.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.service.AccountService;
import com.jbb.mgt.core.service.UserApplyRecordService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

/**
 * Created by Administrator on 2018/5/19.
 */

@Service(UserApplyStatisticAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserApplyStatisticAction extends BasicAction {

    public static final String ACTION_NAME = "UserApplyStatisticAction";

    private static Logger logger = LoggerFactory.getLogger(UserApplyStatisticAction.class);

    private Response response;

    @Autowired
    private UserApplyRecordService userApplyRecordService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new UserApplyStatisticAction.Response();
    }

    /**
     * 查询统计数据
     * 
     * @param statuses
     * @param startDate
     * @param endDate
     */
    public void getStatisticsNumber(Integer[] statuses, String startDate, String endDate) {
        logger.debug(">getStatisticsNumber statuses={}, startDate{} endDate{}", statuses, startDate, endDate);
        if (ArrayUtils.isEmpty(statuses)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "statuses");
        }
        Timestamp tsStartDate = Util.parseTimestamp(startDate, TimeZone.getDefault());
        Timestamp tsEndDate = Util.parseTimestamp(endDate, TimeZone.getDefault());
        this.response.count = userApplyRecordService.getStatisticsNumber(statuses, tsStartDate, tsEndDate);
        this.response.totalCount = userApplyRecordService.getStatisticsNumber(statuses, null, null);
        logger.debug("<getStatisticsNumber");
    }

    public void statisticAssign(int assignType, String startDate, String endDate, Integer accountId) {
        logger.debug(">statisticAssign assignType={}, startDate{} endDate{} accountId{}", assignType, startDate,
            endDate, accountId);
        Integer user_apply_record_s_user_type = null;
        if (assignType < 1 || assignType > 3) {
            throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "assignType");
        } else if (assignType == 3) {
            user_apply_record_s_user_type = 2;
        } else if (assignType == 2) {
            user_apply_record_s_user_type = 1;
        } else if (assignType == 1) {
            user_apply_record_s_user_type = 0;
        }
        Timestamp startS = StringUtil.isEmpty(startDate) ? null : Util.parseTimestamp(startDate, TimeZone.getDefault());
        Timestamp endS = StringUtil.isEmpty(startDate) ? null : Util.parseTimestamp(endDate, TimeZone.getDefault());
        accountId = null == accountId || accountId <= 0 ? null : accountId;
        List<Account> list =
            accountService.countOrgAccountInitNumber(this.account.getOrgId(), assignType, startS, endS, accountId);

        Long aLong = userService.selectUserDetailsTotal(null, startS, endS, this.account.getOrgId(), true,
            user_apply_record_s_user_type);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setPassword(null);
            }
            this.response.accounts = new ArrayList<>();
            this.response.accounts.addAll(list);
        }
        this.response.total = aLong;
        logger.debug("<statisticAssign");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public Integer count;
        public Integer totalCount;
        public Long total;
        public List<Account> accounts;

        public Integer getTotalCount() {
            return totalCount;
        }

        public Integer getCount() {
            return count;
        }

    }

}
