package com.jbb.mgt.server.rs.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.rs.action.ChannelStaDailyAction.ChannelsDailyAction;
import com.jbb.mgt.rs.action.ChannelStaDailyAction.StatisticFeedbackDailyAction;
import com.jbb.mgt.rs.action.channelAppFunnel.ChannelAppFunnelAction;
import com.jbb.mgt.rs.action.channelFunnel.ChannelFunnelAction;
import com.jbb.mgt.rs.action.channelFunnelFilter.ChannelFunnelFilterAction;
import com.jbb.mgt.rs.action.channelUser.ChannelUserAction;
import com.jbb.mgt.rs.action.fin.FinMerchantAction;
import com.jbb.mgt.rs.action.ipStatistic.IpAddressStatisticAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;

@Path("statistic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbMgtStatisticServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbMgtStatisticServices.class);

    @GET
    @Path("/channels/daily")
    public ActionResponse channelsDaily(@HeaderParam(API_KEY) String userKey,
        @QueryParam("channelCode") String[] channelCodes, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate, @QueryParam("userType") Integer userType) {
        logger.debug(">channelsDaily()");

        ChannelsDailyAction action = getBean(ChannelsDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        action.getChannelsDaily(channelCodes, startDate, endDate, userType);
        logger.debug("<channelsDaily()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channels/compare")
    public ActionResponse channelsCompare(@HeaderParam(API_KEY) String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("userType") Integer userType) {
        logger.debug(">channelsCompare()");

        ChannelsDailyAction action = getBean(ChannelsDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        action.getChannelsCompare(startDate, endDate, userType);
        logger.debug("<channelsCompare()");
        return action.getActionResponse();
    }

    /*  @GET
    @Path("/merchants/daily")
    public ActionResponse getMerchantDaily(@HeaderParam("API_KEY") String userKey,
        @QueryParam("merchantId") int[] merchantIds, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getMerchantDaily()");
        FinMerchantAction action = getBean(FinMerchantAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        action.getMerchantDaily(merchantIds, startDate, endDate);
        logger.debug("<getMerchantDaily()");
        return action.getActionResponse();
    }
    
    @GET
    @Path("/merchants/compare")
    public ActionResponse getMerchantCompare(@HeaderParam("API_KEY") String userKey,
        @QueryParam("merchantId") int[] merchantIds, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getMerchantCompare()");
        FinMerchantAction action = getBean(FinMerchantAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        action.getMerchantCompare(merchantIds, startDate, endDate);
        logger.debug("<getMerchantCompare()");
        return action.getActionResponse();
    }*/

    @GET
    @Path("/channel/feedback")
    public ActionResponse channelsFeedback(@HeaderParam(API_KEY) String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("channelCode") String channelCode, @QueryParam("userType") Integer userType) {
        logger.debug(">channelsFeedback()");
        StatisticFeedbackDailyAction action = getBean(StatisticFeedbackDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        action.getChannelFeedback(channelCode, startDate, endDate, userType);
        logger.debug("<channelsFeedback()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channelFunnel/daily")
    public ActionResponse getChannelFunnelDaily(@HeaderParam("API_KEY") String userKey,
        @QueryParam("channelCode") String[] channelCodes, @QueryParam("uType") Integer uType,
        @QueryParam("mode") Integer mode, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate, @QueryParam("salesId") Integer salesId) {
        logger.debug(">getChannelFunnelDaily()");
        ChannelFunnelAction action = getBean(ChannelFunnelAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_141};
        action.validateUserAccess(ps);
        action.getChannelFunnelDaily(channelCodes, uType, startDate, endDate, salesId, mode);
        logger.debug("<getChannelFunnelDaily()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channelFunnel/compare")
    public ActionResponse getChannelFunnelCompare(@HeaderParam("API_KEY") String userKey,
        @QueryParam("uType") Integer uType, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate, @QueryParam("salesId") Integer salesId, @QueryParam("mode") Integer mode,
        @QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {
        logger.debug(">getChannelFunnelCompare()");
        ChannelFunnelAction action = getBean(ChannelFunnelAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_141};
        action.validateUserAccess(ps);
        action.getChannelFunnelCompare(uType, startDate, endDate, salesId, mode, pageNo, pageSize);
        logger.debug("<getChannelFunnelCompare()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channelUser/daily")
    public ActionResponse getChannelUserDaily(@HeaderParam("API_KEY") String userKey,
        @QueryParam("channelCode") String channelCode, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getChannelFunnelDaily()");
        ChannelUserAction action = getBean(ChannelUserAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        action.getChannelUserDaily(channelCode, startDate, endDate);
        logger.debug("<getChannelFunnelDaily()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channelUser/compare")
    public ActionResponse getChannelUserCompare(@HeaderParam("API_KEY") String userKey,
        @QueryParam("statisticDate") String statisticDate) {
        logger.debug(">getChannelUserCompare()");
        ChannelUserAction action = getBean(ChannelUserAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        action.getChannelUserCompare(statisticDate);
        logger.debug("<getChannelUserCompare()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channelAppFunnel/daily")
    public ActionResponse getChannelAppFunnelDaily(@HeaderParam("API_KEY") String userKey,
        @QueryParam("channelCode") String[] channelCodes, @QueryParam("groupName") String groupName,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("salesId") Integer salesId) {
        logger.debug(">getChannelAppFunnelDaily()");
        ChannelAppFunnelAction action = getBean(ChannelAppFunnelAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P1_102, Constants.MGT_P1_101, Constants.MGT_P1_144};
        action.validateUserAccess(ps);
        action.getChannelFunnelDaily(channelCodes, groupName, startDate, endDate, salesId);
        logger.debug("<getChannelAppFunnelDaily()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channelAppFunnel/compare")
    public ActionResponse getChannelAppFunnelCompare(@HeaderParam("API_KEY") String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("salesId") Integer salesId) {
        logger.debug(">getChannelAppFunnelCompare()");
        ChannelAppFunnelAction action = getBean(ChannelAppFunnelAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P1_102, Constants.MGT_P1_101, Constants.MGT_P1_144};
        action.validateUserAccess(ps);
        action.getChannelAppFunnelCompare(startDate, endDate, salesId);
        logger.debug("<getChannelAppFunnelCompare()");
        return action.getActionResponse();
    }

    @GET
    @Path("ipStatistic")
    public ActionResponse getIpAddressStatistic(@HeaderParam(API_KEY) String userKey, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize, @QueryParam("registerCnt") Integer registerCnt,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        logger.debug(">getIpAddressStatistic()");
        IpAddressStatisticAction action = getBean(IpAddressStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_143};
        action.validateUserAccess(ps);
        action.getIpAddressStatistic(pageNo, pageSize, registerCnt, startDate, endDate);
        logger.debug("<getIpAddressStatistic()");
        return action.getActionResponse();
    }

    /*@GET
    @Path("ipUsers")
    public ActionResponse getIpAddressUsers(@HeaderParam(API_KEY) String userKey, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize, @QueryParam("ipAddress") String ipAddress,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        logger.debug(">getIpAddressUsers()");
        IpAddressStatisticAction action = getBean(IpAddressStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_143};
        action.validateUserAccess(ps);
        action.getIpAddressUsers(pageNo, pageSize, ipAddress, startDate, endDate);
        logger.debug("<getIpAddressUsers()");
        return action.getActionResponse();
    }*/

    @GET
    @Path("/channelFunnel/filter")
    public ActionResponse getChannelFunnelFilter(@HeaderParam("API_KEY") String userKey) {
        logger.debug(">getChannelFunnelFilter()");
        ChannelFunnelFilterAction action = getBean(ChannelFunnelFilterAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_145};
        action.validateUserAccess(ps);
        action.getChannelFunnelFilter();
        logger.debug("<getChannelFunnelFilter()");
        return action.getActionResponse();
    }

    @POST
    @Path("/channelFunnel/filter")
    public ActionResponse insertChannelFunnelFilter(@HeaderParam("API_KEY") String userKey,
        ChannelFunnelFilterAction.Request req) {
        logger.debug(">insertChannelFunnelFilter()");
        ChannelFunnelFilterAction action = getBean(ChannelFunnelFilterAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_145};
        action.validateUserAccess(ps);
        action.insertChannelFunnelFilter(req);
        logger.debug("<insertChannelFunnelFilter()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/channelFunnel/filter/{filterId}")
    public ActionResponse updatePropertiesByName(@HeaderParam(API_KEY) String userKey,
        @PathParam("filterId") Integer filterId, ChannelFunnelFilterAction.Request req) {
        logger.debug(">updateChannelFunnelFilter()");
        ChannelFunnelFilterAction action = getBean(ChannelFunnelFilterAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_145};
        action.validateUserAccess(ps);
        action.updateChannelFunnelFilter(filterId, req);
        logger.debug("<updateChannelFunnelFilter()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/channelFunnel/filter/{filterId}")
    public ActionResponse deleteChannelFunnelFilter(@HeaderParam("API_KEY") String userKey,
        @PathParam("filterId") Integer filterId) {
        logger.debug(">deleteChannelFunnelFilter()");
        ChannelFunnelFilterAction action = getBean(ChannelFunnelFilterAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_145};
        action.validateUserAccess(ps);
        action.deleteChannelFunnelFilter(filterId);
        logger.debug("<deleteChannelFunnelFilter()");
        return action.getActionResponse();
    }
}
