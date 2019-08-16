package com.jbb.mgt.rs.action.jiGuang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.JiGuangUserDevice;
import com.jbb.mgt.core.service.JiGuangService;
import com.jbb.mgt.server.rs.action.BasicAction;

@Service(JiGuangAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JiGuangAction extends BasicAction {
    public static final String ACTION_NAME = "JiGuangAction";

    private static Logger logger = LoggerFactory.getLogger(JiGuangAction.class);

    @Autowired
    private JiGuangService jiGuangService;

    public void saveJiGuangUserDevice(Request req) {
        logger.debug(
            ">saveJiGuangUserDevice() deviceType = " + req.deviceType + "registrationId = " + req.registrationId);
        JiGuangUserDevice userDevice = new JiGuangUserDevice();
        userDevice.setRegistrationId(req.registrationId);
        userDevice.setDeviceType(req.deviceType);
        userDevice.setAlias(req.alias);
        userDevice.setTag(req.tag);
        userDevice.setApplicationId(req.applicationId);
        userDevice.setUserId(this.user.getUserId());
        userDevice.setStatus((req.status != null && req.status == true) ? true : false);
        jiGuangService.saveUserDevice(userDevice);
        logger.debug("<saveJiGuangUserDevice()");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String registrationId;
        public String deviceType;
        public String alias;
        public Integer applicationId;
        public Boolean status;
        public String tag;
    }

}
