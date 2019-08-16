package com.jbb.mgt.rs.action.xjlUserApply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;

@Service(XjlUserInfoAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlUserInfoAction extends BasicAction {
    public static final String ACTION_NAME = "XjlUserInfoAction";

    private static Logger logger = LoggerFactory.getLogger(XjlUserInfoAction.class);
    private Response response;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private UserService userService;

    public void getUserInfo(Integer applyId, Integer userId) {
        if (applyId == null && userId == null) {
            throw new MissingParameterException("jbb.mgt.exception.user.notFound");
        }
        this.response.user = userService.selectXjlUserByApplyId(applyId, userId, this.account.getOrgId());
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public User user;

        public User getUser() {
            return user;
        }

    }

}
