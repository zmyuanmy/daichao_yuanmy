package com.jbb.mgt.server.rs.services;

import java.io.UnsupportedEncodingException;

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

import com.jbb.mgt.rs.action.channel.ChannelAction;
import com.jbb.mgt.rs.action.club.ClubReportAction;
import com.jbb.mgt.rs.action.msgCode.XjlRemindMsgCodeAction;
import com.jbb.mgt.rs.action.report.XjlTdPreloanReportAction;
import com.jbb.mgt.rs.action.upload.MailListUploadAction;
import com.jbb.mgt.rs.action.whtianbeiReport.XjlWhtianbeiReportAction;
import com.jbb.mgt.rs.action.xjlApplyRecord.XjlApplyInfoAction;
import com.jbb.mgt.rs.action.xjlApplyRecord.XjlApplyRecordAction;
import com.jbb.mgt.rs.action.xjlApplyRecord.XjlApplyRecordAction.ApplyRequest;
import com.jbb.mgt.rs.action.xjlLog.XjlRenewalLogAction;
import com.jbb.mgt.rs.action.xjlPay.TransferAction;
import com.jbb.mgt.rs.action.xjlTodayStatistic.XjlTodayStatisticAction;
import com.jbb.mgt.rs.action.xjlUserApply.XjlUserAction;
import com.jbb.mgt.rs.action.xjlUserApply.XjlUserInfoAction;
import com.jbb.mgt.rs.action.xjlVintage.XjlVintageAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;

