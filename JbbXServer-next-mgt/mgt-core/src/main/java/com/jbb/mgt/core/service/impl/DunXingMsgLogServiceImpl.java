package com.jbb.mgt.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jbb.mgt.core.dao.DunXingMsgLogDao;
import com.jbb.mgt.core.domain.DunXingMsgLog;
import com.jbb.mgt.core.service.DunXingMsgLogService;
import com.jbb.mgt.helipay.util.HeliUtil;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("DunXingMsgLogService")
public class DunXingMsgLogServiceImpl implements DunXingMsgLogService {

    private static Logger logger = LoggerFactory.getLogger(DunXingMsgLogServiceImpl.class);

    @Autowired
    private DunXingMsgLogDao dunXingMsgLogDao;

    @Override
    public void insertDunXingMsgLog(DunXingMsgLog dunXingMsgLog) {
        dunXingMsgLogDao.insertDunXingMsgLog(dunXingMsgLog);
    }

    @Override
    public DunXingMsgLog selectDunXingMsgLogById(String orderId) {
        return dunXingMsgLogDao.selectDunXingMsgLogById(orderId);
    }

    @Override
    public void updateDunXingMsgLog(String orderId, String sendStatus, String errorMsg) {
        dunXingMsgLogDao.updateDunXingMsgLog(orderId, sendStatus, errorMsg);
    }

    @Override
    public boolean checkExistPhoneNumber(String phoneNumber) {
        return dunXingMsgLogDao.checkExistPhoneNumber(phoneNumber);
    }

    @Override
    public List<DunXingMsgLog> selectDunxingMsgLogs(String date, int accountId, String status, String phoneNumber) {
        return dunXingMsgLogDao.selectDunxingMsgLogs(date, accountId, status, phoneNumber);
    }

    @Override
    public void sendMsgCodeNew(int accountId, String phoneNumber, String inviteCode) {
        String template = PropertyManager.getProperty("jbb.mgt.dunxing.msg.template");
        String msgBody = PropertyManager.getProperty("jbb.mgt.dunxing.msg.body");
        String msg = msgBody + template;
        String result = "";
        String params = phoneNumber + "," + inviteCode;
        try {
            result = sendPostNew(msg, params);
            logger.info("sendMsgCodeNew result = " + result);
        } catch (IOException e) {
            throw new MissingParameterException("jbb.error.exception.dunxing.error");
        }

        JSONObject object = JSON.parseObject(result);
        if (object == null) {
            logger.error("DunXing sendMsgCodeNewError Object Is Null");
            throw new MissingParameterException("jbb.error.exception.dunxing.error");
        }

        String code = object.getString("result");
        String msgid = object.getString("msgid");
        if (!code.equals("0")) {
            logger.error("DunXing Send Msg Error msg = " + code);
            throw new MissingParameterException("jbb.error.exception.dunxing.error");
        }
        if (StringUtil.isEmpty(msgid)) {
            logger.error("DunXing Send Msg Error msg is null");
            throw new MissingParameterException("jbb.error.exception.dunxing.error");
        }

        DunXingMsgLog dunXingMsgLog = new DunXingMsgLog();
        dunXingMsgLog.setAccountId(accountId);
        dunXingMsgLog.setOrderId(msgid);
        dunXingMsgLog.setPhoneNumber(phoneNumber);
        dunXingMsgLog.setModeId("");
        dunXingMsgLog.setSendDate(DateUtil.getCurrentTimeStamp());
        dunXingMsgLog.setSendStatus("0");// 发送中
        dunXingMsgLogDao.insertDunXingMsgLog(dunXingMsgLog);
    }

    @Override
    public DunXingMsgLog selectLastDunXingMsgLogByPhoneNumber(String phoneNumber) {
        return dunXingMsgLogDao.selectLastDunXingMsgLogByPhoneNumber(phoneNumber);
    }

    @Override
    public void sendMiaoDiMsgCode(int accountId, String phoneNumber, String inviteCode) {
        String result = "";
        try {
            result = sendMiaoDiPost(inviteCode, phoneNumber);
            logger.info("sendMiaoDiMsgCode result = " + result);
        } catch (IOException e) {
            throw new MissingParameterException("jbb.error.exception.miaodi.error");
        }
        JSONObject object = JSON.parseObject(result);
        if (object == null) {
            logger.error("MiaoDi sendMsgCodeNewError Object Is Null");
            throw new MissingParameterException("jbb.error.exception.miaodi.error");
        }

        String respCode = object.getString("respCode");
        String respDesc = object.getString("respDesc");
        if (!respCode.equals("00000")) {
            logger.error("MiaoDi Send Msg Error msg = " + respDesc);
            throw new MissingParameterException("jbb.error.exception.miaodi.error");
        }

        DunXingMsgLog dunXingMsgLog = new DunXingMsgLog();
        dunXingMsgLog.setAccountId(accountId);
        dunXingMsgLog.setOrderId(HeliUtil.generateOrderId());
        dunXingMsgLog.setPhoneNumber(phoneNumber);
        dunXingMsgLog.setModeId("");
        dunXingMsgLog.setSendDate(DateUtil.getCurrentTimeStamp());
        dunXingMsgLog.setSendStatus("0");// 发送中
        dunXingMsgLogDao.insertDunXingMsgLog(dunXingMsgLog);
    }

