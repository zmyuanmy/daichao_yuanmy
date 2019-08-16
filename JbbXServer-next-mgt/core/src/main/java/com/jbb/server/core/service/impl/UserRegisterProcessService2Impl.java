package com.jbb.server.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.MapContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.domain.LoanPlatformPolicy;
import com.jbb.domain.RecommandCntPolicy;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.IDCardUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.dao.AccountDao;
import com.jbb.server.core.dao.StatisticDataDao;
import com.jbb.server.core.domain.Property;
import com.jbb.server.core.domain.User;
import com.jbb.server.core.domain.UserProperty;
import com.jbb.server.core.service.AccountService;
import com.jbb.server.core.service.PushUserService;
import com.jbb.server.core.service.UserRegisterProcessService;
import com.jbb.server.core.util.LenderesUtil;
import com.jbb.server.core.util.SpringUtil;
import com.jbb.server.shared.map.StringMapper;

@Service("userRegisterProcessService")
public class UserRegisterProcessService2Impl implements UserRegisterProcessService {

    private static Logger logger = LoggerFactory.getLogger(UserRegisterProcessService2Impl.class);

    private static int DEFAULT_RECOMMAND_CNT = 3;

    private static Map<Integer, Integer> userCntMap = new HashMap<Integer, Integer>();

    private static final JexlEngine JEXL = new JexlBuilder().cache(512).strict(true).silent(false).create();

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private StatisticDataDao statisticDataDao;

    @Override
    public Set<User> applyToLendUser(int userId) {

        User user = accountService.getUser(userId);

        if (user == null) {
            logger.warn("not found user, userId=" + userId);
            return null;
        }

        String sourceId = user.getSourceId();
        if (sourceId != null && sourceId.indexOf("mgt") == 0) {
            // 如果是用户来自己于笨鸟风控管理系统，直接返回
            return null;

        }

        // 检查用户是否处理过
        if (LenderesUtil.APPLY_USER_SET.contains(userId)) {
            logger.warn("duplicate message, userId=" + userId);
            return null;
        }
        LenderesUtil.APPLY_USER_SET.add(userId);
        // 结束

        // 检查用户是否在N天内申请过，如果申请过，直接返回
        int days = PropertyManager.getIntProperty("jbb.user.applied.in.days", 3);
        //
        if (isAppliedInXDays(userId, days)) {
            logger.info("user {} applied in {} days", userId, days);
            return null;
        }
        // 获取policy文件
        String policyContent = PropertyManager.getProperty(Property.SYS_LOAN_POLICY);

        if (policyContent == null) {
            logger.error("not found policy.");
            return null;
        }
        LoanPlatformPolicy policy = null;
        try {
            policy = StringMapper.readPolicy(policyContent);
        } catch (Exception e) {
            logger.error("load policy error =" + e.getMessage());
            e.printStackTrace();
        }

        // 按策略获取出借人列表
        Set<User> targetUsers = getLoanUsersByPolicy(policy, user);

        // 用户申请
        if (targetUsers != null && targetUsers.size() > 0) {
            int[] targetUserIds = getTargetUserIds(targetUsers);
            saveApplayUser(userId, targetUserIds);
            // 接口推送
            pushDataByInterface(user, targetUserIds);
        }
        
        return targetUsers;

    }

    private int[] getTargetUserIds(Set<User> users) {
        int[] userIds = new int[users.size()];
        int i = 0;
        for (User user : users) {
            userIds[i++] = user.getUserId();
        }
        return userIds;
    }

    // 检查在天内是否申请过，并且有推荐过给相应出借方
    private boolean isAppliedInXDays(int userId, int days) {
        long todayStart = DateUtil.getStartTsOfDay(DateUtil.getCurrentTime());
        Timestamp start = new Timestamp(todayStart - days * DateUtil.DAY_MILLSECONDES);
        return accountDao.checkUserApplied(userId, start);
    }

    private void pushDataByInterface(User user, int[] targetUserIds) {
        logger.debug(">pushDataByInterface, userId =" + user.getUserId());
        for (int i = 0; i < targetUserIds.length; i++) {
            int targetUserId = targetUserIds[i];
            UserProperty pBeanName =
                accountService.searchUserPropertiesByUserIdAndName(targetUserId, UserProperty.P_BEAN_CLASS);
            UserProperty pServerPath =
                accountService.searchUserPropertiesByUserIdAndName(targetUserId, UserProperty.P_SERVER_PATH);
            if (pBeanName != null && pServerPath != null && !StringUtil.isEmpty(pBeanName.getValue())
                && !StringUtil.isEmpty(pServerPath.getValue())) {
                logger.info(" post to userId=  " + targetUserId + " , beanName =" + pBeanName.getValue()
                    + " , serverPath =" + pServerPath.getValue());
                PushUserService pushUserService = SpringUtil.getBean(pBeanName.getValue(), PushUserService.class);
                pushUserService.postUserData(user, pServerPath.getValue());
            }
        }
        logger.debug("<pushDataByInterface");
    }

