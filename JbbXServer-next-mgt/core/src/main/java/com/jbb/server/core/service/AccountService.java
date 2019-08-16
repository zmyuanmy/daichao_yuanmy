package com.jbb.server.core.service;

import com.jbb.server.common.exception.WrongUserKeyException;
import com.jbb.server.core.domain.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Type AccountService.java
 * @Desc
 * @author VincentTang
 * @date 2017年10月30日 下午4:36:08
 * @version
 */
public interface AccountService {
    void addUserProperties(int userId, List<UserProperty> list);

    void updateUserProperties(int userId, List<UserProperty> list);

    void deleteUserProperties(int userId, List<UserProperty> list);
    
    List<UserProperty> searchUserProperties(int userId, String name);

    UserProperty searchUserPropertiesByUserIdAndName(int userId, String name);
    
    Long getLongProperty(int userId, String name);

    User login(String userKey, int[] applicationIds) throws WrongUserKeyException;

    User loginByCode(String phoneNumber, String msgCode, String platform);

    User loginByPassword(String phoneNumber, String password, String platform);

    UserKey createUserKey(int userId, int applicationId, long expiry, boolean extend);

    User createUser(String phoneNumber, String nickName, String password, String msgCode, String sourceId, String platform);

    User logoutUser(String userKey, String objectId);
    
    User getUser(int userId);
    
    User getUserByPhoneNumber(String phoneNumber);

    void updateUser(User user);

    void updatePassword(int userId, String password);

    void updateAvatarPic(int userId, String avatarPic);

    boolean checkNickname(String nickname);
    
    boolean checkUserExist(int userId);

    User createUser(User user, String msgCode);

    List<User> getApplyUsers(Integer userId, Timestamp start, Timestamp end , Boolean detail);

    boolean checkMsgCode(String phoneNumber, String msgCode);
    
    void saveUserApplyRecords(int userId, int[] targetUserIds);
    
    boolean hasPriv(int userId, int toUserId, String privName);
    
    List<User> getUsersByRole(Integer roleId);

    User createUserFormAdmin(int adminUserId, String phoneNumber, String username, String password, Integer roleId);

    void saveUserProperty(int userId, String name, String value);
    
    List<UserVerifyResult> getUserVerifyResults(int userId);
    
    void updateTargetUserReason(int userId, int targetUserId,  LenderReason reason) ;

    boolean checkUserVerified(int userId, String verifyType, int verifyStep);

    void saveUserVerifyResult(int userId, String verifyType, int verifyStep, boolean verified);

    boolean checkIdcardExist(String idcardNo, int userId);
    
    boolean isVerified(int userId, boolean isLender);
    
    User createUserByMgtInfo(String phoneNumber,String sourceId);

    IouVerify getIouVerifyByUserId(int userId);



}

/**
 * Revision history -------------------------------------------------------------------------
 * 
 * Date Author Note ------------------------------------------------------------------------- 2017年10月30日 VincentTang
 * creat
 */