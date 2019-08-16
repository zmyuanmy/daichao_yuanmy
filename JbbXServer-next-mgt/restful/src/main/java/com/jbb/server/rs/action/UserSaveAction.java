package com.jbb.server.rs.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.DuplicateEntityException;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.IDCardUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.domain.ChuangLanPhoneNumber;
import com.jbb.server.core.domain.ChuangLanPhoneNumberRsp;
import com.jbb.server.core.domain.ChuangLanWoolCheck;
import com.jbb.server.core.domain.ChuangLanWoolCheckRsp;
import com.jbb.server.core.domain.User;
import com.jbb.server.core.domain.UserProperty;
import com.jbb.server.core.service.AccountService;
import com.jbb.server.core.service.ChuangLanService;
import com.jbb.server.core.service.PaService;
import com.jbb.server.core.service.UserEventLogService;
import com.jbb.server.core.service.UserRegisterProcessService;
import com.jbb.server.core.service.UserService;
import com.jbb.server.core.util.PasswordUtil;
import com.jbb.server.core.util.ProcessUserOnStepOneUtil;
import com.jbb.server.rs.pojo.ActionResponse;
import com.jbb.server.rs.pojo.request.ReUser;

@Service(UserSaveAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserSaveAction extends BasicAction {

    private static Logger logger = LoggerFactory.getLogger(UserSaveAction.class);

    public static final String ACTION_NAME = "UserSaveAction";

    private Response response;

    @Autowired
    private UserEventLogService userEventLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaService paService;

    @Autowired
    private UserRegisterProcessService userRegisterProcessService;

    @Autowired
    private ChuangLanService chuangLanService;

    @Autowired
    private AccountService accountService;

    @Override
    protected ActionResponse makeActionResponse() {
        return response = new Response();
    }

    public void registerUser(String phoneNumber, String msgCode, String nickname, String password, String platform,
        String sourceId) {
        if (logger.isDebugEnabled()) {
            logger.debug(">registerUser()" + ", phoneNumber=" + phoneNumber + ",  msgCode=" + msgCode + ", sourceId="
                + sourceId + ", nickName=" + nickname + ", platform=" + platform);
        }

        // 检查nickname唯一性
        if (!StringUtil.isEmpty(nickname) && coreAccountService.checkNickname(nickname)) {
            throw new WrongParameterValueException("jbb.error.exception.nicknameDuplicate");
        }

        // 添加检查代码
        if (StringUtil.isEmpty(phoneNumber)) {
            throw new WrongParameterValueException("jbb.error.exception.phoneNumberEmpty");
        }
        if (!PasswordUtil.isValidUserPassword(password)) {
            throw new WrongParameterValueException("jbb.error.exception.passwordCheckError");
        }

        // 注册用户
        User user = coreAccountService.createUser(phoneNumber, nickname, password, msgCode, sourceId, platform);
        if (user.getUserId() != 0) {
            // 插入注册日志
            int userId = user.getUserId();
            String remoteAddress = this.getRemoteAddress();
            userEventLogService.insertEventLog(userId, sourceId, Constants.Event_LOG_EVENT_USER_EVENT,
                Constants.Event_LOG_EVENT_USER_ACTION_REGISTER, null, null, remoteAddress);
        }
        logger.debug("<registerUser");
    }

    private User convertToUser(ReUser reUser) {

        User user = new User();
        user.setPhoneNumber(reUser.getPhoneNumber());
        user.setUserName(reUser.getUserName());
        user.setIdCardNo(reUser.getIdCardNo());
        user.setWechat(reUser.getWechat());
        user.setProperites(reUser.getProperites());
        return user;
    }

    // 分步检查用户提交的数据
    private void checkH5UserInfo(ReUser reUser, Integer step, String sourceId, Integer validateSesameCreditScoreStep) {

        if (validateSesameCreditScoreStep == null) {
            validateSesameCreditScoreStep = 1;
        }

        if (step == null || step == 1) {

            if (StringUtil.isEmpty(sourceId)) {
                throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "sourceId");
            }

            if (StringUtil.isEmpty(reUser.getPhoneNumber())) {
                throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "phoneNumber");
            }

            // 第一步验证
            if ((step == null || validateSesameCreditScoreStep == null || validateSesameCreditScoreStep == 1)
                && !userService.checkExistPropertyAndNotNull(reUser.getProperites(), Constants.SESAME_CREDIT_SCORE)) {
                throw new MissingParameterException("jbb.error.exception.missing.param", "zh",
                    "property->sesameCreditScore");
            }

        }

        if (step == null || step == 2) {
            if (StringUtil.isEmpty(reUser.getUserName())) {
                throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "username");
            }

            if (StringUtil.isEmpty(reUser.getIdCardNo())) {
                throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "idcardno");
            }

            if (StringUtil.isEmpty(reUser.getWechat())) {
                throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "wechat");
            }

            // 第二步验证
            if ((step != null && step == 2 && validateSesameCreditScoreStep == 2)
                && !userService.checkExistPropertyAndNotNull(reUser.getProperites(), Constants.SESAME_CREDIT_SCORE)) {
                throw new MissingParameterException("jbb.error.exception.missing.param", "zh",
                    "property->sesameCreditScore");
            }

            // 检查身份证号
            String idcard = reUser.getIdCardNo();
            if (!IDCardUtil.validate(idcard) || !userService.chekcIdCardRegion(idcard)) {
                throw new WrongParameterValueException("jbb.error.exception.idCardError", "zh");
            }
        }

    }

    public void registerUserFromH5(ReUser reUser, String sourceId, String msgCode, String platform,
        List<Integer> targetUserIds, Integer step, Boolean test, Integer validateSesameCreditScoreStep) {

        logger.debug(">registerUserFromH5() , reUser" + reUser + "scourceId=" + sourceId + "platform=" + platform);
        if (StringUtil.isEmpty(msgCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "msgCode");
        }

        // 检查数据
        checkH5UserInfo(reUser, step, sourceId, validateSesameCreditScoreStep);
        
        //TODO 用户隐藏标记
        //user.setHidden(isHiddenUser);
        try {
            // 注册用户
            User user = convertToUser(reUser);
            user.setSourceId(sourceId);
            user.setPlatform(platform);
            coreAccountService.createUser(user, msgCode);
            
            //是否隐藏用户，羊毛检测和空号检测
            boolean isHiddenUser = false;
            List<UserProperty> userProperties = new ArrayList<UserProperty>();
            try {
                isHiddenUser = hideUser(reUser.getPhoneNumber(), userProperties, sourceId);
                userProperties.add(new UserProperty("hidden",isHiddenUser?"1":"0"));
            } catch (Exception e) {
                // nothing to do
                logger.error("hideUserForChannel with error, " + e.getMessage());
            }
            
            if (userProperties != null && userProperties.size() > 0) {
                accountService.addUserProperties(user.getUserId(), userProperties);
            }
            
            if (user.getUserId() != 0) {
                // 插入注册日志
                int userId = user.getUserId();
                String remoteAddress = this.getRemoteAddress();
                userEventLogService.insertEventLog(userId, sourceId, Constants.Event_LOG_EVENT_USER_EVENT,
                    Constants.Event_LOG_EVENT_USER_ACTION_REGISTER, null, null, remoteAddress);
            }
            this.user = user;
            // 注册第一步：延时发送至MQ， 延时x秒
            int delay = PropertyManager.getIntProperty("jbb.user.register.step1.delay", 300);

            if (test != null && true == test) {
                // nothing to do ,测试数据不发送
            } else {
                ProcessUserOnStepOneUtil.sendUserWithDelay(user.getUserId(), delay);
            }

        } catch (DuplicateEntityException e) {
            // 获取并更新用户信息
            this.user = this.coreAccountService.loginByCode(reUser.getPhoneNumber(), msgCode, platform);
            
            //忽略检查手机号
            String[] ignorePhones = PropertyManager.getProperties("jbb.user.register.check.ignore");
            boolean unckeck = false;
            if(CommonUtil.inArray(user.getPhoneNumber(), ignorePhones)){
                unckeck = true;
                test = true;
            }
            
            if (!unckeck && this.user.getIdCardNo() != null && !this.user.getIdCardNo().equals(reUser.getIdCardNo())) {
                throw new WrongParameterValueException("jbb.error.exception.idcardnoNotEqual");
            }
            if (!unckeck && this.user.getUserName() != null && !this.user.getUserName().equals(reUser.getUserName())) {
                throw new WrongParameterValueException("jbb.error.exception.usernameNotEqual");
            }
            
            updateUser(null, reUser);

            // 注册第二步， 更新用户信息时，发送用户ID到消息队列，进行下一步处理。
            // 发送消息队列，进行注册广播 , 后续处理分配 到相应的申请机构
            int registerUserId = this.user.getUserId();

            // 测试数据不发送
            if (test != null && true == test) {
                // nothing todo， 测试数据不发送
            } else {
                // 移除第一步的延时发送至MQ
                ProcessUserOnStepOneUtil.removeSendTask(registerUserId);
                // 立即处理推送
                Set<User> lenders = userRegisterProcessService.applyToLendUser(registerUserId);
                if (lenders != null && lenders.size() > 0) {
                    this.response.lenderNames = new ArrayList<String>(lenders.size());
                    lenders.forEach((user) -> {
                        this.response.lenderNames.add(user.getUserName());
                    });
                }
            }

            int userId = user.getUserId();
            String remoteAddress = this.getRemoteAddress();
            if (userId != 0) {
                // 插入更新日志
                userEventLogService.insertEventLog(userId, sourceId, Constants.Event_LOG_EVENT_USER_EVENT,
                    Constants.Event_LOG_EVENT_USER_ACTION_UPDATE, null, null, remoteAddress);
            }

        }

        logger.debug("<registerUserFromH5()");
    }

    public void updateUser(String password, ReUser reUser) {
        logger.debug(">updateUser(), reUser = {}", reUser);

        if (StringUtil.trimToNull(password) != null) {
            if (!PasswordUtil.isValidUserPassword(password)) {
                throw new WrongParameterValueException("jbb.error.exception.passwordCheckError");
            }
            // 更新密码
            coreAccountService.updatePassword(user.getUserId(), password);
        }
        // 将请求的reUser中的更新数据添加到原来的user对象
        boolean changed = convertReUser2User(user, reUser);
        // 更新用户数据到数据库
        if (changed) {
            // 检查nickname唯一性
            String nickname = user.getNickName();
            if (!StringUtil.isEmpty(StringUtil.trimToNull(reUser.getNickName()))) {
                if (!StringUtil.isEmpty(nickname) && coreAccountService.checkNickname(nickname)) {
                    throw new WrongParameterValueException("jbb.error.exception.nicknameDuplicate");
                }
            }
            coreAccountService.updateUser(user);
        }

        logger.debug("<updateUser()");
    }

    // TODO
    private boolean convertReUser2User(User user, ReUser reUser) {
        if (reUser == null) {
            return false;
        }
        boolean changed = false;
        String value;

        if ((value = StringUtil.trimToNull(reUser.getBankCardNo())) != null) {
            user.setBankCardNo(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getBankName())) != null) {
            user.setBankName(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getEmail())) != null) {
            user.setEmail(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getIdCardNo())) != null) {
            user.setIdCardNo(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getNickName())) != null) {
            user.setNickName(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getUserName())) != null) {
            user.setUserName(value);
            changed = true;
        }

        if ((value = StringUtil.trimToNull(reUser.getSex())) != null) {
            user.setSex(value);
            changed = true;
        }
        if (reUser.getAge() != null) {
            user.setAge(reUser.getAge());
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getNation())) != null) {
            user.setNation(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getIdcardAddress())) != null) {
            user.setIdcardAddress(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getWechat())) != null) {
            user.setWechat(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getQqNumber())) != null) {
            user.setQqNumber(value);
            changed = true;
        }
        if (reUser.getAddressBookNumber() != null) {
            user.setAddressBookNumber(reUser.getAddressBookNumber());
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getPhoneAuthenticationTime())) != null) {
            user.setPhoneAuthenticationTime(value);
            changed = true;
        }
        if (reUser.getMarried() != null) {
            user.setMarried(reUser.getMarried());
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getLiveAddress())) != null) {
            user.setLiveAddress(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getParentAddress())) != null) {
            user.setParentAddress(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getEducation())) != null) {
            user.setEducation(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getOccupation())) != null) {
            user.setOccupation(value);
            changed = true;
        }
        if ((value = StringUtil.trimToNull(reUser.getWechat())) != null) {
            user.setWechat(value);
            changed = true;
        }
        if (reUser.getProperites() != null && reUser.getProperites().size() > 0) {
            user.setProperites(reUser.getProperites());
            changed = true;
        }
        return changed;

    }

    private boolean hideUser(String phoneNumber, List<UserProperty> userProperties, String sourceId) {
        boolean mobileCheckEnabled = PropertyManager.getBooleanProperty("jbb.user.mobileCheck.enable", false);
        boolean woolCheckEnabled = PropertyManager.getBooleanProperty("jbb.user.woolCheck.enable", false);
        
        String[] woolCheckIgnores = PropertyManager.getProperties("jbb.user.woolCheck.ignoreChannel");
        String[] mobileCheckIgnores = PropertyManager.getProperties("jbb.user.mobileCheck.ignoreChannel");
        
        
        if (mobileCheckEnabled && !CommonUtil.inArray(sourceId, mobileCheckIgnores)) {
            // 空号检测
            ChuangLanPhoneNumberRsp mobileCheckRsp = chuangLanService.validateMobile(phoneNumber, true);
            if (mobileCheckRsp != null && mobileCheckRsp.getData() != null) {
                ChuangLanPhoneNumber data = mobileCheckRsp.getData();
                if (!Constants.STATUS_REAL_PHONE_NUMBER.equals(data.getStatus())) {
                    userProperties.add(new UserProperty("mobileCheckResult", data.getStatus()));
                    return true;
                }
            }
        }
        if (woolCheckEnabled  && !CommonUtil.inArray(sourceId, woolCheckIgnores)) {
            // 羊毛检测
            ChuangLanWoolCheckRsp woolCheckRsp = chuangLanService.woolCheck(phoneNumber, this.getRemoteAddress());
            if (woolCheckRsp != null && woolCheckRsp.getData() != null) {
                ChuangLanWoolCheck data = woolCheckRsp.getData();
                if (Constants.STATUS_WOOL_CHECK_B1.equals(data.getStatus())
                    || Constants.STATUS_WOOL_CHECK_B2.equals(data.getStatus())) {
                    userProperties.add(new UserProperty("woolcheckResult", data.getStatus()));
                    return true;
                }
            }
        }
        /* if (deductionUser(channel.getChannelCode())) {
            // 是否扣量
            userProperties
                .add(new UserProperty("hidden", Constants.USER_PROPERTY_DECREMENT_RESULT_VALUE));
            return true;
        }*/
        return false;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private List<String> lenderNames;

        public List<String> getLenderNames() {
            return lenderNames;
        }

    }

}
