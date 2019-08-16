package com.jbb.mgt.server.rs.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.jbb.mgt.rs.action.xjlPay.QuickPayAction;
import com.jbb.mgt.rs.action.xjlPay.TransferAction;
import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.rs.action.baseinfo.BaseInfoAction;
import com.jbb.mgt.rs.action.credit.CreditAction;
import com.jbb.mgt.rs.action.qianchengNotify.QianChengNotifyAction;
import com.jbb.mgt.rs.action.usercard.UserCardAction;
import com.jbb.mgt.rs.action.xjlApplyRecord.XjlApplyRecordAction;
import com.jbb.mgt.rs.action.xjlPay.XjlPayBankAction;
import com.jbb.mgt.rs.action.xjlUserApply.XjlUserApplyAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.mgt.server.rs.pojo.RsHelipayNotifyVo;
import com.jbb.server.common.util.StringUtil;

@Path("xjl")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@NoCache
public class JBBMgtXjlServices extends BasicRestfulServices {

    private static Logger logger = LoggerFactory.getLogger(JBBMgtXjlServices.class);

    @GET
    @Path("/banks")
    public ActionResponse getXjlPayBank(@HeaderParam(API_KEY) String userKey) {
        logger.debug(">getXjlPayBank()");
        XjlPayBankAction action = getBean(XjlPayBankAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.getXjlPayBank();
        logger.debug("<getXjlPayBank()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user/cards")
    public ActionResponse getUserCards(@HeaderParam("API_KEY") String userKey) {
        logger.debug(">getUserCards()");
        UserCardAction action = getBean(UserCardAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.getUserCards();
        logger.debug("<getUserCards()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/bank/msgCode")
    public ActionResponse getMsgCode(@HeaderParam("API_KEY") String userKey, UserCardAction.Request request,
        @QueryParam("type") String type) throws Exception {
        logger.debug(">getMsgCode()");
        UserCardAction action = getBean(UserCardAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.getMsgCode(request, type);
        logger.debug("<getMsgCode()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/cards")
    public ActionResponse insertUserCards(@HeaderParam("API_KEY") String userKey, @QueryParam("orderId") String orderId,
        UserCardAction.Request request) throws Exception {
        logger.debug(">insertUserCards()");
        UserCardAction action = getBean(UserCardAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.insertUserCards(request, orderId);
        logger.debug("<insertUserCards()");
        return action.getActionResponse();
    }

    @DELETE
    @Path("/user/cards")
    public ActionResponse removeAcceptUserCard(@HeaderParam("API_KEY") String userKey,
        @QueryParam("cardNo") String cardNo) {
        logger.debug(">removeAcceptUserCard()");
        UserCardAction action = getBean(UserCardAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.removeAcceptUserCard(cardNo);
        logger.debug("<removeAcceptUserCard()");
        return action.getActionResponse();
    }

    @PUT
    @Path("/user/cards")
    public ActionResponse acceptUserCard(@HeaderParam("API_KEY") String userKey, @QueryParam("cardNo") String cardNo) {
        logger.debug(">removeAcceptUserCard()");
        UserCardAction action = getBean(UserCardAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.acceptUserCard(cardNo);
        logger.debug("<removeAcceptUserCard()");
        return action.getActionResponse();
    }

    @GET
    @Path("/base/info")
    public ActionResponse getBaseInfo(@HeaderParam("API_KEY") String userKey, @QueryParam("education") String education,
        @QueryParam("maritalStatus") String maritalStatus, @QueryParam("relationList") String relationList,
        @QueryParam("loanPurpose") String loanPurpose, @QueryParam("provinceList") String provinceList,
        @QueryParam("professionType") String professionType) {
        logger.debug(">getBaseInfo()");
        BaseInfoAction action = getBean(BaseInfoAction.ACTION_NAME);
        action.getBaseInfo(education, maritalStatus, relationList, loanPurpose, provinceList, professionType);
        logger.debug("<getBaseInfo()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user/credit")
    public ActionResponse getCredit(@HeaderParam(API_KEY) String userKey, @QueryParam("deviceId") String deviceId,
        @QueryParam("getAggrements") Boolean getAgreements, @QueryParam("orgId") int orgId) {
        logger.debug(">getCredit()");
        CreditAction action = getBean(CreditAction.ACTION_NAME);
        if (!StringUtil.isEmpty(userKey)) {
            action.validateEntryUserKey(userKey);
        }
        action.getCredit(deviceId, getAgreements, orgId);
        logger.debug("<getCredit()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/apply")
    public ActionResponse xjlUserApply(@HeaderParam("API_KEY") String userKey, @QueryParam("orgId") Integer orgId,
        XjlUserApplyAction.Request req) {
        logger.debug(">xjlUserApply()");
        XjlUserApplyAction action = getBean(XjlUserApplyAction.ACTION_NAME);
        if (!StringUtil.isEmpty(userKey)) {
            action.validateEntryUserKey(userKey);
        }
        action.xjlUserApply(orgId, req);
        logger.debug("<xjlUserApply()");
        return action.getActionResponse();
    }

    @GET
    @Path("/user/apply")
    public ActionResponse getXjlApplyRecordsByUserId(@HeaderParam("API_KEY") String userKey,
        @QueryParam("orgId") Integer orgId, @QueryParam("isToReturn") Boolean isToReturn) {
        logger.debug(">getXjlApplyRecordsByUserId()");
        XjlApplyRecordAction action = getBean(XjlApplyRecordAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.getXjlApplyRecordsByUserId(orgId, isToReturn);
        logger.debug("<getXjlApplyRecordsByUserId()");
        return action.getActionResponse();
    }

    @POST
    @Path("/verify/notify")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public String notifyQianCheng(@Context HttpServletRequest request, @Context HttpServletResponse response)
        throws UnsupportedEncodingException {
        logger.debug(">notifyQianCheng()");
        QianChengNotifyAction action = getBean(QianChengNotifyAction.ACTION_NAME);
        String result = action.notifyQianCheng(request, response);
        logger.debug("<notifyQianCheng()");
        return result;
    }

    @POST
    @Path("/user/helipay/msgcode")
    public ActionResponse sendUserOrderMesCode(@HeaderParam("API_KEY") String userKey, QuickPayAction.Request req,
        @Context HttpServletRequest request) throws Exception {
        logger.debug(">sendUserOrderMesCode()");
        QuickPayAction action = getBean(QuickPayAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.sendUserOrderMesCode(req, request.getRemoteAddr());
        logger.debug("<sendUserOrderMesCode()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/helipay/quickpay")
    public ActionResponse quickPayConfirmPay(@HeaderParam("API_KEY") String userKey,
        @QueryParam("orderId") String orderId, @QueryParam("msgCode") String msgCode,
        @Context HttpServletRequest request) throws Exception {
        logger.debug(">quickPayConfirmPay()");
        QuickPayAction action = getBean(QuickPayAction.ACTION_NAME);
        action.validateEntryUserKey(userKey);
        action.quickPayConfirmPay(orderId, msgCode, request.getRemoteAddr());
        logger.debug("<quickPayConfirmPay()");
        return action.getActionResponse();
    }

    @POST
    @Path("/user/changjiepay/notify")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void notifyChangJieResult(@Context HttpServletRequest request, @Context HttpServletResponse response)
        throws IOException {
        logger.debug(">notifyChangJieResult()");
        QuickPayAction action = getBean(QuickPayAction.ACTION_NAME);
        action.notifyChangJieResult(request, response);
        response.getWriter().write("success");
        response.getWriter().flush();
        logger.debug("<notifyChangJieResult()");
    }

    @POST
    @Path("/user/helipay/notify")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public String notifyHeLiPayResult(@Form RsHelipayNotifyVo reqVo, @Context HttpServletRequest request,
        @Context HttpServletResponse response) {
        logger.debug(">notifyHeLiPayResult()");
        QuickPayAction action = getBean(QuickPayAction.ACTION_NAME);
        String result = action.notifyHeLiPayResult(reqVo, request, response);
        logger.debug("<notifyHeLiPayResult()");
        return result;
    }

    @POST
    @Path("/user/changjietransfer/notify")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void notifyChangJieTransferResult(@Context HttpServletRequest request, @Context HttpServletResponse response)
        throws IOException {
        logger.debug(">notifyChangJieTransferResult()");
        TransferAction action = getBean(TransferAction.ACTION_NAME);
        action.notifyTransfer(request, response);
        response.getWriter().write("success");
        response.getWriter().flush();
        logger.debug("<notifyChangJieTransferResult()");
    }



}