    @Override
    public void sendMsgCode(int accountId, String phoneNumber, String inviteCode, String msgModeId) {
        StringBuilder sb = new StringBuilder();

        // String tid = PropertyManager.getProperty("jbb.mgt.dunxing.msg.tid");
        String url = PropertyManager.getProperty("jbb.mgt.dunxing.msg.url");
        String apiKey = PropertyManager.getProperty("jbb.mgt.dunxing.msg.apiKey");
        String orderId = HeliUtil.generateOrderId();
        sb.append("{");
        sb.append("\"apikey\":\"" + apiKey + "\",");
        sb.append("\"sbaid\":\"" + HeliUtil.generateOrderId() + "\",");
        sb.append("\"pid\":\"12\",");
        sb.append("\"tid\":\"" + msgModeId + "\",");
        sb.append("\"users\":");
        sb.append("[");
        sb.append(
            "{\"inviteCode\":\"" + inviteCode + "\",\"mobile\":\"" + phoneNumber + "\",\"suid\":\"" + orderId + "\"}");
        sb.append("]");
        sb.append("}");
        logger.info("DunXing request " + sb.toString());
        String response = sendPost(url, sb.toString());
        logger.info("DunXing response " + response);

        JSONObject object = JSON.parseObject(response);
        if (object == null) {
            logger.error("DunXing SendMsgError object Is Null");
            throw new MissingParameterException("jbb.error.exception.dunxing.error");
        }

        String code = object.getString("code");
        String msg = object.getString("msg");
        if (!code.equals("C000")) {
            logger.error("DunXing Send Msg Error msg = " + msg);
            throw new MissingParameterException("jbb.error.exception.dunxing.error");
        }

        DunXingMsgLog dunXingMsgLog = new DunXingMsgLog();
        dunXingMsgLog.setAccountId(accountId);
        dunXingMsgLog.setOrderId(orderId);
        dunXingMsgLog.setPhoneNumber(phoneNumber);
        dunXingMsgLog.setModeId(msgModeId);
        dunXingMsgLog.setSendDate(DateUtil.getCurrentTimeStamp());
        dunXingMsgLog.setSendStatus("0");// 发送中
        dunXingMsgLogDao.insertDunXingMsgLog(dunXingMsgLog);

    }

    private String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("connection", "keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String sendPostNew(String message, String params) throws IOException {
        String url = PropertyManager.getProperty("jbb.mgt.dunxing.msg.url.new");
        String account = PropertyManager.getProperty("jbb.mgt.dunxing.msg.account");
        String password = PropertyManager.getProperty("jbb.mgt.dunxing.msg.password");
        String product = PropertyManager.getProperty("jbb.mgt.dunxing.msg.pid");
        String result = "";

        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap.put("account", account);
        paramsMap.put("pswd", password);
        paramsMap.put("needstatus", true);
        paramsMap.put("msg", message);
        paramsMap.put("params", params);
        paramsMap.put("resptype", "json");
        if (!StringUtil.isEmpty(product)) {
            paramsMap.put("product", 1);
        }
        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String, Object> param : paramsMap.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        HttpUtil.HttpResponse response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url,
            HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED, postDataBytes, null);

        if (response.getResponseCode() == HttpUtil.STATUS_OK) {
            byte[] byteArray = response.getResponseBody();
            try {
                result = new String(byteArray, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("sendPostNew Error e = " + e);
            }
        }
        return result;
    }

    private String sendMiaoDiPost(String inviteCode, String phoneNumber) throws IOException {
        String url = PropertyManager.getProperty("jbb.mgt.miaodi.msg.url");
        String sid = PropertyManager.getProperty("jbb.mgt.miaodi.msg.sid");
        String token = PropertyManager.getProperty("jbb.mgt.miaodi.msg.token");
        String template = PropertyManager.getProperty("jbb.mgt.miaodi.msg.template");
        String timestample = DateUtil.getOrderNum();
        String result = "";

        String sig = DigestUtils.md5Hex(sid + token + timestample);
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        paramsMap.put("accountSid", sid);
        paramsMap.put("templateid", template);
        paramsMap.put("param", inviteCode);
        paramsMap.put("to", phoneNumber);
        paramsMap.put("timestamp", timestample);// yyyyMMddHHmmss
        paramsMap.put("sig", sig);
        paramsMap.put("respDataType", "JSON");

        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String, Object> param : paramsMap.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        HttpUtil.HttpResponse response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url,
            HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED, postDataBytes, null);

        if (response.getResponseCode() == HttpUtil.STATUS_OK) {
            byte[] byteArray = response.getResponseBody();
            try {
                result = new String(byteArray, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("sendMiaoDiPost Error e = " + e);
            }
        }
        return result;
    }
}
