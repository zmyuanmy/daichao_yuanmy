package com.jbb.server.core.service.impl;

import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.*;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.dao.*;
import com.jbb.server.core.domain.*;
import com.jbb.server.core.service.AccountService;
import com.jbb.server.core.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author VincentTang
 * @Type AccountServiceImpl.java
 * @Desc
 * @date 2017年10月30日 下午4:36:39
 */
@Service("AccountService")
public class AccountServiceImpl implements AccountService {

    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private static final int USER_KEY_SIZE = 64;

    public static final String FROMPEER = "jbb_admin";
    public static final String CHANGE_PHONE = "changePhone";
    public static final String CHANGE_WECHAT = "changeWechat";
    public static final String CHANGE_QQ = "changeQqNumber";
    public static final String CHECK_PERSONAL_DATA = "checkPersonalData";

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserPrivDao userPrivDao;

    @Autowired
    private UserDeviceDao userDeviceDao;

    @Autowired
    private UserVerifyDao userVerifyDao;

    @Autowired
    private MessageCodeDao messageCodeDao;

    @Autowired
    private RegionCodeDao regionCodeDao;

    @Autowired
    private UserEventLogDao userEventLogDao;

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private PlatformTransactionManager txManager;

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

    @Override
    public void saveUserProperty(int userId, String name, String value) {
        accountDao.insertUserProperty(userId, name, value);
    }

    @Override
    public void addUserProperties(int userId, List<UserProperty> properties) {
        accountDao.insertUserProperties(userId, properties);
    }

    @Override
    public void updateUserProperties(int userId, List<UserProperty> properties) {
        accountDao.insertUserProperties(userId, properties);
    }

    @Override
    public void deleteUserProperties(int userId, List<UserProperty> list) {
        for (UserProperty userProperty : list) {
            accountDao.deleteUserPropertyByUserIdAndName(userId, userProperty.getName());
        }
    }

    @Override
    public List<UserProperty> searchUserProperties(int userId, String name) {
        return accountDao.selectUserProperties(userId, name);
    }

    @Override
    public UserProperty searchUserPropertiesByUserIdAndName(int userId, String name) {
        return accountDao.selectUserPropertyByUserIdAndName(userId, name);
    }

    @Override
    public Long getLongProperty(int userId, String name) {
        UserProperty p = searchUserPropertiesByUserIdAndName(userId, name);
        if (p == null) {
            return null;
        }
        String value = p.getValue();
        if ((value != null) && (value.length() > 0)) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                logger.warn("Exception in parsing of the long property value: " + name + "='" + value + "'"
                    + ", userId= " + userId);
            }
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User login(String userKey, int[] applicationIds) throws WrongUserKeyException {
        if (userKey == null)
            throw new WrongUserKeyException();

        User user = accountDao.authenticate(userKey);

        if (user == null) {
            throw new WrongUserKeyException();
        }

        int keyAppId = user.getUserKey().getApplicationId();
        boolean found = false;
        for (int appId : applicationIds) {
            if (appId == keyAppId) {
                found = true;
                break;
            }
        }
        if (!found)
            throw new WrongUserKeyException();

        loginProcess(user, keyAppId);

        return user;
    }

    private void insertLoginLog(User user, String platform) {
        userEventLogDao.insertEventLog(user.getUserId(), null, Constants.Event_LOG_EVENT_USER_EVENT,
            Constants.Event_LOG_EVENT_USER_ACTION_LOGIN, Constants.Event_LOG_EVENT_USER_LABEL_PALTFORM, platform, null,
            DateUtil.getCurrentTimeStamp());
    }

    private void loginProcess(User user, int appId) {
        if (UserKey.APPLICATION_USER == appId) {
            Integer roleId = user.getRoleId();
            if (roleId != null) {
                user.setPermissions(accountDao.getRolePermissions(roleId));
            }
        }
    }

