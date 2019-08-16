package com.jbb.server.rs.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.server.rs.action.UserCheckJbbIdNameAction;
import com.jbb.server.rs.action.UserMgtIouCreatAction;
import com.jbb.server.rs.action.UserMgtIouUpdateAction;
import com.jbb.server.rs.action.UserSynchronizeAction;
import com.jbb.server.rs.action.intergrate.MobileAuthAction;
import com.jbb.server.rs.pojo.ActionResponse;
import com.jbb.server.rs.pojo.request.ReMgtIou;

@Path("integrate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbIntegrateService extends BasicRestfulServices {
    private static Logger logger = LoggerFactory.getLogger(JbbIntegrateService.class);

    @POST
    @Path("/auth/mobile")
    public ActionResponse notifyValidateMobile(@HeaderParam(API_KEY) String apiKey,
        @QueryParam("phoneNumber") String phoneNumber, @QueryParam("userId") int userId) {
        logger.debug(">notifyValidateMobile() phoneNumber=" + phoneNumber + ", userId=" + userId);
        MobileAuthAction action = getBean(MobileAuthAction.ACTION_NAME);
        action.validateMgtPlatformApiKey(apiKey);
        action.authMobile(phoneNumber, userId);
        logger.debug("<notifyValidateMobile()");
        return action.getActionResponse();
    }

    @POST
    @Path("/fill/iou")
    public ActionResponse fillMgtIou(@HeaderParam("API_KEY") String apiKey, ReMgtIou reIou) {
        // 创建借条，返回借条id
        logger.debug(">fillMgtIou() ");
        UserMgtIouCreatAction action = getBean(UserMgtIouCreatAction.ACTION_NAME);
        action.validateMgtPlatformApiKey(apiKey);
        action.insertMgtIou(reIou);
        logger.debug("<fillMgtIou()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/iou/{iouCode}/status/{status}")
    public ActionResponse updateMgtStatus(@HeaderParam(API_KEY) String apiKey, @PathParam("iouCode") String iouCode,
        @QueryParam("extentionDate") String extentionDateTs, @PathParam("status") int status) {
        // mgt调用 更新借条状态
        logger.debug(
            ">updateMgtStatus(), iouCode = " + iouCode + ",status=" + status + ",extentionDate=" + extentionDateTs);
        UserMgtIouUpdateAction action = getBean(UserMgtIouUpdateAction.ACTION_NAME);
        action.validateMgtPlatformApiKey(apiKey);
        action.changeMgtStatus(iouCode, status, extentionDateTs);
        logger.debug("<updateMgtStatus()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/sync")
    public ActionResponse SychMgtUser(@HeaderParam("API_KEY") String apiKey,
        @QueryParam("phoneNumber") String phoneNumber) {
        // 根据phonenumber匹配用户，不存在就新建用户返回id，存在就直接返回id
        logger.debug(">SychMgtUser()");
        UserSynchronizeAction action = getBean(UserSynchronizeAction.ACTION_NAME);
        action.validateMgtPlatformApiKey(apiKey);
        action.insertMgtUser(phoneNumber);
        logger.debug("<SychMgtUser()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user/check")
    public ActionResponse checkMgtUser(@HeaderParam("API_KEY") String apiKey, @QueryParam("userName") String userName,
        @QueryParam("userId") Integer userId) {
        // mgt调用 保存账户id到jbb系统
        logger.debug(">checkMgtUser() ");
        UserCheckJbbIdNameAction action = getBean(UserCheckJbbIdNameAction.ACTION_NAME);
        action.validateMgtPlatformApiKey(apiKey);
        action.checkJbbIdName(userId, userName);
        logger.debug("<checkMgtUser()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/user/account")
    public ActionResponse insertAccountId(@HeaderParam("API_KEY") String apiKey,
        @QueryParam("accountId") Integer accountId, @QueryParam("userId") int userId) {
        // mgt调用 保存账户id到jbb系统
        logger.debug(">insertAccountId() ");
        UserCheckJbbIdNameAction action = getBean(UserCheckJbbIdNameAction.ACTION_NAME);
        action.validateMgtPlatformApiKey(apiKey);
        action.insertUserAccount(userId, accountId);
        logger.debug("<insertAccountId()");
        return action.getActionResponse();
    }
}