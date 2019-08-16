package com.jbb.mgt.rs.action.eos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jbb.mgt.rs.action.eos.EosDigHistoryAction.Response;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Service(EosStrategyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EosStrategyAction extends BasicAction {
    public static final String ACTION_NAME = "EosStrategyAction";

    private static Logger logger = LoggerFactory.getLogger(EosStrategyAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getStrategyListByName(String tokenName, String strategyId) {
        logger.debug(">getStrategyListByName() tokenName= " + tokenName + ",strategyId=" + strategyId);
        
        
        
        logger.debug("<getStrategyListByName()");
    }

}
