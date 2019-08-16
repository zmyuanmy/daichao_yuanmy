package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.jbb.mgt.core.dao.ChannelDao;
import com.jbb.mgt.core.dao.UserDao;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.mapper.Mapper;
import com.jbb.mgt.core.domain.mq.RegisterEvent;
import com.jbb.mgt.core.service.RecommandService;
import com.jbb.mgt.core.service.RegisterEventService;
import com.jbb.mgt.server.core.util.JexlUtil;
import com.jbb.mgt.server.core.util.OrgDataFlowSettingsUtil;
import com.jbb.mgt.server.core.util.RegisterEventUtil;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.mq.MqClient;
import com.jbb.server.mq.Queues;

@Service("RegisterEventService")
public class RegisterEventServiceImpl implements RegisterEventService {

    private static int DELAY_STEP = 1;

    private static Logger logger = LoggerFactory.getLogger(RegisterEventServiceImpl.class);

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RecommandService recommandService;

    @Override
    public void publishRegisterEvent(RegisterEvent event) {
        try {
            final long msgExpiry = PropertyManager.getLongProperty("ppc.registeruser.message.expiry", 300L) * 1000L;
            MqClient.send(Queues.JBB_MGT_USER_REGISTER_QUEUE_ADDR, Mapper.toBytes(event), msgExpiry);
        } catch (Exception e) {
            logger.error("Exception in sending register user event", e);
        }

    }

    /**
     * 处理消息
     */
    @Override
    public void processMessage(RegisterEvent event) {
        logger.debug("> processMessage(), event= " + event);
        if (event == null) {
            logger.debug("< processMessage(), event is null");
            return;
        }

        if (StringUtil.isEmpty(event.getChannelCode()) || event.getUserId() == 0 || event.getStep() == 0) {
            // 来源不明渠道，或者用户ID为空，或者step为空。 不做推荐
            logger.debug("< processMessage(), chnanelCode is empty, or user id is not set, or step is not set");
            return;
        }
        int delay = event.getDelay();
        if (delay == 0) {
            delay = PropertyManager.getIntProperty("jbb.user.register.step.delay", 600);
        }
        // 如果是第一步，delay，稍后处理
        if (event.getStep() == DELAY_STEP) {
            logger.debug("delay to process, event= " + event);
            RegisterEventUtil.addRegisterEventWithDelay(event, delay);
        } else {
            // 其他步骤，马上处理
            logger.debug("processing, event = " + event);
            RegisterEventUtil.removeSendTask(event);
            processEvent(event);
        }
        logger.debug("< processMessage()");
    }

    /**
     * 处理RegisterEvent
     */
    @Override
    public void processEvent(RegisterEvent event) {
        logger.debug("> processEvent(), event= " + event);

        Channel channel = channelDao.selectChannelByCode(event.getChannelCode());
        User user = userDao.selectUserById(event.getUserId());

        if (!channel.isJbbChannel()) {
            // 非JBB渠道，不做推荐
            logger.debug("< processEvent(), channel is not JBB Channel, channel code = " + channel.getChannelCode());
            return;
        }

        if (user == null) {
            // 用户不存在，不做推荐
            logger.debug("< processEvent(), user not found");
            return;
        }

        // Start 检查用户是否处理过
        int userId = user.getUserId();
        if (OrgDataFlowSettingsUtil.APPLY_USER_SET.contains(userId)) {
            logger.warn("duplicate message, userId=" + userId);
            return;
        }
        // END

        // 继续推荐流程
        OrgDataFlowSettingsUtil.APPLY_USER_SET.add(userId);
        
        // 如果超过N天直接返回
        int days = PropertyManager.getIntProperty("jbb.user.applied.in.days", 30);
      
        if (isAppliedInXDays(userId, days)) {
            logger.info("user {} applied in {} days", userId, days);
            return ;
        }

        //List<Organization> orgs = recommandService.getRecommandOrgs(user, channel, event.getUserType(), false);
        // // 推荐至组织
        // int[] orgIds = new int[orgs.size()];
        // int i = 0;
        // // 推荐的组织
        // for (Organization org : orgs) {
        // logger.debug(" org id = " + org.getOrgId() + " , org name = " + org.getName());
        // orgIds[i++] = org.getOrgId();
        // }

        // //4. 提交， 目前是后台提交，后续做接口，前端调用过来提交
        // recommandService.applyToOrgs(user, orgs);

        // 推荐给创世：0831 - VincentTang
        int orgId = event.getSpecialRecommandOrgId();
        
        //添加过滤条件检查
        Organization org = OrgDataFlowSettingsUtil.ORGS.get(orgId);
        String script = org.getFilterScript();
        boolean executeFlag  = false;
        if(!StringUtil.isEmpty(script)){
            executeFlag = JexlUtil.executeJexl(script, user, channel);
        }else{
            executeFlag = true;
        }
       
        if (executeFlag){
            recommandService.applyToOrg(user, orgId, Constants.USER_TYPE_REGISTER, channel.getChannelCode());
        }
        
        logger.debug("< processEvent()");
    }
    
    // 检查在天内是否申请过，并且有推荐过给相应出借方
    private boolean isAppliedInXDays(int userId, int days) {
        long todayStart = DateUtil.getStartTsOfDay(DateUtil.getCurrentTime());
        Timestamp start = new Timestamp(todayStart - days * DateUtil.DAY_MILLSECONDES);
        return userDao.checkUserApplied(userId, start);
    }

}