    private boolean isMatchJexlExpress(String jexlScript, User user, int score, String platform) {
        if (StringUtil.isEmpty(jexlScript)) {
            return true;
        }

        if (user == null) {
            return false;
        }

        // 执行JEXL语句
        try {

            JexlScript jexlS = JEXL.createScript(jexlScript);
            // populate the context
            JexlContext context = new MapContext();
            String idCard = user.getIdCardNo();
            context.set("score", score);
            context.set("platform", platform);
            context.set("sourceId", user.getSourceId());
            // 证件里的年龄，性别，地区
            if (IDCardUtil.validate(idCard)) {
                context.set("age", IDCardUtil.calculateAge(idCard));
                context.set("sex", IDCardUtil.calculateSex(idCard));
                context.set("area", IDCardUtil.getProvinceCode(idCard));
            } else {
                context.set("age", 0);
                context.set("area", null);
                context.set("sex", null);
            }
            // execute
            boolean result = (Boolean)jexlS.execute(context);
            logger.debug("jexl execute, resule={}, jexlS={} , score={}, platform={}, user={}, lenderId={} ", result,
                jexlScript, score, platform, user);
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    private Set<User> getLoanUsersByPolicy(LoanPlatformPolicy policy, User user) {
        String sourceId = user.getSourceId();
        // 用户芝麻分
        UserProperty up =
            accountService.searchUserPropertiesByUserIdAndName(user.getUserId(), Constants.SESAME_CREDIT_SCORE);
        int score = 0;
        if (up != null && !StringUtil.isEmpty(up.getValue())) {
            try {
                score = Integer.valueOf(up.getValue());
            } catch (NumberFormatException e) {
                // nothing to do
            }
        }
        // 用户注册平台信息
        UserProperty upPlatform =
            accountService.searchUserPropertiesByUserIdAndName(user.getUserId(), Constants.SIGNIN_PLATFROM);
        String platform = "";
        if (upPlatform != null) {
            platform = upPlatform.getValue();
        }
        logger.info("user={} , platform={}, score ={} ", user, platform, score);
        // 获取 默认推荐数
        int recommandMaxCnt = PropertyManager.getIntProperty("jbb.lender.limit", DEFAULT_RECOMMAND_CNT);

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
                boolean flag = isMatchJexlExpress(p.getJexlScript(), user, score, platform);
                if (flag) {
                    recommandMaxCnt = (p.getRecommandCnt() < recommandMaxCnt ? p.getRecommandCnt() : recommandMaxCnt);
                }
            }
        }
        logger.info("recommandMaxCnt : " + recommandMaxCnt);

        if (recommandMaxCnt == 0) {
            logger.warn("because of recommandMaxCnt is zero, user not recommand to any lender, user = {}", user);
            return null;
        }

        // 获取满足条件的出借人列表
        Set<User> allLenderes = new HashSet<User>();

        LenderesUtil.LENDERES.forEach((userId, e) -> {
            allLenderes.add(e);
        });

        // 过滤出借人列表，获取满足条件的列表
        Set<User> lenders = new HashSet<User>();
        long startTs = DateUtil.getTodayStartTs();
        Timestamp start = new Timestamp(startTs);
        Timestamp end = new Timestamp(startTs + DateUtil.DAY_MILLSECONDES);

        for (User lender : allLenderes) {
            // 获取出借人今天已经推荐的用户数，看是否超过总数，超过总数限制，则不再推荐
            int recommandedCnt = statisticDataDao.countUsersByLendId(lender.getUserId(), start, end);

            // for test
            // Integer cnt = userCntMap.get(lender.getUserId());
            // recommandedCnt = cnt == null ? 0 : cnt;
            // end for test

            int maxLimit = lender.getPropertyIntVal(UserProperty.P_CHANNEL_MAX_CNT, 0);
            if (recommandedCnt >= maxLimit || maxLimit == 0) {
                // 如果已经推荐数大于设置数， 则跳过此出借人
                continue;
            }

            // 按借款人条件过滤出借人
            UserProperty p =
                accountDao.selectUserPropertyByUserIdAndName(lender.getUserId(), Constants.FILTER_EXPRESSION);
            if (p == null || isMatchJexlExpress(p.getValue(), user, score, platform)) {
                lenders.add(lender);
            }
        }

        // 按分组过滤候先lenders
        lenders = filterLendersByGroup(lenders, policy.getGroups());

        if (lenders.size() != 0 && lenders.size() <= recommandMaxCnt) {
            // 待筛选出借人数少于需要推荐的人数， 直接返回
            return lenders;
        }

        // 决策最终的推荐出借人(2-3人)

        Set<User> sourceLenders = new HashSet<User>();
        Set<User> exsSourceLenders = new HashSet<User>();
        // 通过来源配置，确定第一批出借方
        for (User lender : lenders) {
            String sources = lender.getPropertyVal(UserProperty.P_CHANNEL_SOURCES);
            if (!StringUtil.isEmpty(sources) && sources.indexOf(sourceId) != -1) {
                sourceLenders.add(lender);
            } else {
                exsSourceLenders.add(lender);
            }
        }

        int size = sourceLenders.size();
        Set<User> resultList = null;
        if (size >= recommandMaxCnt) {
            // 直接从来源列表，决定最终推荐的出借人
            resultList = getRecommand(recommandMaxCnt, sourceLenders);
        } else {
            // 从非来源列表决策出 剩下的出借人 recommandMaxCnt - size
            resultList = getRecommand(recommandMaxCnt - size, exsSourceLenders);
            resultList.addAll(sourceLenders);
        }
        return resultList;
    }

