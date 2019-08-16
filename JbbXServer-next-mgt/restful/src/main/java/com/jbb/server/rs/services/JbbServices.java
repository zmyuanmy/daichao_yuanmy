package com.jbb.server.rs.services;

import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.core.domain.UserProperty;
import com.jbb.server.rs.action.*;
import com.jbb.server.rs.pojo.ActionResponse;
import com.jbb.server.rs.pojo.request.*;
import net.sf.json.JSONObject;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * API Services
 *
 * @author VincentTang
 * @date 2017年12月20日
 */

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbServices.class);
    
    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        logger.debug(">ping() ");
        logger.debug("<ping()");
        return "pong";
    }

    @GET
    @Path("/leancloud/info")
    public ActionResponse getLeanCloudConfig(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getLeanCloudConfig() ");
        LeanCloudInfoAction action = getBean(LeanCloudInfoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.readLeanCloudInfo();
        logger.debug("<getLeanCloudConfig()");
        return action.getActionResponse();
    }

    @GET
    @Path("/msgCode")
    public ActionResponse sendCode(@QueryParam("phoneNumber") String phoneNubmer,
        @QueryParam("sessionId") String sessionId, @QueryParam("sig") String sig, @QueryParam("token") String token,
        @QueryParam("scene") String scene) {
        logger.debug(">sendCode() phoneNubmer={}", phoneNubmer);

        MsgCodeAction action = getBean(MsgCodeAction.ACTION_NAME);
        action.sendCode(phoneNubmer, sessionId, sig, token, scene);
        logger.debug("<sendCode()");
        return action.getActionResponse();
    }

    @POST
    @Path("/device/seqnumber")
    public ActionResponse postDeviceSeqNum(@QueryParam("sequenceNumber") String sequenceNumber,
        @QueryParam("sourceId") String sourceId) {
        logger.debug(">postDeviceSeqNum() sequenceNumber={}", sequenceNumber);
        PostDeviceSeqNumberAction action = getBean(PostDeviceSeqNumberAction.ACTION_NAME);
        action.saveSeqNumber(sequenceNumber, sourceId);
        logger.debug("<postDeviceSeqNum()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user")
    public ActionResponse registerUser(@QueryParam("phoneNumber") String phoneNumber,
        @QueryParam("msgCode") String msgCode, @HeaderParam("password") String password,
        @QueryParam("sourceId") String sourceId, @QueryParam("nickname") String nickname,
        @QueryParam("platform") String platform) {

        if (logger.isDebugEnabled()) {
            logger.debug(">registerUser()" + ", phoneNumber=" + phoneNumber + ",  msgCode=" + msgCode + ", sourceId="
                + sourceId + ", nickname=" + nickname + ", platform=" + platform);
        }

        UserSaveAction action = getBean(UserSaveAction.ACTION_NAME);
        action.registerUser(phoneNumber, msgCode, nickname, password, platform, sourceId);
        return action.getActionResponse();
    }

    @POST
    @Path("/user/h5")
    public ActionResponse registerUserFromH5(@QueryParam("sourceId") String sourceId,
        @QueryParam("platform") String platform, @QueryParam("msgCode") String msgCode,
        @QueryParam("step") Integer step, @QueryParam("test") Boolean test,
        @QueryParam("validateSesameCreditScoreStep") Integer validateSesameCreditScoreStep,
        @QueryParam("targetUserId") List<Integer> targetUserIds, ReUser reUser) {

        if (logger.isDebugEnabled()) {
            logger.debug(">registerUser()" + ", msgCode=" + msgCode + ", reUser=" + reUser + ", sourceId=" + sourceId
                + " ,validateSesameCreditScoreStep=" + validateSesameCreditScoreStep);
        }

        UserSaveAction action = getBean(UserSaveAction.ACTION_NAME);
        action.registerUserFromH5(reUser, sourceId, msgCode, platform, targetUserIds, step, test,
            validateSesameCreditScoreStep);
        return action.getActionResponse();
    }

    @POST
    @Path("/user/contacts")
    public ActionResponse postConstacts(@HeaderParam(API_KEY) String userKey, JSONObject req) {
        logger.debug(">postConstacts()");
        UserContanctAction action = getBean(UserContanctAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.processContacts(req);   
        return action.getActionResponse();
    }

    @GET
    @Path("/user")
    public ActionResponse getUser(@HeaderParam(API_KEY) String userKey, @QueryParam("userId") Integer userId,
        @QueryParam("detail") Boolean detail, @QueryParam("simpleWithVerifyInfo") Boolean simpleWithVerifyInfo) {
        logger.debug(">getUser() userId={}", userId);
        UserInfoAction action = getBean(UserInfoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        boolean withDetail = (detail != null && detail);
        boolean simple = (simpleWithVerifyInfo != null && simpleWithVerifyInfo);
        if (simple && userId != null) {
            action.getSimpleUserInfo(userId);
        } else {
            action.createUserInfo(withDetail, userId);
        }

        logger.debug("<getUser()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/user")
    public ActionResponse putUser(@HeaderParam(API_KEY) String userKey, @HeaderParam("password") String password,
        ReUser reUser) {
        logger.debug(">putUser() reUser={}", reUser);

        UserSaveAction action = getBean(UserSaveAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.updateUser(password, reUser);
        logger.debug("<putUser()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/avatar/refresh")
    public ActionResponse refreshAvatar(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">refreshAvatar()");
        UserInfoAction action = getBean(UserInfoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.refreshAvatarUrl();
        logger.debug("<refreshAvatar()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/login")
    public ActionResponse login(@QueryParam("phoneNumber") String phoneNumber, @QueryParam("msgCode") String msgCode,
        @HeaderParam("password") String password, @HeaderParam(API_KEY) String userKey,
        @QueryParam("platform") String platform, @QueryParam("daysToExpire") Integer daysToExpire,
        @QueryParam("keyType") Integer keyType, HttpServletRequest request) {
        LoginAction action = getBean(LoginAction.ACTION_NAME);

        if (!StringUtil.isEmpty(userKey)) {
            action.loginByKey(userKey, daysToExpire, keyType);
        } else if (!StringUtil.isEmpty(password)) {
            action.loginByPassword(phoneNumber, password, daysToExpire, platform, keyType);
        } else if (!StringUtil.isEmpty(msgCode)) {
            action.loginByMsgCode(phoneNumber, msgCode, daysToExpire, platform, keyType);
        } else {
            throw new WrongParameterValueException("jbb.error.exception.loginEmpty");
        }
        return action.getActionResponse();
    }

    @POST
    @Path("/user/logout")
    public ActionResponse logout(@HeaderParam(API_KEY) String userKey, @QueryParam("objectId") String objectId) {
        logger.debug(">logout()");
        LogoutAction action = getBean(LogoutAction.ACTION_NAME);
        action.logoutUser(userKey, objectId);
        logger.debug("<logout()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user/total")
    public ActionResponse userTotal(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">userTotal()");
        UserTotalAction action = getBean(UserTotalAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getTotal();
        logger.debug("<userTotal()");
        return action.getActionResponse();
    }

    @GET
    @Path("/billings")
    public ActionResponse getBillings(@HeaderParam(API_KEY) String userKey, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate, @QueryParam("detail") Boolean detail) {
        logger.debug(">getBillings(), startDate =" + startDate + ", endDate =" + endDate + ", detail =" + detail);
        BillingAction action = getBean(BillingAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getBillings(startDate, endDate, detail);
        logger.debug("<getBillings()");
        return action.getActionResponse();
    }

    @GET
    @Path("/billing/{billingId}")
    public ActionResponse getBilling(@HeaderParam(API_KEY) String userKey, @PathParam("billingId") Integer billingId,
        @QueryParam("detail") Boolean detail) {
        logger.debug(">deleteBilling(),billingId ={}", billingId);
        BillingAction action = getBean(BillingAction.ACTION_NAME);
        action.validateUserKey(userKey);
        boolean bDetail = (detail != null && detail);
        action.getBilling(billingId, bDetail);
        logger.debug("<deleteBilling()");
        return action.getActionResponse();
    }

    @POST
    @Path("/billing")
    public ActionResponse postBilling(@HeaderParam(API_KEY) String userKey, ReBilling request) {
        logger.debug(">postBilling(), request ={}", request);
        BillingAction action = getBean(BillingAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.createBilling(request);
        logger.debug("<postBillingRecord()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/billing/{billingId}")
    public ActionResponse putBilling(@HeaderParam(API_KEY) String userKey, @PathParam("billingId") Integer billingId,
        ReBilling reBilling) {
        logger.debug(">postBillingRecord(),billingId ={}, reBilling = {}", billingId, reBilling);
        BillingAction action = getBean(BillingAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.updateBilling(billingId, reBilling);
        logger.debug("<postBillingRecord()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/billing/{billingId}")
    public ActionResponse deleteBilling(@HeaderParam(API_KEY) String userKey,
        @PathParam("billingId") Integer billingId) {
        logger.debug(">deleteBilling(),billingId ={}", billingId);
        BillingAction action = getBean(BillingAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.deleteBilling(billingId);
        logger.debug("<deleteBilling()");
        return action.getActionResponse();
    }

    @POST
    @Path("/billingDetail/status")
    public ActionResponse postBillingDetailStatus(@HeaderParam(API_KEY) String userKey,
        @QueryParam("billingId") Integer billingId, @QueryParam("billingDetailId") Integer billingDetailId,
        @QueryParam("status") int status) {
        logger.debug(">postBillingDetailStatus(),billingId =" + billingId + ",billingDetailId =" + billingDetailId
            + ",status =" + status);
        BillingDetailAction action = getBean(BillingDetailAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.updateStatus(billingId, billingDetailId, status);
        logger.debug("<postBillingDetailStatus()");
        return action.getActionResponse();
    }

    @GET
    @Path("/billingDetail/{billingDetailId}")
    public ActionResponse getBillingDetail(@HeaderParam(API_KEY) String userKey,
        @PathParam("billingDetailId") Integer billingDetailId) {
        logger.debug(">getBillingDetail(),billingDetailId =" + billingDetailId);
        BillingDetailAction action = getBean(BillingDetailAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getBillingDetail(billingDetailId);
        logger.debug("<getBillingDetail()");
        return action.getActionResponse();
    }

    @POST
    @Path("/repayment")
    public ActionResponse postRepayment(@HeaderParam(API_KEY) String userKey, ReRepayment request) {
        logger.debug(">postRepayment(),reRepayment ={}", request);
        BillingDetailAction action = getBean(BillingDetailAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.savePayment(request);
        logger.debug("<postRepayment()");
        return action.getActionResponse();
    }

    @GET
    @Path("/aliyun/policy")
    public ActionResponse getPostPolicy(@HeaderParam(API_KEY) String userKey,
        @QueryParam("filePrefix") String filePrefix) {
        logger.debug(">logout()");
        AliyunPolicyAction action = getBean(AliyunPolicyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getPolicy(filePrefix);
        logger.debug("<logout()");
        return action.getActionResponse();
    }

    @GET
    @Path("/loan/platforms")
    public ActionResponse getLoanPlatform(@HeaderParam(API_KEY) String userKey, @QueryParam("loanType") String loanType,
        @QueryParam("all") boolean all, @QueryParam("simple") boolean simple, @QueryParam("env") String env) {
        LoanPlatformAction aciton = getBean(LoanPlatformAction.ACTION_NAME);

        // ignore return when call with env is "prod"
        String[] ignoreEnv = PropertyManager.getProperties("jbb.loan.platform.ignore");
        if (ignoreEnv != null && env != null && CommonUtil.inArray(env, ignoreEnv)) {
            return aciton.getActionResponse();
        }

        if (!StringUtil.isEmpty(userKey)) {
            aciton.validateUserKey(userKey);
        }
        aciton.getLoanPlatforms(loanType, all, simple, env);
        logger.debug("<getLoanPlatform()");
        return aciton.getActionResponse();
    }

    @GET
    @Path("/ads")
    public ActionResponse getAdvertising(@QueryParam("platform") String platform) {
        AdvertisingAction action = getBean(AdvertisingAction.ACTION_NAME);
        action.getAdvertising(platform);
        return action.getActionResponse();
    }

    @GET
    @Path("/company/intro")
    public ActionResponse getCompanyIntroduction() {
        CompanyIntroductionAction action = getBean(CompanyIntroductionAction.ACTION_NAME);
        action.getCompanyIntroduction();
        return action.getActionResponse();
    }

    @GET
    @Path("/share/info")
    public ActionResponse getAppShareInfo() {
        AppShareAction action = getBean(AppShareAction.ACTION_NAME);
        action.getAppShareConfigs();
        return action.getActionResponse();
    }

    @GET
    @Path("/user/nickname")
    public ActionResponse checkNickname(@QueryParam("nickname") String nickname) {
        UserInfoAction action = getBean(UserInfoAction.ACTION_NAME);
        action.checkNickname(nickname);
        return action.getActionResponse();
    }

    @POST
    @Path("/user/basic")
    public ActionResponse getUserHeadAndNickName(@HeaderParam(API_KEY) String userKey, ReUserIds userIds) {
        UserIdsAction action = getBean(UserIdsAction.ACTION_NAME);
        if (!StringUtil.isEmpty(userKey)) {
            action.validateUserKey(userKey);
        }
        action.getUserHeadAndNickName(userIds.getUserIds());
        return action.getActionResponse();
    }

    @POST
    @Path("/user/log")
    public ActionResponse postUserLog(@HeaderParam(API_KEY) String userKey, @QueryParam("sourceId") String sourceId,
        @QueryParam("eventName") String eventName, @QueryParam("eventLabel") String eventLabel,
        @QueryParam("eventAction") String eventAction, @QueryParam("eventValue") String eventValue) {
        LogEventAction action = getBean(LogEventAction.ACTION_NAME);
        if (!StringUtil.isEmpty(userKey)) {
            action.validateUserKey(userKey);
        }
        action.createLogEvent(sourceId, eventName, eventLabel, eventAction, eventValue);
        return action.getActionResponse();
    }

    @POST
    @Path("/user/properties")
    public ActionResponse addUserProperties(@HeaderParam(API_KEY) String userKey, ReUserProperty properties) {
        logger.debug(">addUserProperties()");
        List<UserProperty> list = properties.getProperties();
        UserPropertiesAction action = getBean(UserPropertiesAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.addUserProperties(list);
        logger.debug("<addUserProperties()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/user/properties")
    public ActionResponse updateUserProperties(@HeaderParam(API_KEY) String userKey, ReUserProperty properties) {
        logger.debug(">updateUserProperties()");
        List<UserProperty> list = properties.getProperties();
        UserPropertiesAction action = getBean(UserPropertiesAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.updateUserProperties(list);
        logger.debug("<updateUserProperties()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/user/properties")
    public ActionResponse deleteUserProperties(@HeaderParam(API_KEY) String userKey, ReUserProperty properties) {
        logger.debug(">deleteUserProperties()");
        UserPropertiesAction action = getBean(UserPropertiesAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.deleteUserProperties(properties.getProperties());
        logger.debug("<deleteUserProperties()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user/properties")
    public ActionResponse searchUserPropertiesByUserId(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">searchUserPropertiesByUserId()");
        UserPropertiesAction action = getBean(UserPropertiesAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.searchUserPropertiesByUserId();
        logger.debug("<searchUserPropertiesByUserId()");
        return action.getActionResponse();
    }

    @POST
    @Path("/iou")
    public ActionResponse addIou(@HeaderParam(API_KEY) String userKey, ReIou reIou) {
        logger.debug(">insertIou() ");
        IouAction action = getBean(IouAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.insertIou(reIou);
        logger.debug("<insertIou()");
        return action.getActionResponse();
    }

    @POST
    @Path("/fill/iou")
    public ActionResponse fillIou(@HeaderParam(API_KEY) String userKey,@HeaderParam("password") String tradePassword, @QueryParam("msgCode") String msgCode,
        @QueryParam("isLender") boolean isLender, ReIouFill reIou) {
        logger.debug(">insertIou() ");
        IouAction action = getBean(IouAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.insertIou(msgCode, isLender, reIou,tradePassword);
        logger.debug("<insertIou()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/iou/{iouCode}")
    public ActionResponse updateIou(@HeaderParam(API_KEY) String userKey, @PathParam("iouCode") String iouCode,
        @QueryParam("intentionUserId") Integer intentionUserId, ReIou reIou) {
        logger.debug(">updateIou()");
        IouAction action = getBean(IouAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.updateIou(iouCode, reIou, intentionUserId);
        logger.debug("<updateIou()");
        return action.getActionResponse();
    }

    @GET
    @Path("/iou/canPublish")
    // TODO remove after V1.4
    public ActionResponse exceedLimitOfPublishIou(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">canPublish() ");
        IouAction action = getBean(IouAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.exceedLimitOfPublishIou();
        logger.debug("<canPublish()");
        return action.getActionResponse();
    }

    @GET
    @Path("/iou/verify/publish")
    public ActionResponse verifyPublish(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">verifyPublish() ");
        IouAction action = getBean(IouAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.exceedLimitOfPublishIou();
        logger.debug("<verifyPublish()");
        return action.getActionResponse();
    }

    @GET
    @Path("/iou/{iouCode}")
    public ActionResponse getIouByIouCode(@HeaderParam(API_KEY) String userKey, @PathParam("iouCode") String iouCode) {
        logger.debug(">getIouByIouCode(), iouCode = " + iouCode);
        IouAction action = getBean(IouAction.ACTION_NAME);
        if (!StringUtil.isEmpty(userKey)) {
            action.validateUserKey(userKey);
        }
        action.getIouByIouCode(iouCode);
        logger.debug("<getIouByIouCode()");
        return action.getActionResponse();
    }

    @GET
    @Path("/ious/statistic/amount")
    public ActionResponse statisticIous(@HeaderParam(API_KEY) String userKey, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate, @QueryParam("statisticType") Integer statisticType,
        @QueryParam("type") Integer type) {
        logger.debug(">statisticIous(), start = " + startDate + ", end = " + endDate);

        IousStatisticAction action = getBean(IousStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.statistic(startDate, endDate, statisticType, type);
        logger.debug("<statisticIous()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/iou/{iouCode}")
    public ActionResponse deleteIou(@HeaderParam(API_KEY) String userKey, @PathParam("iouCode") String iouCode) {
        logger.debug(">deleteIou(), iouCode = " + iouCode);
        IouAction action = getBean(IouAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.deleteIou(iouCode);
        logger.debug("<deleteIou()");
        return action.getActionResponse();
    }

    @GET
    @Path("/ious")
    public ActionResponse getIous(@HeaderParam(API_KEY) String userKey, @QueryParam("category") Integer category,
        @QueryParam("status") int[] statuses, @QueryParam("filterStatus") int[] filterStatuses,
        @QueryParam("searchText") String searchText, @QueryParam("amountMin") Integer amountMin,
        @QueryParam("amountMax") Integer amountMax, @QueryParam("borrowingStart") String borrowingStart,
        @QueryParam("borrowingEnd") String borrowingEnd, @QueryParam("repaymentStart") String repaymentStart,
        @QueryParam("repaymentEnd") String repaymentEnd, @QueryParam("phoneNumber") String phoneNumber,
        @QueryParam("userName") String userName, @QueryParam("iouCode") String iouCode,
        @QueryParam("lastIouCode") String lastIouCode, @QueryParam("forward") Integer forward,
        @QueryParam("pageSize") Integer pageSize) {
        logger.debug(">getIous(), category= " + category + ", lastIouCode=" + lastIouCode + ", forward=" + forward);
        IousAction iousAction = getBean(IousAction.ACTION_NAME);
        if (!StringUtil.isEmpty(userKey)) {
            iousAction.validateUserKey(userKey);
        }
        iousAction.getIous(category, forward, lastIouCode, pageSize, statuses, filterStatuses, iouCode, userName,
            phoneNumber, amountMin, amountMax, borrowingStart, borrowingEnd, repaymentStart, repaymentEnd, searchText);
        logger.debug("<getIous()");
        return iousAction.getActionResponse();
    }

    @PUT
    @Path("/iou/{iouCode}/status/{status}")
    public ActionResponse updateStatus(@HeaderParam(API_KEY) String userKey, @PathParam("iouCode") String iouCode,
        @QueryParam("extentionDate") String extentionDateTs, @QueryParam("msgCode") String msgCode,
        @QueryParam("lenderId") Integer lenderId, @PathParam("status") int status,
        @HeaderParam("password") String tradePassword) {
        logger.debug(">changeStatus(), iouCode = " + iouCode + ",msgCode=" + msgCode + ",status=" + status
            + ",lenderId=" + lenderId + ",extentionDate=" + extentionDateTs);
        IouAction action = getBean(IouAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.changeStatus(iouCode, status, msgCode, extentionDateTs, lenderId, tradePassword);
        logger.debug("<changeStatus()");
        return action.getActionResponse();
    }

    @POST
    @Path("/iou/{iouCode}/intention/{status}")
    public ActionResponse updateIntentionStatus(@HeaderParam(API_KEY) String userKey,
        @PathParam("iouCode") String iouCode, @QueryParam("intendedUserId") Integer intendedUserId,
        @PathParam("status") int status) {
        logger.debug(">updateIntentionStatus(), iouCode = " + iouCode + ", status= " + status + ", intendedUserId= "
            + intendedUserId);
        IouIntentionAction action = getBean(IouIntentionAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.updateIntentionStatus(iouCode, status, intendedUserId);
        logger.debug("<updateIntentionStatus()");
        return action.getActionResponse();
    }

    @POST
    @Path("/iou/{iouCode}/follow/{status}")
    public ActionResponse updateFollowStatus(@HeaderParam(API_KEY) String userKey, @PathParam("iouCode") String iouCode,
        @PathParam("status") int status) {
        logger.debug(">updateFollowStatus(), iouCode = " + iouCode + ", status= " + status);
        IouFollowAction action = getBean(IouFollowAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.updateStatus(iouCode, status);
        logger.debug("<updateFollowStatus()");
        return action.getActionResponse();
    }

    @GET
    @Path("/ious/count")
    public ActionResponse countIous(@HeaderParam(API_KEY) String userKey, @QueryParam("category") Integer category) {
        logger.debug(">countIous()");
        IouCountAction action = getBean(IouCountAction.ACTION_NAME);
        if (userKey != null) {
            action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        }
        action.count(category);
        logger.debug("<countIous()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/exchange")
    public ActionResponse userExchangeInfo(@HeaderParam(API_KEY) String userKey, @QueryParam("userId") Integer userId,
        @QueryParam("exchangeType") Integer exchangeType, @QueryParam("convId") String convId,
        @QueryParam("oldMsgId") String oldMsgId, @QueryParam("oldMsgTs") Long oldMsgTs) {
        logger.debug(">userExchangeInfo(), userId = " + userId + ", exchangeType = " + exchangeType + ", convId = "
            + convId + ", oldMsgId = " + oldMsgId);
        UserExchangeAction action = getBean(UserExchangeAction.ACTION_NAME);
        action.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        action.exchangeInfo(userId, exchangeType, convId, oldMsgId, oldMsgTs);
        logger.debug("<userExchangeInfo()");
        return action.getActionResponse();
    }

    @GET
    @Path("/leancloud/signature")
    public ActionResponse getSignature(@HeaderParam(API_KEY) String userKey, @QueryParam("clientId") String clientId,
        @QueryParam("sortedMemberIds") String sortedMemberIds, @QueryParam("convId") String convId,
        @QueryParam("action") String action) {
        logger.debug(">getSignature(),clientId=" + clientId + ",sortedMemberIds=" + sortedMemberIds + ",convId="
            + convId + ",action=" + action);
        SignatureAction signatureAction = getBean(SignatureAction.ACTION_NAME);
        signatureAction.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        signatureAction.getSignature(clientId, sortedMemberIds, convId, action);
        logger.debug("<getSignature()");
        return signatureAction.getActionResponse();
    }

    @POST
    @Path("/aliyun/api/ocrIdCard")
    public ActionResponse orcIdCard(@HeaderParam(API_KEY) String userKey, Object body) {
        logger.debug(">orcIdCard()");
        FaceOcrIdCardAction ocrIdCardAction = getBean(FaceOcrIdCardAction.ACTION_NAME);
        ocrIdCardAction.validateUserKey(userKey);// 检验userKey,并获取到这个user信息
        ocrIdCardAction.ocrIdCard(body);
        logger.debug("<orcIdCard()");
        return ocrIdCardAction.getActionResponse();
    }

    @POST
    @Path("/aliyun/api/checkIdCard")
    public ActionResponse checkIdCard(@HeaderParam(API_KEY) String userKey, @QueryParam("username") String username,
        @QueryParam("cardno") String cardno) {
        logger.debug(">notifyPayResult(), request= " + request);
        CheckIdCardAction action = getBean(CheckIdCardAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.checkFaceIdCard(cardno, username);
        logger.debug("<notifyPayResult()");
        return action.getActionResponse();
    }

    @GET
    @Path("/wxpay/unifiedorder")
    public ActionResponse unifiedorder(@HeaderParam(API_KEY) String userKey,
        @QueryParam("productName") String productName, @QueryParam("attach") String attach) {
        logger.debug(">unifiedorder(), attach= " + attach + " , productName=" + productName);
        WxUnifiedorderAction action = getBean(WxUnifiedorderAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.unifiedorder(productName, attach);
        logger.debug("<unifiedorder()");
        return action.getActionResponse();
    }

    @POST
    @Path("/wxpay/queryOrder")
    public ActionResponse queryOrder(@HeaderParam(API_KEY) String userKey,
        @QueryParam("transactionId") String transactionId, @QueryParam("outTradeNo") String outTradeNo) {
        logger.debug(">nogetAvatarUrltifyPayResult(), request= " + request);
        WxQueryOrderAction action = getBean(WxQueryOrderAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.queryOrder(transactionId, outTradeNo);
        logger.debug("<notifyPayResult()");
        return action.getActionResponse();
    }

    @POST
    @Path("/tencent/livedetect")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public ActionResponse liveDetect(@HeaderParam(API_KEY) String userKey, @MultipartForm ReLiveDetectRequest request) {
        logger.debug(">liveDetect(), validateData= " + request.getValidateData() + ", content length="
            + request.getFileDate().length);
        FaceDetectAction action = getBean(FaceDetectAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.liveDetect(request.getValidateData(), request.getFileDate());
        logger.debug("<liveDetect()");
        return action.getActionResponse();
    }

    @GET
    @Path("/tencent/livedetect")
    public ActionResponse liveDetectGetFour(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">liveDetectGetFour()");
        FaceDetectAction action = getBean(FaceDetectAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.liveDetectGetFour();
        logger.debug("<liveDetectGetFour()");
        return action.getActionResponse();
    }

    @GET
    @Path("/platform/fees")
    public ActionResponse getPlatformFees(@HeaderParam(API_KEY) String userKey, @QueryParam("name") String name) {
        logger.debug(">getPlatformFees(), name= " + name);
        PlatformAction action = getBean(PlatformAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getPlatformFees(name);
        logger.debug("<getPlatformFees()");
        return action.getActionResponse();
    }

    @GET
    @Path("/platform/info")
    public ActionResponse getPlatformInfo() {
        logger.debug(">getPlatformInfo()");
        PlatformAction action = getBean(PlatformAction.ACTION_NAME);
        action.getPlatformInfo();
        logger.debug("<getPlatformFees()");
        return action.getActionResponse();
    }

    @GET
    @Path("/messages")
    public ActionResponse getMessages(@HeaderParam(API_KEY) String userKey, @QueryParam("read") Boolean read,
        @QueryParam("lastMessageId") int lastMessageId, @QueryParam("forward") Integer forward,
        @QueryParam("pageSize") int pageSize) {
        logger.debug(">getMessages() read = " + read + " lastMessageId = " + lastMessageId + " forward = " + forward
            + " pageSize = " + pageSize);
        MessageAction action = getBean(MessageAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getMessages(read, lastMessageId, forward, pageSize);
        logger.debug("<getMessages()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/message/{messageId}/read")
    public ActionResponse updateMessageStatusRead(@HeaderParam(API_KEY) String userKey,
        @PathParam("messageId") int messageId, @QueryParam("read") boolean read) {
        logger.debug(">updateMessageStatusRead() messageId = " + messageId + " ,read = " + read);
        MessageAction action = getBean(MessageAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.updateMessageStatusRead(messageId, read);
        logger.debug("<updateMessageStatusRead()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/message/{messageId}/hidden")
    public ActionResponse updateMessageStatusHidden(@HeaderParam(API_KEY) String userKey,
        @PathParam("messageId") int messageId, @QueryParam("hidden") boolean hidden) {
        logger.debug(">updateMessageStatusRead() messageId = " + messageId + " ,hidden = " + hidden);
        MessageAction action = getBean(MessageAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.updateMessageStatusHidden(messageId, hidden);
        logger.debug("<updateMessageStatusRead()");
        return action.getActionResponse();
    }

    @POST
    @Path("/userdevice")
    public ActionResponse insertUserDevice(@HeaderParam(API_KEY) String userKey, UserDeviceAction.Request req) {
        logger.debug(">saveUserDevice() deviceType = " + req.deviceType + " deviceToken = " + req.deviceToken
            + "installationId = " + req.installationId + "objectId = " + req.objectId);
        UserDeviceAction action = getBean(UserDeviceAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.saveUserDevice(req);
        logger.debug("<insertUserDevice()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/userdevice/logout")
    public ActionResponse logoutDevice(@HeaderParam(API_KEY) String userKey, @QueryParam("objectId") String objectId) {
        logger.debug(">logoutDevice() objectId = " + objectId);
        UserDeviceAction action = getBean(UserDeviceAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.logoutDevice(objectId);
        logger.debug("<logoutDevice()");
        return action.getActionResponse();
    }

    @POST
    @Path("/face/getreturnresult")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void getReturnResult(@HeaderParam("API_KEY") String userKey, @Context HttpServletRequest request,
        @Context HttpServletResponse response) {
        logger.debug(">getreturnresult()");
        FaceAction action = getBean(FaceAction.ACTION_NAME);
        action.getReturnResult(request, response);
        logger.debug("<getreturnresult()");
    }

    @POST
    @Path("/face/notify")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void notifyFace(@HeaderParam("API_KEY") String userKey, @Context HttpServletRequest request,
        @Context HttpServletResponse response) {
        logger.debug(">notifyFace()");
        FaceAction action = getBean(FaceAction.ACTION_NAME);
        action.notyfyFace(request, response);
        logger.debug("<notifyFace()");
    }

    @POST
    @Path("/pw/trade/setting")
    public ActionResponse setUserTradePasswrod(@HeaderParam("API_KEY") String userKey,
        @HeaderParam("password") String password) {
        logger.debug(">setUserTradePasswrod()");
        UserTradePasswordAction action = getBean(UserTradePasswordAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.setUserTradePassword(password);
        logger.debug("<setUserTradePasswrod()");
        return action.getActionResponse();
    }

    @POST
    @Path("/pw/trade/reset")
    public ActionResponse updateUserTradePasswrod(@HeaderParam("API_KEY") String userKey,
        @HeaderParam("password") String password, @QueryParam("msgCode") String msgCode,
        @QueryParam("idcardNo") String idcardNo, @QueryParam("step") String step) {
        logger.debug(">updateUserTradePasswrod()");
        UserTradePasswordAction action = getBean(UserTradePasswordAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.updateUserTradePassword(password, msgCode, idcardNo, step);
        logger.debug("<updateUserTradePasswrod()");
        return action.getActionResponse();
    }

    @POST
    @Path("/pw/trade/checkExist")
    public ActionResponse checkUserTradePassword(@HeaderParam("API_KEY") String userKey) {
        logger.debug(">checkUserTradePassword()");
        UserTradePasswordAction action = getBean(UserTradePasswordAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getUserHasTradePassword();
        logger.debug("<checkUserTradePassword()");
        return action.getActionResponse();
    }

    @POST
    @Path("/iou/{iouCode}/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ActionResponse uploadIouPhoto(@HeaderParam("API_KEY") String userKey,
        @MultipartForm ReLiveDetectRequest request, @PathParam("iouCode") String iouCode) {
        logger.debug(">uploadIouPhoto()");
        IouPhotoAction action = getBean(IouPhotoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.uploadIouPhoto(request.getFileDate(), iouCode);
        logger.debug("<uploadIouPhoto()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/iou/{iouCode}/file/{fileId}")
    public ActionResponse deleteIouPhoto(@HeaderParam("API_KEY") String userKey, @PathParam("iouCode") String iouCode,
        @PathParam("fileId") int fileId) {
        logger.debug(">deleteIouPhoto()");
        IouPhotoAction action = getBean(IouPhotoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.deleteIouPhoto(fileId);
        logger.debug("<deleteIouPhoto()");
        return action.getActionResponse();
    }

    @GET
    @Path("/iou/{iouCode}/files")
    public ActionResponse getIouPhotos(@HeaderParam("API_KEY") String userKey, @PathParam("iouCode") String iouCode) {
        logger.debug(">getIouPhotos()");
        IouPhotoAction action = getBean(IouPhotoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getIouPhotos(iouCode);
        logger.debug("<getIouPhotos()");
        return action.getActionResponse();
    }

    @GET
    @Path("/check/iouIdOrName")
    public ActionResponse checkIdOrName(@HeaderParam("API_KEY") String userKey, @QueryParam("value") String value) {
        logger.debug(">checkIdOrName()");
        CheckIdOrNameAction action = getBean(CheckIdOrNameAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.checkIdOrName(value);
        logger.debug("<checkIdOrName()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/iou/{iouCode}/bindborrower")
    public ActionResponse bindBorrower(@HeaderParam(API_KEY) String userKey, @PathParam("iouCode") String iouCode) {
        logger.debug(">bindBorrower() iouCode = " + iouCode);
        IouPhotoAction action = getBean(IouPhotoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.bindBorrower(iouCode);
        logger.debug("<bindBorrower()");
        return action.getActionResponse();
    }

    @GET
    @Path("/iou/check/verify")
    public ActionResponse iouVerify(@HeaderParam("API_KEY") String userKey, @QueryParam("userId") Integer userId,
        @QueryParam("isLender") Boolean isLender) {
        logger.debug(">iouVerify()");
        IouVerifyAction action = getBean(IouVerifyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.iouVerify(userId, isLender);
        logger.debug("<iouVerify()");
        return action.getActionResponse();
    }

}
