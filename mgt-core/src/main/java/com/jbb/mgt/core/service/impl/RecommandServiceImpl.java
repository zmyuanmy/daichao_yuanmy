package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jbb.domain.LoanPlatformPolicy;
import com.jbb.domain.RecommandCntPolicy;
import com.jbb.domain.SpecialEntryOrgsPolicy;
import com.jbb.domain.SpecialEntryOrgsPolicy.SpecialOrg;
import com.jbb.domain.XorGroupsPolicy;
import com.jbb.domain.XorGroupsPolicy.XorGroup;
import com.jbb.mgt.core.dao.ChannelDao;
import com.jbb.mgt.core.dao.OrganizationUserDao;
import com.jbb.mgt.core.dao.UserApplyRecordDao;
import com.jbb.mgt.core.dao.UserDao;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.DataFlowBase;
import com.jbb.mgt.core.domain.DataFlowSetting;
import com.jbb.mgt.core.domain.OrgAppliedCount;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.OrganizationUser;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.service.AccountService;
import com.jbb.mgt.core.service.OrgRechargeDetailService;
import com.jbb.mgt.core.service.OrgRechargesService;
import com.jbb.mgt.core.service.RecommandService;
import com.jbb.mgt.server.core.util.JexlUtil;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.shared.map.StringMapper;

@Service("RecommandService")
public class RecommandServiceImpl implements RecommandService {

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

