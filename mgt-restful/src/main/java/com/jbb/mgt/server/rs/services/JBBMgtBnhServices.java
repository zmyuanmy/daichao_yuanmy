package com.jbb.mgt.server.rs.services;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

import com.jbb.mgt.rs.action.creditCard.CreditCardAction;
import com.jbb.mgt.rs.action.loanFields.LargeLoanApplyAction;
import com.jbb.mgt.rs.action.upload.FileUploadAction;
import com.jbb.mgt.rs.action.user.BnhUserInfoAction;
import com.jbb.mgt.rs.action.userComplaint.UserComplaintAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Path("bnh")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JBBMgtBnhServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JBBMgtBnhServices.class);
    @POST
    @Path("/largeLoan")
    public ActionResponse largeLoanApply(@HeaderParam(API_KEY) String userKey, Object object) {
        logger.debug(">largeLoanApply()");
        LargeLoanApplyAction action = getBean(LargeLoanApplyAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.largeLoanApply(object);
        logger.debug("<largeLoanApply()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/userInfo")
    public ActionResponse updateUserInfo(@HeaderParam(API_KEY) String userKey, LargeLoanApplyAction.Request req) {
        logger.debug(">updateUserInfo()");
        LargeLoanApplyAction action = getBean(LargeLoanApplyAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.updateUserInfo(req);
        logger.debug("<updateUserInfo()");
        return action.getActionResponse();
    }

   

    @GET
    @Path("/user")
    public ActionResponse getUserInfo(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getUserInfo()");
        BnhUserInfoAction action = getBean(BnhUserInfoAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.selectUserInfo();
        logger.debug("<getUserInfo()");
        return action.getActionResponse();
    }

  

  

    @GET
    @Path("/cityList")
    public ActionResponse selectCity() throws Exception {
        logger.debug(">selectCity()");
        CreditCardAction action = getBean(CreditCardAction.ACTION_NAME);
        action.selectCity();
        logger.debug("<selectCity()");
        return action.getActionResponse();
    }

    @POST
    @Path("/files/user")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png"})
    public ActionResponse postUploadUserIdCard(@HeaderParam(API_KEY) String userKey, byte[] content)
        throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug(">postUploadUserIdCard()" + ", content.length=" + (content != null ? content.length : 0));
        }
        FileUploadAction action = getBean(FileUploadAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.uploadUserImage(content);
        return action.getActionResponse();
    }
    
    @POST
    @Path("/feedback")
    public ActionResponse insertUserComplain(@HeaderParam(API_KEY) String userKey,
        @QueryParam("appName") String appName, UserComplaintAction.Request request) {
        logger.debug(">insertUserComplain()");
        UserComplaintAction action = getBean(UserComplaintAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.insertUserComplaint(appName, request.content);
        logger.debug("<insertUserComplain()");
        return action.getActionResponse();
    }
    
    
}
