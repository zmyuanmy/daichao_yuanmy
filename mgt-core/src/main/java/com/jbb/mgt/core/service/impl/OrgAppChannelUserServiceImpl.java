package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.OrgAppChannelUserDao;
import com.jbb.mgt.core.dao.OrganizationUserDao;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserProperty;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.core.service.OrgAppChannelUserService;
import com.jbb.mgt.core.service.UserPropertiesService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;

@Service("OrgAppChannelUserService")
public class OrgAppChannelUserServiceImpl implements OrgAppChannelUserService {

    private static Logger logger = LoggerFactory.getLogger(OrgAppChannelUserServiceImpl.class);

    @Autowired
    private OrgAppChannelUserDao orgAppChannelUserDao;

    @Autowired
    private OrganizationUserDao organizationUserDao;

    @Autowired
    @Qualifier("UserService")
    private UserService userService;

    @Autowired
    private UserPropertiesService userPropertiesService;

    @Autowired
    private ChannelService channelService;

    @Override
    public void saveOrgAppChannelUser(User user, int orgId, boolean isNew, boolean hidden, Channel channel,
        int applicationId, Integer userType, String remoteAddress) {

        if (user == null || channel == null) {
            return;
        }
        int userId = user.getUserId();
        String channelCode = channel.getChannelCode();

        if (isNew) {
            // 如果是新用户， 直接记结算量
            saveAppChannelUser(orgId, applicationId, channel, userId, userType, hidden);
            return;
        }

        if (!isValidApplication(applicationId)) {
            // 如果申请的applicationId不合法，不计算量给渠道，防暴力刷量
            hidden = true;
        }

        // 1. 是否在此渠道上来的app有注册
        boolean isExistInApp = orgAppChannelUserDao.checkExist(userId, orgId, applicationId, null, null);
        if (isExistInApp) {
            // 如果此app已经有用户结算记录，不处理
            return;
        }

        // 2. applicationId 关联的应用 上无此用户数据， 检查此用户在此组织是否存在
        boolean isExistInOrg = organizationUserDao.checkExist(orgId, userId);

        if (applicationId == User.USER_APPLICATION_ID) {
            // 如果为JBB 整个大库，处理逻辑如下
            if (!isExistInOrg) {
                // 不存在于组织, 插入数据 , 并计结算量
                saveAppChannelUser(orgId, applicationId, channel, userId, userType, hidden);

            } else {
                // 存在于组织, 但不存在于applicationId为0的大库， 插入数据 , 但不计结算量(hidden 为true)
                saveAppChannelUser(orgId, applicationId, channel, userId, userType, true);
            }

            return;

        }

        // 3. 其他非jbb大库的应用ID(非0)， 检查是否今天在其他APP有注册
        long start = DateUtil.getTodayStartTs();
        long end = start + DateUtil.DAY_MILLSECONDES;
        boolean isExistInOtherApp
            = orgAppChannelUserDao.checkExist(userId, orgId, null, new Timestamp(start), new Timestamp(end));

        if (!isExistInApp) {
            if (isExistInOtherApp) {
                // 今天有从别的渠道先行注册，需要插入数据，并标识为隐藏，不给渠道计结算量
                hidden = true;
            }

            if (hidden == false) {
                // 进行羊毛的空号检测
                hidden = checkWoolAndMobile(channel, user, remoteAddress);
            }
            saveAppChannelUser(orgId, applicationId, channel, userId, userType, hidden);
            return;
        }

    }

    private boolean checkWoolAndMobile(Channel channel, User user, String remoteAddress) {
        boolean hidden = false;
        List<UserProperty> userProperties = new ArrayList<UserProperty>();
        try {
            hidden
                = userService.needHiddenUserForChannel(channel, user.getPhoneNumber(), remoteAddress, userProperties);
        } catch (Exception e) {
            // nothing to do
            logger.error("hideUserForChannel with error, " + e.getMessage());
        }
        user.setHidden(hidden);
        // 更新用户
        this.userService.updateUserHiddenStatus(user.getUserId(), hidden);
        // 更新用户properties
        if (userProperties != null && userProperties.size() > 0) {
            userPropertiesService.insertUserProperties(user.getUserId(), userProperties);
        }
        return hidden;
    }

    private boolean isValidApplication(int applicationId) {
        int[] appIds = PropertyManager.getIntProperties("jbb.org.validate.appIds", 0);
        return CommonUtil.inArray(applicationId, appIds);
    }

    private void saveAppChannelUser(int orgId, int applicationId, Channel channel, int userId, Integer userType,
        boolean hidden) {

        orgAppChannelUserDao.insertOrgAppChannelUser(orgId, applicationId, channel.getChannelCode(), userId, userType,
            hidden);

        updateChannelTestFlag(channel);

    }

    private void updateChannelTestFlag(Channel channel) {
        if (channel == null)
            return;
        String channelCode = channel.getChannelCode();

        // 插入日志统计 注册用户同后面的H5商家关联
        String merchantId = null;
        String merchantId2 = null;
        if (channel.getMerchantId() != null) {
            merchantId = String.valueOf(channel.getMerchantId());
        }
        if (channel.getMerchantId2() != null) {
            merchantId = String.valueOf(channel.getMerchantId2());
        }

        /*if (channel.isJbbChannel()) {
            if (channel.getMsgTimes() > 0) {
                // String channelInfo = channel.getChannelName() + channelCode;
                // sendMsgToOpService.sendMsgToOp(channelInfo, channel.getAccount().getPhoneNumber());
                int times = channel.getMsgTimes() - 1;
                channelService.updateMsgTimes(channelCode, times);
                int smsTimes = PropertyManager.getIntProperty("jbb.mgt.channel.times", 0);
                if (times == smsTimes) {
                    // String newMerchantId = PropertyManager.getProperty("jbb.mgt.channel.merchantId");
                    // String newMerchantId2 = PropertyManager.getProperty("jbb.mgt.channel.merchantId2");
                    // merchantId = merchantId == null ? newMerchantId : merchantId;
                    // merchantId2 = merchantId2 == null ? newMerchantId2 : merchantId2;
                    // 更新重定向链接 ，去除test=true
                    String redirectUrl = channel.getRedirectUrl().replace("?test=true", "");
                    redirectUrl = redirectUrl.replace("&test=true", "");
                    channelService.updateChannelTest(channelCode, null, null, redirectUrl);
                }
            }
        }*/
    }

}
