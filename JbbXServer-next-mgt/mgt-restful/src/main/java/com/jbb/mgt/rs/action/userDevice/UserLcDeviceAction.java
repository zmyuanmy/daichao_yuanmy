package com.jbb.mgt.rs.action.userDevice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.UserDevice;
import com.jbb.mgt.core.domain.UserLcDevice;
import com.jbb.mgt.core.service.UserDeviceService;
import com.jbb.mgt.core.service.UserLcDeviceService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Action
 *
 * @author wyq
 * @date 2018/8/28 15:28
 */
@Service(UserLcDeviceAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserLcDeviceAction extends BasicAction {
    public static final String ACTION_NAME = "UserLcDeviceAction";

    private static Logger logger = LoggerFactory.getLogger(UserLcDeviceAction.class);

    @Autowired
    private UserLcDeviceService service;

    public void saveUserLcDevice(Request req) {
        logger.debug(">saveUserLcDevice, UserLcDevice={}", req);
        if (null == req || StringUtil.isEmpty(req.objectId)) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "objectId");
        }
        UserLcDevice ud = generateUserDevice(req);
        service.saveUserDevice(ud);
        logger.debug("<saveUserLcDevice");
    }

    public void logoutDevice(String objectId) {
        logger.debug(">logoutDevice objectId = " + objectId);
        if (StringUtil.isEmpty(objectId)) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "objectId");
        }
        UserLcDevice ud = service.selectUserLcDeviceListByObjectId(objectId);
        if (null == ud) {
            throw new WrongParameterValueException("jbb.mgt.exception.parameterObject.notFound", "zh", "UserLcDevice");
        }
        if (ud.getUserId() != this.user.getUserId()) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.userWrong", "zh");
        }
        service.logoutUserLcDevice(this.user.getUserId(), objectId);
        logger.debug("<logoutDevice");
    }

    private UserLcDevice generateUserDevice(Request req) {
        UserLcDevice ud = new UserLcDevice();
        ud.setUserId(this.user.getUserId());
        if (!StringUtil.isEmpty(req.objectId)) {
            ud.setObjectId(req.objectId);
        }
        if (!StringUtil.isEmpty(req.deviceType)) {
            ud.setDeviceType(req.deviceType);
        }
        if (!StringUtil.isEmpty(req.deviceToken)) {
            ud.setDeviceToken(req.deviceToken);
        }
        if (!StringUtil.isEmpty(req.installationId)) {
            ud.setInstallationId(req.installationId);
        }
        if (!StringUtil.isEmpty(req.appName)) {
            ud.setAppName(req.appName);
        }
        if (null != req.status) {
            ud.setStatus(req.status);
        }
        return ud;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String objectId;
        public String deviceType;
        public String installationId;
        public String deviceToken;
        public Boolean status;
        public String appName;
    }
}
