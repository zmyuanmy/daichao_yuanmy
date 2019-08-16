package com.jbb.mgt.server.rs.services;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import net.sf.json.JSON;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.domain.JuXinLiNotify;
import com.jbb.mgt.core.domain.YxNotify;
import com.jbb.mgt.rs.action.ChannelStaDailyAction.ChannelStaDailyAction;
import com.jbb.mgt.rs.action.Feedback.UserApplyFeedbackAction;
import com.jbb.mgt.rs.action.OrgAssignSettings.OrgAssignSettingAction;
import com.jbb.mgt.rs.action.account.AccountAction;
import com.jbb.mgt.rs.action.account.PasswordAction;
import com.jbb.mgt.rs.action.alipay.AlipayAction;
import com.jbb.mgt.rs.action.app.AppInfoAction;
import com.jbb.mgt.rs.action.app.AppVersionAction;
import com.jbb.mgt.rs.action.batch.TeleFileDownloadAction;
import com.jbb.mgt.rs.action.batch.TeleFileUploadAction;
import com.jbb.mgt.rs.action.batch.TeleMarketingAction;
import com.jbb.mgt.rs.action.batch.TeleMarketingCheckAction;
import com.jbb.mgt.rs.action.batch.TeleMarketingDetailAction;
import com.jbb.mgt.rs.action.batch.TeleMarketingInitAction;
import com.jbb.mgt.rs.action.channel.ChannelAction;
import com.jbb.mgt.rs.action.club.ClubReportAction;
import com.jbb.mgt.rs.action.count.CountAction;
import com.jbb.mgt.rs.action.dflow.DataFlowBaseAction;
import com.jbb.mgt.rs.action.dflow.DataFlowSettingsAction;
import com.jbb.mgt.rs.action.face.FaceAction;
import com.jbb.mgt.rs.action.h5Merchant.H5MerchantAction;
import com.jbb.mgt.rs.action.index.IndexCountAction;
import com.jbb.mgt.rs.action.jiGuang.JiGuangAction;
import com.jbb.mgt.rs.action.juLiXin.JuXinLiAction;
import com.jbb.mgt.rs.action.loan.LoanRecordListAction;
import com.jbb.mgt.rs.action.loan.UserLoanRecordUpdateAction;
import com.jbb.mgt.rs.action.loanRecord.LoanOpLogAction;
import com.jbb.mgt.rs.action.log.UserLogEventAction;
import com.jbb.mgt.rs.action.login.LoginAction;
import com.jbb.mgt.rs.action.logout.LogoutAction;
import com.jbb.mgt.rs.action.msgCode.MsgCodeAction;
import com.jbb.mgt.rs.action.msgReport.MsgReportAction;
import com.jbb.mgt.rs.action.organization.OrganizationAction;
import com.jbb.mgt.rs.action.organization.OrganizationLenderAction;
import com.jbb.mgt.rs.action.organization.OrganizationSecondAction;
import com.jbb.mgt.rs.action.platform.PlatformAction;
import com.jbb.mgt.rs.action.report.TdPreloanReportAction;
import com.jbb.mgt.rs.action.roles.RolesAction;
import com.jbb.mgt.rs.action.upload.FileUploadAction;
import com.jbb.mgt.rs.action.upload.MailListUploadAction;
import com.jbb.mgt.rs.action.upload.UploadUtilAction;
import com.jbb.mgt.rs.action.user.LoginUserAction;
import com.jbb.mgt.rs.action.user.UserAction;
import com.jbb.mgt.rs.action.user.UserApplyAction;
import com.jbb.mgt.rs.action.user.UserApplyAssignAction;
import com.jbb.mgt.rs.action.user.UserApplyDeleteAction;
import com.jbb.mgt.rs.action.user.UserApplyInfoAction;
import com.jbb.mgt.rs.action.user.UserApplyListAction;
import com.jbb.mgt.rs.action.user.UserApplyStatisticAction;
import com.jbb.mgt.rs.action.user.UserApplyUpdateAction;
import com.jbb.mgt.rs.action.user.UserContanctAction;
import com.jbb.mgt.rs.action.user.UserFaceDetectAction;
import com.jbb.mgt.rs.action.user.UserInfoAction;
import com.jbb.mgt.rs.action.user.UserLocationAction;
import com.jbb.mgt.rs.action.user.UserPolicyAction;
import com.jbb.mgt.rs.action.userDevice.UserDeviceAction;
import com.jbb.mgt.rs.action.userDevice.UserLcDeviceAction;
import com.jbb.mgt.rs.action.whtianbeiReport.WhtianbeiReportAction;
import com.jbb.mgt.rs.action.yx.YxReportAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.ReLiveDetectRequest;
import com.jbb.server.common.Constants;
import com.jbb.server.common.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * API Services
 *
 * @author VincentTang
 * @date 2017年12月20日
 */

