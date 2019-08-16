package com.jbb.mgt.rs.action.msgCode;

import com.jbb.mgt.core.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.MessageCode;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.PhoneNumberUtil;
import com.jbb.server.common.util.StringUtil;

@Component(MsgCodeAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MsgCodeAction extends BasicAction {
    private static Logger logger = LoggerFactory.getLogger(MsgCodeAction.class);
    public static final String ACTION_NAME = "msgCodeAction";
    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private AliyunService aliyunService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChuangLanService chuangLanService;

    @Autowired
    private MessageCodeService msgCodeService;

    @Autowired
    private PhoneBlacklistService phoneBlacklistService;

    @Autowired
    private PlatformTransactionManager txManager;

    private Response response;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            logger.warn("Cannot rollback transaction", er);
        }
    }

    public void sendCode(String phoneNumber, Boolean checkPhoneNumber, String channelCode, String sessionId, String sig,
        String token, String scene, String signTemplate) {

        // 增加检查黑名单逻辑
        phoneBlacklistService.checkPhoneNumberExist(phoneNumber);

        boolean checkFlag = (checkPhoneNumber != null && checkPhoneNumber == true);
        if (checkFlag) {
            User user = userService.selectUser(Constants.JBB_ORG, phoneNumber);
            if (user != null) {
                this.response.isExist = true;
                this.response.userId = user.getUserId();
                return;
            }
        }

        // 忽略一些手机号的短信发送
        String[] ignoreRegisterMobiles = PropertyManager.getProperties("jbb.user.register.check.ignore");
        if (CommonUtil.inArray(phoneNumber, ignoreRegisterMobiles)) {
            return;
        }

        boolean codeVerifyEnable = PropertyManager.getBooleanProperty("jbb.aliyun.code.enable", false);

        if (codeVerifyEnable && !StringUtil.isEmpty(sessionId) && !StringUtil.isEmpty(sig)) {
            aliyunService.afsCheck(sessionId, sig, token, scene, this.getRemoteAddress());
        }

        // 检查同渠道验证码发送是否有超过一分钟。
        if (!PhoneNumberUtil.isValidPhoneNumber(phoneNumber)) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.phoneNumber.Incorrect");
        }
        long nowTs = DateUtil.getCurrentTime();
        MessageCode msgCodeInfo = msgCodeService.getMessageCode(phoneNumber, channelCode);
        if (msgCodeInfo != null && (nowTs - msgCodeInfo.getCreationDate().getTime()) < DateUtil.MINUTE_MILLSECONDES) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.getMsgCodeInShortTime");
        }

        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            Channel channel = channelService.getChannelByCode(channelCode);
            if (channel == null || channel.getStatus() == 1 || channel.getStatus() == 2) {
                throw new WrongParameterValueException("jbb.mgt.error.exception.channelNotFound", "zh", channelCode);
            }

            String signName = PropertyManager.getProperty("jbb.org.msg.template." + signTemplate);
            chuangLanService.sendMsgCode(phoneNumber, channelCode, signName, this.getRemoteAddress());
            logger.info(">sendMsgCode(), phoneNumber=" + phoneNumber + " , channelCode=" + channelCode
                + " , remoteAddress=" + this.getRemoteAddress());
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction (release user lock)
            rollbackTransaction(txStatus);
        }
        logger.debug("<sendMsgCode()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private Boolean isExist;
        private Integer userId;

        public Boolean getIsExist() {
            return isExist;
        }

        public Integer getUserId() {
            return userId;
        }

    }

}
