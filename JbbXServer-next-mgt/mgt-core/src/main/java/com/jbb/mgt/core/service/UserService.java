package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserKey;
import com.jbb.mgt.core.domain.UserProperty;
import com.jbb.server.common.exception.WrongUserKeyException;

public interface UserService {

    /**
     * 新增用户
     *
     * @param user
     */
    void insertUser(User user);

    /**
     * 通过ID获取用户
     *
     * @param userId
     * @return
     */
    User selectUserById(int userId, int orgId);

    // 内部使用，action层需要传入orgId
    User selectUserById(int userId);

    /**
     * 通过jbbUserID获取用户
     *
     * @param jbbUserId
     * @param orgId
     * @return
     */
    User selectJbbUserById(int jbbUserId, int orgId);

    User selectUserByApplyId(int applyId, int orgId);

    /**
     * 按组织与手机号获取用户
     *
     * @param orgId
     * @param phoneNumber
     * @return
     */
    User selectUser(Integer orgId, String phoneNumber);

    // 更新用户
    void updateUser(User user);

    // 账户通过userKey登录
    User login(String userKey) throws WrongUserKeyException;

    UserKey selectUserKeyByUserIdAndAppId(int userId, int applicationId);

    void insertUserKey(UserKey userKey);

    void updateUserKey(UserKey userKey);

    UserKey createUserKey(int userId, int applicationId, long expiry, boolean b);

    User logoutUser(String userKey);

    /**
     * 查询用户列表
     * 
     * @param orgId
     * 
     * @param passWord 密码
     * @param phoneNumber 账户
     * @param channelCode 渠道号
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    List<User> selectUsers(int orgId, String phoneNumber, String passWord, String channelCode, Timestamp startDate,
        Timestamp endDate);

    /**
     * 按渠道获取用户明细
     * 
     * @param startDate 开始时间
     * @param startDate 结束时间
     * @param channelCode
     * @param orgId
     * @param isGetJbbChannels 是否获取帮帮导流的明细。默认为false. false为不获取， true为仅获取帮帮导流的用户明细
     * @return
     */
    List<User> selectUserDetails(String channelCode, Timestamp startDate, Timestamp endDate, Integer orgId,
        Boolean isGetJbbChannels, Integer userType);

    /**
     * 查询统计数
     *
     * @param channelCode
     * @param startDate
     * @param endDate
     * @return
     */
    int countUserByChannelCode(int orgId, String channelCode, Timestamp startDate, Timestamp endDate);

    Integer getPushCount(Integer orgId, Integer type);

    /**
     * 通过手机号获取用户列表
     * 
     * @param phoneNumber 电话号码
     * @return
     */
    List<User> selectUserByPhoneNumber(String phoneNumber);

    /**
     * 按渠道获取 借帮帮组织导流的去向(仅仅借帮帮组织人员有权调用该方法)
     *
     * @param channelCode
     * @param startDate
     * @param endDate
     * @param zhima
     * @param userType
     * @param includeWool
     * @param includeEmptyMobile
     * @return
     */
    List<User> selectUserApplyDetails(String channelCode, Timestamp startDate, Timestamp endDate, Integer zhima,
        Integer userType, Boolean includeWool, Boolean includeEmptyMobile, boolean includeHiddenChannel);

    /**
     * 按渠道获取 借帮帮组织导流的去向(渠道的去向列表)
     *
     * @param startDate 开始时间
     * @param startDate 结束时间
     * @param channelCode
     * @return
     */
    List<Organization> selectUserApplyDetailsList(String channelCode, Timestamp startDate, Timestamp endDate);

    /**
     * 按渠道获取用户明细总数
     *
     * @param startDate 开始时间
     * @param startDate 结束时间
     * @param channelCode
     * @param orgId
     * @param isGetJbbChannels 是否获取帮帮导流的明细。默认为false. false为不获取， true为仅获取帮帮导流的用户明细
     * @return
     */
    Long selectUserDetailsTotal(String channelCode, Timestamp startDate, Timestamp endDate, Integer orgId,
        Boolean isGetJbbChannels, Integer userType);

    boolean checkUserApplied(int userId, Timestamp start, Integer orgId);

    User selectXjlUserByApplyId(Integer applyId, Integer userId, int orgId);
    
    
    boolean needHiddenUserForChannel(Channel channel, String phoneNumber, String remoteAddress, List<UserProperty> userProperties);

    
    void updateUserHiddenStatus(int userId,  boolean hiddenStatus);

    boolean checkUserExistInOrg(int orgId, String phoneNumber);
}