    /**
     * Generate a new user key for authenticated user
     *
     * @param userId user ID
     * @param applicationId user key application ID
     * @param expiry key expiry period in milliseconds (-1 means it will not be expired)
     * @param keySize new key size in bytes
     * @param extend extend key expiry date, if it is less than requested date
     * @return user key
     */
    private UserKey createUserKey(int userId, int applicationId, String oauthClientId, long expiry, int keySize,
        boolean extend) {
        if (logger.isDebugEnabled()) {
            logger.debug("|>createUserKey() userId=" + userId + ", applicationId=" + applicationId + ", oauthClientId="
                + oauthClientId + ", expiry=" + expiry + ", keySize=" + keySize + ", extend=" + extend);
        }

        long maxKeyExpiry = PropertyManager.getIntProperty("jbb.core.userKey.maxExpiry", -1) * Constants.ONE_DAY_MILLIS;
        if ((maxKeyExpiry > 0) && ((expiry <= 0) || (expiry > maxKeyExpiry))) {
            expiry = maxKeyExpiry;
        }

        long currentTime = DateUtil.getCurrentTime();
        long expiryTime = expiry <= 0 ? Constants.LAST_SYSTEM_MILLIS : currentTime + expiry;

        boolean success = false;
        Exception ex = null;
        UserKey userKey = null;

        for (int i = 0; !success && (i < 10); i++) {
            boolean createNew = true;
            boolean update = false;

            userKey = accountDao.getUserKey(userId, applicationId, oauthClientId);

            if (userKey != null) {
                update = true;

                long keyExpiry = userKey.getExpiry().getTime();
                if (!userKey.isDeleted() && (keyExpiry > currentTime)) {
                    if (extend) {
                        createNew = false;
                        extend = (keyExpiry < expiryTime - Constants.ONE_DAY_MILLIS)
                            || (expiryTime < currentTime + Constants.ONE_DAY_MILLIS) && (keyExpiry < expiryTime);
                    } else if (keyExpiry > currentTime + Constants.ONE_DAY_MILLIS) {
                        createNew = false;
                    }
                }
            }

            if (createNew) {
                userKey = generateUserKey(expiryTime, keySize);
                userKey.setUserId(userId);
                userKey.setApplicationId(applicationId);
                userKey.setOauthClientId(oauthClientId);

                try {
                    if (update) {
                        accountDao.updateUserKey(userKey);
                    } else if (!accountDao.insertUserKey(userKey)) {
                        long keyExpiry = userKey.getExpiry().getTime();
                        if (keyExpiry <= currentTime) {
                            // current key expired
                            continue;
                        }
                    }
                } catch (DeadlockLoserDataAccessException e) {
                    ex = e;
                    if (logger.isDebugEnabled()) {
                        logger.debug("Deadlock in " + (update ? "updating" : "inserting") + " new user key " + userKey
                            + ", extend=" + extend + " : " + e.toString());
                    }
                    continue;
                } catch (Exception e) {
                    ex = e;
                    logger.warn("Exception in " + (update ? "updating" : "inserting") + " new user key " + userKey
                        + ", extend=" + extend, e);
                    continue;
                }
            } else if (extend) {
                // extend expiry time
                userKey.setExpiry(new Timestamp(expiryTime));
                try {
                    accountDao.updateUserKey(userKey);
                } catch (DeadlockLoserDataAccessException e) {
                    ex = e;
                    if (logger.isDebugEnabled()) {
                        logger.debug("Deadlock in extending new user key " + userKey + " : " + e.toString());
                    }
                    continue;
                } catch (Exception e) {
                    ex = e;
                    logger.warn("Exception in extending new user key " + userKey, e);
                    continue;
                }
            }

            success = true;
        }

        if (!success) {
            throw new ExecutionException("Cannot generate user key for userId=" + userId + ", applicationId="
                + applicationId + ", oauthClientId=" + oauthClientId + ", keySize=" + keySize, ex);
        }

        logger.debug("|<createUserKey()");
        return userKey;
    }

    private UserKey generateUserKey(long expiryTime, int keySize) {
        UserKey userKey = new UserKey();
        userKey.setUserKey(StringUtil.secureRandom(keySize).substring(0, keySize));
        userKey.setExpiry(new Timestamp(expiryTime));

        return userKey;
    }

    @Override
    public UserKey createUserKey(int userId, int applicationId, long expiry, boolean extend) {
        return createUserKey(userId, applicationId, UserKey.EMPTY_OAUTH_CLIENT_ID, expiry, USER_KEY_SIZE, extend);
    }

