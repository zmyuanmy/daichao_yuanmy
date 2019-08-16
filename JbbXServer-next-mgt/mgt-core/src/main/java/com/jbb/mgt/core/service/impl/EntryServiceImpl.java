package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.ChannelDao;
import com.jbb.mgt.core.dao.OrganizationUserDao;
import com.jbb.mgt.core.dao.UserDao;
import com.jbb.mgt.core.dao.UserPropertiesDao;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.OrganizationUser;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.service.EntryService;
import com.jbb.server.common.util.StringUtil;

@Service("EntryService")
public class EntryServiceImpl implements EntryService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private UserPropertiesDao userPropertiesDao;
    @Autowired
    private OrganizationUserDao organizationUserDao;
    private static Logger logger = LoggerFactory.getLogger(EntryService.class);

    @Override
    public void insertEntry(Integer userId, String name, String value, boolean isHidden) {
        userPropertiesDao.insertUserProperty(userId, name, value, isHidden);
    }

    @Override
    public void check(Integer userId) {
        List<OrganizationUser> s = organizationUserDao.selectOrganizationUsers(userId, null, null);
        if (s != null && s.size() != 0) {
            User u = userDao.selectUserById(userId);
            for (int i = 0; i < s.size(); i++) {
                Channel c1 = channelDao.selectChannelByCode("cl" + s.get(i).getOrgId());
                boolean booleanCheck = verifyChannel(u, c1);
                if (booleanCheck) {
                    s.get(i).setEntryStatus(true);
                    organizationUserDao.updateOrganizationUser(s.get(i));
                }
            }
        }
    }

    private boolean verifyChannel(User user, Channel channel) {
        boolean b = true;
        if (null != channel) {
            // 是否需要qq
            if (channel.isQqRequired()) {
                if (StringUtil.isEmpty(user.getQq())) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "qq");
                    return b;
                }
            }
            // 是否需要微信
            if (channel.isWechatRequired()) {
                if (StringUtil.isEmpty(user.getWechat())) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "wechat");
                    return b;
                }
            }
            // 是否需要芝麻分
            if (channel.isZhimaRequired()) {
                if (null == user.getZhimaScore()) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "zhima");
                    return b;
                }
            }
            // 是否需要身份证
            if (channel.isIdcardInfoRequired()) {
                if (StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getIdCard())) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "IdcardAndName");
                    return b;
                }
            }
            // 身份证头像
            if (channel.isIdcardRearRequired()) {
                if (StringUtil.isEmpty(user.getIdcardRear())) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "idcardRear");
                    return b;
                }
            }
            // 身份证反面
            if (channel.isIdcardBackRequired()) {
                if (StringUtil.isEmpty(user.getIdcardBack())) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "idcardBack");
                    return b;
                }
            }
            // 手持
            if (channel.isHeaderRequired()) {
                if (StringUtil.isEmpty(user.getIdcardInfo())) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "idcardHead");
                    return b;
                }
            }
            // 联系人1
            if (channel.isMobileContract1Required()) {
                if (StringUtil.isEmpty(user.getContract1Relation()) || StringUtil.isEmpty(user.getContract1Username())
                    || StringUtil.isEmpty(user.getContract1Phonenumber())) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "mobile1");
                    return b;
                }
            }
            // 联系人2
            if (channel.isMobileContract2Required()) {
                if (StringUtil.isEmpty(user.getContract2Relation()) || StringUtil.isEmpty(user.getContract2Username())
                    || StringUtil.isEmpty(user.getContract2Phonenumber())) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "mobile2");
                    return b;
                }
            }
            // 运行商认证
            if (channel.isMobileServiceInfoRequired()) {
                if (user.isMobileVerified() == false) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "yys");
                    return b;
                }
            }

            // 京东认证
            if (channel.isJingdongRequired()) {
                if (user.isJingdongVerified() == false) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "jingdong");
                    return b;
                }
            }
            // 公积金认证
            if (channel.isGjjRequired()) {
                if (user.isGjjVerified() == false) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "gjj");
                    return b;
                }
            }
            // 社保认证
            if (channel.isSjRequired()) {
                if (user.isSiVerified() == false) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "si");
                    return b;
                }
            }
            // 学信认证
            if (channel.isChsiRequired()) {
                if (user.isChsiVerified() == false) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "chsi");
                    return b;
                }
            }
            /*  淘宝认证
            if (channel.isTaobaoRequired()) {
                if (user.is) {
                    b = false;
                    return b;
                }
            }*/
            // 公司信息认证
            if (channel.isCompanyRequired()) {
                if (null == user.getJobInfo()) {
                    b = false;
                    logger.info("userId" + user.getUserId() + "company");
                    return b;
                }
            }
        }
        return b;
    }

}
