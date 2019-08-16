package com.jbb.mgt.server.rs.services;

import java.io.IOException;

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

import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.common.comm.ServiceClient.Request;
import com.jbb.mgt.rs.action.creditCard.CreditCardAction;
import com.jbb.mgt.rs.action.creditCard.CreditCardCategorieAction;
import com.jbb.mgt.rs.action.loanFields.ApplyOrgsAction;
import com.jbb.mgt.rs.action.loanFields.LargeLoanApplyAction;
import com.jbb.mgt.rs.action.loanFields.LoanFieldAction;
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

    @GET
    @Path("/loanFields")
    public ActionResponse getLoanFields(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getLoanFields()");
        LoanFieldAction action = getBean(LoanFieldAction.ACTION_NAME);
        action.getLoanFields();
        logger.debug("<getLoanFields()");
        return action.getActionResponse();
    }

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

    @POST
    @Path("/largeLoanOrgs")
    public ActionResponse largeLoanApplyOrgs(@HeaderParam(API_KEY) String userKey,
        @QueryParam("orgId") Integer[] orgIds, ApplyOrgsAction.Request req) {
        logger.debug(">largeLoanApplyOrgs()");
        ApplyOrgsAction action = getBean(ApplyOrgsAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.largeLoanApplyOrgs(req, orgIds);
        logger.debug("<largeLoanApplyOrgs()");
        return action.getActionResponse();
    }

    @GET
    @Path("/cardCategorieList")
    public ActionResponse getCreditCardCategorieById() throws Exception {
        logger.debug(">getCreditCardCategorie()");
        CreditCardCategorieAction action = getBean(CreditCardCategorieAction.ACTION_NAME);
        action.getCreditCardCategorie();
        logger.debug("<getCreditCardCategorie()");
        return action.getActionResponse();
    }

    @GET
    @Path("/creditCard/{categoryId}")
    public ActionResponse getCreditCardByCategoryId(@QueryParam("cityName") String cityName,
        @PathParam("categoryId") Integer categoryId) throws Exception {
        logger.debug(">getCreditCardByCategoryId()");
        CreditCardAction action = getBean(CreditCardAction.ACTION_NAME);
        action.getCreditCardByCategoryId(cityName, categoryId);
        logger.debug("<getCreditCardByCategoryId()");
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
    @Path("/creditCardList")
    public ActionResponse getCreditCardByCity(@QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize,
        @QueryParam("cityName") String cityName) throws Exception {
        logger.debug(">getCreditCardByCity()");
        CreditCardAction action = getBean(CreditCardAction.ACTION_NAME);
        action.getCreditCardByCity(pageNo, pageSize, cityName, null, false, null, null);
        logger.debug("<getCreditCardByCity()");
        return action.getActionResponse();
    }

    @GET
    @Path("/check/largeApply")
    public ActionResponse checkLargeLoanApply(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">checkLargeLoanApply()");
        LargeLoanApplyAction action = getBean(LargeLoanApplyAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.checkLargeLoanApply();
        logger.debug("<checkLargeLoanApply()");
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
