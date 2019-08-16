package com.jbb.server.rs.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.domain.User;

/**
 * Created by inspironsun on 2018/5/29
 */

@Service(UserCheckJbbIdNameAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserCheckJbbIdNameAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(UserCheckJbbIdNameAction.class);
    public static final String ACTION_NAME = "UserCheckJbbIdNameAction";

    public void checkJbbIdName(int userId, String userName) {
        logger.info(">checkJbbIdName() userId=" + userId + " ,userName=" + userName);

        if (StringUtil.isEmpty(userName)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "userName");
        }

        if (userId == 0) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "userId");
        }

        // 根据userid获取use对象
        User user = this.coreAccountService.getUser(userId);

        if (user == null) {
            throw new ObjectNotFoundException("jbb.integrate.exception.userNotFound");
        }

        if (user.getUserName() == null || !userName.equals(user.getUserName())) {
            throw new ObjectNotFoundException("jbb.integrate.exception.userNotFound");
        }
        
        if(!this.coreAccountService.isVerified(userId, true)){
            throw new WrongParameterValueException("jbb.error.exception.user.notVerify");
        }

        logger.info("<checkJbbIdName()");

    }

    public void insertUserAccount(int userId, Integer accountId) {
        logger.info(">insertUserAccount() userId=" + userId + " ,accountId=" + accountId);
        User user = this.coreAccountService.getUser(userId);
        if (user == null) {
            throw new ObjectNotFoundException("jbb.integrate.exception.userNotFound");
        }
        if (accountId == null || accountId == 0) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "accountId");
        }
        this.coreAccountService.saveUserProperty(userId, "accountId", accountId.toString());
        logger.info("<insertUserAccount()");
    }
}