    // 以下由概率计算推荐的用户， 返回下标
    private Set<User> getRecommand(int recommandCnt, Set<User> lenders) {
        // 如果为空，直接返回
        if (lenders == null) {
            return new HashSet<User>();
        }
        // 如果数据量少于推荐量，直接返回
        if (lenders.size() <= recommandCnt) {
            return lenders;
        }

        // 按权重筛选推荐列表
        int size = lenders.size();
        double totalP = 0.0;
        User[] users = new User[size];
        double[] userPs = new double[size];
        int index = 0;

        for (User user : lenders) {
            int weight = user.getPropertyIntVal(UserProperty.P_CHANNEL_WEIGHT, 1);
            totalP += weight;
            users[index] = user;
            userPs[index] = totalP;
            index++;
        } ;

        Set<User> recommandUsers = new HashSet<User>();
        int cnt = 0;

        logger.info("percent {}", userPs);
        for (int i = 0; i < recommandCnt;) {
            double p = Math.random() * totalP;
            int userIndex = 0;
            for (int j = 0; j < userPs.length; j++) {
                if (userPs[j] >= p) {
                    userIndex = j;
                    break;
                }
            }
            logger.info("random p = " + p);
            logger.info("userIndex = " + userIndex);
            if (!recommandUsers.contains(users[userIndex])) {
                recommandUsers.add(users[userIndex]);
                cnt++;
            }

            if (cnt >= recommandCnt) {
                break;
            }
        }

        return recommandUsers;
    }

    /**
     * 过滤同一个组的用户，避免将相当的用户推荐给相同的公司，不同的组
     * 
     * @param currRecommandUsers
     * @param selectedUser
     * @param groups
     * @return
     */
    private Set<User> filterLendersByGroup(Set<User> lenderes, String[] groups) {
        Set<User> rSet = new HashSet<User>();
        Map<String, List<User>> groupUsers = new HashMap<String, List<User>>();
        for (User u1 : lenderes) {
            String gName = findGroupStr(u1.getUserId(), groups);
            if (gName == null) {
                // 未找到任何分组
                logger.info("add to list: userId =" + u1.getUserId());
                rSet.add(u1);
            } else {
                // 找到分组
                List<User> gList = groupUsers.get(gName);
                if (gList == null) {
                    gList = new ArrayList<User>();
                    groupUsers.put(gName, gList);
                }
                gList.add(u1);
            }
        }

        // 遍历并随机选择各组里的待推荐用户
        groupUsers.forEach((gName, gList) -> {
            int index = (int)(Math.random() * gList.size());
            rSet.add(gList.get(index));
            logger.info("add to list: userId =" + gList.get(index).getUserId());
        });

        return rSet;
    }

    /**
     * 检查用户是否在同一个组,并返回分组的ID串
     * 
     * @param user1
     * @param user2
     * @param groups
     * @return
     */
    private String findGroupStr(int user, String[] groups) {
        logger.info(">findGroupStr user = " + user);
        if (groups == null || groups.length == 0) {
            logger.info("<findGroupStr gStr= null");
            return null;
        }
        for (String gStr : groups) {
            if (gStr.indexOf(String.valueOf(user)) != -1) {
                logger.info("<findGroupStr gStr = " + gStr);
                return gStr;
            }
        }
        logger.info("<findGroupStr gStr= null");
        return null;
    }

    private void saveApplayUser(int userId, int[] targetUserIds) {
        try {
            logger.info("userId = " + userId);
            for (int i = 0; i < targetUserIds.length; i++) {
                logger.info("------->" + targetUserIds[i]);
                Integer cnt = userCntMap.get(targetUserIds[i]);
                userCntMap.put(targetUserIds[i], cnt == null ? 1 : ++cnt);
            }

            accountService.saveUserApplyRecords(userId, targetUserIds);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void printCnt() {
        int total = 0;
        for (Entry<Integer, Integer> e : userCntMap.entrySet()) {
            logger.info(e.getKey() + "\t" + e.getValue());
            total += e.getValue();
        }
        logger.info(" total \t" + total);
    }

}
