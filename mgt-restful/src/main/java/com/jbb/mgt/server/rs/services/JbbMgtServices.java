package com.jbb.mgt.server.rs.services;

import java.io.IOException;

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
import com.jbb.mgt.rs.action.account.AccountAction;
import com.jbb.mgt.rs.action.account.PasswordAction;
import com.jbb.mgt.rs.action.app.AppInfoAction;
import com.jbb.mgt.rs.action.app.AppVersionAction;
import com.jbb.mgt.rs.action.channel.ChannelAction;
import com.jbb.mgt.rs.action.dflow.DataFlowBaseAction;
import com.jbb.mgt.rs.action.dflow.DataFlowSettingsAction;
import com.jbb.mgt.rs.action.h5Merchant.H5MerchantAction;
import com.jbb.mgt.rs.action.index.IndexCountAction;
import com.jbb.mgt.rs.action.jiGuang.JiGuangAction;
import com.jbb.mgt.rs.action.loan.LoanRecordListAction;
import com.jbb.mgt.rs.action.loan.UserLoanRecordUpdateAction;
import com.jbb.mgt.rs.action.loanRecord.LoanOpLogAction;
import com.jbb.mgt.rs.action.log.UserLogEventAction;
import com.jbb.mgt.rs.action.login.LoginAction;
import com.jbb.mgt.rs.action.logout.LogoutAction;
import com.jbb.mgt.rs.action.msgCode.MsgCodeAction;
import com.jbb.mgt.rs.action.msgReport.MsgReportAction;
import com.jbb.mgt.rs.action.organization.OrganizationAction;
import com.jbb.mgt.rs.action.roles.RolesAction;
import com.jbb.mgt.rs.action.upload.FileUploadAction;
import com.jbb.mgt.rs.action.upload.UploadUtilAction;
import com.jbb.mgt.rs.action.user.LoginUserAction;
import com.jbb.mgt.rs.action.user.UserAction;
import com.jbb.mgt.rs.action.user.UserApplyAction;
import com.jbb.mgt.rs.action.user.UserApplyDeleteAction;
import com.jbb.mgt.rs.action.user.UserApplyInfoAction;
import com.jbb.mgt.rs.action.user.UserApplyListAction;
import com.jbb.mgt.rs.action.user.UserApplyStatisticAction;
import com.jbb.mgt.rs.action.user.UserApplyUpdateAction;
import com.jbb.mgt.rs.action.user.UserContanctAction;
import com.jbb.mgt.rs.action.user.UserInfoAction;
import com.jbb.mgt.rs.action.user.UserLocationAction;
import com.jbb.mgt.rs.action.user.UserPolicyAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;
import com.jbb.server.common.util.StringUtil;

import net.sf.json.JSONObject;


