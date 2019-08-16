package com.jbb.mgt.rs.action.userDevice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.UserDevice;
import com.jbb.mgt.core.service.UserDeviceService;
import com.jbb.mgt.rs.action.channel.ChannelAction;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 用户设备信息Action
 *
 * @author wyq
 * @date 2018/8/28 15:28
 */
@Service(UserDeviceAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserDeviceAction extends BasicAction {
    public static final String ACTION_NAME = "UserDeviceAction";

    private static Logger logger = LoggerFactory.getLogger(UserDeviceAction.class);

    @Autowired
    private UserDeviceService service;

    public void saveUserDevice(Request req){
        logger.debug(">createChannel, UserDevice={}", req);
        if (null == req || null == req.uuid){
            throw new WrongParameterValueException("jbb.error.exception.missing.param","zh","uuid");
        }
        UserDevice ud = generateUserDevice(req);
        service.saveUserDevice(ud);
        logger.debug("<createChannel");
    }

    private UserDevice generateUserDevice(Request req){
        UserDevice ud = new UserDevice();
        ud.setUserId(this.user.getUserId());
        ud.setUuid(StringUtil.isEmpty(req.uuid) ? null : req.uuid);
        ud.setModel(StringUtil.isEmpty(req.model) ? null : req.model);
        ud.setPlatform(StringUtil.isEmpty(req.platform) ? null : req.platform);
        ud.setVersion(StringUtil.isEmpty(req.version) ? null : req.version);
        ud.setManufacturer(StringUtil.isEmpty(req.manufacturer) ? null : req.manufacturer);
        ud.setVirtual(null != req.isVirtual && req.isVirtual ? null : req.isVirtual);
        ud.setSerial(StringUtil.isEmpty(req.serial) ? null : req.serial);
        return ud;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String cordova;
        public String model;
        public String platform;
        public String uuid;
        public String version;
        public String manufacturer;
        public Boolean isVirtual;
        public String serial;
    }
}
