package com.jbb.mgt.server.rs.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.rs.action.integrate.IouUpdateAction;
import com.jbb.mgt.rs.action.integrate.UserContantAction;
import com.jbb.mgt.rs.action.integrate.UserListAction;
import com.jbb.mgt.rs.action.integrate.UserLoanRecordAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Path("integrate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbMgtIntegrateService extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbMgtIntegrateService.class);

    @POST
    @Path("/demo")
    public ActionResponse demo(@HeaderParam(API_KEY) String apiKey) {

        // 检验JBB APIKEY
        // action.validateJbbPlatformApiKey(apiKey);
        return null;
    }

    @POST
    @Path("/user")
    public ActionResponse getUserByPhoneNumber(@HeaderParam(API_KEY) String apiKey,
        @QueryParam("phoneNumber") String phoneNumber) {
        logger.debug(">getUserByPhoneNumber(),phoneNumber=" + phoneNumber);
        UserListAction action = getBean(UserListAction.ACTION_NAME);
        action.validateJbbPlatformApiKey(apiKey);
        action.getUserByPhoneNumber(phoneNumber);
        logger.debug("<getUserByPhoneNumber()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/iou")
    public ActionResponse updateIou(@HeaderParam(API_KEY) String apiKey, IouUpdateAction.Request request) {
        logger.debug(">updateIou()");
        IouUpdateAction action = getBean(IouUpdateAction.ACTION_NAME);
        action.validateJbbPlatformApiKey(apiKey);
        action.updateIou(request);
        logger.debug("<updateIou()");
        return action.getActionResponse();
    }

    @POST
    @Path("/iou")
    public ActionResponse createIou(@HeaderParam(API_KEY) String apiKey, UserLoanRecordAction.Request request) {
        logger.debug(">createIou()");
        UserLoanRecordAction action = getBean(UserLoanRecordAction.ACTION_NAME);
        action.validateJbbPlatformApiKey(apiKey);
        action.createUserLoanRecord(request);
        logger.debug("<createIou()");
        return action.getActionResponse();
    }

    @POST
    @Path("/contacts")
    public ActionResponse contants(@HeaderParam(API_KEY) String apiKey, UserContantAction.Request request,
        @QueryParam("jbbUserId") Integer jbbUserId) {
        logger.debug(">contants()");
        UserContantAction action = getBean(UserContantAction.ACTION_NAME);
        action.validateJbbPlatformApiKey(apiKey);
        action.insertContants(request, jbbUserId);
        logger.debug("<contants()");
        return action.getActionResponse();
    }

}