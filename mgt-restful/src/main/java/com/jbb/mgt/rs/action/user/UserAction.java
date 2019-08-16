package com.jbb.mgt.rs.action.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbb.mgt.core.domain.AccountOpLog;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.IPAddressInfo;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserAddresses;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.domain.UserJob;
import com.jbb.mgt.core.domain.UserKey;
import com.jbb.mgt.core.domain.mapper.Mapper;
import com.jbb.mgt.core.domain.mq.RegisterEvent;
import com.jbb.mgt.core.service.AccountOpLogService;
import com.jbb.mgt.core.service.AliyunService;
import com.jbb.mgt.core.service.AreaZonesService;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.core.service.EntryService;
import com.jbb.mgt.core.service.RecommandService;
import com.jbb.mgt.core.service.SendMsgToOpService;
import com.jbb.mgt.core.service.UserAddressService;
import com.jbb.mgt.core.service.UserApplyRecordService;
import com.jbb.mgt.core.service.UserJobService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.server.core.util.PasswordUtil;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.AccessDeniedException;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.DesensitizedUtil;
import com.jbb.server.common.util.DigitUtil;
import com.jbb.server.common.util.IDCardUtil;
import com.jbb.server.common.util.PhoneNumberUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.mq.MqClient;
import com.jbb.server.mq.Queues;
import com.jbb.server.shared.rs.Util;

/**
 * 借款用户Action
 * <p>
 * author 姜刘鸿
 *
 * @date 2018/4/26
 */
