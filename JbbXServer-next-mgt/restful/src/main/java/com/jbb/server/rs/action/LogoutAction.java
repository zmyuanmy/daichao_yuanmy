package com.jbb.server.rs.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jbb.server.core.domain.User;

@Component(LogoutAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LogoutAction extends BasicAction {
    private static Logger logger = LoggerFactory.getLogger(LogoutAction.class);
    public static final String ACTION_NAME = "logoutAction";

    /**
     * 
     * @param userKey
     * @param objectId 设备ID
     */
    public void logoutUser(String userKey, String objectId) {
        logger.debug(">logoutUser()");

        User user = coreAccountService.logoutUser(userKey, objectId);

        if (user != null) {
            setResultCode(SUCCESS, null);

        } else {
            logger.debug("Wrong API key: [{}]", userKey);
            escalateErrorCode(WRONG_API_KEY);
        }

        logger.debug("<logoutUser()");
    }
}
