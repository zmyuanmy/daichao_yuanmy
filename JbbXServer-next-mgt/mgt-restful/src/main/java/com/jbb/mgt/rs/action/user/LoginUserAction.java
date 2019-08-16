package com.jbb.mgt.rs.action.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jbb.mgt.rs.action.xiaoCaiMi.XiaoCaiMiAction;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.dao.MessageCodeDao;
import com.jbb.mgt.core.domain.*;
import com.jbb.mgt.core.service.*;
import com.jbb.mgt.rs.action.integrate.SyncJbbUserIdThread;
import com.jbb.mgt.server.core.util.PasswordUtil;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.RsLoginLog;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.Call3rdApiException;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@Service(LoginUserAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoginUserAction extends BasicAction {

    public static final String ACTION_NAME = "LoginUserAction";

    private static Logger logger = LoggerFactory.getLogger(LoginUserAction.class);

    private static DefaultTransactionDefinition NEW_TX_DEFINITION =
        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    @Qualifier("UserService")
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private AliyunService aliyunService;

    @Autowired
    @Qualifier("MessageCodeDao")
    private MessageCodeDao messageCodeDao;

    @Autowired
    @Qualifier("SystemService")
    private SystemService systemService;

    @Autowired
    private UserApplyRecordService userApplyRecordService;

    @Autowired
    private UserPropertiesService userPropertiesService;

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private OrganizationUserService organizationUserService;

    @Autowired
    private UserEventLogService userEventLogService;

    @Autowired
    private UserLoginLogService userLoginLogService;

    @Autowired
    private OrgAppChannelUserService orgAppChannelUserService;

    @Autowired
    private XjlUserService xjlUserService;

    @Autowired
    private FlashsdkLoginService flashsdkLoginService;

    @Autowired
    private PhoneBlacklistService phoneBlacklistService;

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

    public void loginUserByPassword(String phoneNumber, String password, Long expiry, Integer applicationId,
        String channelCode, String platform) {
        logger.debug(">loginUserByPassword(), phoneNumber=" + phoneNumber + ",expiry=" + expiry + ",applicationId="
            + applicationId);

        if (StringUtil.isEmpty(phoneNumber)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "phoneNumber");
        }

        if (StringUtil.isEmpty(password)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "password");
        }

        if (expiry == null) {
            expiry = Constants.ONE_DAY_MILLIS;
        }

        if (applicationId == null) {
            applicationId = User.USER_APPLICATION_ID;
        }

        // 增加检查黑名单逻辑
        phoneBlacklistService.checkPhoneNumberExist(phoneNumber);

        User user = this.userService.selectUser(null, phoneNumber);

        if (user == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.phoneNotFound");
        }

        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            throw new WrongParameterValueException("jbb.error.exception.passwordError");
        }

        Channel channel = channelService.getChannelByCode(channelCode);

        int orgId = channel.getAccount().getOrgId();
        createData(user, orgId, false, false, expiry, applicationId, channel, platform, 2);

        logger.debug("<loginUserByPassword()");

    }

    public void loginUserByPhoneNumber(String phoneNumber, String msgCode, String password, String channelCode,
        String delegateCode, Integer initAccountId, String platform, String mobileManufacture, Boolean test,
        Integer userType, Long expiry, Integer applicationId) {
        logger.debug(">loginUserByPhoneNumber(), phoneNumber=" + phoneNumber + ",msgCode=" + msgCode + ",channelCode="
            + channelCode + ",test=" + test + ",platform=" + platform + ",mobileManufacture=" + mobileManufacture
            + ",userType=" + userType + ",expiry=" + expiry + ",applicationId=" + applicationId);

        if (!StringUtil.isEmpty(password) && !PasswordUtil.isValidUserPassword(password)) {
            throw new WrongParameterValueException("jbb.error.exception.passwordCheckError");
        }

        if (expiry == null) {
            expiry = Constants.ONE_DAY_MILLIS;
        }

        if (applicationId == null) {
            applicationId = User.USER_APPLICATION_ID;
        }

        if (userType == null) {
            userType = Constants.USER_TYPE_REGISTER;
        }

        verifyParam(phoneNumber, msgCode, channelCode);

        // 增加检查黑名单逻辑
        phoneBlacklistService.checkPhoneNumberExist(phoneNumber);

        TransactionStatus txStatus = null;

        boolean isMsgCoede =
            messageCodeDao.checkMsgCode(phoneNumber, channelCode, msgCode, DateUtil.getCurrentTimeStamp());

        // 如果在忽略列表里，不验证验证码
        String[] ignoreRegisterMobiles = PropertyManager.getProperties("jbb.user.register.check.ignore");
        if (CommonUtil.inArray(phoneNumber, ignoreRegisterMobiles)) {
            isMsgCoede = true;
        }
        // end

        if (!isMsgCoede) {
            logger.warn("message code error, phoneNumber=" + phoneNumber + " , channelCode=" + channelCode
                + " ,msgCode=" + msgCode);
            throw new WrongParameterValueException("jbb.mgt.error.exception.wrongMsgCode");
        }

        // 获取进件渠道信息
        Channel channel = channelService.getChannelByCode(channelCode);
        int orgId = channel.getAccount().getOrgId();

        // 获取代理渠道信息
        Channel delegateChannel = null;
        // 移除代理功能 0115 by Vincent
        // if (!StringUtil.isEmpty(delegateCode)) {
        // delegateChannel = channelService.getChannelByCode(delegateCode);
        // }

        Account creator = this.coreAccountService.getAccountById(channel.getCreator(), null, false);

        boolean isNewUser = false;
        // 是否需要在渠道端隐藏用户
        boolean isHiddenUser = false;
        boolean isDelegateMode = false;
        
        User user = null;

        // 类锁， 防止暴力注册
        // TODO 后续移除， 由于历史 原因，库里没有对手机号加 unique 标识， 后续在DB 加唯一标识处理。
        synchronized (LoginUserAction.class) {
            try {

                user = this.userService.selectUser(null, phoneNumber);
                isNewUser = user == null ? true : false;
                // 是否需要在渠道端隐藏用户
                isHiddenUser = false;
                isDelegateMode = false;

                txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
                if (user == null) {
                    user = new User();
                    user.setPhoneNumber(phoneNumber);
                    user.setCreationDate(DateUtil.getCurrentTimeStamp());
                    user.setIpAddress(this.getRemoteAddress());
                    user.setPlatform(platform);
                    user.setMobileManufacture(mobileManufacture);
                    user.setFirstChannelCode(channelCode);

                    if (!StringUtil.isEmpty(password)) {
                        user.setPassword(PasswordUtil.passwordHash(password));
                    }

                    IPAddressInfo ipAddressInfo = aliyunService.getIPAddressInfo(this.getRemoteAddress());
                    if (ipAddressInfo != null) {
                        user.setIpArea(ipAddressInfo.getIpArea());
                    }

                    // 是否需要在渠道端隐藏用户
                    List<UserProperty> userProperties = new ArrayList<UserProperty>();
                    try {
                        isHiddenUser = userService.needHiddenUserForChannel(channel, phoneNumber,
                            this.getRemoteAddress(), userProperties);
                    } catch (Exception e) {
                        // nothing to do
                        logger.error("hideUserForChannel with error, " + e.getMessage());
                    }
                    user.setHidden(isHiddenUser);
                    // 插入用户
                    this.userService.insertUser(user);
                    // 更新用户properties
                    if (userProperties != null && userProperties.size() > 0) {
                        userPropertiesService.insertUserProperties(user.getUserId(), userProperties);
                    }

                    // 插入日志统计 注册用户同后面的H5商家关联
                    String merchantId = null;
                    if ("ios".equals(platform) && channel.getMerchantId2() != null) {
                        merchantId = String.valueOf(channel.getMerchantId2());
                    } else if (channel.getMerchantId() != null) {
                        merchantId = String.valueOf(channel.getMerchantId());
                    }
                    userEventLogService.insertEventLog(user.getUserId(), channel.getChannelCode(), null, "userMerchant",
                        "register", "merchantId", merchantId, this.getRemoteAddress(), DateUtil.getCurrentTimeStamp());

                } else {

                    // 如果有设置密码，再更新密码
                    if (!StringUtil.isEmpty(password)) {
                        user.setPassword(PasswordUtil.passwordHash(password));
                    }
                    this.userService.updateUser(user);

                    Boolean isExist = organizationUserService.checkExist(creator.getOrgId(), user.getUserId());

                    if (isExist) {
                        createData(user, orgId, isNewUser, false, expiry, applicationId, channel, platform, userType);
                        txManager.commit(txStatus);
                        txStatus = null;
                        return;
                    }
                }

                saveToOrgAndApplyRecord(user, creator.getOrgId(), initAccountId, channelCode, userType, false);

                // // 更新jbbId
                // new SyncJbbUserIdThread(user).start();

                txManager.commit(txStatus);
                txStatus = null;
            } finally {
                rollbackTransaction(txStatus);
            }
            
            // 创建用户Key

            createData(user, orgId, isNewUser, isHiddenUser, expiry, applicationId, channel, platform,
                isDelegateMode ? Constants.USER_TYPE_DELEGATE : userType);
        }

       
        logger.debug("<loginUserByPhoneNumber()");
    }

    public void bnhUserLogin(String phoneNumber, String msgCode, String channelCode, String platform,
        String mobileManufacture, Integer userType, Long expiry, Integer applicationId, Request req) throws Exception {
        logger.debug(">flashsdkLogin()");
        expiry = expiry == null ? -1 : expiry * Constants.ONE_DAY_MILLIS;
        applicationId = applicationId == null ? User.USER_APPLICATION_ID : applicationId;
        userType = userType == null ? Constants.USER_TYPE_REGISTER : userType;
        if (req != null && !StringUtil.isEmpty(req.appId)) {
            phoneNumber = flashsdkLoginService.flashsdkLogin(req.appId, req.accessToken, req.telecom, req.timestamp,
                req.randoms, req.sign, req.version, req.device, req.platform);
            if (phoneNumber == null) {
                throw new Call3rdApiException("jbb.mgt.exception.flashsdk.login");
            }
            // 埋前面的坑 - By Vincent
            if (StringUtil.isEmpty(channelCode)) {
                channelCode = "";
                String[] androidFlag = PropertyManager.getProperties("jbb.mgt.flashsdk.android.flag");
                if (CommonUtil.inArray(req.platform, androidFlag)) {
                    channelCode = "prod-android";
                } else {
                    channelCode = "prod-ios";
                }
            }

        } else {
            verifyParam(phoneNumber, msgCode, channelCode);
            boolean isMsgCoede =
                messageCodeDao.checkMsgCode(phoneNumber, channelCode, msgCode, DateUtil.getCurrentTimeStamp());
            String[] ignoreRegisterMobiles = PropertyManager.getProperties("jbb.user.register.check.ignore");
            if (CommonUtil.inArray(phoneNumber, ignoreRegisterMobiles)) {
                isMsgCoede = true;
            }
            if (!isMsgCoede) {
                logger.warn("message code error, phoneNumber=" + phoneNumber + " , channelCode=" + channelCode
                    + " ,msgCode=" + msgCode);
                throw new WrongParameterValueException("jbb.mgt.error.exception.wrongMsgCode");
            }
        }
        // 增加检查黑名单逻辑
        phoneBlacklistService.checkPhoneNumberExist(phoneNumber);

        Channel channel = channelService.getChannelByCode(channelCode);
        Account creator = this.coreAccountService.getAccountById(channel.getCreator(), null, false);
        int orgId = channel.getAccount().getOrgId();
        boolean isNewUser = false;
        TransactionStatus txStatus = null;
        
        User user = null;

        // 类锁， 防止暴力注册
        // TODO 后续移除， 由于历史 原因，库里没有对手机号加 unique 标识， 后续在DB 加唯一标识处理。
        synchronized (LoginUserAction.class) {

            user = this.userService.selectUser(null, phoneNumber);
            isNewUser = user == null ? true : false;

            try {

                txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
                if (user == null) {
                    user = new User();
                    user.setPhoneNumber(phoneNumber);
                    user.setCreationDate(DateUtil.getCurrentTimeStamp());
                    user.setIpAddress(this.getRemoteAddress());
                    user.setPlatform(platform);
                    user.setFirstChannelCode(channelCode);
                    IPAddressInfo ipAddressInfo = aliyunService.getIPAddressInfo(this.getRemoteAddress());
                    if (ipAddressInfo != null) {
                        user.setIpArea(ipAddressInfo.getIpArea());
                    }
                    // 插入用户
                    this.userService.insertUser(user);
                }
                saveToOrgAndApplyRecord(user, creator.getOrgId(), null, channelCode, userType, false);
                // 更新jbbId
                // new SyncJbbUserIdThread(user).start();
                txManager.commit(txStatus);
                txStatus = null;
            } finally {
                rollbackTransaction(txStatus);
            }
            
         // 创建用户Key
            createData(user, orgId, isNewUser, false, expiry, applicationId, channel, platform, userType);
        }
        
        logger.debug("<flashsdkLogin()");

    }

    private void saveToOrgAndApplyRecord(User user, int orgId, Integer initAccountId, String channelCode,
        Integer userType, boolean jbbFlag) {

        int sUserType = (userType == null ? 1 : userType);
        // 插入组织
        OrganizationUser orgUser =
            new OrganizationUser(orgId, user.getUserId(), orgId, sUserType, channelCode, jbbFlag);
        organizationUserService.createOrganizationUser(orgUser);
        // 插入申请
        this.insertApplyRecord(user, orgId, initAccountId, channelCode, sUserType);

    }

    // 检查在天内是否申请过，并且有推荐过给相应出借方
    private boolean isAppliedInXDays(int userId, int days, int orgId) {
        long todayStart = DateUtil.getStartTsOfDay(DateUtil.getCurrentTime());
        Timestamp start = new Timestamp(todayStart - days * DateUtil.DAY_MILLSECONDES);
        return userService.checkUserApplied(userId, start, orgId);
    }

    private void verifyParam(String phoneNumber, String msgCode, String channelCode) {
        if (StringUtil.isEmpty(phoneNumber)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "phoneNumber");
        }
        if (StringUtil.isEmpty(msgCode)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "msgCode");
        }
        if (StringUtil.isEmpty(channelCode)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "channelCode");
        }
    }

    private void insertApplyRecord(User user, int orgId, Integer initAccountId, String channelCode, int userType) {

        // 从H5链接进来,组织存在,再把这个这个用户插入到mgt_user_apply_records
        if (orgId > 0) {
            UserApplyRecord userApplyRecord = new UserApplyRecord();
            userApplyRecord.setUserId(user.getUserId());
            userApplyRecord.setOrgId(orgId);
            userApplyRecord.setCreationDate(DateUtil.getCurrentTimeStamp());
            userApplyRecord.setStatus(1);
            userApplyRecord.setsUserType(userType);
            userApplyRecord.setsChannelCode(channelCode);
            userApplyRecord.setsOrgId(orgId);

            // initAccountId判断是否从电销,中介链接过来
            if (initAccountId != null && initAccountId > 0) {

                // 检查这份审核人是否存在,是否与渠道创建人为一个组织
                Account account = coreAccountService.getAccountById(initAccountId, null, false);
                if (account != null && account.getOrgId() == orgId) {
                    userApplyRecord.setAssignAccId(initAccountId);
                    userApplyRecord.setAssingDate(DateUtil.getCurrentTimeStamp());
                    userApplyRecord.setInitAccId(initAccountId);
                    userApplyRecord.setStatus(2);
                } else {
                    throw new WrongParameterValueException("jbb.mgt.exception.apply.initOrOrgIdError");
                }
            } else {

                // Start 获取组织自动分配设置，并实施自动分配
                Account acc =
                    this.coreAccountService.getOrgAutoAssignAccount(orgId, Constants.TYPE_USER_AUTO_SETTING_SELF);
                if (acc != null) {
                    userApplyRecord.setInitAccId(acc.getAccountId());
                    userApplyRecord.setAssingDate(DateUtil.getCurrentTimeStamp());
                    userApplyRecord.setStatus(2);
                }

                // End

            }
            userApplyRecordService.createUserApplyRecord(userApplyRecord);
        }
    }

    private void createData(User user, int orgId, boolean isNew, boolean hidden, long expiry, int applicationId,
        Channel channel, String platform, Integer userType) {
        int userId = user.getUserId();
        String channelCode = channel.getChannelCode();

        // 插入日志Start
        UserLoginLog log =
            new UserLoginLog(user.getUserId(), channelCode, applicationId, platform, userType, this.getRemoteAddress());
        userLoginLogService.insertUserLoginLog(log);
        if (channelCode.equals("xjl")) {
            xjlUserService.saveLastLoginDate(orgId, userId);
        }
        // 插入登录日志END

        // 计算渠道结算量 start
        orgAppChannelUserService.saveOrgAppChannelUser(user, orgId, isNew, hidden, channel, applicationId, userType,
            this.getRemoteAddress());
        // end

        UserKey userKey = user.getKey();
        boolean createNewKey = (userKey == null);

        if (!createNewKey) {
            Timestamp keyExpiry = userKey.getExpiry();
            createNewKey =
                (keyExpiry == null) || (keyExpiry.getTime() < System.currentTimeMillis() + Constants.ONE_DAY_MILLIS);
        }

        if (createNewKey) {
            // create new user key
            userKey = userService.createUserKey(userId, applicationId, expiry, true);
        }
        response.userId = userId;
        response.key = userKey.getUserKey();
        Timestamp keyExpiry = userKey.getExpiry();
        response.keyExpire = StringUtil.printDateTime(keyExpiry, DateUtil.getCurrentCalendar());
        response.keyExpireMs = keyExpiry.getTime();

    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private Integer userId;
        private String key;
        private String keyExpire;
        private long keyExpireMs;

        private List<RsLoginLog> loginLogs;

        public Integer getUserId() {
            return userId;
        }

        public String getKey() {
            return key;
        }

        public String getKeyExpire() {
            return keyExpire;
        }

        public long getKeyExpireMs() {
            return keyExpireMs;
        }

        public List<RsLoginLog> getLoginLogs() {
            return loginLogs;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String appId;
        public String accessToken;
        public String telecom;
        public String timestamp;
        public String randoms;
        public String sign;
        public String version;
        public String device;
        public String platform;
    }

}