@Service(UserAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserAction extends BasicAction {

    public static final String ACTION_NAME = "UserAction";

    private static Logger logger = LoggerFactory.getLogger(UserAction.class);

    private Response response;

    @Autowired
    @Qualifier("UserService")
    private UserService userService;
    @Autowired
    @Qualifier("ChannelService")
    private ChannelService channelService;
    @Autowired
    @Qualifier("UserApplyRecordService")
    private UserApplyRecordService userApplyRecordService;
    @Autowired
    @Qualifier("AreaZonesService")
    private AreaZonesService areaZonesService;
    @Autowired
    @Qualifier("AccountOpLogService")
    private AccountOpLogService AccountOpLogService;

    @Autowired
    private AliyunService aliyunService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private RecommandService recommandService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private UserJobService userJobService;
    @Autowired
    private SendMsgToOpService sendMsgToOpService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void createUser(String channelCode, Integer userType, Integer step, Boolean test, Boolean getRecommand,
        String password, Request request) {
        logger.debug(">createUser", request);

        if (!StringUtil.isEmpty(password) && !PasswordUtil.isValidUserPassword(password)) {
            throw new WrongParameterValueException("jbb.error.exception.passwordCheckError");
        }

        Channel channel = channelService.getChannelByCode(channelCode);
        if (channel.getStatus() == 1 || channel.getStatus() == 2) {
            throw new MissingParameterException("jbb.error.exception.channelFrozenOrDelete");
        }
        User user = generateUserForEdit(this.user, channel, request);
        user.setPassword(PasswordUtil.passwordHash(password));

        IPAddressInfo ipAddressInfo = aliyunService.getIPAddressInfo(this.getRemoteAddress());
        if (ipAddressInfo != null) {
            user.setIpArea(ipAddressInfo.getIpArea());
        }

        userService.updateUser(user);
        // 如果小金条地址为空就添加h5
        UserJob userJob = request.userJob == null ? request.jobInfo : request.userJob;
        insertXjlUserInfo(request.liveAddress, userJob);

        entryService.check(user.getUserId());
        boolean testF = (test != null && test == true);

        // 如果在忽略列表里，则为测试账户，不推荐
        String[] ignoreRegisterMobiles = PropertyManager.getProperties("jbb.user.register.check.ignore");
        if (CommonUtil.inArray(user.getPhoneNumber(), ignoreRegisterMobiles)) {
            testF = true;
        }
        // end

        if (testF) {
            // 测试账户不推荐
            return;
        }

        int userTypeV = (userType == null ? 1 : userType);

        boolean flagGetRecommand = (getRecommand != null && getRecommand == true);
        if (channel.isJbbChannel()) {
            if (channel.getMsgTimes() > 0) {
                // String string = channel.getChannelName() + channelCode;
                // sendMsgToOpService.sendMsgToOp(string, channel.getAccount().getPhoneNumber());
                int times = channel.getMsgTimes() - 1;
                channelService.updateMsgTimes(channelCode, times);
            }
            if (!flagGetRecommand) {
                // 不推荐
                return;
            }
            // 其他步骤，马上处理
            // 1. 移除延时提交任务
//            RegisterEvent event = new RegisterEvent(1, user.getUserId(), channelCode, userType);
//            logger.debug("get recommand orgs, and remove event = " + event);
//            MqClient.send(Queues.JBB_MGT_USER_REGISTER_REMOVE_QUEUE_ADDR, Mapper.toBytes(event), 0,
//                MqClient.HIGH_PRIORITY);

            // 2. 获取推荐的组织
//            boolean entryStatus = userTypeV == 1 ? true : false;
//            List<Organization> orgs = recommandService.getRecommandOrgs(user, channel, userTypeV, entryStatus);
//            logger.debug("get recommand orgs, orgs size = " + (orgs != null ? orgs.size() : 0));
//            if (!CommonUtil.isNullOrEmpty(orgs)) {
//                this.response.recommandOrgs = new ArrayList<Organization>(orgs.size());
//                int[] orgIds = new int[orgs.size()];
//                int i = 0;
//                // 3. 返回 推荐的组织
//                for (Organization org : orgs) {
//                    logger.debug(" org id =  " + org.getOrgId() + " , org name =  " + org.getName());
//                    orgIds[i++] = org.getOrgId();
//                    this.response.recommandOrgs.add(new Organization(org.getOrgId(), org.getName(),
//                        org.getCompanyName(), org.getRecommandUserType()));
//                }
//                // 4. 提交， 目前是后台提交，后续做接口，前端调用过来提交
//                // 此片不使用userTypeV，而是使用user.getUserType()， 获取推荐组织内部分调整用户来源，将进件的推荐给注册的用户。
//                // recommandService.applyToOrgs(user, orgs);
//
//                // 4.1 0831 10 分钟后，如果用户没有提交，推荐给创世
//                RegisterEvent event2 = new RegisterEvent(1, user.getUserId(), channel.getChannelCode(), 2, 600);
//                MqClient.send(Queues.JBB_MGT_USER_REGISTER_QUEUE_ADDR, Mapper.toBytes(event2), 0);
//
//            }

        }

        logger.debug("<createUser");
    }

    private void insertXjlUserInfo(UserAddresses liveAddress, UserJob jobInfo) {
        int userId = this.user.getUserId();
        if (null != liveAddress) {
            liveAddress.setUserId(userId);
            if (liveAddress.getAddressId() == 0) {
                userAddressService.insertUserAddresses(liveAddress);
            } else {
                userAddressService.updateUserAddresses(liveAddress);
            }

        }
        if (null != jobInfo) {
            jobInfo.setUserId(userId);
            userJobService.saveUserJobInfo(jobInfo);
            if (null != jobInfo.getJobAddress()) {
                UserAddresses addresses = jobInfo.getJobAddress();
                addresses.setUserId(userId);
                if (addresses.getAddressId() == 0) {
                    userAddressService.insertUserAddresses(addresses);
                } else {
                    userAddressService.updateUserAddresses(addresses);
                }
                jobInfo.setAddressId(addresses.getAddressId());
                userJobService.saveUserJobInfo(jobInfo);
            }
        }
    }

    public void applyToOrgs(List<Organization> orgs) {
//        // 1 移除延时推荐
//        RegisterEvent event = new RegisterEvent(1, user.getUserId(), null, 0);
//        MqClient.send(Queues.JBB_MGT_USER_REGISTER_REMOVE_QUEUE_ADDR, Mapper.toBytes(event), 0, MqClient.HIGH_PRIORITY);

        // 推荐给组织
        logger.debug(">applyToOrgs: orgIds =" + orgs);
        if (CommonUtil.isNullOrEmpty(orgs)) {
            return;
        }
        recommandService.applyToOrgs(user, orgs);
        logger.debug("<applyToOrgs");
    }

    /**
     * 获取渠道列表
     * 
     * @param sourcePhoneNumber 账户
     * @param sourcePassword 密码
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    public void getUserList(int pageNo, int pageSize, String sourcePassword, String sourcePhoneNumber, String startDate,
        String endDate) {
        logger.debug(">getUserList(),sourcePhoneNumber=" + sourcePhoneNumber + ",sourcePassword=" + sourcePassword
            + ",startDate=" + startDate + ",endDate=" + endDate);
        Channel channel = channelService.checkPhoneNumberAndPassword(sourcePhoneNumber, sourcePassword);

        Timestamp tsStartDate = StringUtil.isEmpty(startDate) ? null : Util.parseTimestamp(startDate, getTimezone());
        Timestamp tsEndDate = StringUtil.isEmpty(endDate) ? null : Util.parseTimestamp(endDate, getTimezone());

        PageHelper.startPage(pageNo, pageSize);
        List<User> list = userService.selectUsers(channel.getAccount().getOrgId(), sourcePhoneNumber, sourcePassword,
            channel.getChannelCode(), tsStartDate, tsEndDate);

        PageInfo<User> pageInfo = new PageInfo<User>(list);
        this.response.pageInfo = pageInfo;

        PageHelper.clearPage();
        logger.debug("<getUserList()");
    }

    // 获取渠道用户明细
    public void getUserDetails(int pageNo, int pageSize, String channelCode, String startDate, String endDate,
        Boolean isGetJbbChannels, Integer userType) {
        logger.debug(">getUserDetails(),pageNo=" + pageNo + ",pageSize=" + pageSize + ",startDate=" + startDate
            + ",endDate=" + endDate);
        Boolean GetJbbChannels = isGetJbbChannels == null ? false : isGetJbbChannels;

        Timestamp tsStartDate = StringUtil.isEmpty(startDate) ? null : Util.parseTimestamp(startDate, getTimezone());
        Timestamp tsEndDate = StringUtil.isEmpty(endDate) ? null : Util.parseTimestamp(endDate, getTimezone());

        PageHelper.startPage(pageNo, pageSize);
        Integer orgId = this.account.getOrgId();
        List<User> list
            = userService.selectUserDetails(channelCode, tsStartDate, tsEndDate, orgId, GetJbbChannels, userType);

        PageInfo<User> pageInfo = new PageInfo<User>(list);
        this.response.pageInfo = pageInfo;

        PageHelper.clearPage();
        logger.debug("<getUserDetails(),pageNo=" + pageNo + ",pageSize=" + pageSize + ",startDate=" + startDate
            + ",endDate=" + endDate);

    }

    private User generateUserForEdit(User user, Channel channel, Request request) {

        if (channel.isIdcardInfoRequired() && StringUtil.isEmpty(request.username)) {
            throw new MissingParameterException("jbb.mgt.exception.username.empty");
        }
        /* if (StringUtil.isEmpty(request.username)) {
             throw new MissingParameterException("jbb.error.exception.wrong.paramvalue", "zh", "username");
         } else if (!ChineseNameUtil.checkChinese(request.username)) {
             throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "username");
         }
        }*/

        if (channel.isIdcardInfoRequired()) {
            if (StringUtil.isEmpty(request.idcard) || !IDCardUtil.validate(request.idcard)) {
                throw new MissingParameterException("jbb.mgt.exception.idcard.error");
            }

        } else if (!StringUtil.isEmpty(request.idcard) && !IDCardUtil.validate(request.idcard)) {
            throw new WrongParameterValueException("jbb.mgt.exception.idcard.error");
        }

        if (!StringUtil.isEmpty(request.username)) {
            user.setUserName(request.username);
        }
        if (!StringUtil.isEmpty(request.idcard)) {
            user.setIdCard(request.idcard);
        }

        if (channel.isQqRequired() && StringUtil.isEmpty(request.qq)) {
            throw new MissingParameterException("jbb.mgt.exception.qq.empty");
        }

        if (!StringUtil.isEmpty(request.qq)) {
            user.setQq(request.qq);
        }

        if (channel.isWechatRequired() && StringUtil.isEmpty(request.wechat)) {
            throw new MissingParameterException("jbb.mgt.exception.wechat.empty");
        }

        if (!StringUtil.isEmpty(request.wechat)) {
            user.setWechat(request.wechat);
        }

        if (channel.isZhimaRequired()) {
            if (!DigitUtil.checkDigit(request.zhimaScore)) {
                throw new WrongParameterValueException("jbb.mgt.exception.zhima.error");
            }
        }

        if (request.zhimaScore > 0) {
            user.setZhimaScore(request.zhimaScore);
        }

        if (channel.isIdcardRearRequired()) {
            if (StringUtil.isEmpty(request.idcardRear)) {
                throw new MissingParameterException("jbb.mgt.exception.idcardRear.empty");
            }
        }

        if (!StringUtil.isEmpty(request.idcardRear)) {
            user.setIdcardRear(request.idcardRear);
        }

        if (channel.isIdcardBackRequired()) {
            if (StringUtil.isEmpty(request.idcardBack)) {
                throw new MissingParameterException("jbb.mgt.exception.idcardBack.empty");
            }
        }

        if (!StringUtil.isEmpty(request.idcardBack)) {
            user.setIdcardBack(request.idcardBack);
        }

        if (channel.isHeaderRequired()) {
            if (StringUtil.isEmpty(request.idcardInfo)) {
                throw new MissingParameterException("jbb.mgt.exception.idcardInfo.empty");
            }
        }

        if (!StringUtil.isEmpty(request.idcardInfo)) {
            user.setIdcardInfo(request.idcardInfo);
        }

        if (channel.isMobileContract1Required()) {
            if (StringUtil.isEmpty(request.contract1Username)
                || !PhoneNumberUtil.isValidPhoneNumber(request.contract1Phonenumber)) {
                throw new MissingParameterException("jbb.mgt.exception.usernameorphone1.empty");
            }
            // 判断紧急联系人1名字是否为中文
            /* if (!ChineseNameUtil.checkChinese(request.contract1Username)) {
                throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh",
                    "contract1Username");
            }*/
            // 判断紧急联系人1手机号是否合法
        } else if (!StringUtil.isEmpty(request.contract1Phonenumber)
            && !PhoneNumberUtil.isValidPhoneNumber(request.contract1Phonenumber)) {
            throw new WrongParameterValueException("jbb.mgt.exception.contract1Phonenumber.error");
        }

        if (!StringUtil.isEmpty(request.contract1Relation)) {
            user.setContract1Relation(request.contract1Relation);
        }
        if (!StringUtil.isEmpty(request.contract1Username)) {
            user.setContract1Username(request.contract1Username);
        }
        if (!StringUtil.isEmpty(request.contract1Phonenumber)) {
            user.setContract1Phonenumber(request.contract1Phonenumber);
        }

        if (channel.isMobileContract2Required()) {
            if (StringUtil.isEmpty(request.contract2Username)
                || !PhoneNumberUtil.isValidPhoneNumber(request.contract2Phonenumber)
                || request.contract2Phonenumber.equals(request.contract1Phonenumber)) {
                throw new MissingParameterException("jbb.mgt.exception.usernameorphone2.empty");
            }
            // 判断紧急联系人2的名字是否为中文 是否跟联系人1相同
            /*   if (!ChineseNameUtil.checkChinese(request.contract2Username)) {
                throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh",
                    "contract2Username");
            } else if (request.contract2Username.equals(request.contract1Username)) {
                throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh",
                    "contractUsername");
            }*/
            // 判断紧急联系人2的手机号是否合法 是否跟联系人1相同
        } else if (!StringUtil.isEmpty(request.contract2Phonenumber)
            && !PhoneNumberUtil.isValidPhoneNumber(request.contract2Phonenumber)) {
            throw new WrongParameterValueException("jbb.mgt.exception.contract2Phonenumber.error");
        } else if (!StringUtil.isEmpty(request.contract2Phonenumber)
            && request.contract2Phonenumber.equals(request.contract1Phonenumber)) {
            throw new WrongParameterValueException("jbb.mgt.exception.Phonenumber.same");
        }

        // 公司信息
        if (channel.isCompanyRequired()) {
            if (null == request.userJob || StringUtil.isEmpty(request.userJob.getCompany())
                || request.userJob.getJobAddress() == null
                || StringUtil.isEmpty(request.userJob.getJobAddress().getAddress())) {
                throw new MissingParameterException("jbb.error.exception.CompanyNotFound");
            }
        }

        if (!StringUtil.isEmpty(request.contract2Relation)) {
            user.setContract2Relation(request.contract2Relation);
        }
        if (!StringUtil.isEmpty(request.contract2Username)) {
            user.setContract2Username(request.contract2Username);
        }
        if (!StringUtil.isEmpty(request.contract2Phonenumber)) {
            user.setContract2Phonenumber(request.contract2Phonenumber);
        }
        if (null != request.zhimaMin) {
            user.setZhimaMin(request.zhimaMin);
        }
        if (null != request.zhimaMax) {
            user.setZhimaMax(request.zhimaMax);
        }

        if (!StringUtil.isEmpty(request.contract1XjlRelation)) {
            user.setContract1XjlRelation(request.contract1XjlRelation);
        }
        if (!StringUtil.isEmpty(request.contract1XjlUsername)) {
            user.setContract1XjlUsername(request.contract1XjlUsername);
        }
        if (!StringUtil.isEmpty(request.contract1XjlPhonenumber)) {
            if (!PhoneNumberUtil.isValidPhoneNumber(request.contract1XjlPhonenumber)) {
                throw new WrongParameterValueException("jbb.mgt.exception.contract1Phonenumber.error");
            }
            user.setContract1XjlPhonenumber(request.contract1XjlPhonenumber);
        }

        if (!StringUtil.isEmpty(request.contract2XjlRelation)) {
            user.setContract2XjlRelation(request.contract2XjlRelation);
        }
        if (!StringUtil.isEmpty(request.contract2XjlUsername)) {
            if (request.contract2XjlUsername.equals(request.contract1XjlUsername)) {
                throw new WrongParameterValueException("jbb.mgt.exception.contractUsername.same");
            }
            user.setContract2XjlUsername(request.contract2XjlUsername);
        }
        if (!StringUtil.isEmpty(request.contract2XjlPhonenumber)) {
            if (!PhoneNumberUtil.isValidPhoneNumber(request.contract2XjlPhonenumber)) {
                throw new WrongParameterValueException("jbb.mgt.exception.contract2Phonenumber.error");
            }
            if (request.contract2XjlPhonenumber.equals(request.contract1XjlPhonenumber)) {
                throw new WrongParameterValueException("jbb.mgt.exception.Phonenumber.same");
            }
            user.setContract2XjlPhonenumber(request.contract2XjlPhonenumber);
        }
        if (null != request.maritalStatus) {
            user.setMaritalStatus(request.maritalStatus);
        }
        if (!StringUtil.isEmpty(request.education)) {
            user.setEducation(request.education);
        }
        if (!StringUtil.isEmpty(request.avatarPic)) {
            user.setAvatarPic(request.avatarPic);
        }
        if (!StringUtil.isEmpty(request.occupation)) {
            user.setOccupation(request.occupation);
        }
        user.setXjlBasicInfoVerified(false);
        if (!StringUtil.isEmpty(user.getEducation()) && null != user.getMaritalStatus() && null != request.liveAddress
            && !StringUtil.isEmpty(request.liveAddress.getAddress()) && null != request.jobInfo
            && !StringUtil.isEmpty(request.jobInfo.getCompany()) && !StringUtil.isEmpty(request.jobInfo.getOccupation())
            && null != request.jobInfo.getStartDate() && null != request.jobInfo.getJobAddress()
            && !StringUtil.isEmpty(request.jobInfo.getJobAddress().getAddress())
            && !StringUtil.isEmpty(user.getContract1XjlRelation())
            && !StringUtil.isEmpty(user.getContract1XjlUsername())
            && !StringUtil.isEmpty(user.getContract1XjlPhonenumber())
            && !StringUtil.isEmpty(user.getContract2XjlRelation())
            && !StringUtil.isEmpty(user.getContract2XjlUsername())
            && !StringUtil.isEmpty(user.getContract2XjlPhonenumber())) {
            user.setXjlBasicInfoVerified(true);
        }

        return user;
    }

    /**
     * 返回用户补充资料链接
     *
     * @param applyId
     */
    public void getUserAddDataH5Url(Integer applyId, Boolean refresh) {
        boolean refreshFlag = (refresh != null && refresh == true);
        if (null == applyId || applyId == 0) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "applyId");
        }
        UserApplyRecord userApplyRecord = userApplyRecordService.selectUserApplyRecordInfoByApplyId(applyId);
        if (null == userApplyRecord) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.wrong.paramvalue", "zh", "applyId");
        }

        UserKey userKey
            = userService.selectUserKeyByUserIdAndAppId(userApplyRecord.getUserId(), User.ADDINFO_APPLICATION_ID);

        boolean isNullUserKey = (userKey == null);
        long currentTime = DateUtil.getCurrentTime();
        if (userKey == null
            || (userKey != null && (userKey.getExpiry().getTime() < currentTime) || userKey.isDeleted())) {
            // 如果userKey为null 或者 userKey时间失效或者被删除 强制刷新url
            refreshFlag = true;
        }

        if (refreshFlag) {
            userKey = new UserKey();
            userKey.setUserKey(StringUtil.secureRandom(128).substring(0, 64));
            userKey.setExpiry(new Timestamp(DateUtil.getCurrentTime() + DateUtil.DAY_MILLSECONDES));
            userKey.setApplicationId(User.ADDINFO_APPLICATION_ID);
            userKey.setDeleted(false);
            userKey.setUserId(userApplyRecord.getUserId());
            if (!isNullUserKey) {
                userService.updateUserKey(userKey);
            } else {
                userService.insertUserKey(userKey);
            }
        }

        this.response.h5Url = PropertyManager.getProperty("jbb.mgt.org.h5.server.address") + "?s=cl"
            + userApplyRecord.getOrgId() + "&key=" + userKey.getUserKey();
        this.response.urlExpireMs = userKey.getExpiry().getTime();
    }

    /**
     * 查询操作日志
     *
     * @param applyId
     */
    public void getOplogs(Integer applyId) {
        if (null == applyId || applyId == 0) {
            throw new WrongParameterValueException("jbb.mgt.error.exception.missing.param", "zh", "applyId");
        }
        List<AccountOpLog> accountOpLogs = AccountOpLogService.selectAccountOpLogByApplyId(applyId);
        this.response.opLogs = new ArrayList<>();
        this.response.opLogs.addAll(accountOpLogs);

    }

    /**
     * 按渠道获取 借帮帮组织导流的去向(仅仅借帮帮组织人员有权调用该方法)
     *
     * @param startDate 开始时间
     * @param startDate 结束时间
     * @param channelCode
     * @return
     */
    public void selectUserApplyDetails(int pageNo, int pageSize, String channelCode, String startDate, String endDate,
        Integer zhima, Integer userType, Boolean includeWool, Boolean includeEmptyMobile,
        Boolean includeHiddenChannel) {
        logger.debug(
            ">selectUserApplyDetails(),channelCode=" + channelCode + ",startDate=" + startDate + ",endDate=" + endDate);
        if (this.account.getOrgId() != 1) {
            throw new AccessDeniedException("jbb.error.validateAdminAccess.accessDenied");
        }
        if (null != userType) {
            userType = (userType == 1 || userType == 2 || userType == 3) ? userType : null;
        }
        Timestamp startTs = Util.parseTimestamp(startDate, this.getTimezone());
        Timestamp endTs = Util.parseTimestamp(endDate, this.getTimezone());
        boolean includeHiddenChannelF = (includeHiddenChannel != null && includeHiddenChannel == true);
        pageSize = pageSize != 0 ? pageSize : 10;
        PageHelper.startPage(pageNo, pageSize);
        List<User> list = userService.selectUserApplyDetails(channelCode, startTs, endTs, zhima, userType, includeWool,
            includeEmptyMobile, includeHiddenChannelF);
        for (int i = 0; i < list.size(); i++) {
            User ru = list.get(i);
            if (!(((this.isOrgAdmin() || this.isOpOrgAdmin()) && this.account.getOrgId() == Constants.JBB_ORG)
                || this.isSysAdmin() || this.isOpSysAdmin())) {
                if (!StringUtil.isEmpty(ru.getUserName())) {
                    ru.setUserName(DesensitizedUtil.chineseName(ru.getUserName()));
                }
                if (!StringUtil.isEmpty(ru.getQq())) {
                    ru.setQq("*");
                }
                if (!StringUtil.isEmpty(ru.getWechat())) {
                    ru.setWechat("*");
                }
                if (!StringUtil.isEmpty(ru.getPhoneNumber())) {
                    ru.setPhoneNumber(DesensitizedUtil.mobilePhone(ru.getPhoneNumber()));
                }
            }
        }
        PageInfo<User> pageInfo = new PageInfo<>(list);
        this.response.pageInfo = pageInfo;
        PageHelper.clearPage();
        logger.debug("<selectUserApplyDetails()");
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String username;
        public String idcard;
        public int zhimaScore;
        public String qq;
        public String wechat;
        public String idcardRear;// 身份证正面
        public String idcardBack;// 身份证反面
        public String idcardInfo;// 手持身份证
        public String contract1Relation;// 联系人1关系
        public String contract1Username;// 联系人1名字
        public String contract1Phonenumber;// 联系人1联系方式
        public String contract2Relation;// 联系人2关系
        public String contract2Username;// 联系人2名字
        public String contract2Phonenumber;
        public Integer zhimaMin;
        public Integer zhimaMax;
        public Integer maritalStatus;// 婚姻状况
        public String education;// 教育
        public String contract1XjlRelation;// 联系人1关系
        public String contract1XjlUsername;// 联系人1名字
        public String contract1XjlPhonenumber;// 联系人1联系方式
        public String contract2XjlRelation;// 联系人2关系
        public String contract2XjlUsername;// 联系人2名字
        public String contract2XjlPhonenumber;// 联系人2联系方式
        public UserAddresses liveAddress;// 地址
        public UserJob jobInfo;// 小金条工作信息
        public UserJob userJob;// h5单位
        public String avatarPic;//头像
        public String occupation;//职业
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ApplyToOrgsRequest {
        public List<Organization> orgs;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public PageInfo<User> pageInfo;

        private Integer totalCnt;

        private Integer todayCnt;

        private String h5Url;
        private Long urlExpireMs;

        private List<AccountOpLog> opLogs;

        private List<Organization> recommandOrgs;

        public List<AccountOpLog> getOpLogs() {
            return opLogs;
        }

        public String getH5Url() {
            return h5Url;
        }

        public Integer getTotalCnt() {
            return totalCnt;
        }

        public Integer getTodayCnt() {
            return todayCnt;
        }

        public PageInfo<User> getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfo<User> pageInfo) {
            this.pageInfo = pageInfo;
        }

        public Long getUrlExpireMs() {
            return urlExpireMs;
        }

        public List<Organization> getRecommandOrgs() {
            return recommandOrgs;
        }

    }

}
