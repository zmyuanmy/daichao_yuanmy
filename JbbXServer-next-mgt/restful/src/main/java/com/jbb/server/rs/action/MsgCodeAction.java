package com.jbb.server.rs.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.service.AliyunService;
import com.jbb.server.core.service.ChuangLanService;

@Component(MsgCodeAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MsgCodeAction extends BasicAction {
    private static Logger logger = LoggerFactory.getLogger(MsgCodeAction.class);
    public static final String ACTION_NAME = "msgCodeAction";

    @Autowired
    private AliyunService aliyunService;

    @Autowired
    private ChuangLanService chuangLanService;

    public void sendCode(String phoneNumber, String sessionId, String sig, String token, String scene) {
        logger.debug(">sendMsgCode()");

        logger.debug(">sencCode() phoneNumber=" + phoneNumber);
        // aliyunService.sendCode(phoneNumber);
        boolean codeVerifyEnable = PropertyManager.getBooleanProperty("jbb.aliyun.code.enable", false);

        if (codeVerifyEnable && !StringUtil.isEmpty(sessionId) && !StringUtil.isEmpty(sig)) {
            aliyunService.afsCheck(sessionId, sig, token, scene, this.getRemoteAddress());
        }
        chuangLanService.sendMsgCode(phoneNumber);
        logger.debug("<sencCode()");

        logger.debug("<sendMsgCode()");
    }

}
