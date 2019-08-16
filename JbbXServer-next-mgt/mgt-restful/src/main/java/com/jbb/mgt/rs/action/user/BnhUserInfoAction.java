package com.jbb.mgt.rs.action.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserProperty;
import com.jbb.mgt.core.service.UserPropertiesService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Service(BnhUserInfoAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BnhUserInfoAction extends BasicAction {

    public static final String ACTION_NAME = "BnhUserInfoAction";

    private static Logger logger = LoggerFactory.getLogger(BnhUserInfoAction.class);

    private Response response;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private UserPropertiesService userPropertiesService;

    public void selectUserInfo() {
        logger.debug(">selectBnhUserInfo()");
        User userInfo = this.user;
        List<UserProperty> propertys = userPropertiesService.selectUserPropertyList(this.user.getUserId());
        userInfo.setUserPropertys(propertys);
        response.user = userInfo;
        logger.debug(">selectBnhUserInfo()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private User user;

        public User getUser() {
            return user;
        }

    }

}
