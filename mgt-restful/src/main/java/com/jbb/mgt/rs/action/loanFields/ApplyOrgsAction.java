package com.jbb.mgt.rs.action.loanFields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Service(ApplyOrgsAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ApplyOrgsAction extends BasicAction {
    public static final String ACTION_NAME = "ApplyOrgsAction";
    private static Logger logger = LoggerFactory.getLogger(ApplyOrgsAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void largeLoanApplyOrgs(Request req, Integer[] orgIds) {
        // TODO Auto-generated method stub
        Integer[] s = req == null || req.orgIds == null ? orgIds : req.orgIds;
        for (int i = 0; i < s.length; i++) {
            logger.info("largeLoanApplyOrgs:userId=" + this.user.getUserId() + "  orgIds=" + s[i]);
        }

        // 添加日志
        // 处理申请
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer[] orgIds;

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

    }

}
