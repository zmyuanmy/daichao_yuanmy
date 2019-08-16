package com.jbb.mall.rs.action.appinfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mall.server.rs.action.BasicAction;
import com.jbb.mall.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author wyq
 * @date 2019/1/21 16:40
 */
@Service(AppInfoAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AppInfoAction extends BasicAction {

    public static final String ACTION_NAME = "AppInfoAction";

    private static Logger logger = LoggerFactory.getLogger(AppInfoAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getAppinfo(String type, String appName) {
        logger.debug(">getAppinfo type=" + type + " appName=" + appName);
        appName = StringUtil.isEmpty(appName) ? "bnh" : appName;
        this.response.telephone = PropertyManager.getProperty("jbb.mall.telephone" + "." + appName);
        this.response.time = PropertyManager.getProperty("jbb.mall.time" + "." + appName);
        this.response.explain = PropertyManager.getProperty("jbb.mall.explain" + "." + appName);
        this.response.invoiceDescription = PropertyManager.getProperty("jbb.mall.invoiceDescription" + "." + appName);
        this.response.serviceAgreement = PropertyManager.getProperty("jbb.mall.serviceAgreement" + "." + appName);
        this.response.aboutUs = PropertyManager.getProperty("jbb.mall.aboutUs" + "." + appName);
        this.response.corporateName = PropertyManager.getProperty("jbb.mall.corporateName" + "." + appName);
        this.response.copyright = PropertyManager.getProperty("jbb.mall.copyright" + "." + appName);
        this.response.userInfoAuthAgreement = PropertyManager.getProperty("jbb.mall.userInfoAuthAgreement" + "." + appName);
        logger.debug("<getAppinfo");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public String telephone;
        public String time;
        public String explain;
        public String invoiceDescription;
        public String serviceAgreement;
        public String aboutUs;
        public String userInfoAuthAgreement;
        public String corporateName;
        public String copyright;
    }
}
