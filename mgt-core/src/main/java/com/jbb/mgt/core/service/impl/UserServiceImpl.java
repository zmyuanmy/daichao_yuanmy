package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jbb.mgt.core.dao.UserDao;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChuangLanPhoneNumber;
import com.jbb.mgt.core.domain.ChuangLanPhoneNumberRsp;
import com.jbb.mgt.core.domain.ChuangLanWoolCheck;
import com.jbb.mgt.core.domain.ChuangLanWoolCheckRsp;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserKey;
import com.jbb.mgt.core.domain.UserProperty;
import com.jbb.mgt.core.service.ChuangLanService;
import com.jbb.mgt.core.service.SystemService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ExecutionException;
import com.jbb.server.common.exception.WrongUserKeyException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@Service("UserService")
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final int ACCOUNT_KEY_SIZE = 64;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ChuangLanService chuangLanService;
    
    @Autowired
    @Qualifier("SystemService")
    private SystemService systemService;

    @Override
    public void insertUser(User user) {
        userDao.insertUser(user);

    }

    @Override
    public User selectUserById(int userId, int orgId) {
        return userDao.selectUserById(userId, orgId);
    }

    @Override
    public User selectUserById(int userId) {
        return userDao.selectUserById(userId);
    }

    @Override
    public User selectJbbUserById(int jbbUserId, int orgId) {
        return userDao.selectJbbUserById(jbbUserId, orgId);
    }

    @Override
    public List<User> selectUsers(int orgId, String phoneNumber, String passWord, String channelCode,
        Timestamp startDate, Timestamp endDate) {
        return userDao.selectUsers(orgId, channelCode, startDate, endDate);
    }

    @Override
    public int countUserByChannelCode(int orgId, String channelCode, Timestamp startDate, Timestamp endDate) {
        return userDao.countUserByChannelCode(orgId, channelCode, startDate, endDate);
    }

    @Override
    public User selectUser(Integer orgId, String phoneNumber) {
        return userDao.selectUser(orgId, phoneNumber);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User login(String userKey) throws WrongUserKeyException {
        if (userKey == null)
            throw new WrongUserKeyException();

        User user = userDao.selectUserByUserKey(userKey);

        if (user == null) {
            throw new WrongUserKeyException();
        }

        /* loginProcess(user);*/
        return user;
    }

    /* private void loginProcess(User user) {
        // 获取用户权限
        user.setPermissions(userDao.getAccountPermissions(account.getAccountId()));
    }*/

    @Override
    public UserKey createUserKey(int userId, int applicationId, long expiry, boolean extend) {
        return createUserKey(userId, applicationId, expiry, ACCOUNT_KEY_SIZE, extend);
    }

    @Override
    public UserKey selectUserKeyByUserIdAndAppId(int userId, int applicationId) {
        return userDao.selectUserKeyByUserIdAndAppId(userId, applicationId);
    }

    @Override
    public void insertUserKey(UserKey userKey) {
        userDao.insertUserKey(userKey);
    }

    @Override
    public void updateUserKey(UserKey userKey) {
        userDao.updateUserKey(userKey);
    }

    /**
     * Generate a new user key for authenticated user
     *
     * @param userId user ID
     * @param expiry key expiry period in milliseconds (-1 means it will not be expired)
     * @param keySize new key size in bytes
     * @param extend extend key expiry date, if it is less than requested date
     * @return user key
     */
    private UserKey createUserKey(int userId, int applicationId, long expiry, int keySize, boolean extend) {
        if (logger.isDebugEnabled()) {
            logger.debug("|>createUserKey() userId=" + userId + ", expiry=" + expiry + ", keySize=" + keySize
                + ", extend=" + extend);
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

            userKey = userDao.selectUserKeyByUserIdAndAppId(userId, applicationId);

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
                try {
                    if (update) {
                        userDao.updateUserKey(userKey);
                    } else if (!userDao.insertUserKey(userKey)) {
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
                    userDao.updateUserKey(userKey);
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
            throw new ExecutionException("Cannot generate user key for userId=" + userId + ", keySize=" + keySize, ex);
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
    public User logoutUser(String userKey) {
        logger.debug(">logoutUser()");
        User user = userDao.selectUserByUserKey(userKey);
        if (user == null) {
            logger.debug("<logoutUser() not authenticated");
            return null;
        }
        userDao.deleteUserKey(userKey);
        logger.debug("<logoutUser()");
        return user;
    }

    @Override
    public Integer getPushCount(Integer orgId, Integer type) {
        return userDao.getPushCount(orgId, type);
    }

    @Override
    public User selectUserByApplyId(int applyId, int orgId) {
        return userDao.selectUserByApplyId(applyId, orgId);
    }

    @Override
    public List<User> selectUserDetails(String channelCode, Timestamp startDate, Timestamp endDate, Integer orgId,
        Boolean isGetJbbChannels, Integer userType) {
        return userDao.selectUserDetails(channelCode, startDate, endDate, orgId, isGetJbbChannels, userType);
    }

    @Override
    public List<User> selectUserByPhoneNumber(String phoneNumber) {
        return userDao.selectUserByPhoneNumber(phoneNumber);
    }

    @Override
    public List<User> selectUserApplyDetails(String channelCode, Timestamp startDate, Timestamp endDate, Integer zhima,
        Integer userType, Boolean includeWool, Boolean includeEmptyMobile, boolean includeHiddenChannel) {
        return userDao.selectUserApplyDetails(channelCode, startDate, endDate, zhima, userType, includeWool,
            includeEmptyMobile, includeHiddenChannel);
    }

    @Override
    public List<Organization> selectUserApplyDetailsList(String channelCode, Timestamp startDate, Timestamp endDate) {
        return userDao.selectUserApplyDetailsList(channelCode, startDate, endDate);
    }

    @Override
    public Long selectUserDetailsTotal(String channelCode, Timestamp startDate, Timestamp endDate, Integer orgId,
        Boolean isGetJbbChannels, Integer userType) {
        return userDao.selectUserDetailsTotal(channelCode, startDate, endDate, orgId, isGetJbbChannels, userType);
    }

    @Override
    public boolean checkUserApplied(int userId, Timestamp start, Integer orgId) {
        return userDao.checkUserApplied(userId, start, orgId);
    }

    @Override
    public User selectXjlUserByApplyId(Integer applyId, Integer userId, int orgId) {

        return userDao.selectXjlUserByApplyId(applyId, userId, orgId);
    }

    @Override
    public boolean needHiddenUserForChannel(Channel channel, String phoneNumber, String remoteAddress,
        List<UserProperty> userProperties) {

        boolean mobileCheckEnabled = PropertyManager.getBooleanProperty("jbb.user.mobileCheck.enable", false);
        boolean woolCheckEnabled = PropertyManager.getBooleanProperty("jbb.user.woolCheck.enable", false);

        if (channel.isJbbChannel() && mobileCheckEnabled && channel.isCheckMobile()) {
            // 空号检测
            ChuangLanPhoneNumberRsp mobileCheckRsp = chuangLanService.validateMobile(phoneNumber, true);
            if (mobileCheckRsp != null && mobileCheckRsp.getData() != null) {
                ChuangLanPhoneNumber data = mobileCheckRsp.getData();
                if (!Constants.STATUS_REAL_PHONE_NUMBER.equals(data.getStatus())) {
                    userProperties.add(new UserProperty(null, "mobileCheckResult", data.getStatus()));

                    return true;
                }
            }
        }
        if (channel.isJbbChannel() && woolCheckEnabled && channel.isCheckWool()) {
            // 羊毛检测
            ChuangLanWoolCheckRsp woolCheckRsp = chuangLanService.woolCheck(phoneNumber, remoteAddress);
            if (woolCheckRsp != null && woolCheckRsp.getData() != null) {
                ChuangLanWoolCheck data = woolCheckRsp.getData();
                if (Constants.STATUS_WOOL_CHECK_B1.equals(data.getStatus())
                    || Constants.STATUS_WOOL_CHECK_B2.equals(data.getStatus())) {
                    userProperties.add(new UserProperty(null, "woolcheckResult", data.getStatus()));
                    return true;
                }
            }
        }
        if (deductionUser(channel)) {
            // 是否扣量
            userProperties
                .add(new UserProperty(null, "decrementResult", Constants.USER_PROPERTY_DECREMENT_RESULT_VALUE));
            return true;
        }
        return false;
    }
//    
//    private boolean deductionUser(String channelCode) {
//        String name = "sys.policy.dataflow.channel." + channelCode;
//        String flow = PropertyManager.getProperty(name);
//        boolean hideFlag = false;
//        if (!StringUtil.isEmpty(flow)) {
//            try {
//                DateFlowChannelSetting setting = StringMapper.readDateFlowChannelSetting(flow);
//                double p = Math.random();
//                if (setting != null && setting.isEnabled() && p < ((double)setting.getPercent() / 100)) {
//                    hideFlag = true;
//                }
//            } catch (Exception e) {
//                logger.warn("StringMapper.readDateFlowChannelSetting with error: " + e.getMessage());
//            }
//        }
//        return hideFlag;
//    }
//    

    private boolean deductionUser(Channel channel) {
     
        boolean hideFlag = false;
        if (channel != null) {
            try {
                double p = Math.random();
                if (channel.isReduceEnable() && p < ((double)channel.getReducePercent() / 100)) {
                    hideFlag = true;
                }
            } catch (Exception e) {
                logger.warn("StringMapper.readDateFlowChannelSetting with error: " + e.getMessage());
            }
        }
        return hideFlag;
    }

    @Override
    public void updateUserHiddenStatus(int userId, boolean hiddenStatus) {
       userDao.updateUserHiddenStatus(userId, hiddenStatus);
    }
    
    @Override
    public boolean checkUserExistInOrg(int orgId, String phoneNumber) {
       return userDao.checkUserExistInOrg(orgId, phoneNumber, null, null);
    }

    
}
