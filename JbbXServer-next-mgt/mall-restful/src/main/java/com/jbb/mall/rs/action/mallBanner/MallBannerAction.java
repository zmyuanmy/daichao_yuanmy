package com.jbb.mall.rs.action.mallBanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mall.core.service.MallBannerService;
import com.jbb.mall.server.rs.action.BasicAction;
import com.jbb.mall.server.rs.pojo.ActionResponse;

/**
 * @author ThinkPad
 * @date 2019/01/17
 */

@Service(MallBannerAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MallBannerAction extends BasicAction {

    public static final String ACTION_NAME = "MallBannerAction";

    private static Logger logger = LoggerFactory.getLogger(MallBannerAction.class);

    private Response response;

    @Autowired
    private MallBannerService mallBannerService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getMallBanner() {
        logger.debug(">getMallBanner()");
        mallBannerService.getMallBanner();
        this.response.UserLoginPuvs = mallBannerService.getMallBanner();
        logger.debug("<getMallBanner()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private String UserLoginPuvs;

        public String getUserLoginPuvs() {
            return UserLoginPuvs;
        }

    }

}
