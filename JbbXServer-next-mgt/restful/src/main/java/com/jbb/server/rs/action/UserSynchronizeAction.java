package com.jbb.server.rs.action;

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
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.domain.User;
import com.jbb.server.core.service.AccountService;
import com.jbb.server.core.service.UserEventLogService;
import com.jbb.server.rs.pojo.ActionResponse;

@Service(UserSynchronizeAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserSynchronizeAction extends BasicAction {
    private static Logger logger = LoggerFactory.getLogger(UserSynchronizeAction.class);
    public static final String ACTION_NAME = "UserSynchronizeAction";

    private Response response;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserEventLogService userEventLogService;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    public void insertMgtUser(String phoneNumber) {
        logger.debug(">insertMgtUser(), phoneNumber = " + phoneNumber);
        // 根据手机号比对users在用户表中是否存在，如果存在则不做操作，如果不存在则新建user对象，增加userporperty表中内容，返回userID
        if (StringUtil.isEmpty(phoneNumber)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "phoneNumber");
        }
        User userApp = accountService.getUserByPhoneNumber(phoneNumber);
        if (userApp == null) {
            // 新建user 对象
            User user = coreAccountService.createUserByMgtInfo(phoneNumber, "mgt");
            if (user.getUserId() != 0) {
                logger.debug("user.getUserId() " + user.getUserId());
                int userId = user.getUserId();
                this.response.userId = userId;
                // 插入注册日志
                String remoteAddress = this.getRemoteAddress();
                userEventLogService.insertEventLog(userId, "mgt", Constants.Event_LOG_EVENT_USER_EVENT,
                    Constants.Event_LOG_EVENT_USER_ACTION_REGISTER, null, null, remoteAddress);
            }
        } else {
            int userId = userApp.getUserId();
            this.response.userId = userId;
        }

        logger.debug("<insertMgtUser()");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public int userId;
    }
}