    @Override
    public boolean checkMsgCode(String phoneNumber, String msgCode) {
        return messageCodeDao.checkMsgCode(phoneNumber, msgCode, DateUtil.getCurrentTimeStamp());
    }

    @Override
    public User createUserFormAdmin(int adminUserId, String phoneNumber, String username, String password,
        Integer roleId) {
        if (logger.isDebugEnabled()) {
            logger.debug(
                "|>createUserForAdmin() phoneNumber=" + phoneNumber + ", username=" + username + ", roleId=" + roleId);
        }
        TransactionStatus txStatus = null;
        User user = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            // 检查手机号是否存在
            if (accountDao.checkUserSamePhoneNumber(phoneNumber)) {
                throw new DuplicateEntityException("jbb.error.exception.duplicateUser");
            }
            // 创建用户
            user = new User();
            user.setUserName(username);
            user.setPhoneNumber(phoneNumber);
            user.setPassword(PasswordUtil.passwordHash(password));
            user.setRoleId(roleId);
            user.setPlatform(String.valueOf(adminUserId));
            accountDao.insertUserBasicInfo(user);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction (release user lock)
            rollbackTransaction(txStatus);
        }
        logger.debug("|<createUserForAdmin()");
        return user;
    }

    @Override
    public User createUser(String phoneNumber, String nickName, String password, String msgCode, String sourceId,
        String platform) {
        if (logger.isDebugEnabled()) {
            logger.debug("|>createUser() phoneNumber=" + phoneNumber + ", nickName=" + nickName + ", smsCode=" + msgCode
                + ", sourceId=" + sourceId + ", platform=" + platform);
        }
        TransactionStatus txStatus = null;
        User user = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            // 检查手机号是否存在
            if (accountDao.checkUserSamePhoneNumber(phoneNumber)) {
                throw new DuplicateEntityException("jbb.error.exception.duplicateUser");
            }
            // 验证短信码
            if (!checkMsgCode(phoneNumber, msgCode)) {
                throw new AccessDeniedException("jbb.error.exception.accessDenied.msgCodeExpire");
            }
            // 创建用户
            user = new User(phoneNumber, PasswordUtil.passwordHash(password), nickName, DateUtil.getCurrentTime());
            user.setSourceId(sourceId);
            user.setPlatform(platform);
            accountDao.insertUserBasicInfo(user);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction (release user lock)
            rollbackTransaction(txStatus);
        }
        logger.debug("|<createUser()");
        return user;
    }

    @Override
    public User createUser(User user, String msgCode) {
        if (logger.isDebugEnabled()) {
            logger.debug("|>createUser() user=" + user + ", smsCode=" + msgCode);
        }
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            String phoneNumber = user.getPhoneNumber();
            // 检查手机号是否存在
            if (accountDao.checkUserSamePhoneNumber(phoneNumber)) {
                throw new DuplicateEntityException("jbb.error.exception.duplicateUser");
            }
            // 验证短信码
            if (!checkMsgCode(phoneNumber, msgCode)) {
                throw new AccessDeniedException("jbb.error.exception.accessDenied.msgCodeExpire");
            }
            // 创建用户
            accountDao.insertUserBasicInfo(user);

            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction (release user lock)
            rollbackTransaction(txStatus);
        }
        logger.debug("|<createUser()");
        return user;
    }

    @Override
    public User loginByCode(String phoneNumber, String msgCode, String platform) {
        boolean isMsgCodeOk = messageCodeDao.checkMsgCode(phoneNumber, msgCode, DateUtil.getCurrentTimeStamp());
        if (!isMsgCodeOk) {
            throw new AccessDeniedException("jbb.error.exception.accessDenied.msgCodeExpire");
        }
        User user = accountDao.getUserByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new ObjectNotFoundException("jbb.error.exception.userNotFound");
        }
        insertLoginLog(user, platform);
        return user;
    }

    @Override
    public User loginByPassword(String phoneNumber, String password, String platform) {
        User user = accountDao.getUserByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new ObjectNotFoundException("jbb.error.exception.userNotFound");
        }
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            if (!PasswordUtil.verifyPasswordOld(password, user.getPassword())) {
                throw new WrongParameterValueException("jbb.error.exception.passwordError");
            }
        }
        insertLoginLog(user, platform);
        return user;
    }

    @Override
    public User logoutUser(String userKey, String objectId) {
        logger.debug(">logoutUser()");
        User user = accountDao.authenticate(userKey);
        if (user == null) {
            logger.debug("<logoutUser() not authenticated");
            return null;
        }
        accountDao.deleteUserKey(userKey);

        if (StringUtil.isEmpty(objectId)) {
            userDeviceDao.updateUserDeviceStatus(user.getUserId(), objectId, true);
        }

        logger.debug("<logoutUser()");
        return user;
    }

    @Override
    public void updateUser(User user) {
        logger.debug(">updateUser()");
        accountDao.updateUser(user);
        logger.debug("<logoutUser()");
    }

    @Override
    public void updatePassword(int userId, String password) {
        accountDao.updateUserPassword(userId, PasswordUtil.passwordHash(password));
    }

    @Override
    public void updateAvatarPic(int userId, String avatarPic) {
        accountDao.updateUserAvatar(userId, avatarPic);
    }

    @Override
    public boolean checkNickname(String nickname) {
        return accountDao.checkUserSameNickName(nickname);
    }

    @Override
    public User getUser(int userId) {
        return accountDao.getUser(userId);
    }

    @Override
    public List<User> getApplyUsers(Integer userId, Timestamp start, Timestamp end, Boolean detail) {
        List<User> users = accountDao.getApplyUsers(userId, start, end, detail);
        return users;
    }

    @Override
    public void saveUserApplyRecords(int userId, int[] targetUserIds) {
        accountDao.saveUserApplyRecords(userId, targetUserIds);
    }

    @Override
    public boolean hasPriv(int userId, int toUserId, String privName) {
        return userPrivDao.checkUserPrivByPrivName(userId, toUserId, privName);
    }

    @Override
    public List<User> getUsersByRole(Integer roleId) {
        return accountDao.getUsers(null, roleId, true);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return accountDao.getUserByPhoneNumber(phoneNumber);
    }

    @Override
    public void updateTargetUserReason(int userId, int targetUserId, LenderReason reason) {
        String reasonStr = StringUtil.prepareStringField(reason.getReason(), 50);
        String reasonDesc = StringUtil.prepareStringField(reason.getReasonDesc(), 50);
        if (reasonDesc != null && reason != null) {
            accountDao.updateTargetUserReason(userId, targetUserId, reasonStr, reasonDesc, reason.getPoint());
        }
    }

    @Override
    public boolean checkUserExist(int userId) {
        return accountDao.checkUserIdExist(userId);
    }

    @Override
    public List<UserVerifyResult> getUserVerifyResults(int userId) {
        return userVerifyDao.selectUserVerifyResult(userId);
    }

    @Override
    public boolean checkUserVerified(int userId, String verifyType, int verifyStep) {
        return userVerifyDao.checkUserVerified(userId, verifyType, verifyStep);
    }

    @Override
    public void saveUserVerifyResult(int userId, String verifyType, int verifyStep, boolean verified) {
        userVerifyDao.saveUserVerifyResult(userId, verifyType, verifyStep, verified);
    }

    @Override
    public boolean checkIdcardExist(String idcardNo, int userId) {
        return accountDao.checkIdCardExist(idcardNo, userId);
    }

    @Override
    public boolean isVerified(int userId, boolean isLender) {
        if (isLender) {
            return this.checkUserVerified(userId, Constants.VERIFY_TYPE_REALNAME, 1)
                && this.checkUserVerified(userId, Constants.VERIFY_TYPE_REALNAME, 2)
                && this.checkUserVerified(userId, Constants.VERIFY_TYPE_VIDEO, 1);
        } else {
            return this.checkUserVerified(userId, Constants.VERIFY_TYPE_REALNAME, 1)
                && this.checkUserVerified(userId, Constants.VERIFY_TYPE_REALNAME, 2)
                && this.checkUserVerified(userId, Constants.VERIFY_TYPE_VIDEO, 1)
                && this.checkUserVerified(userId, Constants.VERIFY_TYPE_MOBILE, 1);
        }

    }

    @Override
    public User createUserByMgtInfo(String phoneNumber, String sourceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("|>createUserByMgtInfo() phoneNumber=" + phoneNumber + ", sourceId=" + sourceId);
        }
        TransactionStatus txStatus = null;
        User user = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            // 创建用户
            user = new User();
            user.setSourceId(sourceId);
            user.setPhoneNumber(phoneNumber);
            user.setCreationDate(DateUtil.getCurrentTimeStamp());
            accountDao.insertUserBasicInfo(user);
            // 添加userproperty 表数据
            List<UserProperty> list = new ArrayList<UserProperty>();
            UserProperty propertysigntime
                = new UserProperty("mgt_signup_time", DateUtil.getCurrentTimeStamp().toString());// 注册时间
            UserProperty propertlogin = new UserProperty("mgt_user_login", "0");// 0表示未登录过,1表示已登陆过
            list.add(propertysigntime);
            list.add(propertlogin);
            addUserProperties(user.getUserId(), list);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction (release user lock)
            rollbackTransaction(txStatus);
        }
        logger.debug("|<createUserByMgtInfo()");
        return user;
    }

    @Override
    public IouVerify getIouVerifyByUserId(int userId) {
        logger.debug(">getIouVerifyByUserId()");
        User user = getUser(userId);
        String wechat = user.getWechat();
        String qqNumber = user.getQqNumber();
        boolean hasContact = false;
        //芝麻分
        boolean infoCheck = false;
        UserProperty scoreproperty = this.searchUserPropertiesByUserIdAndName(userId, "sesameCreditScore");
        if(!StringUtil.isEmpty(wechat)&&!StringUtil.isEmpty(qqNumber)&&scoreproperty!=null&&!scoreproperty.getValue().equals("0")){
            infoCheck = true;
        }
        if(user.isHasContacts()){
            hasContact = true;
        }
        List<UserVerifyResult> verifyResults = getUserVerifyResults(userId);
        boolean realName1 = false;
        boolean realName2 = false;
        boolean mobile = false;
        boolean video = false;


        if (verifyResults != null) {
            for (UserVerifyResult result : verifyResults) {
                if (result.getVerifyType().equals("realName")) {

                    if (result.getVerifyStep() == 1) {
                        realName1 = result.isVerified();
                    }

                    if (result.getVerifyStep() == 2) {
                        realName2 = result.isVerified();
                    }
                }

                if (result.getVerifyType().equals("video")) {
                    video = result.isVerified();
                }

                if (result.getVerifyType().equals("mobile")) {
                    mobile = result.isVerified();
                }
            }
        }

        int authCount = ordersDao.selectUserProductCount(userId, "auth");
        String authbody = PropertyManager.getProperty("jbb.wx.pay.auth.body");
        String authdetail = PropertyManager.getProperty("jbb.wx.pay.auth.detail");
        int authfee = PropertyManager.getIntProperty("jbb.wx.pay.auth.fee", 0);

        int iouCount = ordersDao.selectUserProductCount(userId, "iou");
        String ioubody = PropertyManager.getProperty("jbb.wx.pay.iou.body");
        String ioudetail = PropertyManager.getProperty("jbb.wx.pay.iou.detail");
        int ioufee = PropertyManager.getIntProperty("jbb.wx.pay.iou.fee", 0);

        IouVerify iouVerify = new IouVerify();
        iouVerify.setAuthBody(authbody);
        iouVerify.setAuthCount(authCount);
        iouVerify.setAuthDetail(authdetail);
        iouVerify.setAuthFee(authfee);
        iouVerify.setIouBody(ioubody);
        iouVerify.setIouCount(iouCount);
        iouVerify.setIouDetail(ioudetail);
        iouVerify.setIouFee(ioufee);
        iouVerify.setMobile(mobile);
        iouVerify.setVideo(video);
        iouVerify.setRealName1(realName1);
        iouVerify.setRealName2(realName2);
        iouVerify.setErrorMsg("");
        iouVerify.setInfoCheck(infoCheck);
        iouVerify.setHasContacts(hasContact);
        iouVerify.setUserName(user.getUserName());
        logger.debug("<getIouVerifyByUserId()");
        return iouVerify;
    }

}