@Path("mgt")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbMgtServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbMgtServices.class);

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        logger.debug(">ping() ");
        logger.debug("<ping()");
        return "test";
    }

    @GET
    @Path("/msgCode")
    public ActionResponse sendCode(@QueryParam("phoneNumber") String phoneNumber,
        @QueryParam("checkPhoneNumber") Boolean checkPhoneNumber, @QueryParam("channelCode") String channelCode,
        @QueryParam("sessionId") String sessionId, @QueryParam("sig") String sig, @QueryParam("token") String token,
        @QueryParam("scene") String scene, @QueryParam("signTemplateId") String signTemplateId) {
        logger.debug(">sendCode() phoneNumber={},  channelCodee={}, checkPhoneNumber={}", phoneNumber, channelCode,
            checkPhoneNumber);
        MsgCodeAction action = getBean(MsgCodeAction.ACTION_NAME);
        action.sendCode(phoneNumber, checkPhoneNumber, channelCode, sessionId, sig, token, scene, signTemplateId);
        logger.debug("<sendCode()");
        return action.getActionResponse();
    }

    @GET
    @Path("/login")
    public ActionResponse login(@HeaderParam("password") String password, @QueryParam("username") String username,
        @QueryParam("sessionId") String sessionId, @QueryParam("sig") String sig, @QueryParam("token") String token,
        @QueryParam("scene") String scene, @QueryParam("agree") Boolean agree) {
        logger.debug(">login(), username = " + username + ",  agree = " + agree);
        LoginAction action = getBean(LoginAction.ACTION_NAME);
        action.loginByPassword(username, password, sessionId, sig, token, scene);
        logger.debug("<login()");
        return action.getActionResponse();
    }

    @GET
    @Path("/login/logs")
    public ActionResponse getLoginLogs(@HeaderParam(API_KEY) String userKey, @QueryParam("orgId") Integer orgId,
        @QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {
        logger.debug(">getLoginLogs(), orgId = " + orgId);
        LoginAction action = getBean(LoginAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getLoginLogs(pageNo, pageSize, orgId);
        logger.debug("<getLoginLogs()");
        return action.getActionResponse();
    }

    @POST
    @Path("/logout")
    public ActionResponse logout(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">logout()");
        LogoutAction action = getBean(LogoutAction.ACTION_NAME);
        action.logout(userKey);
        logger.debug("<login()");
        return action.getActionResponse();
    }

    @POST
    @Path("/password/reset")
    public ActionResponse resetPassword(@HeaderParam(API_KEY) String userKey,
        @HeaderParam("password") String password) {
        logger.debug(">resetPassword()");
        PasswordAction action = getBean(PasswordAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.resetPassword(password);
        logger.debug("<login()");
        return action.getActionResponse();
    }

    @POST
    @Path("/channel")
    public ActionResponse insertChannel(@HeaderParam(API_KEY) String userKey, ChannelAction.Request request) {
        logger.debug(">insertChannel() ");
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_ORGADMIN, Constants.MGT_P_SYSADMIN, Constants.MGT_P_MGTCHANNEL,
            Constants.MGT_P1_101, Constants.MGT_P1_102, Constants.MGT_P1_103, Constants.XJL_MGT_P2_205,
            Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202};
        action.validateUserAccess(ps);

        action.createChannel(request);
        logger.debug("<insertChannel()");
        return action.getActionResponse();
    }

    @POST
    @Path("/delegate/channel")
    public ActionResponse insertDelegateChannel(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">insertDelegateChannel() ");
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_ORGADMIN, Constants.MGT_P_SYSADMIN, Constants.MGT_P_MGTCHANNEL};
        action.validateUserAccess(ps);
        action.delegateChannel();
        logger.debug("<insertDelegateChannel()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channel/login")
    public ActionResponse loginChannel(@HeaderParam("password") String sourcePassword,
        @QueryParam("sourcePhoneNumber") String sourcePhoneNumber) {
        logger.debug(">loginChannel()");
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        action.checkChannelPhoneNumberAndPassword(sourcePhoneNumber, sourcePassword);
        logger.debug("<loginChannel()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channel/countUser/{channelCode}")
    public ActionResponse getCountUser(@HeaderParam(API_KEY) String userKey, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate, @PathParam("channelCode") String channelCode) {
        logger.debug(">getCountUser()");
        UserApplyAction action = getBean(UserApplyAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_ORGADMIN, Constants.MGT_P_SYSADMIN};
        action.validateUserAccess(ps);

        action.countUserApplyRecords(channelCode, startDate, endDate);
        logger.debug("<getCountUser()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channel/{channelCode}")
    public ActionResponse getChannelInfo(@HeaderParam(API_KEY) String userKey,
        @PathParam("channelCode") String channelCode, @QueryParam("simple") Boolean simple) {
        logger.debug(">getChannelInfo(), channelCode= " + channelCode);
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        if (userKey != null) {
            // 检查userKey
            action.validateUserKey(userKey);
            // 验证权限
            int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_MGTCHANNEL};
            action.validateUserAccess(ps);
        }

        action.getChannelByCode(channelCode, simple);
        logger.debug("<getChannelInfo()");
        return action.getActionResponse();
    }

    /**
     * 渠道号查看
     *
     * @param userKey
     * @param sourcePassword
     * @param sourcePhoneNumber
     * @param startDate
     * @param endDate
     * @return
     */
    @GET
    @Path("/channel/registerData")
    public ActionResponse getUserList(@HeaderParam("password") String sourcePassword, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize, @QueryParam("sourcePhoneNumber") String sourcePhoneNumber,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {

        logger.debug(">getUserList(), sourcePhoneNumber= " + sourcePhoneNumber);
        UserAction action = getBean(UserAction.ACTION_NAME);

        action.getUserList(pageNo, pageSize, sourcePassword, sourcePhoneNumber, startDate, endDate);
        logger.debug("<getUserList()");
        return action.getActionResponse();
    }

    /**
     * 查看渠道号明细
     */
    @GET
    @Path("/channel/users")
    public ActionResponse getUserDetails(@HeaderParam(API_KEY) String userKey, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize, @QueryParam("channelCode") String channelCode,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("isGetJbbChannels") Boolean isGetJbbChannels, @QueryParam("userType") Integer userType) {

        logger.debug(">getUserDetails()");
        UserAction action = getBean(UserAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getUserDetails(pageNo, pageSize, channelCode, startDate, endDate, isGetJbbChannels, userType);
        logger.debug("<getUserDetails()");
        return action.getActionResponse();
    }

    @GET
    @Path("/channels")
    public ActionResponse getChannels(@HeaderParam(API_KEY) String userKey, @QueryParam("searchText") String searchText,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("zhima") Integer zhima, @QueryParam("userType") Integer userType,
        @QueryParam("includeWool") Boolean includeWool, @QueryParam("includeEmptyMobile") Boolean includeEmptyMobile,
        @QueryParam("includeHiddenChannel") Boolean includeHiddenChannel, @QueryParam("simple") Boolean simple,
        @QueryParam("getAdStatistic") Boolean getAdStatistic, @QueryParam("getReducePercent") Boolean getReducePercent,
        @QueryParam("groupName") String groupName, @QueryParam("isReduce") Boolean isReduce,
        @QueryParam("accountId") Integer accountId, @QueryParam("mode") Integer mode) {
        logger.debug(">getChannels() ");
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_CHANNEL, Constants.MGT_P1_101,
            Constants.MGT_P1_102, Constants.MGT_P1_103, Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202,
            Constants.XJL_MGT_P2_205, Constants.XJL_MGT_P2_206, Constants.XJL_MGT_P2_207, Constants.XJL_MGT_P2_208,
            Constants.XJL_MGT_P2_209, Constants.XJL_MGT_P2_210, Constants.XJL_MGT_P2_211};
        if (simple == null || !simple) {
            action.validateUserAccess(ps);
        }
        action.getChannels(searchText, startDate, endDate, zhima, userType, includeWool, includeEmptyMobile,
            includeHiddenChannel, simple, getAdStatistic, groupName, getReducePercent, isReduce, accountId, mode);
        logger.debug("<getChannels()");
        return action.getActionResponse();
    }

    @GET
    @Path("/statistic")
    public ActionResponse getChannelStatistic(@HeaderParam(API_KEY) String userKey,
        @QueryParam("channelCode") String channelCode, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate, @QueryParam("zhima") Integer zhima,
        @QueryParam("userType") Integer userType, @QueryParam("includeWool") Boolean includeWool,
        @QueryParam("includeEmptyMobile") Boolean includeEmptyMobile,
        @QueryParam("includeHiddeChannel") boolean includeHiddeChannel) {

        logger.debug(">getChannelStatistic() ");
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_MGTCHANNEL,
            Constants.MGT_P1_101, Constants.MGT_P1_102, Constants.MGT_P1_103};
        action.validateUserAccess(ps);
        action.selectChannelStatisticS(channelCode, startDate, endDate, zhima, userType, includeWool,
            includeEmptyMobile, includeHiddeChannel);
        logger.debug("<getChannelStatistic()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/channel/{channelCode}")
    public ActionResponse putChannelInfo(@HeaderParam(API_KEY) String userKey,
        @PathParam("channelCode") String channelCode, ChannelAction.Request request) {
        logger.debug(">putChannelInfo(), channelCode= " + channelCode);
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_MGTCHANNEL,
            Constants.MGT_P1_101, Constants.MGT_P1_102, Constants.MGT_P1_103, Constants.XJL_MGT_P2_205,
            Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202};
        action.validateUserAccess(ps);
        action.updateChannelByCode(channelCode, request);
        logger.debug("<putChannelInfo()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/channel/{channelCode}")
    public ActionResponse deleteChannelInfo(@HeaderParam(API_KEY) String userKey,
        @PathParam("channelCode") String channelCode) {
        logger.debug(">putChannelInfo(), channelCode= " + channelCode);
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_MGTCHANNEL,
            Constants.MGT_P1_101, Constants.MGT_P1_102, Constants.MGT_P1_103, Constants.XJL_MGT_P2_205,
            Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202};
        action.validateUserAccess(ps);
        action.deleteChannelInfo(channelCode);
        logger.debug("<putChannelInfo()");
        return action.getActionResponse();
    }

    @POST
    @Path("/account")
    public ActionResponse insertAccount(@HeaderParam(API_KEY) String userKey, AccountAction.Request request) {
        logger.debug(">insertAccount() ");
        AccountAction action = getBean(AccountAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102};
        action.validateUserAccess(ps);
        action.insertAccount(request);
        logger.debug("<insertAccount()");
        return action.getActionResponse();
    }

    @GET
    @Path("/accounts")
    public ActionResponse getAccounts(@HeaderParam(API_KEY) String userKey, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize, @QueryParam("detail") boolean detail) {
        logger.debug(">getAccounts() ");
        AccountAction action = getBean(AccountAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102};
        action.validateUserAccess(ps);
        action.getAccounts(pageNo, pageSize, detail);
        logger.debug("<getAccounts()");
        return action.getActionResponse();
    }

    @GET
    @Path("/account")
    public ActionResponse getAccountlInfo(@HeaderParam(API_KEY) String userKey,
        @QueryParam("accountId") Integer accountId) {
        logger.debug(">getAccountlInfo(), accountId= " + accountId);
        AccountAction action = getBean(AccountAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.getAccountlById(accountId);
        logger.debug("<getAccountlInfo()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/account")
    public ActionResponse updateAccount(@HeaderParam(API_KEY) String userKey,
        @QueryParam("accountId") Integer accountId, AccountAction.Request request) {
        logger.debug(">updateAccount(), accountId= " + accountId);
        AccountAction action = getBean(AccountAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.updateAccountlById(accountId, request);
        logger.debug("<updateAccount()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/account/status")
    public ActionResponse updateAccountStatus(@HeaderParam(API_KEY) String userKey,
        @QueryParam("accountId") Integer accountId, @QueryParam("isDeleted") boolean isDeleted) {
        logger.debug(">updateAccountStates(), accountId= " + accountId);
        AccountAction action = getBean(AccountAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P1_101, Constants.MGT_P1_102};
        action.validateUserAccess(ps);
        action.updateAccountStatus(accountId, isDeleted);
        logger.debug("<updateAccountStates()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/account/{accountId}/freeze")
    public ActionResponse freezeAccount(@HeaderParam(API_KEY) String userKey, @PathParam("accountId") int accountId) {
        logger.debug(">freezeAccount(), accountId= " + accountId);
        AccountAction action = getBean(AccountAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_ORGADMIN, Constants.MGT_P1_102};
        action.validateUserAccess(ps);
        action.freezeAccountById(accountId);
        logger.debug("<freezeAccount()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/account/{accountId}/unfreeze")
    public ActionResponse thawAccount(@HeaderParam(API_KEY) String userKey, @PathParam("accountId") int accountId) {
        logger.debug(">thawAccount(), accountId= " + accountId);
        AccountAction action = getBean(AccountAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_ORGADMIN, Constants.MGT_P1_102};
        action.validateUserAccess(ps);
        action.thawAccountById(accountId);
        logger.debug("<thawAccount()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/channel/{channelCode}/freeze")
    public ActionResponse freezeChannelInfo(@HeaderParam(API_KEY) String userKey,
        @PathParam("channelCode") String channelCode) {
        logger.debug(">putChannelInfo(), channelCode= " + channelCode);
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_MGTCHANNEL,
            Constants.MGT_P1_101, Constants.MGT_P1_102, Constants.MGT_P1_103, Constants.XJL_MGT_P2_205,
            Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202};
        action.validateUserAccess(ps);
        action.freezeChannelInfo(channelCode);
        logger.debug("<putChannelInfo()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/channel/{channelCode}/unfreeze")
    public ActionResponse thawChannelInfo(@HeaderParam(API_KEY) String userKey,
        @PathParam("channelCode") String channelCode) {
        logger.debug(">putChannelInfo(), channelCode= " + channelCode);
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_MGTCHANNEL,
            Constants.MGT_P1_101, Constants.MGT_P1_102, Constants.MGT_P1_103, Constants.XJL_MGT_P2_205,
            Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202};
        action.validateUserAccess(ps);
        action.thawChannelInfo(channelCode);
        logger.debug("<putChannelInfo()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user/policy")
    public ActionResponse getPostPolicy(@HeaderParam(API_KEY) String userKey,
        @QueryParam("filePrefix") String filePrefix) {
        logger.debug(">logout()");
        UserPolicyAction action = getBean(UserPolicyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getPolicy(filePrefix);
        logger.debug("<logout()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/log")
    public ActionResponse logUserEvent(@QueryParam("userId") Integer userId, @QueryParam("cookieId") String cookieId,
        @QueryParam("channelCode") String channelCode, @QueryParam("eventName") String eventName,
        @QueryParam("eventLabel") String eventLabel, @QueryParam("eventAction") String eventAction,
        @QueryParam("eventValue") String eventValue, @QueryParam("eventValue2") String eventValue2) {
        logger.debug(">logUserEvent()");
        UserLogEventAction action = getBean(UserLogEventAction.ACTION_NAME);
        action.createLogEvent(userId, cookieId, channelCode, eventName, eventLabel, eventAction, eventValue,
            eventValue2);
        logger.debug("<logUserEvent()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user")
    public ActionResponse getUserInfo(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getUserInfo()");
        UserInfoAction action = getBean(UserInfoAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.selectUserInfo();
        logger.debug("<getUserInfo()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user")
    public ActionResponse insertMgtUser(@HeaderParam(API_KEY) String userKey, UserAction.Request req,
        @QueryParam("channelCode") String channelCode, @QueryParam("getRecommand") Boolean getRecommand,
        @QueryParam("userType") Integer userType, @QueryParam("step") Integer step,
        @HeaderParam("password") String password, @QueryParam("test") Boolean test) {
        logger.debug(">insertMgtUser()");
        UserAction action = getBean(UserAction.ACTION_NAME);
        // 检查userKey
        action.validateEntryUserKey(userKey);
        action.createUser(channelCode, userType, step, test, getRecommand, password, req);
        logger.debug("<insertMgtUser()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/user/applyToOrgs")
    public ActionResponse applyToOrgs(@HeaderParam(API_KEY) String userKey, UserAction.ApplyToOrgsRequest request) {
        logger.debug(">applyToOrgs()");
        UserAction action = getBean(UserAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.applyToOrgs(request.orgs);
        logger.debug("<applyToOrgs()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/loginByMsgCode")
    public ActionResponse loginByMsgCode(@QueryParam("msgCode") String msgCode,
        @QueryParam("phoneNumber") String phoneNumber, @QueryParam("channelCode") String channelCode,
        @QueryParam("dCode") String delegateCode, @QueryParam("platform") String platform,
        @QueryParam("mobileManufacture") String mobileManufacture, @QueryParam("test") Boolean test,
        @QueryParam("initAccountId") Integer initAccountId, @QueryParam("userType") Integer userType,
        @QueryParam("expiry") Long expiry, @QueryParam("applicationId") Integer applicationId,
        @HeaderParam("password") String password) {
        logger.debug(">loginByMsgCode(), phoneNumber=" + phoneNumber + ",msgCode=" + msgCode + ",channelCode="
            + channelCode + ",test=" + test + ",platform=" + platform + ",mobileManufacture=" + mobileManufacture
            + ",userType=" + userType + ",expiry=" + expiry + ",applicationId=" + applicationId);
        LoginUserAction action = getBean(LoginUserAction.ACTION_NAME);

        action.loginUserByPhoneNumber(phoneNumber, msgCode, password, channelCode, delegateCode, initAccountId,
            platform, mobileManufacture, test, userType, expiry, applicationId);
        logger.debug("<loginByMsgCode()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/loginByPassword")
    public ActionResponse loginByPassword(@QueryParam("phoneNumber") String phoneNumber,
        @QueryParam("expiry") Long expiry, @HeaderParam("password") String password,
        @QueryParam("channelCode") String channelCode, @QueryParam("platform") String platform,
        @QueryParam("applicationId") Integer applicationId) {
        logger.debug(
            ">loginByPassword(), phoneNumber=" + phoneNumber + ",expiry=" + expiry + ",applicationId=" + applicationId);
        LoginUserAction action = getBean(LoginUserAction.ACTION_NAME);
        action.loginUserByPassword(phoneNumber, password, expiry, applicationId, channelCode, platform);
        logger.debug("<loginByMsgCode()");
        return action.getActionResponse();
    }

    @POST
    @Path("/bnh/userLogin")
    public ActionResponse bnhUserLogin(@QueryParam("msgCode") String msgCode,
        @QueryParam("phoneNumber") String phoneNumber, @QueryParam("channelCode") String channelCode,
        @QueryParam("platform") String platform, @QueryParam("mobileManufacture") String mobileManufacture,
        @QueryParam("userType") Integer userType, @QueryParam("expiry") Long expiry,
        @QueryParam("applicationId") Integer applicationId, LoginUserAction.Request req) throws Exception {
        logger.debug(">bnhUserLogin(), phoneNumber=" + phoneNumber + ",msgCode=" + msgCode + ",channelCode="
            + channelCode + ",platform=" + platform + ",mobileManufacture=" + mobileManufacture + ",userType="
            + userType + ",expiry=" + expiry + ",applicationId=" + applicationId);
        LoginUserAction action = getBean(LoginUserAction.ACTION_NAME);

        action.bnhUserLogin(phoneNumber, msgCode, channelCode, platform, mobileManufacture, userType, expiry,
            applicationId, req);
        logger.debug("<bnhUserLogin()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/location")
    public ActionResponse insertUserLocation(@HeaderParam(API_KEY) String userKey, UserLocationAction.Request req) {
        logger.debug(">insertUserLocation()");
        UserLocationAction action = getBean(UserLocationAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.insertUserLocation(req);
        logger.debug("<insertUserLocation()");
        return action.getActionResponse();
    }

    @GET
    @Path("/org/user/h5Url")
    public ActionResponse getUserAddDataH5Url(@HeaderParam(API_KEY) String userKey,
        @QueryParam("applyId") Integer applyId, @QueryParam("refresh") Boolean refresh) {
        logger.debug(">getUserAddDataH5Url()");
        UserAction action = getBean(UserAction.ACTION_NAME);
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_PRE_CHECK,
            Constants.MGT_P_FIN_CHECK, Constants.MGT_P_ASSIGN, Constants.MGT_P_BILL};
        action.validateUserAccess(ps);
        action.getUserAddDataH5Url(applyId, refresh);
        logger.debug("<getUserAddDataH5Url()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user/apply/{applyId}/oplogs")
    public ActionResponse getOplogs(@HeaderParam(API_KEY) String userKey, @PathParam("applyId") Integer applyId) {
        logger.debug(">getOplogs()");
        UserAction action = getBean(UserAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getOplogs(applyId);
        logger.debug("<getOplogs()");
        return action.getActionResponse();
    }

    @GET
    @Path("/org/loan/{loanId}/oplogs")
    public ActionResponse getLoadOplogs(@HeaderParam(API_KEY) String userKey, @PathParam("loanId") Integer loanId,
        @QueryParam("opType") Integer[] opTypes) {
        logger.debug(">getLoadOplogs()");
        LoanOpLogAction action = getBean(LoanOpLogAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getLoanOpLogs(loanId, opTypes);
        logger.debug("<getLoadOplogs()");
        return action.getActionResponse();
    }

    @POST
    @Path("/org/loan/{loanId}/oplog")
    public ActionResponse insertLoanOpLog(@HeaderParam(API_KEY) String userKey, @PathParam("loanId") int loanId,
        LoanOpLogAction.Request request) {
        logger.debug(">insertLoanOpLog(), loanId=" + loanId + ", req=" + request);
        LoanOpLogAction action = getBean(LoanOpLogAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.insertLoanOpLog(loanId, request);
        logger.debug("<insertLoanOpLog()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user/apply/statisticsNumber")
    public ActionResponse getStatisticsNumber(@HeaderParam(API_KEY) String userKey,
        @QueryParam("statuse") Integer[] statuses, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getStatisticsNumber()");
        UserApplyStatisticAction action = getBean(UserApplyStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getStatisticsNumber(statuses, startDate, endDate);
        logger.debug("<getStatisticsNumber()");
        return action.getActionResponse();
    }

    @POST
    @Path("/org/apply/{applyId}")
    public ActionResponse updateUserApplyRecord(@HeaderParam(API_KEY) String userKey,
        @PathParam("applyId") Integer applyId, UserApplyUpdateAction.Request request) {
        logger.debug(">updateUserApplyRecord(), applyId=" + applyId + ", req=" + request);
        UserApplyUpdateAction action = getBean(UserApplyUpdateAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.updateUserApplyRecord(applyId, request);
        logger.debug("<updateUserApplyRecord()");
        return action.getActionResponse();
    }

    @GET
    @Path("/org/apply/{applyId}")
    public ActionResponse getUserApplyRecord(@HeaderParam(API_KEY) String userKey,
        @PathParam("applyId") Integer applyId) {
        logger.debug(">getUserApplyRecord(), applyId=" + applyId + ", req=" + request);
        UserApplyInfoAction action = getBean(UserApplyInfoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getUserApplyRecord(applyId);
        logger.debug("<getUserApplyRecord()");
        return action.getActionResponse();
    }

   
    @GET
    @Path("/org/apply/{applyId}/userInfo")
    public ActionResponse queryApplyInfo(@HeaderParam(API_KEY) String userKey, @PathParam("applyId") Integer applyId) {
        logger.debug(">queryApplyInfo(), applyId=" + applyId);
        UserApplyAction action = getBean(UserApplyAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getUserDetailInfo(applyId);
        logger.debug("<queryApplyInfo()");
        return action.getActionResponse();
    }

   
    @GET
    @Path("/org/apply")
    public ActionResponse getUserApplyRecords(@HeaderParam(API_KEY) String userKey, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize, @QueryParam("orderBy") String orderBy, @QueryParam("desc") Boolean desc,
        @QueryParam("status") int[] statuses, @QueryParam("op") String op, @QueryParam("getTotal") Boolean getTotal,
        @QueryParam("phoneNumberSearch") String phoneNumberSearch, @QueryParam("channelSearch") String channelSearch,
        @QueryParam("usernameSearch") String usernameSearch, @QueryParam("assignNameSearch") String assignNameSearch,
        @QueryParam("initNameSearch") String initNameSearch, @QueryParam("finalNameSearch") String finalNameSearch,
        @QueryParam("loanNameSearch") String loanNameSearch, @QueryParam("idcardSearch") String idcardSearch,
        @QueryParam("jbbIdSearch") String jbbIdSearch, @QueryParam("feedback") Boolean feedback,
        @QueryParam("sOrgId") Integer sOrgId, @QueryParam("sUserType") Integer sUserType,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("initAccountId") Integer initAccountId, @QueryParam("finalAccountId") Integer finalAccountId,
        @QueryParam("loanAccountId") Integer loanAccountId, @QueryParam("currentAccountId") Integer currentAccountId,
        @QueryParam("channelCode") String channelCode, @QueryParam("verified") Boolean verified

    ) {
        logger.debug(">getUserApplyRecords(), pageNo=" + pageNo + "pageSize=" + pageSize + "statuses=" + statuses
            + "phoneNumberSearch=" + phoneNumberSearch + "channelSearch=" + channelSearch + "usernameSearch="
            + usernameSearch + "op=" + op + "op=" + initNameSearch + "initNameSearch=" + initNameSearch
            + "assignNameSearch=" + assignNameSearch + "finalNameSearch=" + finalNameSearch + "loandNameSearch="
            + loanNameSearch + "idcardSearch=" + idcardSearch + "jbbIdSearch=" + jbbIdSearch + "feedback=" + feedback
            + "sOrgId=" + sOrgId + "sUserType=" + sUserType + "startDate=" + startDate + "endDate=" + endDate
            + "initAccountId=" + initAccountId + "finalAccountId=" + finalAccountId + "loanAccountId=" + loanAccountId
            + "currentAccountId=" + currentAccountId + "channelCode=" + channelCode + "verified=" + verified);

        UserApplyListAction action = getBean(UserApplyListAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getUserApplyRecords(pageNo, pageSize, getTotal, orderBy, desc, op, statuses, phoneNumberSearch,
            channelSearch, usernameSearch, assignNameSearch, initNameSearch, finalNameSearch, loanNameSearch,
            idcardSearch, jbbIdSearch, feedback, sOrgId, sUserType, startDate, endDate, initAccountId, finalAccountId,
            loanAccountId, currentAccountId, channelCode, verified);
        logger.debug("<getUserApplyRecords()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/org/applys")
    public ActionResponse deleteUserApplys(@HeaderParam(API_KEY) String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("isDelete") Boolean isDelete) {
        logger.debug(">deleteUserApplys()");
        UserApplyDeleteAction action = getBean(UserApplyDeleteAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN};
        action.validateUserAccess(ps);
        action.deleteUserApplys(startDate, endDate, isDelete);
        logger.debug("<deleteUserApplys()");
        return action.getActionResponse();
    }

    @GET
    @Path("/org/loans")
    public ActionResponse getUserLoanRecords(@HeaderParam(API_KEY) String userKey, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize, @QueryParam("orderBy") String orderBy, @QueryParam("desc") Boolean desc,
        @QueryParam("status") int[] statuses, @QueryParam("op") String op, @QueryParam("iouStatus") int[] iouStatuses,
        @QueryParam("iouDateType") int iouDateType, @QueryParam("phoneNumberSearch") String phoneNumberSearch,
        @QueryParam("channelSearch") String channelSearch, @QueryParam("usernameSearch") String usernameSearch,
        @QueryParam("idcardSearch") String idcardSearch, @QueryParam("jbbIdSearch") String jbbIdSearch

    ) {
        logger.debug(">getUserLoanRecords(), pageNo=" + pageNo + "pageSize=" + pageSize + "statuses=" + statuses
            + "phoneNumberSearch=" + phoneNumberSearch + "channelSearch=" + channelSearch + "usernameSearch="
            + usernameSearch + "op=" + op + "op=" + "idcardSearch=" + idcardSearch + "jbbIdSearch=" + jbbIdSearch);

        LoanRecordListAction action = getBean(LoanRecordListAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getUserApplyRecords(pageNo, pageSize, orderBy, desc, op, statuses, iouStatuses, iouDateType,
            phoneNumberSearch, channelSearch, usernameSearch, idcardSearch, jbbIdSearch);
        logger.debug("<getUserLoanRecords()");
        return action.getActionResponse();
    }

    @POST
    @Path("/org/loan")
    public ActionResponse saveUserLoanRecord(@HeaderParam(API_KEY) String userKey,
        UserLoanRecordUpdateAction.Request request) {
        logger.debug(">saveUserLoanRecord(), req =  " + request);
        UserLoanRecordUpdateAction action = getBean(UserLoanRecordUpdateAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        action.saveUserLoanRecord(request);
        logger.debug("<saveUserLoanRecord()");
        return action.getActionResponse();
    }

    @GET
    @Path("/dataflow/setting")
    public ActionResponse getDataFlowSetting(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getDataFlowSetting()");
        DataFlowSettingsAction action = getBean(DataFlowSettingsAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.getDataFlowSetting();
        logger.debug("<getDataFlowSettings()");
        return action.getActionResponse();
    }

    @POST
    @Path("/dataflow/setting")
    public ActionResponse updateDataFlowSetting(@HeaderParam(API_KEY) String userKey,
        DataFlowSettingsAction.Request req) {
        logger.debug(">updateDataFlowSettings()");
        DataFlowSettingsAction action = getBean(DataFlowSettingsAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_ORGADMIN, Constants.MGT_P_CHANNEL};
        action.validateUserAccess(ps);
        action.updateDataFlowSetting(req);
        logger.debug("<updateDataFlowSettings()");
        return action.getActionResponse();
    }

    @GET
    @Path("/dataflow/config")
    public ActionResponse getDataFlowConfig(@HeaderParam(API_KEY) String userKey,
        @QueryParam("includeHidden") Boolean includeHidden) {
        logger.debug(">getDataFlowConfig(), includeHidden =  " + includeHidden);
        DataFlowBaseAction action = getBean(DataFlowBaseAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getDataFlowConfig(includeHidden);
        logger.debug("<getDataFlowConfig()");
        return action.getActionResponse();
    }

    @GET
    @Path("/roles")
    public ActionResponse getRolesAndPermissions(@HeaderParam(API_KEY) String userKey,
        @QueryParam("applicationId") Integer applicationId) {
        logger.debug(">getRolesAndPermissions()");
        RolesAction action = getBean(RolesAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.getRolesAndPermissions(applicationId);
        logger.debug("<getRolesAndPermissions()");
        return action.getActionResponse();
    }


    @POST
    @Path("/files/user")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png"})
    public ActionResponse postUploadUserIdCard(@HeaderParam(API_KEY) String userKey, @QueryParam("type") Integer type,
        byte[] content) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug(">postUploadUserIdCard()" + ", content.length=" + (content != null ? content.length : 0));
        }
        FileUploadAction action = getBean(FileUploadAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.uploadUserIdCard(content, type);
        return action.getActionResponse();
    }

    

    @POST
    @Path("/user/appcontacts")
    public ActionResponse postConstacts(@HeaderParam(API_KEY) String userKey, JSONObject req) {
        logger.debug(">postConstacts()");
        UserContanctAction action = getBean(UserContanctAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.processContacts(req);
        logger.debug("<postConstacts()");
        return action.getActionResponse();
    }


    @POST
    @Path("/files/screenshot")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png"})
    public ActionResponse postScreenshot(@HeaderParam(API_KEY) String userKey, @QueryParam("type") Integer type,
        byte[] content, @QueryParam("userId") Integer userId) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug(">postScreenshot()" + ", content.length=" + (content != null ? content.length : 0));
        }
        FileUploadAction action = getBean(FileUploadAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.uploadScreenShot(content, type, userId);
        return action.getActionResponse();
    }


    @GET
    @Path("/statistic/total")
    public ActionResponse getIndexData(@HeaderParam(API_KEY) String userKey, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getIndexData()");
        IndexCountAction action = getBean(IndexCountAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.indexCountData(startDate, endDate);
        logger.debug("<getIndexData()");
        return action.getActionResponse();
    }

    @GET
    @Path("/org/info")
    public ActionResponse get(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getOrganizationById()");
        OrganizationAction action = getBean(OrganizationAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getOrganizationById();
        logger.debug("<getOrganizationById()");
        return action.getActionResponse();
    }

    /*@GET
    @Path("/channelUrl")
    public ActionResponse getRedirectUrl(@QueryParam("channelCode") String channelCode,
        @QueryParam("sourceId") String sourceId, @Context UriInfo ui, @Context HttpServletResponse response)
        throws IOException {
        logger.debug(">getRedirectUrl()");
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        if (StringUtil.isEmpty(channelCode)) {
            channelCode = sourceId;
        }
        action.getRedirectUrl(channelCode, response, ui);
        logger.debug("<getRedirectUrl()");
        return action.getActionResponse();
    }*/

    @GET
    @Path("/h5config")
    public ActionResponse getH5Config(@HeaderParam(API_KEY) String userKey,
        @QueryParam("channelCode") String channelCode, @QueryParam("platform") String platform) {
        logger.debug(">getH5Config() ");
        H5MerchantAction action = getBean(H5MerchantAction.ACTION_NAME);
        if (!StringUtil.isEmpty(userKey)) {
            action.validateEntryUserKey(userKey);
        }
        action.selectH5ConfigByChannelCode(channelCode, platform);
        logger.debug("<getH5Config() ");
        return action.getActionResponse();
    }



    @GET
    @Path("/channel/statistic")
    public ActionResponse getStatisticByChannelCode(@HeaderParam("password") String sourcePassword,
        @QueryParam("sourcePhoneNumber") String sourcePhoneNumber, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getStatisticByChannelCode()");
        ChannelStaDailyAction action = getBean(ChannelStaDailyAction.ACTION_NAME);
        action.getStatisticByChannelCode(sourcePhoneNumber, sourcePassword, startDate, endDate);
        logger.debug("<getStatisticByChannelCode()");
        return action.getActionResponse();
    }
    @GET
    @Path("/channel/groupName")
    public ActionResponse getChannelGroupName(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getChannelGroupName()");
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getChannelGroupName();
        logger.debug("<getChannelGroupName()");
        return action.getActionResponse();
    }

    @GET
    @Path("/message/report")
    public JSONObject getMsgReport(@QueryParam("receiver") String receiver, @QueryParam("pswd") String pswd,
        @QueryParam("msgid") String msgid, @QueryParam("reportTime") String reportTime,
        @QueryParam("mobile") String mobile, @QueryParam("status") String status,
        @QueryParam("notifyTime") String notifyTime, @QueryParam("statusDesc") String statusDesc,
        @QueryParam("uid") String uid, @QueryParam("length") int length) {
        logger.debug(">getMsgReport()");
        MsgReportAction action = getBean(MsgReportAction.ACTION_NAME);
        String clCode = MsgReportAction.SUCCESS;
        try {
            action.getMsgReport(receiver, pswd, msgid, reportTime, mobile, status, notifyTime, statusDesc, uid, length);
        } catch (Exception e) {
            clCode = MsgReportAction.FAIL;
        }
        JSONObject json = new JSONObject();
        json.put("clcode", clCode);
        logger.debug("<getMsgReport()");
        return json;
    }


    @POST
    @Path("/user/jiguang/device")
    public ActionResponse saveJiGuangUserDevice(@HeaderParam(API_KEY) String userKey, JiGuangAction.Request req) {
        logger.debug(">saveJiGuangUserDevice()");
        JiGuangAction action = getBean(JiGuangAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.saveJiGuangUserDevice(req);
        logger.debug("<saveJiGuangUserDevice()");
        return action.getActionResponse();
    }

    @GET
    @Path("/promotion")
    public ActionResponse getChannelPromotion(@QueryParam("sourcePhoneNumber") String sourcePhoneNumber,
        @HeaderParam("password") String sourcePassword, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getJuXinLiReport(), channelCode = " + sourcePhoneNumber);
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        action.getChannelPromotion(sourcePhoneNumber, sourcePassword, startDate, endDate);
        logger.debug("<getJuXinLiReport() ");
        return action.getActionResponse();
    }

    @POST
    @Path("/img")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM})
    public ActionResponse imgUpload(@HeaderParam(API_KEY) String userKey, @QueryParam("type") Integer type,
        byte[] content) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug(">postUpload()" + ", content.length=" + (content != null ? content.length : 0));
        }
        UploadUtilAction action = getBean(UploadUtilAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.validateJbbAccount();
        // 验证权限
        action.upload(content, type);
        return action.getActionResponse();
    }

    @GET
    @Path("/app/about")
    public ActionResponse getAppInfoByAppName(@QueryParam("appName") String appName) {
        logger.debug(">getAppInfoByAppName(), appName = " + appName);
        AppInfoAction action = getBean(AppInfoAction.ACTION_NAME);
        action.getAppInfoByAppName(appName);
        logger.debug("<getAppInfoByAppName() ");
        return action.getActionResponse();
    }

    @GET
    @Path("/appVersion/info")
    public ActionResponse getAppVersionInfo(@QueryParam("marketingName") String marketingName,
        @QueryParam("productName") String productName, @QueryParam("version") String version) {
        logger.debug(">getPlatformInfo()");
        AppVersionAction action = getBean(AppVersionAction.ACTION_NAME);
        action.getAppVersionInfo(marketingName, productName, version);
        logger.debug("<getPlatformFees()");

        return action.getActionResponse();
    }

}
