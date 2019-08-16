package com.jbb.mgt.rs.action.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.AppInfoVo;
import com.jbb.mgt.core.service.AppInfoService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

/**
 * @author wyq
 * @date 2019/1/5 18:08
 */
@Service(AppInfoAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AppInfoAction extends BasicAction {
    private static Logger logger = LoggerFactory.getLogger(AppInfoAction.class);

    public static final String ACTION_NAME = "AppInfoAction";

    private Response response;

    @Autowired
    private AppInfoService service;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    public void getAppInfoByAppName(String appName){
        logger.debug(">getAppInfoByAppName");
        this.response.appInfoVo = service.getAppInfoByAppName(appName);
        logger.debug("<getAppInfoByAppName");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public AppInfoVo appInfoVo;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request extends ActionResponse {
        public String appName;
    }
}
