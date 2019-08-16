package com.jbb.mgt.server.rs.services;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.rs.action.ChannelStaDailyAction.ChannelStaDailyAction;
import com.jbb.mgt.rs.action.channelAccountInfo.ChannelAccountInfoAction;
import com.jbb.mgt.rs.action.fin.FinMerchantAction;
import com.jbb.mgt.rs.action.fin.FinOrgDelStaDailyAction;
import com.jbb.mgt.rs.action.finOrgSalesRelation.FinOrgSalesRelationAction;
import com.jbb.mgt.rs.action.finfile.FinFileAction;
import com.jbb.mgt.rs.action.loanChannel.LoanChannelAction;
import com.jbb.mgt.rs.action.loanstatistic.LoanPlatformStatisticAction;
import com.jbb.mgt.rs.action.mgtFinOrgStatisticDaily.FinAdStatisticDailyAction;
import com.jbb.mgt.rs.action.mgtFinOrgStatisticDaily.MgtFinOrgStatisticDailyAction;
import com.jbb.mgt.rs.action.mgtFinOrgStatisticDaily.OrgChannelStatisticDailyAction;
import com.jbb.mgt.rs.action.platformUv.PlatformUvAction;
import com.jbb.mgt.rs.action.summaryReport.SummaryReportAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;

