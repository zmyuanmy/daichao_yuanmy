package com.jbb.mgt.server.rs.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.rs.action.app.AppConfigAction;
import com.jbb.mgt.rs.action.loanAreaTag.LoanAreaTagAction;
import com.jbb.mgt.rs.action.pf.LoanPlatformAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Path("pf")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JBBPfServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JBBPfServices.class);

    /*@GET
    @Path("/platforms")
    public ActionResponse getPlatformByTagId(@HeaderParam(API_KEY) String userKey,
        @QueryParam("areaTagId") int[] areaTagIds, @QueryParam("getConfig") Boolean getConfig,
        @QueryParam("os") String os) {
        logger.debug(">getPlatformByTagId()");
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.getPlatformByTagId(areaTagIds, getConfig, os);
        logger.debug("<getPlatformByTagId()");
        return action.getActionResponse();
    }*/

    @GET
    @Path("v2/platforms")
    public ActionResponse getPlatformByTagIdV2(@HeaderParam(API_KEY) String userKey,
        @QueryParam("areaTagId") int[] areaTagIds, @QueryParam("getConfig") Boolean getConfig,
        @QueryParam("os") String os) {
        logger.debug(">getPlatformByTagIdV2()");
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        /*action.validateEntryUserKey(userKey);*/
        action.getPlatformByTagIdV2(areaTagIds, getConfig, os);
        logger.debug("<getPlatformByTagIdV2()");
        return action.getActionResponse();
    }

    @GET
    @Path("/loanTags")
    public ActionResponse getLoanTags(@HeaderParam(API_KEY) String userKey, @QueryParam("os") String os,
        @QueryParam("appName") String appName) {
        logger.debug(">getLoanTags()");
        LoanAreaTagAction action = getBean(LoanAreaTagAction.ACTION_NAME);
        /* action.validateEntryUserKey(userKey);*/
        action.getLoanTags(appName, os);
        logger.debug("<getLoanTags()");
        return action.getActionResponse();
    }

    @GET
    @Path("/publish/platforms")
    public ActionResponse getPlatformPublishByDate(@HeaderParam(API_KEY) String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        logger.debug(">getPlatformPublishByDate()");
        LoanPlatformAction action = getBean(LoanPlatformAction.ACTION_NAME);
        /* action.validateEntryUserKey(userKey);*/
        action.getPlatformPublishByDate(startDate, endDate);
        logger.debug("<getPlatformPublishByDate()");
        return action.getActionResponse();
    }

    @GET
    @Path("/appConfig")
    public String getAppConfig(@QueryParam("appName") String appName, @QueryParam("version") String version,
        @QueryParam("os") String os, @QueryParam("channel") String channel) {
        logger.debug(">getAppConfig()");
        AppConfigAction action = getBean(AppConfigAction.ACTION_NAME);
        String config = action.getAppConfig(appName, version, os, channel);
        logger.debug("<getAppConfig()");
        return config;
    }

}