@Path("xjlmanager")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JbbXjlManagerServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JbbXjlManagerServices.class);

    /**
     * 获取vintage数据
     * 
     * @param userKey
     * @param startDate
     * @param endDate
     * @return
     */
    @GET
    @Path("/org/vintage")
    public ActionResponse getVintage(@HeaderParam("API_KEY") String userKey, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate) {
        logger.debug(">getVintage()");
        XjlVintageAction action = getBean(XjlVintageAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202};
        action.validateUserAccess(ps);
        action.getVintage(startDate, endDate);
        logger.debug(">getVintage()");
        return action.getActionResponse();
    }

    /**
     * 获取申请列表
    
     */
    /*@GET
    @Path("/org/applys")
    public ActionResponse getApplys(@HeaderParam("API_KEY") String userKey, @QueryParam("startDate") String startDate,
        @QueryParam("endDate") String endDate, @QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize,
        @QueryParam("status") String[] status, @QueryParam("searchText") String searchText,
        @QueryParam("userId") Integer userId, @QueryParam("dateType") Integer dateType,
        @QueryParam("channelCode") String channelCode, @QueryParam("finalAccId") Integer finalAccId,
        @QueryParam("collectorAccId") Integer collectorAccId, @QueryParam("listType") String listType,
        @QueryParam("loanType") Integer loanType, @QueryParam("getStatusCount") Boolean getStatusCount,
        @QueryParam("getLoanTotal") Boolean getLoanTotal) {
        logger.debug(">getApplys()");
        XjlApplyRecordAction action = getBean(XjlApplyRecordAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps
            = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_207, Constants.XJL_MGT_P2_208};
        action.validateUserAccess(ps);
        action.getXjlApplyRecords(pageNo, pageSize, startDate, endDate, status, searchText, userId, dateType,
            channelCode, finalAccId, collectorAccId, listType, loanType, getStatusCount, getLoanTotal);
        logger.debug(">getApplys()");
        return action.getActionResponse();
    }*/

    @POST
    @Path("/org/apply/pull")
    public ActionResponse applyPull(@HeaderParam(API_KEY) String userKey, ApplyRequest req,
        @QueryParam("listType") String listType) {
        XjlApplyRecordAction action = getBean(XjlApplyRecordAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.XJL_MGT_P2_207, Constants.XJL_MGT_P2_208};
        action.validateUserAccess(ps);
        action.applyPull(listType, req);
        return action.getActionResponse();
    }

    /**
     * 代扣
     * 
     * @param userKey
     * @param password
     * @param force
     * @param applyId
     * @return
     * @throws UnsupportedEncodingException
     */
    @POST
    @Path("/transfer")
    public ActionResponse payTransfer(@HeaderParam("API_KEY") String userKey, @HeaderParam("password") String password,
        @QueryParam("force") boolean force, @QueryParam("applyId") String applyId)
        throws UnsupportedEncodingException {
        logger.debug(">payTransfer()");
        TransferAction action = getBean(TransferAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.XJL_MGT_P2_209};
        action.validateUserAccess(ps);
        action.payTransfer(password, force, applyId);
        logger.debug("<payTransfer()");
        return action.getActionResponse();
    }

    /**
     * 获取整体绩效数据
     * 
     * @param userKey
     * @param startDate
     * @param endDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET
    @Path("/performance")
    public ActionResponse getPerformance(@HeaderParam("API_KEY") String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {
        logger.debug(">getperformance()");
        XjlApplyRecordAction action = getBean(XjlApplyRecordAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_212};
        action.validateUserAccess(ps);
        action.getXjlPerformance(startDate, endDate, pageNo, pageSize);
        logger.debug(">getperformance()");
        return action.getActionResponse();
    }

    /**
     * 管理员审核订单 vincent
     * 
     * @param userKey
     * @param applyId
     * @param status
     * @param req
     * @return
     * @throws UnsupportedEncodingException
     */
    @PUT
    @Path("org/apply/{applyId}/status/{status}")
    public ActionResponse updateXjlApplyRecordStatus(@HeaderParam("API_KEY") String userKey,
        @PathParam("applyId")String applyId, @PathParam("status") Integer status, XjlApplyRecordAction.Request req)
        throws UnsupportedEncodingException {
        logger.debug(">updateXjlApplyRecordStatus()");
        XjlApplyRecordAction action = getBean(XjlApplyRecordAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_207,
            Constants.MGT_P_SYSADMIN, Constants.MGT_P_ORGADMIN};
        action.validateUserAccess(ps);
        action.updateXjlApplyRecordStatus(applyId, status, req);
        logger.debug("<updateXjlApplyRecordStatus()");
        return action.getActionResponse();
    }

    /**
     * 发送提醒短信
     * 
     * @param userKey
     * @param applyId
     * @param userId
     * @return
     */
    @GET
    @Path("/msgCode")
    public ActionResponse remindSendCode(@HeaderParam(API_KEY) String userKey, @QueryParam("applyId") String applyId,
        @QueryParam("userId") Integer userId, @QueryParam("status") Integer status) {
        logger.debug(">remindSendCode() userId={},  applyId={}", userId, applyId);
        XjlRemindMsgCodeAction action = getBean(XjlRemindMsgCodeAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.XJL_MGT_P2_209};
        action.validateUserAccess(ps);
        action.remindSendCode(applyId, userId, status);
        logger.debug("<remindSendCode()");
        return action.getActionResponse();
    }

    /**
     * 今日数据
     * 
     * @param userKey
     * @param startDate
     * @param endDate
     * @return
     */
    @GET
    @Path("/todayStatistic")
    public ActionResponse getTodayStatistic(@HeaderParam(API_KEY) String userKey,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        logger.debug(">getTodayStatistic() ");
        XjlTodayStatisticAction action = getBean(XjlTodayStatisticAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_212};
        action.validateUserAccess(ps);
        action.getTodayStatistic(startDate, endDate);
        logger.debug("<getTodayStatistic() ");
        return action.getActionResponse();
    }

    /**
     * 用户一键领取
     * 
     * @param userKey
     * @param req
     * @return
     */
    @POST
    @Path("/user/receive")
    public ActionResponse receiveUsers(@HeaderParam(API_KEY) String userKey, XjlUserAction.Request req) {
        logger.debug(">receiveUsers()");
        XjlUserAction action = getBean(XjlUserAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.XJL_MGT_P2_211};
        action.validateUserAccess(ps);
        action.receiveUsers(req);
        logger.debug("<receiveUsers()");
        return action.getActionResponse();
    }

    /**
     * 用户拉黑转白
     * 
     * @param userKey
     * @param userId
     * @param status
     * @return
     */
    @PUT
    @Path("/user/status")
    public ActionResponse updateUserStatus(@HeaderParam(API_KEY) String userKey, @QueryParam("userId") Integer userId,
        @QueryParam("status") Integer status) {
        logger.debug(">updateUserStatus()");
        XjlUserAction action = getBean(XjlUserAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_210};
        action.validateUserAccess(ps);
        action.updateUserStatus(userId, status);
        logger.debug("<updateUserStatus()");
        return action.getActionResponse();
    }

    /**
     * 获取user用户列表
     * 
     * @param userKey
     * @param searchText
     * @param startDate
     * @param endDate
     * @param channelCode
     * @param flag
     * @param isAppLogin
     * @param isUserInfoVerified
     * @param isMobileVerified
     * @param isVideoVerified
     * @param isRealnameVerified
     * @param hasContacts
     * @param isTaobaoVerified
     * @param userStatus
     * @return
     */
    @GET
    @Path("/users")
    public ActionResponse getXjlUsers(@HeaderParam(API_KEY) String userKey, @QueryParam("searchText") String searchText,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
        @QueryParam("channelCode") String channelCode, @QueryParam("flag") String flag,
        @QueryParam("isAppLogin") Boolean isAppLogin, @QueryParam("isUserInfoVerified") Boolean isUserInfoVerified,
        @QueryParam("isMobileVerified") Boolean isMobileVerified,
        @QueryParam("isVideoVerified") Boolean isVideoVerified,
        @QueryParam("isRealnameVerified") Boolean isRealnameVerified, @QueryParam("hasContacts") Boolean hasContacts,
        @QueryParam("isTaobaoVerified") Boolean isTaobaoVerified, @QueryParam("userStatus") Integer userStatus,
        @QueryParam("enableReceive") Boolean enableReceive, @QueryParam("pageNo") int pageNo,
        @QueryParam("pageSize") int pageSize) {
        logger.debug(">getXjlUsers()");
        XjlUserAction action = getBean(XjlUserAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps
            = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_210, Constants.XJL_MGT_P2_211};
        action.validateUserAccess(ps);
        action.getXjlUsers(searchText, startDate, endDate, channelCode, flag, isAppLogin, isUserInfoVerified,
            isMobileVerified, isVideoVerified, isRealnameVerified, hasContacts, isTaobaoVerified, userStatus,
            enableReceive, pageNo, pageSize);
        logger.debug("<getXjlUsers()");
        return action.getActionResponse();
    }

    /**
     * 用户通讯录
     * 
     * @param userKey
     * @param userId
     * @return
     */
    @GET
    @Path("/user/contants/{userId}")
    public ActionResponse getXjlUserContants(@HeaderParam(API_KEY) String userKey,
        @PathParam("userId") Integer userId) {
        logger.debug(">getXjlUserContants() ");
        MailListUploadAction action = getBean(MailListUploadAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_209,
            Constants.XJL_MGT_P2_210, Constants.XJL_MGT_P2_211};
        action.validateUserAccess(ps);
        action.getXjlUserContants(userId);
        logger.debug("<getXjlUserContants() ");
        return action.getActionResponse();
    }

    /**
     * 用户YYS DS 数据
     * 
     * @param userKey
     * @param userId
     * @param taskId
     * @param channelType
     * @param channelCode
     * @return
     */
    @GET
    @Path("/club/report")
    public ActionResponse getXjlClubReport(@HeaderParam(API_KEY) String userKey, @QueryParam("userId") Integer userId,
        @QueryParam("taskId") String taskId, @QueryParam("channelType") String channelType,
        @QueryParam("channelCode") String channelCode) {
        logger.debug(">getXjlClubReport(), userId = " + userId + ", userId=" + userId + ", channelType=" + channelType);
        ClubReportAction action = getBean(ClubReportAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_209,
            Constants.XJL_MGT_P2_210, Constants.XJL_MGT_P2_211};
        action.validateUserAccess(ps);
        if (taskId != null) {
            action.getClubReport(taskId);
        } else if (userId != null) {
            action.getXjlClubReport(userId, channelType, channelCode);
        }
        logger.debug("<getXjlClubReport()");
        return action.getActionResponse();
    }

    /**
     * 魔盒数据
     * 
     * @param userKey
     * @param applyId
     * @param reportId
     * @param userId
     * @param refreshReport
     * @return
     */
    @GET
    @Path("/td/preloan/report")
    public ActionResponse getXjlTdPreloanReport(@HeaderParam(API_KEY) String userKey,
        @QueryParam("applyId") String applyId, @QueryParam("reportId") String reportId, @QueryParam("userId") int userId,
        @QueryParam("refreshReport") Boolean refreshReport) {
        logger
            .debug(">getXjlTdPreloanReport(), applyId = " + applyId + ", reportId=" + reportId + ", userId=" + userId);
        XjlTdPreloanReportAction action = getBean(XjlTdPreloanReportAction.ACTION_NAME);
        action.validateUserKey(userKey);
        if (refreshReport != null && refreshReport == true) {
            int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_209,
                Constants.XJL_MGT_P2_210, Constants.XJL_MGT_P2_211};
            action.validateUserAccess(ps);
        }
        action.getXjlPreloanReport(applyId, userId, refreshReport);
        logger.debug("<getXjlTdPreloanReport()");
        return action.getActionResponse();
    }

    /**
     * 天贝报告
     * 
     * @param userKey
     * @param userId
     * @param applyId
     * @param refreshReport
     * @return
     */
    @GET
    @Path("/whtianbei/report")
    public ActionResponse whtianbeiReport(@HeaderParam(API_KEY) String userKey, @QueryParam("userId") int userId,
        @QueryParam("applyId") String applyId, @QueryParam("refreshReport") Boolean refreshReport) {
        logger.debug(">whtianbeiReport()");
        XjlWhtianbeiReportAction action = getBean(XjlWhtianbeiReportAction.ACTION_NAME);
        action.validateUserKey(userKey);
        if (refreshReport != null && refreshReport == true) {
            int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_209,
                Constants.XJL_MGT_P2_210, Constants.XJL_MGT_P2_211};
            action.validateUserAccess(ps);
        }
        action.getWhtianbeiReport(userId, applyId, refreshReport);
        logger.debug("<whtianbeiReport()");
        return action.getActionResponse();
    }

    /**
     * 根据applyId获取用户信息
     * 
     * @param userKey
     * @param applyId
     * @return
     */
    @GET
    @Path("/apply/userInfo")
    public ActionResponse xjlApplyUserInfo(@HeaderParam(API_KEY) String userKey, @QueryParam("applyId") Integer applyId,
        @QueryParam("userId") Integer userId) {
        logger.debug(">xjlApplyUserInfo(), applyId=" + applyId);
        XjlUserInfoAction action = getBean(XjlUserInfoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_209,
            Constants.XJL_MGT_P2_210, Constants.XJL_MGT_P2_211};
        action.validateUserAccess(ps);
        action.getUserInfo(applyId, userId);
        logger.debug("<xjlApplyUserInfo()");
        return action.getActionResponse();
    }

    /**
     * 根据applyId获取订单
     * 
     * @param userKey
     * @param applyId
     * @return
     */
    @GET
    @Path("/apply/{applyId}")
    public ActionResponse getXjlApplyRecord(@HeaderParam(API_KEY) String userKey, @PathParam("applyId") String applyId) {
        logger.debug(">getXjlApplyRecord(), applyId=" + applyId);
        XjlApplyInfoAction action = getBean(XjlApplyInfoAction.ACTION_NAME);
        action.validateUserKey(userKey);
        int[] ps
            = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_209, Constants.XJL_MGT_P2_210};
        action.validateUserAccess(ps);
        action.getXjlApplyRecord(applyId);
        logger.debug("<getXjlApplyRecord()");
        return action.getActionResponse();
    }

    /**
     * 获取渠道列表
     * 
     * @param userKey
     * @param searchText
     * @param startDate
     * @param endDate
     * @return
     */
    @GET
    @Path("/channels")
    public ActionResponse getChannels(@HeaderParam(API_KEY) String userKey, @QueryParam("searchText") String searchText,
        @QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        logger.debug(">getChannels() ");
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_205};
        action.validateUserAccess(ps);
        action.getXjlChannels(searchText, startDate, endDate);
        logger.debug("<getChannels()");
        return action.getActionResponse();
    }

    /**
     * 修改渠道密码
     * 
     * @param userKey
     * @param channelCode
     * @param phonePassword
     * @return
     */
    @PUT
    @Path("/channel/{channelCode}")
    public ActionResponse putChannelInfo(@HeaderParam(API_KEY) String userKey,
        @PathParam("channelCode") String channelCode, @QueryParam("phonePassword") String phonePassword) {
        logger.debug(">putChannelInfo(), channelCode= " + channelCode);
        ChannelAction action = getBean(ChannelAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_205};
        action.validateUserAccess(ps);
        action.updateXjlChannelByCode(channelCode, phonePassword);
        logger.debug("<putChannelInfo()");
        return action.getActionResponse();
    }
    

	@GET
	@Path("/org/apply")
	public ActionResponse queryApplys(@HeaderParam("API_KEY") String userKey, @QueryParam("pageNo") int pageNo,
			@QueryParam("pageSize") int pageSize, @QueryParam("listType") String listType,
			@QueryParam("searchText") String searchText, @QueryParam("applyStartDate") String applyStartDate,
			@QueryParam("applyEndDate") String applyEndDate, @QueryParam("loanStartDate") String loanStartDate,
			@QueryParam("loanEndDate") String loanEndDate, @QueryParam("repaymentStartDate") String repaymentStartDate,
			@QueryParam("repaymentEndDate") String repaymentEndDate, @QueryParam("loanType") Integer loanType,
			@QueryParam("channelCode") String channelCode, @QueryParam("isReceived") Boolean isReceived,
			@QueryParam("isRenewal") Boolean isRenewal, @QueryParam("status") String[] status,
			@QueryParam("collectorAccId") Integer collectorAccId,
			@QueryParam("repaymentStatus") Integer repaymentStatus) {
		logger.debug(">queryApplys()");
		XjlApplyRecordAction action = getBean(XjlApplyRecordAction.ACTION_NAME);
		// 检查userKey
		action.validateUserKey(userKey);
		// 验证权限
		int[] ps = { Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202, Constants.XJL_MGT_P2_212,
				 Constants.XJL_MGT_P2_209 };
		action.validateUserAccess(ps);
		action.queryXjlApplyRecords(pageNo, pageSize, listType, searchText, applyStartDate, applyEndDate, loanStartDate,
				loanEndDate, repaymentStartDate, repaymentEndDate, loanType, channelCode, isReceived, isRenewal, status,
				collectorAccId, repaymentStatus);

		logger.debug(">queryApplys()");
		return action.getActionResponse();
	}

    /**
     * 获取vintage数据
     * 
     * @param userKey
     * @param startDate
     * @param endDate
     * @return
     */
    @GET
    @Path("/renewal/log")
    public ActionResponse getRenewalLog(@HeaderParam("API_KEY") String userKey, 
    	@QueryParam("applyId") String applyId,
    	@QueryParam("renewalAmount") String renewalAmount,
    	@QueryParam("userId") Integer userId,
        @QueryParam("creationDate") String creationDate) {
        logger.debug(">getRenewalLog()");
        XjlRenewalLogAction action = getBean(XjlRenewalLogAction.ACTION_NAME);
        // 检查userKey
        action.validateUserKey(userKey);
        // 验证权限
        int[] ps = {Constants.XJL_MGT_P2_201, Constants.XJL_MGT_P2_202,Constants.XJL_MGT_P2_212};
        action.validateUserAccess(ps);
        action.getRenewalLog(applyId,renewalAmount,userId,creationDate);
        logger.debug(">getRenewalLog()");
        return action.getActionResponse();
    }

}
