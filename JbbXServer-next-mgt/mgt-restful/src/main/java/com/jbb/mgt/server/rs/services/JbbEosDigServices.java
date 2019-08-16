package com.jbb.mgt.server.rs.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.rs.action.eos.EosDigHistoryAction;
import com.jbb.mgt.rs.action.eos.EosStrategyAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Path("dig")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbEosDigServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbEosDigServices.class);

    /**
     * 上报挖矿数据
     * 
     * @param request
     * @return
     */
    @POST
    @Path("/history")
    public ActionResponse insertEosDigHistory(EosDigHistoryAction.Request request) {
        logger.debug(">insertEosDigHistory() request= " + request);
        EosDigHistoryAction action = getBean(EosDigHistoryAction.ACTION_NAME);
        action.insertEosDigHistory(request);
        logger.debug(">insertEosDigHistory()");
        return action.getActionResponse();
    }

    /**
     * 获取最近50条挖矿数据
     * 
     * @return
     */
    @GET
    @Path("/history")
    public ActionResponse getEosDigHistory() {
        logger.debug(">getEosDigHistory()");
        EosDigHistoryAction action = getBean(EosDigHistoryAction.ACTION_NAME);
        action.getEosDigHistory();
        logger.debug(">getEosDigHistory()");
        return action.getActionResponse();
    }

    /**
     * 获取策略
     * 
     * @param tokenName
     * @param strategyId
     * @return
     */
    @GET
    @Path("/strategy/{tokenName}/{strategyId}")
    public ActionResponse getStrategyById(@PathParam("tokenName") String tokenName,
        @PathParam("strategyId") String strategyId) {
        logger.debug(">getStrategyById()");
        EosStrategyAction action = getBean(EosStrategyAction.ACTION_NAME);
        action.getStrategyListByName(tokenName, strategyId);
        logger.debug(">getStrategyById()");
        return action.getActionResponse();
    }

    /**
     * 获取策略列表
     * 
     * @param tokenName
     * @return
     */
    @GET
    @Path("/strategyList/{tokenName}")
    public ActionResponse getStrategyList(@PathParam("tokenName") String tokenName) {
        logger.debug(">getStrategyList()");
        EosStrategyAction action = getBean(EosStrategyAction.ACTION_NAME);
        action.getStrategyListByName(tokenName, null);
        logger.debug(">getStrategyList()");
        return action.getActionResponse();
    }

    /**
     * 获取token统计数据及获利返奖情况
     * 
     * @param tokenName
     * @return
     */
    @GET
    @Path("/{tokenName}/profit")
    public ActionResponse getEosDigHistoryProfit(@PathParam("tokenName") String tokenName) {
        logger.debug(">getEosDigHistoryProfit()");
        EosDigHistoryAction action = getBean(EosDigHistoryAction.ACTION_NAME);
        action.getEosDigHistoryProfit(tokenName);
        logger.debug(">getEosDigHistoryProfit()");
        return action.getActionResponse();
    }

}
