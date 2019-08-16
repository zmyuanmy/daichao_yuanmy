package com.jbb.mgt.rs.action.app;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.RsIouPlatform;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;

@Service(AppVersionAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AppVersionAction extends BasicAction {
    public static final String ACTION_NAME = "AppVersionAction";

    private static Logger logger = LoggerFactory.getLogger(AppVersionAction.class);
    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getAppVersionInfo(String marketingName, String productName, String version) {
        logger.debug(">getAppVersionInfo");
        if (StringUtil.isEmpty(productName)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "productName");
        }
        String defaultOnlineModel = PropertyManager.getProperty("jbb.app.onlineModel" + "." + productName, "prod");
        if (StringUtil.isEmpty(marketingName) || StringUtil.isEmpty(version)) {
            this.response.onlineModel = defaultOnlineModel;
        } else {
            this.response.onlineModel = PropertyManager.getProperty(
                "jbb.app.onlineModel." + productName + "." + marketingName + "." + version, defaultOnlineModel);
        }

        String latestVersion = PropertyManager.getProperty("jbb.app.isLatestVersion." + productName, "2.0");
        boolean isLatestVersion = false;
        if (version.equals(latestVersion)) {
            isLatestVersion = true;
        }
        this.response.headerModel = PropertyManager.getProperty("jbb.app.headerModel." + productName, "banner");
        this.response.latestAppUrl = PropertyManager.getProperty("jbb.app.latestAppUrl." + productName);
        this.response.latestVersion = latestVersion;
        this.response.isLatestVersion = isLatestVersion;
        this.response.launchUrl = PropertyManager.getProperty("jbb.app.onlineModel.launchUrl") + productName;
        this.response.key = PropertyManager.getProperty("app.3des.key");
        logger.debug("<getAppVersionInfo()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public String onlineModel;
        public Boolean isLatestVersion;
        public String latestVersion;
        public String latestAppUrl;
        public String headerModel;
        public String launchUrl;
        public String key;

    }

}
