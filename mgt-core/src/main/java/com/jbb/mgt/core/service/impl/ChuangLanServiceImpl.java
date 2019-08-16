package com.jbb.mgt.core.service.impl;

import java.io.IOException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.dao.ChuangLanDao;
import com.jbb.mgt.core.dao.MessageCodeDao;
import com.jbb.mgt.core.dao.MobileWoolCheckDao;
import com.jbb.mgt.core.dao.TeleMarketingDetailDao;
import com.jbb.mgt.core.domain.ChuangLanPhoneNumberRsp;
import com.jbb.mgt.core.domain.ChuangLanWoolCheckRsp;
import com.jbb.mgt.core.domain.SmsSendRequest;
import com.jbb.mgt.core.domain.SmsSendResponse;
import com.jbb.mgt.core.service.ChuangLanService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.ChuangLanException;
import com.jbb.server.common.util.ChuangLanSmsUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.common.util.StringUtil;

@Service("ChuangLanService")
public class ChuangLanServiceImpl implements ChuangLanService {

    private static Logger logger = LoggerFactory.getLogger(ChuangLanServiceImpl.class);

    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private ChuangLanDao chuangLanDao;
    @Autowired
    private MessageCodeDao messageCodeDao;

    @Autowired
    private MobileWoolCheckDao mobileWoolCheckDao;

