package com.jbb.mgt.rs.action.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.StringUtil;

/**
 * @author ThinkPad
 * @date 2019/03/18
 */
@Service(AppConfigAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AppConfigAction extends BasicAction {
    public static final String ACTION_NAME = "AppConfigAction";

    private static Logger logger = LoggerFactory.getLogger(AppConfigAction.class);

    public String getAppConfig(String appName, String version, String os, String channel) {
        logger.debug(">getAppConfig appName=" + appName + "version=" + version + "os=" + os);
        if (StringUtil.isEmpty(appName)) {
            return "";
        }
        String configStr = "";
        if (StringUtil.isEmpty(channel)) {
            configStr = PropertyManager.getProperty("jbb.app." + appName + "." + os + "." + version + ".config");
        } else {
            configStr = PropertyManager
                .getProperty("jbb.app." + appName + "." + os + "." + channel + "." + version + ".config");
        }

        logger.debug("<getAppConfig()");
        return configStr;
    }

}