@Path("fin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbMgtFinService extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbMgtFinService.class);

    @POST
    @Path("/file")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png"})
    public ActionResponse insertFinFile(@HeaderParam("API_KEY") String userKey, @QueryParam("orgId") Integer orgId,
        @QueryParam("merchantId") Integer merchantId, @QueryParam("platformId") Integer platformId,
        @QueryParam("fileType") Integer fileType, @QueryParam("fileDateTs") String fileDateTs, byte[] content) {
        logger.debug(">insertFinFile()");
        FinFileAction action = getBean(FinFileAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();

        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_115, Constants.MGT_P1_116, Constants.MGT_P1_117, Constants.MGT_P1_118,
            Constants.MGT_P1_119, Constants.MGT_P1_120};
        action.validateUserAccess(ps);
        action.insertFinFile(orgId, merchantId, platformId, fileType, fileDateTs, content);
        logger.debug("<insertFinFile()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/file/{fileId}")
    public ActionResponse deleteFinFile(@HeaderParam("API_KEY") String userKey, @PathParam("fileId") String fileId) {
        logger.debug(">deleteFinFile()");
        FinFileAction action = getBean(FinFileAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_115, Constants.MGT_P1_116, Constants.MGT_P1_117, Constants.MGT_P1_118,
            Constants.MGT_P1_119, Constants.MGT_P1_120};
        action.validateUserAccess(ps);
        action.deleteFinFile(fileId);
        logger.debug("<deleteFinFile()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channels/statisticByDate/{statisticDate}")
    public ActionResponse channelStatisticByDate(@HeaderParam("API_KEY") String userKey,
        @PathParam("statisticDate") String statisticDate, @QueryParam("groupName") String groupName,
        @QueryParam("salesId") Integer salesId) throws ParseException {
        ChannelStaDailyAction action = getBean(ChannelStaDailyAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_120, Constants.MGT_P1_135, Constants.MGT_P1_136, Constants.MGT_P1_137};
        action.validateUserAccess(ps);
        action.statisticByDate(statisticDate, groupName, salesId);
        return action.getActionResponse();
    }

    @GET
    @Path("/channels/statisticByChannelCode")
    public ActionResponse statisticByChannelCode(@HeaderParam("API_KEY") String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("channelCode") String channelCode, @QueryParam("groupName") String groupName,
        @QueryParam("salesId") Integer salesId) throws ParseException {
        ChannelStaDailyAction action = getBean(ChannelStaDailyAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_120, Constants.MGT_P1_135, Constants.MGT_P1_136, Constants.MGT_P1_137};
        action.validateUserAccess(ps);
        action.statisticByChannelCode(channelCode, startDate, endDate, groupName, salesId);
        return action.getActionResponse();
    }

    @PUT
    @Path("/channels/statisticStatus")
    public ActionResponse statisticStatus(@HeaderParam("API_KEY") String userKey,
        @QueryParam("statisticDate") String statisticDate, @QueryParam("channelCode") String channelCode,
        ChannelStaDailyAction.Request request) {
        ChannelStaDailyAction action = getBean(ChannelStaDailyAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_133, Constants.MGT_P1_135, Constants.MGT_P1_136, Constants.MGT_P1_137};
        action.validateUserAccess(ps);
        action.statisticStatus(statisticDate, channelCode, request);
        return action.getActionResponse();
    }

    @PUT
    @Path("/channels/statistic")
    public ActionResponse updateChannelStatisticDailys(@HeaderParam(API_KEY) String userKey,
        ChannelStaDailyAction.Request req) {
        logger.debug(">updateChannelStatisticDailys()");
        ChannelStaDailyAction action = getBean(ChannelStaDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_137, Constants.MGT_P1_135};
        action.validateUserAccess(ps);
        action.updateStatisticDailies(req);
        logger.debug("<updateChannelStatisticDailys()");
        return action.getActionResponse();
    }

    @POST
    @Path("/channel/bankInfo")
    public ActionResponse insertChannelAccountInfo(@HeaderParam("API_KEY") String userKey,
        ChannelAccountInfoAction.Request request) {
        logger.debug(">insertChannelAccountInfo()");
        ChannelAccountInfoAction action = getBean(ChannelAccountInfoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_103};
        action.validateUserAccess(ps);
        action.insertChannelAccountInfo(request);
        logger.debug("<insertChannelAccountInfo()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channel/bankInfo")
    public ActionResponse getChannelAccountInfo(@HeaderParam("API_KEY") String userKey,
        @QueryParam("channelCode") String channelCode) {
        logger.debug(">getChannelAccountInfo()");
        ChannelAccountInfoAction action = getBean(ChannelAccountInfoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_103};
        action.validateUserAccess(ps);
        action.selectChannelAccountInfo(channelCode);
        logger.debug("<getChannelAccountInfo()");
        return action.getActionResponse();
    }

    @POST
    @Path("/org/{orgId}/sales/{accountId}")
    public ActionResponse insertFinOrgSalesRelation(@HeaderParam("API_KEY") String userKey,
        @PathParam("orgId") Integer orgId, @PathParam("accountId") Integer accountId,
        FinOrgSalesRelationAction.Request req) {
        logger.debug(">insertFinOrgSalesRelation()");
        FinOrgSalesRelationAction action = getBean(FinOrgSalesRelationAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_105};
        action.validateUserAccess(ps);
        action.insertFinOrgSalesRelation(orgId, accountId, req);
        logger.debug("<insertFinOrgSalesRelation()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/org/{orgId}/sales/{accountId}")
    public ActionResponse deleteFinOrgSalesRelation(@HeaderParam("API_KEY") String userKey,
        @PathParam("orgId") Integer orgId, @PathParam("accountId") Integer accountId) {
        logger.debug(">deleteFinOrgSalesRelation()");
        FinOrgSalesRelationAction action = getBean(FinOrgSalesRelationAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_105};
        action.validateUserAccess(ps);
        action.deleteFinOrgSalesRelation(orgId, accountId);
        logger.debug("<deleteFinOrgSalesRelation()");
        return action.getActionResponse();
    }

    @GET
    @Path("/orgs/statisticByDate/{statisticDate}")
    public ActionResponse statisticByDate(@HeaderParam("API_KEY") String userKey,
        @PathParam("statisticDate") String statisticDate, @QueryParam("type") String type,
        @QueryParam("salesId") Integer salesId) {
        logger.debug(">statisticByDate()");
        MgtFinOrgStatisticDailyAction action = getBean(MgtFinOrgStatisticDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_115, Constants.MGT_P1_116, Constants.MGT_P1_117};
        action.validateUserAccess(ps);
        action.statisticByDate(statisticDate, type, salesId);
        logger.debug("<statisticByDate()");
        return action.getActionResponse();
    }

    @GET
    @Path("/orgs/statisticByOrgId")
    public ActionResponse statisticByOrgId(@HeaderParam("API_KEY") String userKey, @QueryParam("orgId") String orgId,
        @QueryParam("type") String type, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">statisticByOrgId()");
        MgtFinOrgStatisticDailyAction action = getBean(MgtFinOrgStatisticDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_115, Constants.MGT_P1_116, Constants.MGT_P1_117};
        action.validateUserAccess(ps);
        action.statisticByOrgId(startDate, endDate, type, Integer.parseInt(orgId));
        logger.debug("<statisticByOrgId()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/org/statisticStatus")
    public ActionResponse changeStatisticStatus(@HeaderParam("API_KEY") String userKey,
        @QueryParam("orgId") Integer orgId, @QueryParam("type") Integer type,
        @QueryParam("statisticDate") String statisticDate, MgtFinOrgStatisticDailyAction.Request req) {
        logger.debug(">changeStatisticStatus()");
        MgtFinOrgStatisticDailyAction action = getBean(MgtFinOrgStatisticDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_136, Constants.MGT_P1_137, Constants.MGT_P1_115, Constants.MGT_P1_116,
            Constants.MGT_P1_117};
        action.validateUserAccess(ps);
        action.updateStatistic(statisticDate, type, orgId, req);
        logger.debug("<changeStatisticStatus()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/org/statisticDaily")
    public ActionResponse saveFinOrgStatisticDaily(@HeaderParam(API_KEY) String userKey,
        MgtFinOrgStatisticDailyAction.Request req) {
        logger.debug(">saveFinOrgStatisticDaily()");
        MgtFinOrgStatisticDailyAction action = getBean(MgtFinOrgStatisticDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_137};
        action.validateUserAccess(ps);
        action.updatemgtFinOrgStatisticDaily(req);
        logger.debug("<saveFinOrgStatisticDaily()");
        return action.getActionResponse();
    }

    @GET
    @Path("/merchants/statisticByDate/{statisticDate}")
    public ActionResponse getMerchantByDate(@HeaderParam("API_KEY") String userKey,
        @PathParam("statisticDate") String statisticDate) {
        logger.debug(">getMerchantByDate()");
        FinMerchantAction action = getBean(FinMerchantAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_118};
        action.validateUserAccess(ps);
        action.getMerchantByDate(statisticDate);
        logger.debug("<getMerchantByDate()");
        return action.getActionResponse();
    }

    @GET
    @Path("/merchants/statisticById")
    public ActionResponse getMerchantByDate(@HeaderParam("API_KEY") String userKey,
        @QueryParam("merchantId") Integer merchantId, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getMerchantById()");
        FinMerchantAction action = getBean(FinMerchantAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_118};
        action.validateUserAccess(ps);
        action.getMerchantById(merchantId, startDate, endDate);
        logger.debug("<getMerchantById()");
        return action.getActionResponse();
    }

    @POST
    @Path("/merchant/statisticStatus")
    public ActionResponse saveMerchant(@HeaderParam("API_KEY") String userKey,
        @QueryParam("statisticDate") String statisticDate, @QueryParam("merchantId") Integer merchantId,
        FinMerchantAction.Request req) {
        logger.debug(">getMerchantById()");
        FinMerchantAction action = getBean(FinMerchantAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();

        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_136, Constants.MGT_P1_137};
        action.validateUserAccess(ps);

        action.saveMerchant(merchantId, statisticDate, req);
        logger.debug("<getMerchantById()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/merchants/statistic")
    public ActionResponse updateFinMerchantList(@HeaderParam(API_KEY) String userKey, FinMerchantAction.Request req) {
        logger.debug(">updateFinMerchantList()");
        FinMerchantAction action = getBean(FinMerchantAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_137};
        action.validateUserAccess(ps);
        action.updateMerchant(req);
        logger.debug("<updateFinMerchantList()");
        return action.getActionResponse();
    }

    @GET
    @Path("/report/profit/{startDate}")
    public ActionResponse getFinOrgStatisticDaily(@HeaderParam("API_KEY") String userKey,
        @QueryParam("type") Integer type, @QueryParam("salesId") Integer salesId,
        @PathParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        logger.debug(">getFinOrgStatisticDaily()");
        OrgChannelStatisticDailyAction action = getBean(OrgChannelStatisticDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_112, Constants.MGT_P1_113, Constants.MGT_P1_114};
        action.validateUserAccess(ps);
        action.getFinOrgStatisticDaily(type, salesId, startDate, endDate);
        logger.debug("<getFinOrgStatisticDaily()");
        return action.getActionResponse();
    }

    @GET
    @Path("/report/summary/{startDate}")
    public ActionResponse getSummaryByDate(@HeaderParam("API_KEY") String userKey,
        @PathParam("startDate") String startDate, @QueryParam("endDate") String endDate) throws ParseException {
        logger.debug(">getSummaryByDate()");
        SummaryReportAction action = getBean(SummaryReportAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_111};
        action.validateUserAccess(ps);
        action.getSummaryByDate(startDate, endDate);
        logger.debug("<getSummaryByDate()");
        return action.getActionResponse();
    }

    @GET
    @Path("/loanPlatform/statisticByDate/{statisticDate}")
    public ActionResponse getPlatformByDate(@HeaderParam("API_KEY") String userKey,
        @PathParam("statisticDate") String statisticDate, @QueryParam("groupName") String groupName,
        @QueryParam("type") Integer type, @QueryParam("salesId") Integer salesId) {
        logger.debug(">getPlatformByDate()");
        LoanPlatformStatisticAction action = getBean(LoanPlatformStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_119, Constants.MGT_P1_133, Constants.MGT_P1_137};
        action.validateUserAccess(ps);
        action.getPlatformByDate(statisticDate, groupName, type, salesId);
        logger.debug("<getPlatformByDate()");
        return action.getActionResponse();
    }

    @GET
    @Path("/loanPlatform/statisticById")
    public ActionResponse getPlatformByDate(@HeaderParam("API_KEY") String userKey,
        @QueryParam("platformId") Integer platformId, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate, @QueryParam("groupName") String groupName,
        @QueryParam("type") Integer type, @QueryParam("salesId") Integer salesId) {
        logger.debug(">getPlatformByDate()");
        LoanPlatformStatisticAction action = getBean(LoanPlatformStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_119, Constants.MGT_P1_133, Constants.MGT_P1_137};
        action.validateUserAccess(ps);
        action.getPlatformById(platformId, startDate, endDate, groupName, type, salesId);
        logger.debug("<getPlatformByDate()");
        return action.getActionResponse();
    }

    @POST
    @Path("/loanPlatform/statisticStatus")
    public ActionResponse savePlatform(@HeaderParam("API_KEY") String userKey,
        @QueryParam("statisticDate") String statisticDate, @QueryParam("platformId") Integer platformId,
        LoanPlatformStatisticAction.Request req) {
        logger.debug(">savePlatform()");
        LoanPlatformStatisticAction action = getBean(LoanPlatformStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_133, Constants.MGT_P1_137, Constants.MGT_P1_142};
        action.validateUserAccess(ps);
        action.savePlatform(platformId, statisticDate, req);
        logger.debug("<savePlatform()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/loanPlatform/statistic")
    public ActionResponse updatePlatformStatisticList(@HeaderParam(API_KEY) String userKey,
        LoanPlatformStatisticAction.Request req) {
        logger.debug(">updatePlatformStatisticList()");
        LoanPlatformStatisticAction action = getBean(LoanPlatformStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_137};
        action.validateUserAccess(ps);
        action.updatePlatformDetailList(req);
        logger.debug("<updatePlatformStatisticList()");
        return action.getActionResponse();
    }

    @GET
    @Path("/report/adProfit/{startDate}")
    public ActionResponse getFinAdStatisticDaily(@HeaderParam("API_KEY") String userKey,
        @QueryParam("salesId") Integer salesId, @PathParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getFinAdStatisticDaily()");
        FinAdStatisticDailyAction action = getBean(FinAdStatisticDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102};
        action.validateUserAccess(ps);
        action.getFinAdStatisticDaily(salesId, startDate, endDate);
        logger.debug("<getFinAdStatisticDaily()");
        return action.getActionResponse();
    }

    @GET
    @Path("/orgdel/statisticByDate/{statisticDate}")
    public ActionResponse getOrgDelStatistics(@HeaderParam("API_KEY") String userKey,
        @PathParam("statisticDate") String statisticDate, @QueryParam("salesId") Integer salesId) {
        logger.debug(">getOrgDelStatistics()");
        FinOrgDelStaDailyAction action = getBean(FinOrgDelStaDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_117};
        action.validateUserAccess(ps);
        action.getOrgDelStatistics(statisticDate, salesId);
        logger.debug("<getOrgDelStatistics()");
        return action.getActionResponse();
    }

    @GET
    @Path("/orgdel/statisticByOrgId")
    public ActionResponse getFinOrgDelStatisticDailys(@HeaderParam("API_KEY") String userKey,
        @QueryParam("orgId") Integer orgId, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getFinOrgDelStatisticDailys()");
        FinOrgDelStaDailyAction action = getBean(FinOrgDelStaDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_117};
        action.validateUserAccess(ps);
        action.getFinOrgDelStatisticDailys(orgId, startDate, endDate);
        logger.debug("<getPlatformByDate()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/orgdel/statisticStatus")
    public ActionResponse updateFinOrgDelStatisticDaily(@HeaderParam("API_KEY") String userKey,
        @QueryParam("statisticDate") String statisticDate, @QueryParam("orgId") Integer orgId,
        FinOrgDelStaDailyAction.Request req) {
        logger.debug(">updateFinOrgDelStatistic()");
        FinOrgDelStaDailyAction action = getBean(FinOrgDelStaDailyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_136, Constants.MGT_P1_137};
        action.validateUserAccess(ps);
        action.updateFinOrgDelStatistic(orgId, statisticDate, req);
        logger.debug("<updateFinOrgDelStatistic()");
        return action.getActionResponse();
    }

    @GET
    @Path("/loanPlatformDetail/statisticByDate")
    public ActionResponse getPlatformDetailByDate(@HeaderParam("API_KEY") String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("salesId") Integer salesId) {
        logger.debug(">getPlatformDetailByDate()");
        LoanPlatformStatisticAction action = getBean(LoanPlatformStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_124};
        action.validateUserAccess(ps);
        action.getPlatformDetailByDate(startDate, endDate, salesId);
        logger.debug("<getPlatformDetailByDate()");
        return action.getActionResponse();
    }

    @GET
    @Path("/loanPlatformDetail/statisticById")
    public ActionResponse getPlatformDetailByDate(@HeaderParam("API_KEY") String userKey,
        @QueryParam("platformId") Integer platformId, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate, @QueryParam("salesId") Integer salesId) {
        logger.debug(">getPlatformByDate()");
        LoanPlatformStatisticAction action = getBean(LoanPlatformStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_124};
        action.validateUserAccess(ps);
        action.getPlatformDetailById(platformId, startDate, endDate, salesId);
        logger.debug("<getPlatformByDate()");
        return action.getActionResponse();
    }

    @GET
    @Path("/loanChannel/daily")
    public ActionResponse loanChannelsDaily(@HeaderParam(API_KEY) String userKey,
        @QueryParam("channelCode") String[] channelCodes, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">loanChannelsDaily()");
        LoanChannelAction action = getBean(LoanChannelAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        action.getLoanChannelStatisticByChannelCode(channelCodes, startDate, endDate);
        logger.debug("<loanChannelsDaily()");
        return action.getActionResponse();
    }

    @GET
    @Path("/loanChannel/compare")
    public ActionResponse loanChannelsCompare(@HeaderParam(API_KEY) String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        logger.debug(">loanChannelsCompare()");
        LoanChannelAction action = getBean(LoanChannelAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        action.getLoanChannelStatisticCompare(startDate, endDate);
        logger.debug("<loanChannelsCompare()");
        return action.getActionResponse();
    }

    @GET
    @Path("/platform/uv")
    public ActionResponse getPlatformUv(@HeaderParam(API_KEY) String userKey, @QueryParam("salesId") Integer salesId,
        @QueryParam("groupName") String groupName, @QueryParam("platformId") Integer platformId,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        logger.debug(">getPlatformUv()");
        PlatformUvAction action = getBean(PlatformUvAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_140};
        action.validateUserAccess(ps);
        action.getPlatformUv(salesId, groupName, platformId, startDate, endDate);
        logger.debug("<getPlatformUv()");
        return action.getActionResponse();
    }

}