    @Autowired
    private TeleMarketingDetailDao teleMarketingDetailDao;


    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ChuangLanWoolCheckRsp woolCheck(String mobile, String ipAddress) {
        String appId = PropertyManager.getProperty("jbb.mgt.woolcheck.api.appId");
        String appKey = PropertyManager.getProperty("jbb.mgt.woolcheck.api.appKey");
        String url = PropertyManager.getProperty("jbb.mgt.woolcheck.api.url");
        StringBuffer sb = new StringBuffer();
        String mimeBoundary = "--" + HttpUtil.CONTENT_TYPE_FORM_DATA_BOUNDARY;
        // appId
        sb = sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"appId\"");
        sb.append("\r\n\r\n");
        sb.append(appId);
        sb.append("\r\n");
        // appKey
        sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"appKey\"");
        sb.append("\r\n\r\n");
        sb.append(appKey);
        sb.append("\r\n");
        // mobile
        sb.append("--").append(mimeBoundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"mobile\"");
        sb.append("\r\n\r\n");
        sb.append(mobile);
        sb.append("\r\n");
        if (!StringUtil.isEmpty(ipAddress)) {
            // ipAddress
            sb.append("--").append(mimeBoundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"ipAddress\"");
            sb.append("\r\n\r\n");
            sb.append(ipAddress);
            sb.append("\r\n");
        }
        // body结束时 boundary前后各需添加两上横线，最添加添回车换行
        sb.append("--").append(mimeBoundary).append("--").append("\r\n");
        try {
            HttpResponse response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url,
                HttpUtil.CONTENT_TYPE_FORM_DATA_WITH_BOUNDARY, sb.toString(), null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                ChuangLanWoolCheckRsp rsp
                    = mapper.readValue(new String(response.getResponseBody()), ChuangLanWoolCheckRsp.class);
                if (ChuangLanWoolCheckRsp.SUCCES_CODE.equals(rsp.getCode())) {
                    mobileWoolCheckDao.insertWoolCheckResult(rsp.getData());
                    return rsp;
                } else {
                    logger.debug("response = " + new String(response.getResponseBody()));
                    return null;
                }

            } else {
                logger.info("response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.error("response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChuangLanPhoneNumberRsp validateMobile(String mobile, boolean saveData) {
        String apiName = PropertyManager.getProperty("jbb.mgt.cl.api.appId");
        String password = PropertyManager.getProperty("jbb.mgt.cl.api.appKey");
        String url = PropertyManager.getProperty("jbb.mgt.cl.api.url");
        url += "?appId=" + apiName + "&appKey=" + password + "&mobile=" + mobile;
        try {
            HttpResponse response
                = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_JSON, null, null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                ChuangLanPhoneNumberRsp rsp
                    = mapper.readValue(new String(response.getResponseBody()), ChuangLanPhoneNumberRsp.class);
                if (ChuangLanPhoneNumberRsp.SUCCES_CODE.equals(rsp.getCode())) {
                    if (saveData) {
                        teleMarketingDetailDao.insertPhoneNumber(rsp.getData());
                    }
                    return rsp;
                } else {
                    logger.debug("response = " + new String(response.getResponseBody()));
                    return null;
                }

            } else {
                logger.info("response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.error("response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendMsgCode(String phoneNumber, String channelCode, String signName, String remoteAddress) {
        if (PropertyManager.contains("jbb.test.accounts", phoneNumber)) {
            logger.info("Test account require msgCode, phoneNumber=" + phoneNumber);
            return;
        }
        String account = PropertyManager.getProperty("jbb.mgt.msgcode.api.account");
        String pswd = PropertyManager.getProperty("jbb.mgt.msgcode.api.pswd");
        String smsSingleRequestServerUrl = PropertyManager.getProperty("jbb.mgt.msgcode.api.smsSingleRequestServerUrl");
        String msgCode = StringUtil.getRandomnum(4);
        String CodeTemplate = PropertyManager.getProperty("jbb.mgt.msgcode.api.template");
        String msg = signName + StringUtil.replace(CodeTemplate, "CodeTemplate", msgCode);
        // 模板： "您好，您的验证码是" + msgCode + "，5分钟内有效，请妥善保管好验证码，防止泄露。"
        String report = PropertyManager.getProperty("jbb.mgt.msgcode.api.report", "false");
        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phoneNumber, report);
        String requestJson = JSON.toJSONString(smsSingleRequest);
        try {
            String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
            SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
            if (smsSingleResponse.getCode() != null && "0".equals(smsSingleResponse.getCode())) {
                long time = System.currentTimeMillis();
                messageCodeDao.saveMessageCode(phoneNumber, channelCode, msgCode, new Timestamp(time),
                    DateUtil.calTimestamp(time, 5 * 60 * 1000));
                chuangLanDao.insertMessageLog(smsSingleResponse.getMsgId(), phoneNumber, channelCode, remoteAddress);
            } else {
                logger.warn(smsSingleResponse.getErrorMsg());
                throw new ChuangLanException("jbb.error.exception.chuanlan.limit", smsSingleResponse.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error(e.toString());
            throw new ChuangLanException();
        }
    }

    @Override
    public void sendMsgCodeWithContent(String phoneNumber, String content) {
        if (PropertyManager.contains("jbb.test.accounts", phoneNumber)) {
            logger.info("Test account require msgCode, phoneNumber=" + phoneNumber);
            return;
        }
        String account = PropertyManager.getProperty("jbb.mgt.msgcode.api.account");
        String pswd = PropertyManager.getProperty("jbb.mgt.msgcode.api.pswd");
        String smsSingleRequestServerUrl = PropertyManager.getProperty("jbb.mgt.msgcode.api.smsSingleRequestServerUrl");

        String msg = content;
        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phoneNumber);
        String requestJson = JSON.toJSONString(smsSingleRequest);
        try {
            String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
            SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
            if (smsSingleResponse.getCode() != null && "0".equals(smsSingleResponse.getCode())) {
                // nothing to do
            } else {
                logger.warn(smsSingleResponse.getErrorMsg());
                throw new ChuangLanException("jbb.error.exception.chuanlan.limit", smsSingleResponse.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error(e.toString());
            throw new ChuangLanException();
        }
    }

    @Override
    public void insertMsgReport(String msgid, String reportTime, String mobile, String status, String notifyTime,
        String statusDesc, String uid, int length) {
        chuangLanDao.insertMsgReport(msgid, reportTime, mobile, status, notifyTime, statusDesc, uid, length);
    }

    @Override
    public void sendMsgCodeXjlRemind(String applyId, Integer accountId, String phoneNumber, String signName, int status,
        String userName, String money, String date, String day) {
        if (PropertyManager.contains("jbb.test.accounts", phoneNumber)) {
            logger.info("Test account require msgCode, phoneNumber=" + phoneNumber);
            return;
        }

        logger.info(">sendMsgCodeXjlRemind(), applyId = " + applyId + " ,accountId =" + accountId + " ,phoneNumber ="
            + phoneNumber + " ,signName =" + signName + " ,status =" + status + " ,userName =" + userName + " ,money ="
            + money + " ,date =" + date + " ,day =" + day);

        String account = PropertyManager.getProperty("jbb.mgt.msgcode.api.account");
        String pswd = PropertyManager.getProperty("jbb.mgt.msgcode.api.pswd");
        String smsSingleRequestServerUrl = PropertyManager.getProperty("jbb.mgt.msgcode.api.smsSingleRequestServerUrl");
        String report = "true";

        String msg = signName + getMsg(status, userName, money, date, day);
        logger.debug("sendMsgCodeXjlRemind msg:" + msg);

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phoneNumber, report);
        String requestJson = JSON.toJSONString(smsSingleRequest);
        try {
            String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
            SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
            if (smsSingleResponse.getCode() != null && "0".equals(smsSingleResponse.getCode())) {
               // xjlMessageDetailDao.insertXjlMsgReport(smsSingleResponse.getMsgId(), applyId, accountId, phoneNumber,
                 //   msg, "SUBMIT", null, null);
            } else {
                logger.warn(smsSingleResponse.getErrorMsg());
                throw new ChuangLanException("jbb.error.exception.chuanlan.limit", smsSingleResponse.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error(e.toString());
            throw new ChuangLanException();
        }
    }

    @Override
    public void runXjlRemind(String applyId, Integer accountId, String phoneNumber, String signName, int status,
        String userName, String money, String date, String day) {
        if (PropertyManager.contains("jbb.test.accounts", phoneNumber)) {
            logger.info("Test account require msgCode, phoneNumber=" + phoneNumber);
            return;
        }
        logger.info(">sendRunMsgCodeXjlRemind(), applyId = " + applyId + " ,accountId =" + accountId + " ,phoneNumber ="
            + phoneNumber + " ,signName =" + signName + " ,status =" + status + " ,userName =" + userName + " ,money ="
            + money + " ,date =" + date + " ,day =" + day);

        String account = PropertyManager.getProperty("jbb.mgt.msgcode.api.account");
        String pswd = PropertyManager.getProperty("jbb.mgt.msgcode.api.pswd");
        String smsSingleRequestServerUrl = PropertyManager.getProperty("jbb.mgt.msgcode.api.smsSingleRequestServerUrl");
        String report = "true";

        String msg = signName + getMsg(status, userName, money, date, day);
        logger.debug("sendRunMsgCodeXjlRemind msg:" + msg);

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phoneNumber, report);
        String requestJson = JSON.toJSONString(smsSingleRequest);
        try {
            String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
            SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
            if (smsSingleResponse.getCode() != null && "0".equals(smsSingleResponse.getCode())) {
             //   xjlMessageDetailDao.insertXjlMsgReport(smsSingleResponse.getMsgId(), applyId, accountId, phoneNumber,
               //     msg, "SUBMIT", null, null);
            } else {
                logger.warn("sendRunMsgCodeXjlRemind error =" + smsSingleResponse.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("sendRunMsgCodeXjlRemind error , error = " + e.getMessage());
        }

        logger.info("<sendRunMsgCodeXjlRemind()");
    }

    @Override
    public void insertMessageLog(String msgid, String phoneNumber, String channelCode, String remoteAddress) {
        chuangLanDao.insertMessageLog(msgid, phoneNumber, channelCode, remoteAddress);
    }

    private String getMsg(int status, String userName, String money, String date, String day) {
        String CodeTemplate = "";
        String msg = "";
        if (status == 1) {
            // 模板： 【小金条】您好，您申请的xxx元，期限7天的借款已放款至默认银行卡，请注意查收。
            CodeTemplate = PropertyManager.getProperty("jbb.mgt.xjl.msgcode.api.loanTemplate");
            msg = StringUtil.replace(CodeTemplate, "Money", money);
        } else if (status == 3) {
            // 模板：【小金条】XXX（姓名），您X年X月X日申请的xxx元，期限7天的借款明天到期，请提前准备，按时还款。
            CodeTemplate = PropertyManager.getProperty("jbb.mgt.xjl.msgcode.api.dueTomorrowTemplate");
            msg = StringUtil.replace(CodeTemplate, "UserName", userName).replace("Date", date).replace("Money", money);
        } else if (status == 5) {
            // 模板： 【小金条】XXX（姓名），您X年X月X日申请的xxx元，期限7天的借款今天到期，请登陆App立即还款。
            CodeTemplate = PropertyManager.getProperty("jbb.mgt.xjl.msgcode.api.repaymentTemplate");
            msg = StringUtil.replace(CodeTemplate, "UserName", userName).replace("Date", date).replace("Money", money);
        } else if (status == 4) {
            // 模板：【小金条】XXX（姓名），您X年X月X日申请的xxx元，期限7天的借款已超X天，请登陆App立即还款。
            CodeTemplate = PropertyManager.getProperty("jbb.mgt.xjl.msgcode.api.overdueTemplate");
            msg = StringUtil.replace(CodeTemplate, "UserName", userName).replace("Date", date).replace("Money", money)
                .replace("Day", day);
        }
        return msg;
    }

}
