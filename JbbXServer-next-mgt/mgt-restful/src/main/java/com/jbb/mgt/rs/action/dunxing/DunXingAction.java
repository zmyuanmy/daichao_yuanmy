package com.jbb.mgt.rs.action.dunxing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.DunXingMsgLog;
import com.jbb.mgt.core.domain.HumanLpPhone;
import com.jbb.mgt.core.service.DunXingMsgLogService;
import com.jbb.mgt.core.service.HumanLpPhoneService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import org.apache.ibatis.javassist.tools.rmi.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.List;

@Service(DunXingAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DunXingAction extends BasicAction {

    public static final String ACTION_NAME = "DunXingAction";

    private static Logger logger = LoggerFactory.getLogger(DunXingAction.class);

    @Autowired
    private DunXingMsgLogService dunXingMsgLogService;

    @Autowired
    private HumanLpPhoneService humanLpPhoneService;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void selectMsgLog(String date, String status, String phoneNumber) {
        logger.debug(">selectMsgLog()");
        List<DunXingMsgLog> dunXingMsgLogs
            = dunXingMsgLogService.selectDunxingMsgLogs(date, this.account.getAccountId(), status, phoneNumber);
        this.response.dunXingMsgLogs = dunXingMsgLogs;
        logger.debug("<selectMsgLog()");
    }

    public void selectHumanLpPhone(String phoneNumber, String startDate, String endDate) {
        logger.debug(">selectHumanLpPhone()");

        if (StringUtil.isEmpty(phoneNumber)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "phoneNumber");
        }
        if (phoneNumber.length() < 4) {
            throw new WrongParameterValueException("jbb.error.exception.errorNumber");
        }
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "startDate");
        }
        Timestamp startTime = DateUtil.parseStrnew(startDate + " 00:00:00");
        Timestamp endTime = null;
        if (!StringUtil.isEmpty(endDate)) {
            endTime = DateUtil.parseStrnew(endDate + " 00:00:00");
        }
        List<HumanLpPhone> humanLpPhones = humanLpPhoneService.selectHumanLpPhoneList(phoneNumber, startTime, endTime);
        this.response.humanLpPhones = humanLpPhones;
        logger.debug("<selectHumanLpPhone()");
    }

    public void sendMsg(String inviteCode, String phoneNumber, String date, String msgModeId) {

        logger.debug(">sendMsg()");

        if (StringUtil.isEmpty(inviteCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "inviteCode");
        }

        if (StringUtil.isEmpty(date)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "date");
        }

        if (StringUtil.isEmpty(phoneNumber)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "phoneNumber");
        }
        if (StringUtil.isEmpty(msgModeId)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "msgModeId");
        }
        Timestamp dateTime = new Timestamp(Long.valueOf(date));
        HumanLpPhone humanLpPhone = humanLpPhoneService.selectHumanLpPhone(phoneNumber, dateTime);
        if (humanLpPhone == null) {
            throw new WrongParameterValueException("jbb.error.exception.HumanLpNotFound");
        }
        String interfaceType = PropertyManager.getProperty("jbb.mgt.dunxing.msg.inteface.type", "3");
        if (interfaceType.equals("1")) {
            dunXingMsgLogService.sendMsgCode(this.account.getAccountId(), humanLpPhone.getPhoneNumber(), inviteCode,
                msgModeId);
        } else if (interfaceType.equals("2")) {
            dunXingMsgLogService.sendMsgCodeNew(this.account.getAccountId(), humanLpPhone.getPhoneNumber(), inviteCode);
        } else {
            dunXingMsgLogService.sendMiaoDiMsgCode(this.account.getAccountId(), humanLpPhone.getPhoneNumber(), inviteCode);
        }
        humanLpPhoneService.updateHumanLpPhoneSendStatus(humanLpPhone.getPhoneNumber(), "0", null);
        logger.debug("<sendMsg()");

    }

    public void notifyMsgCode(HttpServletRequest request) throws IOException {

        logger.debug(">notifyMsgCode()");
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String)enu.nextElement();
            logger.info(paraName + ": " + request.getParameter(paraName));
        }
        String suid = request.getParameter("suid");
        String code = request.getParameter("code");
        String msg = request.getParameter("msg");
        if (StringUtil.isEmpty(suid)) {
            logger.error("Boss Notify Error suid is null ");
            return;
        }

        if (StringUtil.isEmpty(code)) {
            logger.error("Boss Notify Error code is null ");
            return;

        }
        DunXingMsgLog dunXingMsgLog = dunXingMsgLogService.selectDunXingMsgLogById(suid);
        if (dunXingMsgLog == null) {
            logger.error("Boss Notify Error DunXingMsgLog is null ");
        }
        if (!code.equals("C000")) {
            logger.error("Boss Notify Error code = " + code);
            dunXingMsgLogService.updateDunXingMsgLog(suid, "1", msg);
            humanLpPhoneService.updateHumanLpPhoneSendStatus(dunXingMsgLog.getPhoneNumber(), "1",
                DateUtil.getCurrentTimeStamp());
            return;
        }
        dunXingMsgLogService.updateDunXingMsgLog(suid, "2", msg);
        humanLpPhoneService.updateHumanLpPhoneSendStatus(dunXingMsgLog.getPhoneNumber(), "2",
            DateUtil.getCurrentTimeStamp());

    }

    public void notifyMsgCodeNew(HttpServletRequest request) {
        logger.debug("> notifyMsgCodeNew()");
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String)enu.nextElement();
            logger.info(paraName + ": " + request.getParameter(paraName));
        }
        String msgid = request.getParameter("msgid");
        String status = request.getParameter("status");
        if (StringUtil.isEmpty(msgid)) {
            logger.error("Boss Notify Error msgid is null ");
            return;
        }

        if (StringUtil.isEmpty(status)) {
            logger.error("Boss Notify Error status is null ");
            return;
        }
        DunXingMsgLog dunXingMsgLog = dunXingMsgLogService.selectDunXingMsgLogById(msgid);
        if (dunXingMsgLog == null) {
            logger.error("Boss Notify Error DunXingMsgLog is null ");
        }
        if (!status.equals("DELIVRD")) {
            logger.error("Boss Notify Error status = " + status);
            dunXingMsgLogService.updateDunXingMsgLog(msgid, "1", status);
            humanLpPhoneService.updateHumanLpPhoneSendStatus(dunXingMsgLog.getPhoneNumber(), "1",
                DateUtil.getCurrentTimeStamp());
            return;
        }
        dunXingMsgLogService.updateDunXingMsgLog(msgid, "2", status);
        humanLpPhoneService.updateHumanLpPhoneSendStatus(dunXingMsgLog.getPhoneNumber(), "2",
            DateUtil.getCurrentTimeStamp());

        logger.debug("< notifyMsgCodeNew()");
    }

    public void notifyMiaoDiMsg(HttpServletRequest request) throws IOException {
        logger.debug("> notifyMiaoDiMsg()");
        // 获取请求参数
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str = null;
        StringBuffer reqBody = new StringBuffer();
        while ((str = reader.readLine()) != null) {
            reqBody.append(str);
        }

        if (StringUtil.isEmpty(reqBody.toString())) {
            logger.error("notifyMiaoDiMsg error rsp is null");
            return;
        }

        JSONObject rspjson = JSON.parseObject(reqBody.toString());

        if (rspjson == null) {
            logger.error("notifyMiaoDiMsg error rspjson is null");
            return;
        }

        String smsResult = rspjson.getString("smsResult");

        if (StringUtil.isEmpty(smsResult)) {
            logger.error("notifyMiaoDiMsg error smsResult is null");
            return;
        }
        JSONArray array = JSONArray.parseArray(smsResult);

        if (array == null) {
            logger.error("notifyMiaoDiMsg error array is null");
            return;
        }

        JSONObject phoneJson = array.getJSONObject(0);
        String phone = phoneJson.getString("phone");
        String status = phoneJson.getString("status");
        String respMessage = phoneJson.getString("respMessage");

        if (StringUtil.isEmpty(phone)) {
            logger.error("notifyMiaoDiMsg error phoneNumber is null");
            return;
        }
        if (StringUtil.isEmpty(status)) {
            logger.error("notifyMiaoDiMsg error status is null");
            return;
        }

        DunXingMsgLog dunXingMsgLog = dunXingMsgLogService.selectLastDunXingMsgLogByPhoneNumber(phone);
        if (dunXingMsgLog == null) {
            logger.error("notifyMiaoDiMsg error dunXingMsgLog is null");
            return;
        }

        if (!status.equals("0")) {
            logger.error("notifyMiaoDiMsg Error status = " + status);
            dunXingMsgLogService.updateDunXingMsgLog(dunXingMsgLog.getOrderId(), "1", respMessage);
            humanLpPhoneService.updateHumanLpPhoneSendStatus(dunXingMsgLog.getPhoneNumber(), "1",
                DateUtil.getCurrentTimeStamp());
            return;
        }
        dunXingMsgLogService.updateDunXingMsgLog(dunXingMsgLog.getOrderId(), "2", respMessage);
        humanLpPhoneService.updateHumanLpPhoneSendStatus(dunXingMsgLog.getPhoneNumber(), "2",
            DateUtil.getCurrentTimeStamp());
        logger.debug("< notifyMiaoDiMsg()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public List<HumanLpPhone> humanLpPhones;
        public List<DunXingMsgLog> dunXingMsgLogs;
    }
}
