package com.jbb.server.rs.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.core.domain.UserProperty;
import com.jbb.server.core.service.AccountService;
import com.jbb.server.rs.pojo.ActionResponse;

@Service(UserPropertiesAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserPropertiesAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(UserPropertiesAction.class);

    public static final String ACTION_NAME = "UserPropertiesAction";

    private Response response;

    @Autowired
    private AccountService accountService;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    public void addUserProperties(List<UserProperty> list) {
        logger.debug(">addUserProperties(),userId =" + this.user.getUserId() + ", properites = " + list);
        accountService.addUserProperties(this.user.getUserId(), list);
        logger.debug("<addUserProperties()");
    }

    public void updateUserProperties(List<UserProperty> list) {
        logger.debug(">updateUserProperties(),userId =" + this.user.getUserId() + ", properites = " + list);
        accountService.updateUserProperties(this.user.getUserId(), list);
        logger.debug("<updateUserProperties()");
    }

    public void deleteUserProperties(List<UserProperty> list) {
        logger.debug(">deleteUserProperties(),userId =" + this.user.getUserId() + ", properites = " + list);
        accountService.deleteUserProperties(this.user.getUserId(), list);
        logger.debug("<deleteUserProperties()");
    }

    public void searchUserPropertiesByUserId() {
        logger.debug(">searchUserPropertiesByUserId(),userId =" + this.user.getUserId());
        this.response.list = accountService.searchUserProperties(this.user.getUserId(), null);
        logger.debug("<searchUserPropertiesByUserId()");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        
        private List<UserProperty> list;

        public List<UserProperty> getList() {
            return list;
        }

    }

}
