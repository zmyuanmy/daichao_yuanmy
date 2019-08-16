package com.jbb.mgt.rs.action.club;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.service.ClubService;
import com.jbb.mgt.rs.action.channel.ChannelAction;
import com.jbb.mgt.server.rs.action.BasicAction;

@Service(ClubNotifyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ClubNotifyAction extends BasicAction {

    public static final String ACTION_NAME = "ClubNotifyAction";

    private static Logger logger = LoggerFactory.getLogger(ChannelAction.class);

    @Autowired
    private ClubService clubService;

    public boolean notify(String event, String type, String time, String data, String sign, String params) {
        logger.debug(">notify,event =" + event + " , time=" + time + " , data=" + data + " , params=" + params
            + " , sign=" + sign);
        boolean flag= clubService.saveNotify(event, type, time, data, sign, params);
        logger.debug("<notify");
        return flag;
    }

}
