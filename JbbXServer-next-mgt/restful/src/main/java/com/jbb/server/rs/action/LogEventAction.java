package com.jbb.server.rs.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jbb.server.core.service.UserEventLogService;

@Service(LogEventAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LogEventAction extends BasicAction {

    public static final String ACTION_NAME = "LogEventAction";

    @Autowired
    UserEventLogService userEventLogService;

    public void createLogEvent( String sourceId, String eventName, String eventLabel, String eventAction,
        String eventValue) {
        Integer userId = null;
        if(this.user!=null){
            userId =  this.user.getUserId();
        }
        userEventLogService.insertEventLog(userId, sourceId, eventName, eventAction, eventLabel, eventValue, this.getRemoteAddress());
    }

}