package com.jbb.server.rs.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.core.domain.User;
import com.jbb.server.core.service.UserEventLogService;
import com.jbb.server.core.service.UserService;
import com.jbb.server.rs.pojo.ActionResponse;
import com.jbb.server.rs.pojo.RsRecommandUser;

@Service(RecommandUserAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RecommandUserAction extends BasicAction {
    private static Logger logger = LoggerFactory.getLogger(RecommandUserAction.class);
    public static final String ACTION_NAME = "RecommandUserAction";

    private Response response;

    @Autowired
    UserService userService;
    
    @Autowired
    private UserEventLogService userEventLogService;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    public void getRecommandUsers(Long startDateTs, Long endDateTs, Integer userId) {
        logger.debug(">getRecommandUsers()");
        saveLog();
        
        Timestamp start = null;
        Timestamp end = null;
        if (startDateTs != null) {
            start = new Timestamp(startDateTs);
        }

        if (endDateTs != null) {
            end = new Timestamp(endDateTs);
        }
        boolean isSystemAdmin = this.user.checkAccessAllPermission();
        int historyDays = PropertyManager.getIntProperty("jbb.lender.history.days", 7);
        if (!isSystemAdmin
            && (start.getTime() < DateUtil.getTodayStartCurrentTime() - historyDays * DateUtil.DAY_MILLSECONDES)) {
            this.response.users = new ArrayList<RsRecommandUser>();
            logger.debug("<getRecommandUsers()");
            return;
        }

        if (userId == null) {
            userId = this.user.getUserId();
        }

        List<User> users = this.coreAccountService.getApplyUsers(userId, start, end, true);
        List<RsRecommandUser> rUsers = new ArrayList<RsRecommandUser>();

        for (User u : users) {
            if (this.user.isOpUser()) {
                // 如果是运营人员，脱敏处理, 但显示芝麻分
                RsRecommandUser rsUser = new RsRecommandUser(u, true);
                rsUser.setSesameCreditScore(user.getPropertyVal(Constants.SESAME_CREDIT_SCORE));
                rsUser.setQq("*");
                rsUser.setWechat("*");
                rsUser.setSourceId(u.getSourceId());
                rUsers.add(rsUser);
            } else {
                rUsers.add(new RsRecommandUser(u));
            }
        }
        
        this.response.users = rUsers;
        logger.debug("<getRecommandUsers()");
    }
    
    private void saveLog(){
        String remoteAddress = this.getRemoteAddress();
        userEventLogService.insertEventLog(this.user.getUserId(), null, Constants.Event_LOG_EVENT_RECOMMANDS,
            Constants.Event_LOG_EVENT_RECOMMAND_GET, null, null, remoteAddress);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        List<RsRecommandUser> users;

        public List<RsRecommandUser> getUsers() {
            return users;
        }

    }

}
