
package com.jbb.mgt.rs.action.user;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alipay.api.internal.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.service.UserApplyRecordService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@Service(UserApplyDeleteAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserApplyDeleteAction extends BasicAction {

    public static final String ACTION_NAME = "UserApplyDeleteAction";

    private static Logger logger = LoggerFactory.getLogger(UserApplyDeleteAction.class);
    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private UserApplyRecordService userApplyRecordService;

    public void deleteUserApplys(String startDate, String endDate, Boolean isDelete) {
        logger.debug("<deleteUserApplys()");
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        if (StringUtils.isEmpty(endDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "endDate");
        }
        Timestamp startDateTs = Util.parseTimestamp(startDate, getTimezone());
        Timestamp endDateTs = Util.parseTimestamp(endDate, getTimezone());
        long start = startDateTs.getTime();
        long end = startDateTs.getTime();
        long today = DateUtil.getTodayStartCurrentTime();
        if (start - today + 3 * DateUtil.DAY_MILLSECONDES > 0) {
            throw new WrongParameterValueException("jbb.mgt.exception.deleteApplys.startDateError");
        }
        if (end - today + 3 * DateUtil.DAY_MILLSECONDES > 0) {
            endDateTs = new Timestamp(today - 3 * DateUtil.DAY_MILLSECONDES);
        }
        boolean isDeleteF = (isDelete != null && isDelete == true);
        if (isDeleteF) {
            userApplyRecordService.deleteUserApplys(this.account.getOrgId(), startDateTs, endDateTs);
        }
        this.response.count
            = userApplyRecordService.countDeleteUserApplys(this.account.getOrgId(), startDateTs, endDateTs);

        logger.debug("<deleteUserApplys()");

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

    }

}
