package com.jbb.mgt.server.rs.services;

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

import com.jbb.mgt.rs.action.loanAreaTag.LoanAreaTagAction;
import com.jbb.mgt.rs.action.pf.LoanPlatformAction;
import com.jbb.mgt.rs.action.pfmgt.LoanPlatformPublishAction;
import com.jbb.mgt.rs.action.pfmgt.LoanPlatformTagAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;

@Path("pfmgt")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JBBMgtPfServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JBBMgtPfServices.class);

    @GET
    @Path("/loanTags")
    public ActionResponse getLoanTags(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getLoanTags()");
        LoanAreaTagAction action = getBean(LoanAreaTagAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_130};
        action.validateUserAccess(ps);
        action.getLoanTagsForAdmin();
        logger.debug("<getLoanTags()");
        return action.getActionResponse();
    }

    @POST
    @Path("/platformPos")
    public ActionResponse insertLoanPlatformTag(@HeaderParam("API_KEY") String userKey,
        @QueryParam("platformId") int platformId, @QueryParam("areaTagId") int areaTagId, @QueryParam("pos") int pos) {
        logger.debug(">insertLoanPlatformTag()");
        LoanPlatformTagAction action = getBean(LoanPlatformTagAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_131};
        action.validateUserAccess(ps);
        action.insertLoanPlatformTag(platformId, areaTagId, pos);
        logger.debug("<insertLoanPlatformTag()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/platformPos")
    public ActionResponse deleteLoanPlatformTag(@HeaderParam("API_KEY") String userKey,
        @QueryParam("platformId") int platformId, @QueryParam("areaTagId") int areaTagId, @QueryParam("pos") int pos) {
        logger.debug(">deleteLoanPlatformTag()");
        LoanPlatformTagAction action = getBean(LoanPlatformTagAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_131};
        action.validateUserAccess(ps);
        action.deleteLoanPlatformTag(platformId, areaTagId, pos);
        logger.debug("<deleteLoanPlatformTag()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/loanTagStatus")
    public ActionResponse updateLoanTagsStatus(@HeaderParam(API_KEY) String userKey,
        @QueryParam("areaTagId") Integer areaTagId, @QueryParam("freeze") boolean freeze) {
        logger.debug(">updatePlatformStatus(), areaTagId= " + areaTagId + ", freeze= " + freeze);
        LoanAreaTagAction action = getBean(LoanAreaTagAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_130};
        action.validateUserAccess(ps);
        action.updateLoanTagsStatus(areaTagId, freeze);
        logger.debug("<updatePlatformStatus()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/loanTag")
    public ActionResponse updateLoanTags(@HeaderParam(API_KEY) String userKey, @QueryParam("areaTagId") int areaTagId,
        LoanAreaTagAction.Request req) {
        logger.debug(">updateLoanTags()");
        LoanAreaTagAction action = getBean(LoanAreaTagAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_130};
        action.validateUserAccess(ps);
        action.updateLoanTags(areaTagId, req);
        logger.debug("<updateLoanTags()");
        return action.getActionResponse();
    }

    @POST
    @Path("/loanTag")
    public ActionResponse saveLoanTags(@HeaderParam(API_KEY) String userKey, LoanAreaTagAction.Request req) {
        logger.debug(">saveLoanTags()");
        LoanAreaTagAction action = getBean(LoanAreaTagAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_130};
        action.validateUserAccess(ps);
        action.saveLoanTags(req);
        logger.debug("<saveLoanTags()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/loanTag")
    public ActionResponse deleteLoanTags(@HeaderParam(API_KEY) String userKey,
        @QueryParam("areaTagId") Integer areaTagId) {
        logger.debug(">deleteLoanTags()");
        LoanAreaTagAction action = getBean(LoanAreaTagAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_130};
        action.validateUserAccess(ps);
        action.deleteLoanTags(areaTagId);
        logger.debug("<deleteLoanTags()");
        return action.getActionResponse();
    }

    @GET
    @Path("/platform")
    public ActionResponse getPlatformById(@HeaderParam(API_KEY) String userKey,
        @QueryParam("platformId") Integer platformId) {
        logger.debug(">getplatformId(), platformId= " + platformId);
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.getPlatformById(platformId);
        logger.debug("<getplatformId()");
        return action.getActionResponse();
    }

    @GET
    @Path("/platforms")
    public ActionResponse getPlatforms(@HeaderParam(API_KEY) String userKey, @QueryParam("isDeleted") Boolean isDeleted,
        @QueryParam("groupName") String groupName, @QueryParam("type") Integer type,
        @QueryParam("platformName") String platformName, @QueryParam("salesId") Integer salesId) {
        logger.debug(">getPlatforms()");
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129, Constants.MGT_P1_119, Constants.MGT_P1_133, Constants.MGT_P1_137};
        action.validateUserAccess(ps);
        action.getPlatforms(isDeleted, groupName, type, platformName, salesId);
        logger.debug("<getPlatforms()");
        return action.getActionResponse();
    }

    @POST
    @Path("/platform")
    public ActionResponse insertPlatform(@HeaderParam(API_KEY) String userKey, LoanPlatformAction.Request request) {
        logger.debug(">insertPlatform() ");
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.insertPlatform(request);
        logger.debug("<insertPlatform()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/platform")
    public ActionResponse updatePlatformById(@HeaderParam(API_KEY) String userKey,
        @QueryParam("platformId") Integer platformId, LoanPlatformAction.Request request) {
        logger.debug(">updatePlatformById(), platformId= " + platformId);
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.updatePlatformById(platformId, request);
        logger.debug("<updatePlatformById()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/platformStatus")
    public ActionResponse updatePlatformStatus(@HeaderParam(API_KEY) String userKey,
        @QueryParam("platformId") Integer platformId, @QueryParam("isDeleted") boolean isDeleted) {
        logger.debug(">updatePlatformStatus(), platformId= " + platformId + ", isDeleted= " + isDeleted);
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_129};
        action.validateUserAccess(ps);
        action.updatePlatformStatus(platformId, isDeleted);
        logger.debug("<updatePlatformStatus()");
        return action.getActionResponse();
    }

    @GET
    @Path("/loanTag")
    public ActionResponse getLoanTag(@HeaderParam(API_KEY) String userKey, @QueryParam("areaTagId") Integer areaTagId) {
        logger.debug(">getLoanTag()");
        LoanAreaTagAction action = getBean(LoanAreaTagAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_130};
        action.validateUserAccess(ps);
        action.getLoanTagByLoanAreaTagId(areaTagId);
        logger.debug("<getLoanTag()");
        return action.getActionResponse();
    }

    @GET
    @Path("/platformPos")
    public ActionResponse getAreaPlatforms(@HeaderParam(API_KEY) String userKey, @QueryParam("areaId") Integer areaId,
        @QueryParam("tagName") String tagName, @QueryParam("platformId") Integer platformId,
        @QueryParam("pos") Integer pos) {
        logger.debug(">getPlatformTags()");
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_131};
        action.validateUserAccess(ps);
        action.getPlatformTags(areaId, tagName, platformId, pos);
        logger.debug("<getPlatformTags()");
        return action.getActionResponse();
    }

    @GET
    @Path("/areaLoanTags")
    public ActionResponse getLoanTagByAreaId(@HeaderParam(API_KEY) String userKey, @QueryParam("areaId") int areaId) {
        logger.debug(">getLoanTagByAreaId()");
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_131};
        action.validateUserAccess(ps);
        action.getLoanTagByAreaId(areaId);
        logger.debug("<getLoanTagByAreaId()");
        return action.getActionResponse();
    }

    @POST
    @Path("/publish/platform")
    public ActionResponse insertPlatformPublish(@HeaderParam(API_KEY) String userKey,
        LoanPlatformPublishAction.Request request) {
        logger.debug(">insertPlatformPublish()");
        LoanPlatformPublishAction action = getBean(LoanPlatformPublishAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_131};
        action.validateUserAccess(ps);
        action.insertPlatformPublish(request);
        logger.debug("<insertPlatformPublish()");
        return action.getActionResponse();
    }

    @PUT
    @Path("publish/platform/{id}")
    public ActionResponse updatePlatformPublish(@HeaderParam(API_KEY) String userKey, @PathParam("id") int id,
        LoanPlatformPublishAction.Request request) {
        logger.debug(">updatePlatformPublish()");
        LoanPlatformPublishAction action = getBean(LoanPlatformPublishAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_131};
        action.validateUserAccess(ps);
        action.updatePlatformPublish(id, request);
        logger.debug("<updatePlatformPublish()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("publish/platform/{id}")
    public ActionResponse deletePlatformPublish(@HeaderParam(API_KEY) String userKey, @PathParam("id") int id) {
        logger.debug(">deletePlatformPublish()");
        LoanPlatformPublishAction action = getBean(LoanPlatformPublishAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_131};
        action.validateUserAccess(ps);
        action.deletePlatformPublish(id);
        logger.debug("<deletePlatformPublish()");
        return action.getActionResponse();
    }

    @GET
    @Path("publish/platforms")
    public ActionResponse getPlatformPublishByDate(@HeaderParam(API_KEY) String userKey,
        @QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        logger.debug(">getPlatformPublishByDate()");
        LoanPlatformPublishAction action = getBean(LoanPlatformPublishAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102,
            Constants.MGT_P1_131};
        action.validateUserAccess(ps);
        action.getPlatformPublishByDate(pageNo, pageSize, startDate, endDate);
        logger.debug("<getPlatformPublishByDate()");
        return action.getActionResponse();
    }

    @GET
    @Path("/platform/groupName")
    public ActionResponse getPlatformGroupName(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getPlatformGroupName()");
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getPlatformGroupName();
        logger.debug("<getPlatformGroupName()");
        return action.getActionResponse();
    }

}