    private static int DEFAULT_RECOMMAND_CNT = 3;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    private static Logger logger = LoggerFactory.getLogger(RecommandServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserApplyRecordDao userApplyRecordDao;
    @Autowired
    private OrganizationUserDao organizationUserDao;
    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private OrgRechargesService orgRechargesService;
    @Autowired
    private OrgRechargeDetailService orgRechargeDetailService;

    @Autowired
    private PlatformTransactionManager txManager;

    @Override
    public List<Organization> getRecommandOrgs(User user, Channel channel, int userType, boolean entryStatus) {
        if (user == null) {
            return null;
        }

        // 如果超过30天直接返回
        int days = PropertyManager.getIntProperty("jbb.user.applied.in.days", 30);
        int userId = user.getUserId();
        if (isAppliedInXDays(userId, days)) {
            logger.info("user {} applied in {} days", userId, days);
            return null;
        }

        // 设置当前渠道进来的类型
        if (Constants.USER_TYPE_ENTRY == userType) {
            List<Organization> orgs = new ArrayList<Organization>();
            int score = user.getZhimaScore() == null ? 0 : user.getZhimaScore();
            int recommandMaxEntryCnt
                = PropertyManager.getIntProperty("sys.policy.entry.recommand.cnt", DEFAULT_RECOMMAND_CNT);
            boolean enabled = PropertyManager.getBooleanProperty("sys.policy.entry.recommand.enabled", true);
            // 是否580分以上 , 并且是第二页提交资料，满足进件
            if (score >= 580 && entryStatus) {
                // 1. 从进件获取推荐组织
                List<Organization> orgs1 = getRecommandOrgsForEntryUser(user, channel);
                if (!CommonUtil.isNullOrEmpty(orgs1)) {
                    orgs.addAll(orgs1);
                }
                if (orgs.size() >= recommandMaxEntryCnt) {
                    return orgs.subList(0, recommandMaxEntryCnt);
                }
                // 2. 从注册获取推荐组织
                if (enabled) {
                    List<Organization> orgs2 = getRecommandOrgsForRegisterUser(user, channel);
                    if (!CommonUtil.isNullOrEmpty(orgs2)) {
                        orgs.addAll(orgs2);
                    }
                }

                if (orgs.size() >= recommandMaxEntryCnt) {
                    return orgs.subList(0, recommandMaxEntryCnt);
                } else {
                    return orgs;
                }
            } else {
                // 非可卖进件
                // 1. 从配置获取特殊组织
                List<Organization> orgs1 = this.getRecommandOrgsFromEntrySpecialConfig(user, channel);
                if (!CommonUtil.isNullOrEmpty(orgs1)) {
                    orgs.addAll(orgs1);
                }
                if (orgs.size() >= recommandMaxEntryCnt) {
                    return orgs.subList(0, recommandMaxEntryCnt);
                }
                // 2. 如果用户分数大于 560， 从注册获取组织补充
                int scoreLimit = PropertyManager.getIntProperty("sys.policy.entry.recommand.score", 560);
                // 如果少于 560分，再返回
                if (score < scoreLimit) {
                    return orgs;
                }
                if (enabled) {
                    List<Organization> orgs2 = getRecommandOrgsForRegisterUser(user, channel);
                    if (!CommonUtil.isNullOrEmpty(orgs2)) {
                        orgs.addAll(orgs2);
                    }
                }

                if (orgs.size() >= recommandMaxEntryCnt) {
                    return orgs.subList(0, recommandMaxEntryCnt);
                } else {
                    return orgs;
                }

            }
        } else if (Constants.USER_TYPE_REGISTER == userType) {
            return getRecommandOrgsForRegisterUser(user, channel);
        }

        return null;
    }

    @Override
    public synchronized void applyToOrgs(User user, List<Organization> orgs) {
        logger.debug("> applyToOrgs(), user= " + user + ", orgs=" + orgs);

        if (user == null || orgs == null) {
            return;
        }

        // 检查用户是否在N天内申请过，如果申请过，直接返回
        int days = PropertyManager.getIntProperty("jbb.user.applied.in.days", 30);
        if (isAppliedInXDays(user.getUserId(), days)) {
            logger.info("user {} applied in {} days", user.getUserId(), days);
            return;
        }

        Channel jbbChannel = channelDao.selectUserRegisterChannal(user.getUserId(), 1);

        if (jbbChannel == null) {
            // 在借帮帮找不到渠道信息，直接return
            // jbbChannel = channelDao.selectChannelByCode(Constants.JBB_DEFAULT_CHANNEL);
            return;
        }

        for (int i = 0; i < orgs.size(); i++) {
            Organization org = orgs.get(i);
            int orgId = org.getOrgId();
            int recommandUserType = org.getRecommandUserType();
            if (orgId == 0)
                continue;

            TransactionStatus txStatus = null;
            try {
                txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
                if (Constants.USER_TYPE_ENTRY == recommandUserType) {
                    // 如果是进件用户，则需要扣费
                    // DataFlowSetting setting = OrgDataFlowSettingsUtil.ORG_SETTINGS.get(orgId);
                    // int dFlowId = setting.getDataFlowId();
                    // DataFlowBase dfBase = OrgDataFlowSettingsUtil.DF_SETTINGS_BASE.get(dFlowId);
                    // int price = dfBase.getPrice();
                    OrgRecharges o = orgRechargesService.selectOrgRechargesForUpdate(orgId);
                    // o.setTotalDataExpense(o.getTotalDataExpense() + price);
                    orgRechargesService.updateOrgRecharges(o);
                    // orgRechargeDetailService.handleConsumeData(orgId, user.getUserId(), price);
                }

                // 应用申请逻辑
                applyToOrg(user, orgId, recommandUserType, jbbChannel.getChannelCode());

                txManager.commit(txStatus);
                txStatus = null;

            } finally {

                rollbackTransaction(txStatus);
            }

        } ;
        logger.debug("< applyToOrgs()");

    }

    @Override
    public void applyToOrg(User user, int orgId, int userType, String channelCode) {
        logger.debug("> applyToOrg(), userId= " + user.getUserId() + ", orgId=" + orgId);
        this.applyToOrg(user, orgId, userType, channelCode, 0);
        logger.debug("> applyToOrg()");
    }

    @Override
    public void applyToOrg(User user, int orgId, int userType, String channelCode, int flag) {
        logger.debug("> applyToOrg(), userId= " + user.getUserId() + ", orgId=" + orgId + ", flag=" + flag);

        // 插入渠道组织注册表，表明此用户来自借帮帮
        OrganizationUser orgUser = new OrganizationUser(orgId, user.getUserId(), 1, userType, channelCode);
        organizationUserDao.insertOrganizationUser(orgUser);

        // 自动分配
        int autoSettingType = (userType == Constants.USER_TYPE_ENTRY ? Constants.TYPE_USER_AUTO_SETTING_ENTRY
            : Constants.TYPE_USER_AUTO_SETTING_DFLOW);
        UserApplyRecord record = new UserApplyRecord(user.getUserId(), orgId, channelCode, Constants.JBB_ORG, userType);
        record.setFlag(flag);
        record.setStatus(1);

        // Start 获取组织自动分配设置，并实施自动分配

        Account acc = accountService.getOrgAutoAssignAccount(orgId, autoSettingType);
        if (acc != null) {
            record.setInitAccId(acc.getAccountId());
            record.setAssingDate(DateUtil.getCurrentTimeStamp());
            record.setStatus(2);
        }

        // End

        userApplyRecordDao.insertUserApplyRecord(record);
        logger.debug("> applyToOrg()");
    }

    private List<Organization> getRecommandOrgsFromEntrySpecialConfig(User user, Channel channel) {

        logger.debug("> getRecommandOrgsFromEntrySpecialConfig(), user= " + user + ", channel= " + channel);

        // 从系统配置加载 特殊组织的配置
        String speEntryOrgsStr = PropertyManager.getProperty("sys.policy.entry.orgs");

        SpecialEntryOrgsPolicy speEntryOrgs;
        try {
            speEntryOrgs = StringMapper.readSSpecialEntryOrgsPolicy(speEntryOrgsStr);
        } catch (Exception e) {
            logger.error("StringMapper.readSelfGroupsPolicy with error: " + e.getMessage());
            return null;
        }

        if (speEntryOrgs == null) {
            return null;
        }

        SpecialOrg[] specialOrgs = speEntryOrgs.getSpecialOrgs();

        if (specialOrgs == null || specialOrgs.length == 0) {
            return null;
        }

        List<Organization> orgs = new ArrayList<Organization>();
        for (int i = 0; i < specialOrgs.length; i++) {
            SpecialOrg sOrg = specialOrgs[i];
            int orgId = sOrg.getOrgId();
            // 检查此用户是否存在于此组织，如果存在，则不再推荐
            if (userDao.checkUserExistInOrg(orgId, user.getPhoneNumber(), null, null)) {
                continue;
            }
            // 检查是否符合条件
            boolean orgFlag = JexlUtil.executeJexl(sOrg.getJexlScript(), user, channel);
            // Organization org = OrgDataFlowSettingsUtil.ORGS.get(sOrg.getOrgId());
            // if (orgFlag && org != null) {
            // org.setRecommandUserType(Constants.USER_TYPE_REGISTER);
            // orgs.add(org);
            // }
        }

        logger.debug("< getRecommandOrgsFromEntrySpecialConfig()");
        return orgs;
    }

    private List<Organization> getRecommandOrgsForEntryUser(User user, Channel channel) {

        int userId = user.getUserId();

        // 检查用户是否在N天内申请过，如果申请过，直接返回
        int days = PropertyManager.getIntProperty("jbb.user.applied.in.days", 30);
        if (isAppliedInXDays(userId, days)) {
            logger.info("user {} applied in {} days", userId, days);
            return null;
        }

        // 获取 默认推荐数
        int recommandMaxCnt = countRecommandCnt(user);
        logger.info("recommandMaxCnt : " + recommandMaxCnt);

        if (recommandMaxCnt == 0) {
            logger.warn("because of recommandMaxCnt is zero, user not recommand to any organization, user = {}", user);
            return null;
        }

        // 获取满足条件的出借组织
        Set<Integer> toChooseOrgs = new HashSet<Integer>();

        // 获取当天统计数据
        // 获取当天推荐数，检查是否超过最大推荐数限制
        long startTs = DateUtil.getTodayStartTs();
        long endTs = startTs + DateUtil.DAY_MILLSECONDES;

        List<OrgAppliedCount> orgCounts = userDao.countOrgAppliedUsers(null, new Timestamp(startTs),
            new Timestamp(endTs), Constants.USER_TYPE_ENTRY);

        // OrgDataFlowSettingsUtil.ORG_SETTINGS.forEach((orgId, orgSetting) -> {
        // if (orgSetting.isClosed()) {
        // return;
        // }
        //
        // // 检查此用户是否存在于此组织，如果存在，则不再推荐
        // if (userDao.checkUserExistInOrg(orgId, user.getPhoneNumber(), null, null)) {
        // return;
        // }
        //
        // // 检查用户是否还有导流余额
        // logger.info(" ================orgId = " + orgId);
        // OrgRecharges o = orgRechargesService.selectOrgRechargesForUpdate(orgId);
        // if (o.getDataBudget() <= 0) {
        // logger.info(" org's data budget < 0 , orgId = " + orgId);
        // return;
        // }
        //
        // // 检查是否符合组织条件，检查分两部分， 一是org setting
        // int dfId = orgSetting.getDataFlowId();
        // DataFlowBase dfBase = OrgDataFlowSettingsUtil.DF_SETTINGS_BASE.get(dfId);
        //
        // if (dfBase == null) {
        // return;
        // }
        //
        // boolean orgFlag = JexlUtil.executeJexl(dfBase.getJexlScript(), user, channel);
        //
        // if (!orgFlag)
        // return;
        //
        // // 获取当天推荐数，检查是否超过最大推荐数限制
        // int todayCnt = getOrgTodayCnt(orgCounts, orgId);
        //
        // if (todayCnt >= orgSetting.getMaxValue()) {
        // return;
        // }
        // // 获取系统 对组织配置的策略
        // String script = PropertyManager.getProperty("sys.policy.org.script." + orgId);
        // boolean sFlag = JexlUtil.executeJexl(script, user, channel);
        //
        // if (!sFlag)
        // return;
        //
        // logger.info(" orgId = " + orgId + " , orgFlag =" + orgFlag + " , sFlag =" + sFlag);
        // if (orgFlag && sFlag) {
        // toChooseOrgs.add(orgId);
        // }
        // });
        //
        // // 执行互斥策略，相同组的组织不同时推荐
        // String xorGroupsStr = PropertyManager.getProperty("sys.policy.xor.groups");
        // Set<Integer> toRecommandOrgs = filterOrgsByGroupPolicy(toChooseOrgs, xorGroupsStr);
        // Set<Integer> recommandOrgs = null;
        // if (toRecommandOrgs.size() != 0 && toRecommandOrgs.size() <= recommandMaxCnt) {
        // // 待筛选出借人数少于需要推荐的人数， 直接返回
        //
        // recommandOrgs = toRecommandOrgs;
        // } else {
        // recommandOrgs = filterOrgsBySettings(recommandMaxCnt, toRecommandOrgs);
        // }
        //
        // // 返回出借组织
        // return getOrganizationByIds(recommandOrgs, Constants.USER_TYPE_ENTRY);
        return null;
    }

    private List<Organization> getOrganizationByIds(Set<Integer> recommandOrgs, int recommandUserType) {
        List<Organization> orgs = new ArrayList<Organization>();
        // if (!CommonUtil.isNullOrEmpty(recommandOrgs)) {
        // recommandOrgs.forEach(orgId -> {
        // Organization org = OrgDataFlowSettingsUtil.ORGS.get(orgId);
        // org.setRecommandUserType(recommandUserType);
        // orgs.add(org);
        // });
        // }
        return orgs;
    }

    private int getOrgTodayCnt(List<OrgAppliedCount> orgCounts, int orgId) {
        if (orgCounts == null || orgCounts.size() == 0) {
            return 0;
        }
        for (int i = 0; i < orgCounts.size(); i++) {
            OrgAppliedCount orgCnt = orgCounts.get(i);
            if (orgCnt.getOrgId() == orgId) {
                return orgCnt.getCnt();
            }
        }
        return 0;
    }

    private List<Organization> getRecommandOrgsForRegisterUser(User user, Channel channel) {
        return getRecommandOrgsForRegisterUser(user, channel, null);
    }

    private List<Organization> getRecommandOrgsForRegisterUser(User user, Channel channel, Integer recommandMaxCnt) {
        int userId = user.getUserId();

        // 检查用户是否在N天内申请过，如果申请过，直接返回
        int days = PropertyManager.getIntProperty("jbb.user.applied.in.days", 30);
        if (isAppliedInXDays(userId, days)) {
            logger.info("user {} applied in {} days", userId, days);
            return null;
        }

        // 获取 默认推荐数
        if (recommandMaxCnt == null) {
            recommandMaxCnt = this.countRecommandCntForRegisterUser(user, channel);
        }

        logger.info("recommandMaxCnt : " + recommandMaxCnt);

        if (recommandMaxCnt == 0) {
            logger.warn("because of recommandMaxCnt is zero, user not recommand to any organization, user = {}", user);
            return null;
        }

        // 获取满足条件的出借组织
        Set<Integer> toChooseOrgs = new HashSet<Integer>();

        // 获取当天推荐数，检查是否超过最大推荐数限制
        long startTs = DateUtil.getTodayStartTs();
        long endTs = startTs + DateUtil.DAY_MILLSECONDES;

        List<OrgAppliedCount> orgCounts = userDao.countOrgAppliedUsers(null, new Timestamp(startTs),
            new Timestamp(endTs), Constants.USER_TYPE_REGISTER);

        // // OrgDataFlowSettingsUtil.ORGS.forEach((orgId, org) -> {
        //
        // if (org.getDataEnabled() == null || org.getDataEnabled() == false) {
        // return;
        // }
        //
        // // 检查此用户是否存在于此组织，如果存在，则不再推荐
        // if (userDao.checkUserExistInOrg(orgId, user.getPhoneNumber(), null, null)) {
        // return;
        // }
        //
        // // 获取当天推荐数，检查是否超过最大推荐数限制
        // logger.info(" ================orgId = " + orgId);
        // int todayCnt = getOrgTodayCnt(orgCounts, orgId);
        // int wantCnt = org.getCnt();
        // if (todayCnt >= wantCnt) {
        // return;
        // }
        //
        // // 检查是否符合组织条件
        // String jexlScript = org.getFilterScript();
        // boolean orgFlag = JexlUtil.executeJexl(jexlScript, user, channel);
        // if (!orgFlag) {
        // logger.info(" fileter orgId by jexlScript " + orgId);
        // return;
        // }
        //
        // logger.info(" orgId = " + orgId + " , orgFlag =" + orgFlag);
        // if (orgFlag) {
        // toChooseOrgs.add(orgId);
        // }
        // });
        //
        // // 执行互斥策略，相同组的组织不同时推荐
        // String xorGroupsStr = PropertyManager.getProperty("sys.policy.xor.dflow.groups");
        // Set<Integer> toRecommandOrgs = filterOrgsByGroupPolicy(toChooseOrgs, xorGroupsStr);
        // Set<Integer> recommandOrgs = null;
        // if (toRecommandOrgs.size() != 0 && toRecommandOrgs.size() <= recommandMaxCnt) {
        // // 待筛选出借人数少于需要推荐的人数， 直接返回
        //
        // recommandOrgs = toRecommandOrgs;
        // } else {
        // recommandOrgs = filterOrgsBySettingsForRegisterUser(recommandMaxCnt, toRecommandOrgs);
        // }
        //
        // // 返回出借组织
        // return getOrganizationByIds(recommandOrgs, Constants.USER_TYPE_REGISTER);
        return null;
    }

    @Override
    public Set<Integer> filterOrgsByGroupPolicy(Set<Integer> toChooseOrgs, String xorGroupsPolicyStr) {
        XorGroupsPolicy xorGroupsPolicy = null;

        try {
            xorGroupsPolicy = StringMapper.readXorGroupsPolicy(xorGroupsPolicyStr);
        } catch (Exception e) {
            logger.error("StringMapper.readXorGroupsPolicy with error:" + e.getMessage());
            return toChooseOrgs;
        }

        Map<String, Set<Integer>> map = new HashMap<String, Set<Integer>>();
        if (xorGroupsPolicy == null) {
            return toChooseOrgs;
        }

        XorGroup[] xorGroups = xorGroupsPolicy.getXorGroups();
        if (xorGroups == null || xorGroups.length == 0) {
            return toChooseOrgs;
        }

        for (int i = 0; i < xorGroups.length; i++) {
            XorGroup xorGroup = xorGroups[i];
            int[] orgs = xorGroup.getOrgs();
            if (orgs == null || orgs.length == 0) {
                continue;
            }
            Set<Integer> groupSet = new HashSet<Integer>();
            for (int orgId : orgs) {
                if (toChooseOrgs.contains(orgId)) {
                    groupSet.add(orgId);
                }
            }
            if (groupSet.size() > 0) {
                map.put(xorGroup.getGroupName(), groupSet);
            }
        }

        if (map.size() == 0) {
            return toChooseOrgs;
        }

        Set<Integer> setA = new HashSet<Integer>();
        Set<Integer> setD = new HashSet<Integer>();
        map.forEach((key, setC) -> {
            // 差值
            toChooseOrgs.removeAll(setC);

            // 差值
            Set<Integer> setE = new HashSet<Integer>();
            setE.addAll(setC);
            setE.removeAll(setD);

            if (setE.size() == 0) {
                return;
            }

            // 先把一个用户U
            Integer u = getRandomElement(setE);

            setA.removeAll(setE);
            setA.add(u);

            setD.addAll(setE);
            setD.remove(u);

        });
        // 合并
        toChooseOrgs.addAll(setA);
        return toChooseOrgs;
    }

    /**
     * 从set中随机取得一个元素
     * 
     * @param set
     * @return
     */
    public static <E> E getRandomElement(Set<E> set) {
        int index = (int)(Math.random() * set.size());
        int i = 0;
        for (E e : set) {
            if (i == index) {
                return e;
            }
            i++;
        }
        return null;
    }

    private int countRecommandCnt(User user) {
        int recommandMaxCnt = PropertyManager.getIntProperty("jbb.recommand.cnt", DEFAULT_RECOMMAND_CNT);
        return recommandMaxCnt;
    }

    /**
     * 获取渠道用户最大推荐数
     * 
     * @param user
     * @return
     */
    private int countRecommandCntForRegisterUser(User user, Channel channel) {
        int recommandMaxCnt = PropertyManager.getIntProperty("jbb.recommand.dflow.cnt", DEFAULT_RECOMMAND_CNT);
        String dflowMaxCntSettings = PropertyManager.getProperty("jbb.recommand.dflow.cnt");
        LoanPlatformPolicy policy = null;
        try {
            policy = StringMapper.readPolicy(dflowMaxCntSettings);
            // 获取推荐数策略
            RecommandCntPolicy[] recommandCntPolicy = policy.getRecommandCntPolicy();
            if (!CommonUtil.isNullOrEmpty(recommandCntPolicy)) {
                for (RecommandCntPolicy p : recommandCntPolicy) {
                    if (recommandMaxCnt <= p.getRecommandCnt()) {
                        logger.debug(" recommand policy -> continue, policy limit {} >= {} ", p.getRecommandCnt(),
                            recommandMaxCnt);
                        continue;
                    }

                    logger.debug(" recommand policy -> " + p);
                    boolean flag = JexlUtil.executeJexl(p.getJexlScript(), user, channel);
                    if (flag) {
                        recommandMaxCnt
                            = (p.getRecommandCnt() < recommandMaxCnt ? p.getRecommandCnt() : recommandMaxCnt);
                    }
                }
            }
            logger.info("recommandMaxCnt : " + recommandMaxCnt);
        } catch (Exception e) {
            logger.error("load policy error =" + e.getMessage());
        }

        return recommandMaxCnt;
    }

    // 检查在天内是否申请过，并且有推荐过给相应出借方
    private boolean isAppliedInXDays(int userId, int days) {
        long todayStart = DateUtil.getStartTsOfDay(DateUtil.getCurrentTime());
        Timestamp start = new Timestamp(todayStart - days * DateUtil.DAY_MILLSECONDES);
        return userDao.checkUserApplied(userId, start);
    }

    private Set<Integer> filterOrgsBySettings(int recommandCnt, Set<Integer> orgs) {

        // 如果为空，直接返回
        if (orgs == null) {
            return new HashSet<Integer>();
        }
        // 如果数据量少于推荐量，直接返回
        if (orgs.size() <= recommandCnt) {
            return orgs;
        }

        // 按权重筛选推荐列表
        int size = orgs.size();
        double totalP = 0.0;
        int[] users = new int[size];
        double[] userPs = new double[size];
        int index = 0;

        for (Integer orgId : orgs) {
            // int weight = PropertyManager.getIntProperty("sys.org.weight." + orgId, 1);
          //  DataFlowSetting setting = OrgDataFlowSettingsUtil.ORG_SETTINGS.get(orgId);
            /* //int weight = setting.getMaxValue();
            totalP += weight;
            users[index] = orgId;
            userPs[index] = totalP;
            index++;*/
        } ;

        Set<Integer> recommandOrgs = new HashSet<Integer>();
        int cnt = 0;
        for (int i = 0; i < recommandCnt;) {
            double p = Math.random() * totalP;
            int userIndex = 0;
            for (int j = 0; j < userPs.length; j++) {
                if (userPs[j] >= p) {
                    userIndex = j;
                    break;
                }
            }
            if (!recommandOrgs.contains(users[userIndex])) {
                recommandOrgs.add(users[userIndex]);
                cnt++;
            }

            if (cnt >= recommandCnt) {
                break;
            }
        }

        return recommandOrgs;

    }

    private Set<Integer> filterOrgsBySettingsForRegisterUser(int recommandCnt, Set<Integer> orgs) {

        // 如果为空，直接返回
        if (orgs == null) {
            return new HashSet<Integer>();
        }
        // 如果数据量少于推荐量，直接返回
        if (orgs.size() <= recommandCnt) {
            return orgs;
        }

        // 按权重筛选推荐列表
        int size = orgs.size();
        double totalP = 0.0;
        int[] users = new int[size];
        double[] userPs = new double[size];
        int index = 0;

        for (Integer orgId : orgs) {
            // int weight = PropertyManager.getIntProperty("sys.org.weight." + orgId, 1);
            /*   Organization org = OrgDataFlowSettingsUtil.ORGS.get(orgId);
            int weight = org.getWeight();
            totalP += weight;
            users[index] = orgId;
            userPs[index] = totalP;
            index++;*/
        } ;

        Set<Integer> recommandOrgs = new HashSet<Integer>();
        int cnt = 0;
        for (int i = 0; i < recommandCnt;) {
            double p = Math.random() * totalP;
            int userIndex = 0;
            for (int j = 0; j < userPs.length; j++) {
                if (userPs[j] >= p) {
                    userIndex = j;
                    break;
                }
            }
            if (!recommandOrgs.contains(users[userIndex])) {
                recommandOrgs.add(users[userIndex]);
                cnt++;
            }

            if (cnt >= recommandCnt) {
                break;
            }
        }

        return recommandOrgs;

    }
}
