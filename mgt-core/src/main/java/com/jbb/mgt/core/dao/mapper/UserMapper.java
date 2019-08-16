package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.OrgAppliedCount;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserKey;

/**
 * 
 * @author hsy
 * 
 *
 */
public interface UserMapper {

    void insertUser(User user);

    User selectUserById(@Param(value = "userId") int userId, @Param(value = "orgId") Integer orgId);

    User selectUserByJbbUserId(@Param(value = "jbbUserId") int userId, @Param(value = "orgId") int orgId);

    User selectUserByApplyId(@Param(value = "applyId") int applyId, @Param(value = "orgId") int orgId);

    User selectUser(@Param("orgId") Integer orgId, @Param("phoneNumber") String phoneNumber);

    void updateUser(User user);

    /**
     * 获取渠道数据列表
     * 
     * @param orgId
     * 
     * @param channelCode 渠道编号
     * @param startDate 开始时间
     * @param startDate 结束时间
     * @return
     */
    List<User> selectUsers(@Param("orgId") int orgId, @Param("channelCode") String channelCode,
        @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

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
    List<User> selectUserDetails(@Param("channelCode") String channelCode, @Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate, @Param("orgId") Integer orgId,
        @Param("isGetJbbChannels") Boolean isGetJbbChannels, @Param("userType") Integer userType);

    /**
     * 查询统计数
     *
     * @param channelCode
     * @param startDate
     * @param endDate
     * @return
     */
    int countUserByChannelCode(@Param("orgId") int orgId, @Param("channelCode") String channelCode,
        @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    /**
     * 新增用户key
     * 
     * @param userKey 用户key对象
     * @return
     */
    boolean insertUserKey(UserKey userKey);

    /**
     * 删除所有符合的用户key
     * 
     * @param userId 用户id
     * @return
     */
    void deleteUserKeyByUserId(@Param(value = "userId") int userId);

    /**
     * 删除所有符合的用户key
     * 
     * @param userKey 用户key
     * @return
     */
    int deleteUserKey(@Param(value = "userKey") String userKey);

    /**
     * 查询用户key信息
     * 
     * @param userId 用户id
     * @param applicationId 地址id
     * @return
     */
    UserKey selectUserKeyByUserIdAndAppId(@Param(value = "userId") int userId,
        @Param(value = "applicationId") int applicationId);

    /**
     * 查询 user信息
     * 
     * @param userKey 用户key
     * @return
     */
    User selectUserByUserKey(@Param(value = "userKey") String userKey, @Param("expiry") Timestamp expiry);

    /**
     * 修改用户key
     * 
     * @param userKey 用户key对象
     * @return
     */
    void updateUserKey(UserKey userKey);

    Integer getPushCount(@Param(value = "orgId") Integer orgId, @Param(value = "type") Integer type);

    /**
     * 通过手机号获取用户列表
     * 
     * @param phoneNumber 电话号码
     * @return
     */
    List<User> selectUserByPhoneNumber(String phoneNumber);

    /**
     * 检查用户是否
     * 
     * @param userId
     * @param start
     * @return
     */
    Integer checkUserApplied(@Param(value = "userId") int userId, @Param(value = "start") Timestamp start,
        @Param(value = "orgId") Integer orgId);

    Integer checkUserExistInOrg(@Param(value = "orgId") int orgId, @Param(value = "phoneNumber") String phoneNumber,
        @Param(value = "jbbUserId") Integer jbbUserId, @Param(value = "idCard") String idCard);

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
    List<User> selectUserApplyDetails(@Param(value = "channelCode") String channelCode,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate,
        @Param(value = "zhima") Integer zhima, @Param(value = "userType") Integer userType,
        @Param(value = "woolcheckResult") Boolean includeWool,
        @Param(value = "mobileCheckResult") Boolean includeEmptyMobile,
        @Param(value = "includeHiddenChannel") boolean includeHiddenChannel);

    /**
     * 按渠道获取 借帮帮组织导流的去向(渠道的去向列表)
     *
     * @param startDate 开始时间
     * @param startDate 结束时间
     * @param channelCode
     * @return
     */
    List<Organization> selectUserApplyDetailsList(@Param(value = "channelCode") String channelCode,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate);

    /**
     * 计数 - 按时间统计某个组织的用户申请数
     * 
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int countAppliedUser(@Param(value = "orgId") Integer orgId, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate, @Param(value = "userType") Integer userType);

    List<OrgAppliedCount> countOrgAppliedUsers(@Param(value = "orgId") Integer orgId,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate,
        @Param(value = "userType") Integer userType);

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
    Long countUserApplyRecords(@Param("channelCode") String channelCode, @Param("startDate") Timestamp startDate,
        @Param("endDate") Timestamp endDate, @Param("orgId") Integer orgId,
        @Param("isGetJbbChannels") Boolean isGetJbbChannels, @Param("userType") Integer userType);

    User selectXjlUserByApplyId(@Param("applyId") Integer applyId, @Param("userId") Integer userId,
        @Param("orgId") int orgId);
    
    void updateUserHiddenStatus(@Param("userId") int userId, @Param("hiddenStatus") boolean hiddenStatus);

    // 根据MD5手机号检查用户
    int checkPhoneNumberMd5ExistUser(@Param("phoneNumberMd5")String phoneNumberMd5);
}