@Path("mgt")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbMgtServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbMgtServices.class);

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        logger.debug(">ping() ");
        logger.debug("<ping()");
        return "pong";
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

    @GET
    @Path("/td/preloan/report")
    public ActionResponse getTdPreloanReport(@HeaderParam(API_KEY) String userKey, @QueryParam("applyId") int applyId,
        @QueryParam("reportId") String reportId, @QueryParam("userId") int userId,
        @QueryParam("refreshReport") Boolean refreshReport) {
        logger.debug(">getTdPreloanReport(), applyId = " + applyId + ", reportId=" + reportId + ", userId=" + userId);
        TdPreloanReportAction action = getBean(TdPreloanReportAction.ACTION_NAME);
        action.validateUserKey(userKey);
        if (refreshReport != null && refreshReport == true) {
            int[] ps = {Constants.MGT_P_ORGADMIN, Constants.MGT_P_PRE_CHECK, Constants.MGT_P_ASSIGN,
                Constants.MGT_P_FIN_CHECK, Constants.MGT_P_BILL};
            action.validateUserAccess(ps);
        }
        action.getPreloanReport(applyId, userId, refreshReport);
        logger.debug("<getTdPreloanReport()");
        return action.getActionResponse();
    }

    @GET
    @Path("/club/report")
    public ActionResponse getClubReport(@HeaderParam(API_KEY) String userKey, @QueryParam("userId") Integer userId,
        @QueryParam("taskId") String taskId, @QueryParam("channelType") String channelType,
        @QueryParam("channelCode") String channelCode) {
        logger.debug(">getClubReport(), userId = " + userId + ", userId=" + userId + ", channelType=" + channelType);
        ClubReportAction action = getBean(ClubReportAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.MGT_P_ORGADMIN, Constants.MGT_P_PRE_CHECK, Constants.MGT_P_ASSIGN,
            Constants.MGT_P_FIN_CHECK, Constants.MGT_P_BILL};
        action.validateUserAccess(ps);
        if (taskId != null) {
            action.getClubReport(taskId);
        } else if (userId != null) {
            action.getClubReport(userId, channelType, channelCode);
        }
        logger.debug("<getTdPreloanReport()");
        return action.getActionResponse();
    }

    @POST
    @Path("/club/h5/notify")
    public ActionResponse clubH5Notify(@QueryParam("userId") Integer userId, @QueryParam("taskId") String taskId) {
        logger.debug(">getClubReport(), userId = " + userId + ", taskId=" + taskId);
        ClubReportAction action = getBean(ClubReportAction.ACTION_NAME);
        action.notifyToGetAndSaveReport(userId, taskId);
        logger.debug("<getClubReport()");
        return action.getActionResponse();
    }

    @POST
    @Path("/yixiang/notify")
    @Produces(MediaType.TEXT_PLAIN)
    public String yiXiangNotify(YxNotify notify) {
        logger.debug(">yiXiangNotify() ");
        YxReportAction action = getBean(YxReportAction.ACTION_NAME);
        action.notify(notify);
        logger.debug("<yiXiangNotify() ");
        return "success";
    }

    @GET
    @Path("/yixiang/h5")
    public ActionResponse getYiXiangH5Url(@HeaderParam(API_KEY) String userKey, @QueryParam("userId") int userId,
        @QueryParam("applyId") int applyId, @QueryParam("reportType") String reportType) {
        logger.debug(">getYiXiangH5Url(), userId = " + userId + " ,applyId=" + applyId + " ,reportType=" + reportType);
        YxReportAction action = getBean(YxReportAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.generateH5Url(userId, applyId, reportType);
        logger.debug("<getYiXiangH5Url() ");
        return action.getActionResponse();
    }

    @GET
    @Path("/yixiang/report")
    public ActionResponse getReport(@HeaderParam(API_KEY) String userKey, @QueryParam("userId") Integer userId,
        @QueryParam("applyId") Integer applyId, @QueryParam("taskId") String taskId,
        @QueryParam("reportType") String reportType) {
        logger.debug(">getReport(), userId = " + userId + " ,applyId=" + applyId + " ,taskId=" + taskId
            + " ,reportType=" + reportType);
        YxReportAction action = getBean(YxReportAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getReport(userId, applyId, taskId, reportType);
        logger.debug("<getYiXiangH5Url() ");
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

    @POST
    @Path("/org/assign")
    public ActionResponse assignUser(@HeaderParam(API_KEY) String userKey, UserApplyAssignAction.Request req) {
        logger.debug(">assignUser()");
        UserApplyAssignAction action = getBean(UserApplyAssignAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_ASSIGN};
        action.validateUserAccess(ps);
        action.assignApplyUsers(req);
        logger.debug("<assignUser()");
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

    /**
     * 获取用户详细信息
     *
     * @param userKey
     * @param applyId
     * @param type 获取详细信息的类型
     * @return
     */
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

    /**
     * 获取用户列表
     *
     * @param userKey
     * @param type 列表类型
     * @return
     */
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
    @Path("/tele/upload")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "text/plain"})
    public ActionResponse postTelephoneFile(@HeaderParam(API_KEY) String userKey, @QueryParam("price") Integer price,
        byte[] content, @QueryParam("fileName") String fileName) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug(">postTelephoneFile()" + ", content.length=" + (content != null ? content.length : 0));
        }
        TeleFileUploadAction action = getBean(TeleFileUploadAction.ACTION_NAME);
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_TELEMARKETING};
        action.validateUserAccess(ps);
        action.teleUpload(price, content, fileName);
        logger.debug("<postTelephoneFile()");
        return action.getActionResponse();
    }

    /**
     * 上传组织图处，上传组织logo和背景
     *
     * @param userKey
     * @param content
     * @return
     * @throws IOException
     */
    @POST
    @Path("/files/org")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png"})
    public ActionResponse postUpload(@HeaderParam(API_KEY) String userKey, @QueryParam("type") Integer type,
        byte[] content) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug(">postUpload()" + ", content.length=" + (content != null ? content.length : 0));
        }
        FileUploadAction action = getBean(FileUploadAction.ACTION_NAME);
        action.validateUserKey(userKey);

        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_MGTCHANNEL,
            Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_205};
        action.validateUserAccess(ps);
        action.upload(content, type);
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
    @Path("/user/contants/{userId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ActionResponse uploadContants(@HeaderParam(API_KEY) String userKey, @PathParam("userId") Integer userId,
        File file) throws IOException {
        logger.debug(">uploadContants() ");
        MailListUploadAction action = getBean(MailListUploadAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.uploadContants(file, userId);
        logger.debug("<uploadContants() ");
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

    @GET
    @Path("/user/contants/{userId}")
    public ActionResponse getUserContants(@HeaderParam(API_KEY) String userKey, @PathParam("userId") Integer userId) {
        logger.debug(">getUserContants() ");
        MailListUploadAction action = getBean(MailListUploadAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getUserContants(userId);
        logger.debug("<getUserContants() ");
        return action.getActionResponse();
    }

    @POST
    @Path("/organization/lender")
    public ActionResponse insertOrgLender(@HeaderParam(API_KEY) String userKey,
        OrganizationLenderAction.Request request) throws IOException {
        logger.debug(">insertOrgLender() ");
        OrganizationLenderAction action = getBean(OrganizationLenderAction.ACTION_NAME);
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN};
        action.validateUserAccess(ps);
        action.insertOrgLender(request);
        logger.debug("<insertOrgLender() ");
        return action.getActionResponse();
    }

    @PUT
    @Path("/organization/lender")
    public ActionResponse updateOrgLender(@HeaderParam(API_KEY) String userKey,
        OrganizationLenderAction.Request request) throws IOException {
        logger.debug(">updateOrgLender() ");
        OrganizationLenderAction action = getBean(OrganizationLenderAction.ACTION_NAME);
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN};
        action.validateUserAccess(ps);
        action.updateOrgLender(request);
        logger.debug("<updateOrgLender() ");
        return action.getActionResponse();
    }

    @GET
    @Path("/organization/lender")
    public ActionResponse getOrgLender(@HeaderParam(API_KEY) String userKey) throws IOException {
        logger.debug(">getOrgLender() ");
        OrganizationLenderAction action = getBean(OrganizationLenderAction.ACTION_NAME);
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN};
        action.validateUserAccess(ps);
        action.getOrgLender();
        logger.debug("<getOrgLender() ");
        return action.getActionResponse();
    }

    @POST
    @Path("/organization/secondlevelorg")
    public ActionResponse insertSecondLevelOrg(@HeaderParam(API_KEY) String userKey,
        OrganizationSecondAction.Request request) throws IOException {
        logger.debug(">insertSecondLevelOrg() ");
        OrganizationSecondAction action = getBean(OrganizationSecondAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.insertSecondLevelOrg(request);
        logger.debug("<insertSecondLevelOrg() ");
        return action.getActionResponse();
    }

    @PUT
    @Path("/organization/secondlevelorg")
    public ActionResponse updateSecondLevelOrg(@HeaderParam(API_KEY) String userKey,
        @QueryParam("orgName") String orgName, @QueryParam("password") String password) throws IOException {
        logger.debug(">updateSecondLevelOrg() ");
        OrganizationSecondAction action = getBean(OrganizationSecondAction.ACTION_NAME);
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_ORGADMIN};
        action.validateUserAccess(ps);
        action.updateSecondLevelOrg(orgName, password);
        logger.debug("<updateSecondLevelOrg() ");
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
    @Path("/tele/download")
    @Produces(MediaType.MEDIA_TYPE_WILDCARD)
    public Response downloadTelephoneFile(@HeaderParam(API_KEY) String userKey, @QueryParam("batchId") Integer batchId,
        @Context HttpServletResponse response) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug(">downloadTelephoneFile()" + ", betchId=" + batchId);
        }

        TeleFileDownloadAction action = getBean(TeleFileDownloadAction.ACTION_NAME);
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_TELEMARKETING};
        action.validateUserAccess(ps);
        Response rs = buildResponse(() -> {
            return action.teledownload(response, batchId);
        }, () -> "downloading telephones for batchId=" + batchId);

        logger.debug("<downloadTelephoneFile()");
        return rs;
    }

    @PUT
    @Path("/tele/confirm")
    public ActionResponse updateTeleMarketing(@HeaderParam(API_KEY) String userKey,
        @QueryParam("batchId") Integer batchId) {
        logger.debug(">updateTeleMarketing()");
        TeleMarketingCheckAction action = getBean(TeleMarketingCheckAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_TELEMARKETING};
        action.validateUserAccess(ps);
        action.updateTeleMarketing(batchId);
        logger.debug("<updateTeleMarketing()");
        return action.getActionResponse();
    }

    @GET
    @Path("/tele/betches")
    public ActionResponse getTeleMarketings(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getTeleMarketings()");
        TeleMarketingAction action = getBean(TeleMarketingAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_TELEMARKETING};
        action.validateUserAccess(ps);
        action.getTeleMarketings();
        logger.debug("<getTeleMarketings()");
        return action.getActionResponse();
    }

    @GET
    @Path("/tele/betchDetail")
    public ActionResponse getTeleMarketingDetailS(@HeaderParam(API_KEY) String userKey,
        @QueryParam("betchId") String betchId) {
        logger.debug(">getTeleMarketingDetailS()");
        TeleMarketingDetailAction action = getBean(TeleMarketingDetailAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_TELEMARKETING};
        action.validateUserAccess(ps);
        action.getTeleMarketingDetailS(betchId);;
        logger.debug("<getTeleMarketingDetailS()");
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
    @Path("/org/budget")
    public ActionResponse getMarginData(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getMarginData()");
        IndexCountAction action = getBean(IndexCountAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.orgMarginData();
        logger.debug("<getMarginData()");
        return action.getActionResponse();
    }

    @POST
    @Path("/tele/assign")
    public ActionResponse insertTeleInits(@HeaderParam("API_KEY") String userKey, TeleMarketingAction.Request req) {
        logger.debug(">insertTeleInits()");
        TeleMarketingAction action = getBean(TeleMarketingAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_ASSIGN,
            Constants.MGT_P_TELEMARKETING};
        action.validateUserAccess(ps);
        action.insertTeleInits(req);
        logger.debug("<insertTeleInits()");
        return action.getActionResponse();
    }

    @GET
    @Path("/tele/betchInit")
    public ActionResponse selectTeleInitsByAccountId(@HeaderParam("API_KEY") String userKey) {
        logger.debug(">selectTeleInitsByAccountId()");
        TeleMarketingInitAction action = getBean(TeleMarketingInitAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN, Constants.MGT_P_TELEMARKETING,
            Constants.MGT_P_PRE_CHECK};
        action.validateUserAccess(ps);
        action.selectTeleInitsByAccountId();
        logger.debug("<selectTeleInitsByAccountId()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/tele/betchInit")
    public ActionResponse signBetchInit(@HeaderParam(API_KEY) String userKey, TeleMarketingInitAction.Request request) {
        logger.debug(">signBetchInit()");
        TeleMarketingInitAction action = getBean(TeleMarketingInitAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.updateTeleMarketingInitByIdAndOpCommet(request);
        logger.debug("<signBetchInit()");
        return action.getActionResponse();
    }

    @POST
    @Path("/alipay/trade/page/pay")
    public ActionResponse payAliayOrder(@HeaderParam("API_KEY") String userKey,
        @QueryParam("totalAmount") int totalAmount, @QueryParam("goodsId") String goodsId,
        @QueryParam("outTradeNo") String outTradeNo, @Context HttpServletResponse response) {
        logger.debug(">payAliayOrder()");
        AlipayAction action = getBean(AlipayAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.payAliayOrder(totalAmount, goodsId, outTradeNo, response);
        logger.debug("<payAliayOrder()");
        return action.getActionResponse();
    }

    @POST
    @Path("/alipay/notify")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public String notifyAlipayResult(@HeaderParam("API_KEY") String userKey, @Context HttpServletRequest request,
        @Context HttpServletResponse response) {
        logger.debug(">notifyAlipayResult()");
        AlipayAction action = getBean(AlipayAction.ACTION_NAME);
        String result = action.notifyAlipayResult(request, response);
        logger.debug("<notifyAlipayResult()");
        return result;
    }

    @GET
    @Path("/alipay/trade/query")
    public ActionResponse queryAlipayResult(@HeaderParam("API_KEY") String userKey,
        @QueryParam("outTradeNo") String outTradeNo) {
        logger.debug(">notifyAlipayResult()");
        AlipayAction action = getBean(AlipayAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        action.queryAlipayResult(outTradeNo);
        logger.debug("<notifyAlipayResult()");
        return action.getActionResponse();
    }

    @GET
    @Path("/push/total")
    public ActionResponse getPushCount(@HeaderParam(API_KEY) String userKey, @QueryParam("type") Integer type) {
        logger.debug(">getPushCount()");
        CountAction action = getBean(CountAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getPushCount(type);
        logger.debug("<getPushCount()");
        return action.getActionResponse();
    }

    @GET
    @Path("/diversion/total")
    public ActionResponse getDiversionCount(@HeaderParam(API_KEY) String userKey, @QueryParam("type") Integer type) {
        logger.debug(">getDiversionCount()");
        CountAction action = getBean(CountAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getDiversionCount(type);
        logger.debug("<getDiversionCount()");
        return action.getActionResponse();
    }

    @GET
    @Path("/iou/platform")
    public ActionResponse getPlatform() {
        logger.debug(">getPlatform()");
        PlatformAction action = getBean(PlatformAction.ACTION_NAME);
        action.getPlatforms();
        logger.debug("<getPlatform()");
        return action.getActionResponse();
    }

    /*
     * op=3 初审拒绝  | op=9  通过初审 | op=19 通过复审  |op=7 初审挂起 ,op=5 初审挂起,op=13 复审挂起,op=15  复审挂起
     * op=20 打借条 | op=20 待放款 | op=21 老客户打借条
     * 传type=true 本日 |type=false 累计
     */
    @GET
    @Path("/count/total")
    public ActionResponse getCount(@HeaderParam(API_KEY) String userKey, @QueryParam("op") Integer[] ops,
        @QueryParam("type") Boolean type) {
        logger.debug(">getCount()");
        CountAction action = getBean(CountAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getCount(ops, type);
        logger.debug("<getCount()");
        return action.getActionResponse();
    }

    /*
     * op=1 已经放款 | op=2 拒绝放款
     * 传type=true 本日 |type=false 累计
     */
    @GET
    @Path("/money/total")
    public ActionResponse getmoneyCount(@HeaderParam(API_KEY) String userKey, @QueryParam("op") Integer op,
        @QueryParam("type") Boolean type) {
        logger.debug(">getmoneyCount()");
        CountAction action = getBean(CountAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getmoneyCount(op, type);
        logger.debug("<getmoneyCount()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/ocr/idcard")
    public ActionResponse orcIdCard(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">orcIdCard()");
        FaceAction action = getBean(FaceAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.ocrIdCard();
        logger.debug("<orcIdCard()");
        return action.getActionResponse();
    }

    @GET
    @Path("/whtianbei/report")
    public ActionResponse whtianbeiReport(@HeaderParam(API_KEY) String userKey, @QueryParam("userId") int userId,
        @QueryParam("applyId") int applyId, @QueryParam("refreshReport") Boolean refreshReport) {
        logger.debug(">whtianbeiReport()");
        WhtianbeiReportAction action = getBean(WhtianbeiReportAction.ACTION_NAME);
        action.validateUserKey(userKey);
        if (refreshReport != null && refreshReport == true) {
            int[] ps = {Constants.MGT_P_ORGADMIN, Constants.MGT_P_PRE_CHECK, Constants.MGT_P_ASSIGN,
                Constants.MGT_P_FIN_CHECK, Constants.MGT_P_BILL};
            action.validateUserAccess(ps);
        }
        action.getWhtianbeiReport(userId, applyId, refreshReport);
        logger.debug("<whtianbeiReport()");
        return action.getActionResponse();
    }

    @GET
    @Path("/autoAssignSettings")
    public ActionResponse selectOrgAssignSettings(@HeaderParam(API_KEY) String userKey,
        @QueryParam("assignType") Integer assignType) {
        logger.debug(">selectOrgAssignSettings()");
        OrgAssignSettingAction action = getBean(OrgAssignSettingAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.selectOrgAssignSetting(assignType);
        logger.debug("<selectOrgAssignSettings()");
        return action.getActionResponse();
    }

    @POST
    @Path("/autoAssignSetting")
    public ActionResponse saveOrgAssignSetting(@HeaderParam(API_KEY) String userKey,
        OrgAssignSettingAction.Request req) {
        logger.debug(">saveOrgAssignSetting()");
        OrgAssignSettingAction action = getBean(OrgAssignSettingAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.saveOrgAssignSetting(req);
        logger.debug("<saveOrgAssignSetting()");
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

    @GET
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
    }

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

    @POST
    @Path("/user/apply/{applyId}/feedback")
    public ActionResponse saveUserApplyFeedback(@HeaderParam(API_KEY) String userKey,
        @PathParam("applyId") Integer applyId, UserApplyFeedbackAction.Request req) {
        logger.debug(">saveUserApplyFeedback()");
        UserApplyFeedbackAction action = getBean(UserApplyFeedbackAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.saveUserApplyFeedback(applyId, req);
        logger.debug("<saveUserApplyFeedback()");
        return action.getActionResponse();
    }

    @GET
    @Path("/autoAssignStatistic/{assignType}")
    public ActionResponse statisticAssign(@HeaderParam(API_KEY) String userKey, @PathParam("assignType") int assignType,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("accountId") Integer accountId) {
        logger.debug(">statisticAssign()");
        UserApplyStatisticAction action = getBean(UserApplyStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.statisticAssign(assignType, startDate, endDate, accountId);
        logger.debug("<statisticAssign()");
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
    @Path("/user/livedetect")
    public ActionResponse liveDetectGetFour(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">liveDetectGetFour()");
        UserFaceDetectAction action = getBean(UserFaceDetectAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.liveDetectGetFour();
        logger.debug("<liveDetectGetFour()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/livedetect")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public ActionResponse liveDetect(@HeaderParam(API_KEY) String userKey, @MultipartForm ReLiveDetectRequest request) {
        logger.debug(">liveDetect(), validateData= " + request.getValidateData());
        UserFaceDetectAction action = getBean(UserFaceDetectAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.liveDetect(request);
        logger.debug("<liveDetect()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/device")
    public ActionResponse saveUserDevice(@HeaderParam(API_KEY) String userKey, UserDeviceAction.Request req) {
        logger.debug(">saveUserDevice()");
        UserDeviceAction action = getBean(UserDeviceAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.saveUserDevice(req);
        logger.debug("<saveUserDevice()");

        return action.getActionResponse();
    }

    @POST
    @Path("/lcDevice")
    public ActionResponse saveUserLcDevice(@HeaderParam(API_KEY) String userKey, UserLcDeviceAction.Request req) {
        logger.debug(">saveUserLcDevice()");
        UserLcDeviceAction action = getBean(UserLcDeviceAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.saveUserLcDevice(req);
        logger.debug("<saveUserLcDevice()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/lcDevice/logoout")
    public ActionResponse logoutUserLcDevice(@HeaderParam(API_KEY) String userKey,
        @QueryParam("objectId") String objectId) {
        logger.debug(">logoutUserLcDevice() objectId = " + objectId);
        UserLcDeviceAction action = getBean(UserLcDeviceAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.logoutDevice(objectId);
        logger.debug("<logoutUserLcDevice()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user/club/status")
    public ActionResponse getClubReportStatus(@HeaderParam(API_KEY) String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("channelType") String[] channelTypes) {
        logger.debug(">getClubReportStatus()");
        ClubReportAction action = getBean(ClubReportAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.getClubReportStatus(startDate, endDate, channelTypes);
        logger.debug("<getClubReportStatus()");
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
    @Path("/juxinli/notify")
    @Produces(MediaType.TEXT_PLAIN)
    public String juXinLiAuthorizeNotify(JuXinLiNotify notify) {
        logger.debug(">juXinLiAuthorizeNotify() ");
        JuXinLiAction action = getBean(JuXinLiAction.ACTION_NAME);
        action.juXinLiAuthorizeNotify(notify);
        logger.debug("<juXinLiAuthorizeNotify() ");
        return "OK";
    }

    @GET
    @Path("/juxinli/report")
    public ActionResponse getJuXinLiReport(@HeaderParam(API_KEY) String userKey, @QueryParam("userId") Integer userId,
        @QueryParam("applyId") Integer applyId, @QueryParam("token") String token,
        @QueryParam("reportType") String reportType) {
        logger.debug(">getJuXinLiReport(), userId = " + userId + " ,applyId=" + applyId + " ,token=" + token
            + " ,reportType=" + reportType);
        JuXinLiAction action = getBean(JuXinLiAction.ACTION_NAME);
        action.validateUserKey(userKey);
        action.getReport(userId, applyId, token, reportType);
        logger.debug("<getJuXinLiReport() ");
        return action.getActionResponse();
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
