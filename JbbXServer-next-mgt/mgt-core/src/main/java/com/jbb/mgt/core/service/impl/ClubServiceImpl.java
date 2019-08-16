package com.jbb.mgt.core.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.dao.ClubReportDao;
import com.jbb.mgt.core.dao.OrganizationUserDao;
import com.jbb.mgt.core.dao.UserDao;
import com.jbb.mgt.core.domain.ClubQueryData;
import com.jbb.mgt.core.domain.ClubQueryReportResponse;
import com.jbb.mgt.core.domain.ClubQueryResponse;
import com.jbb.mgt.core.domain.ClubReport;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.service.AliyunService;
import com.jbb.mgt.core.service.ClubService;
import com.jbb.mgt.core.service.EntryService;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CodecUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.common.util.StringUtil;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Service("ClubService")
public class ClubServiceImpl implements ClubService {

    private static Logger logger = LoggerFactory.getLogger(ClubServiceImpl.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public static final String NOTIFY_JBB = "jbb";
    public static final String NOTIFY_MGT = "mgt";

    public static String YYS_REPORT = "YYS_REPORT_";

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    private OrganizationUserDao channelUserDao;

    @Autowired
    private AliyunService aliyunService;
    @Autowired
    private EntryService entryService;

    @Autowired
    private ClubReportDao clubReportDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void saveReport(ClubReport report) {
        clubReportDao.saveReport(report);

    }

    /**
     * 无回调通知接口，需要从页面回调，再调用此方法获取数据并存储。
     */
    @Override
    public void saveReport(Integer userId, String taskId) {
        ClubQueryResponse rsp = getReportFromClub(taskId);
        logger.debug("rsp=" + rsp);

        ClubReport report = new ClubReport(taskId, 0, rsp.getData());
        report.setUserId(userId);
        report.setStatus(ClubReport.STATUS_SUCC);
        // 获取并存储至OSS
        getReportFromClub(taskId);
        clubReportDao.saveReport(report);

    }

    @Override
    public ClubReport getReport(int userId, String channelType, String channelCode, Timestamp startDate,
        Timestamp endDate) {
        ClubReport report =
            clubReportDao.selectReport(userId, null, null, channelType, channelCode, null, null, null, null);
        if (report == null) {
            return null;
        }
        return getReport(report, null);
    }

    @Override
    public ClubReport getReport(String taskId) {
        ClubReport report = clubReportDao.selectReport(null, null, null, null, null, null, taskId, null, null);
        return getReport(report, taskId);
    }

    @Override
    public ClubReport getReport(String identityCode, String realName, String channelType, String channelCode,
        String username) {
        ClubReport report = clubReportDao.selectReport(null, identityCode, realName, channelType, channelCode, username,
            null, null, null);
        return getReport(report, null);
    }

    public ClubReport getReport(ClubReport report, String taskId) {

        if (report != null && (report.getStatus() == ClubReport.STATUS_SUCC
            || report.getStatus() == ClubReport.STATUS_FAILURE || report.getStatus() == ClubReport.STATUS_TIMEOUT)) {
            report.setData(getReportFromOss(report.getTaskId()));
            String reprot = getReportFromOss(YYS_REPORT + report.getTaskId());
            if (reprot != null) {
                report.setReport(reprot);
            } else if(ClubReport.TYPE_YYS.equals(report.getChannelType())) {
                ClubQueryReportResponse rsp = getYysReportFromClub(report.getTaskId(), report.getUserId());
                report.setReport(rsp.getOriginalData());
            }else{
                report.setReport(null);
            }

        } else {
            taskId = report == null ? taskId : report.getTaskId();
            ClubQueryResponse rsp = getReportFromClub(taskId);
            if (rsp != null && rsp.getCode() == ClubQueryResponse.SUCCES_CODE) {

                report = new ClubReport(taskId, 0, rsp.getData());
                report.setStatus(ClubReport.STATUS_SUCC);

                // 获取运营商报告
                if(ClubReport.TYPE_YYS.equals(report.getChannelType())){
                    getYysReportFromClub(taskId, report.getUserId());
                }
                // 存储至数据库
                clubReportDao.saveReport(report);

                try {
                    String content = mapper.writeValueAsString(rsp);
                    report.setData(content);;// 设备返回结果， JSON串
                    saveReportToOss(taskId, mapper.writeValueAsString(rsp));
                } catch (JsonProcessingException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return report;
    }

    public ClubQueryReportResponse getYysReportFromClub(String taskId, Integer userId) {

        String code = PropertyManager.getProperty("jbb.mgt.club.partner.code");
        String key = PropertyManager.getProperty("jbb.mgt.club.partner.key");
        String url = PropertyManager.getProperty("jbb.mgt.club.api.report.query");
        url += "?partner_code=" + code + "&partner_key=" + key;
        StringBuffer data = new StringBuffer("task_id=" + taskId);
        // 将联系人1和2 作为参数传入，获取运营商报告
        User user = appendContract(userId, data);
        String dataStr = String.valueOf(data);
        logger.info("url=" + url + ", data=" + data);
        try {
            HttpResponse response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url,
                HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED, dataStr, null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                String content = new String(response.getResponseBody());
                ClubQueryReportResponse clubReportRsp = mapper.readValue(content, ClubQueryReportResponse.class);
                if (clubReportRsp.getCode() == ClubQueryReportResponse.SUCCES_CODE) {
                    // 获取成功
                    // 存储至OSS
                    String contentData = CodecUtil.gunzip(clubReportRsp.getData());
                    if (!StringUtil.isEmpty(contentData)) {
                        clubReportRsp.setOriginalData(contentData);
                        aliyunService.putObject(Constants.OSS_BUCKET_CLUB_PRELOAN, YYS_REPORT + taskId, contentData);
                    }
                    // TODO 交叉验证用户 实名、运营商标识
                    realNameAndMobileVerified(user, contentData);

                } else {
                    logger.info("response = " + content);
                }

                return clubReportRsp;

            } else {
                logger.debug("response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.debug("response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private User appendContract(Integer userId, StringBuffer data) {
        if (userId == null) {
            logger.info("appendContract ,userId is null");
            return null;
        }
        User user = userDao.selectUserById(userId);
        if (user == null) {
            logger.info("appendContract , not found user, userId=" + userId);
            return null;
        }

        int i = 1;
        if (!StringUtil.isEmpty(user.getContract1Phonenumber())) {
            data.append("&contact" + i + "_mobile=" + user.getContract1Phonenumber());
            data.append("&contact" + i + "_name=" + user.getContract1Username());
            data.append("&contact" + i + "_relation=" + user.getContract1Relation());
            i++;
        }

        if (!StringUtil.isEmpty(user.getContract2Phonenumber())) {
            data.append("&contact" + i + "_mobile=" + user.getContract2Phonenumber());
            data.append("&contact" + i + "_name=" + user.getContract2Username());
            data.append("&contact" + i + "_relation=" + user.getContract2Relation());
            i++;
        }

        if (!StringUtil.isEmpty(user.getContract1XjlPhonenumber())) {
            data.append("&contact" + i + "_mobile=" + user.getContract1XjlPhonenumber());
            data.append("&contact" + i + "_name=" + user.getContract1XjlUsername());
            data.append("&contact" + i + "_relation=" + user.getContract1XjlRelation());
            i++;
        }

        if (!StringUtil.isEmpty(user.getContract2XjlPhonenumber())) {
            data.append("&contact" + i + "_mobile=" + user.getContract2XjlPhonenumber());
            data.append("&contact" + i + "_name=" + user.getContract2XjlUsername());
            data.append("&contact" + i + "_relation=" + user.getContract2XjlRelation());
            i++;
        }

        return user;
    }

    /**
     * 运营商认证： 报告抓取成功为认证成功 实名认证： info_check对象 is_identity_code_valid 是 is_identity_code_reliable 是
     *
     * @param user
     * @param contentData
     */
    private void realNameAndMobileVerified(User user, String contentData) {
        if (user == null) {
            return;
        }
        user.setMobileVerified(true);
        if (!StringUtil.isEmpty(contentData)) {
            JSONObject jsonObject = JSONObject.fromObject(contentData);
            JSONObject info_check = jsonObject.getJSONObject("info_check");
            Object is_identity_code_valid = info_check.get("is_identity_code_valid");
            Object is_identity_code_reliable = info_check.get("is_identity_code_reliable");
            if (is_identity_code_valid.equals("是") && is_identity_code_reliable.equals("是")) {
                user.setRealnameVerified(true);
            }
        }
        userDao.updateUser(user);
        entryService.check(user.getUserId());
    }

    private ClubQueryResponse getReportFromClub(String taskId) {
        String code = PropertyManager.getProperty("jbb.mgt.club.partner.code");
        String key = PropertyManager.getProperty("jbb.mgt.club.partner.key");
        String url = PropertyManager.getProperty("jbb.mgt.club.api.query");
        url += "?partner_code=" + code + "&partner_key=" + key;
        String data = "task_id=" + taskId;
        try {
            HttpResponse response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url,
                HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED, data, null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                String content = new String(response.getResponseBody());
                ClubQueryResponse clubRsp = mapper.readValue(content, ClubQueryResponse.class);

                if (clubRsp.getCode() == ClubQueryResponse.SUCCES_CODE) {
                    // 获取成功

                } else {
                    logger.debug("response = " + content);
                }

                // 存储至OSS
                aliyunService.putObject(Constants.OSS_BUCKET_CLUB_PRELOAN, taskId, content);

                return clubRsp;

            } else {
                logger.debug("response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.debug("response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private String getReportFromOss(String reportId) {
        try {
            return aliyunService.getObject(Constants.OSS_BUCKET_CLUB_PRELOAN, reportId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void saveReportToOss(String reportId, String content) {
        aliyunService.putObject(Constants.OSS_BUCKET_CLUB_PRELOAN, reportId, content);
    }

    @Override
    public boolean saveNotify(String event, String type, String time, String data, String sign, String params) {
        if (StringUtil.isEmpty(data)) {
            return false;
        }
        try {
            Integer userId = null;
            
            boolean rtnFlag = false;

            logger.debug("data=" + data);
            ClubQueryData clubData = mapper.readValue(data, ClubQueryData.class);
            String taskId = clubData.getTaskId();

            logger.debug("taskId=" + taskId);

            ClubQueryResponse rsp = getReportFromClub(taskId);
            logger.debug("rsp=" + rsp.getCode());

            ClubReport report = new ClubReport(taskId, 0, rsp.getData());
            if (params != null) {
                try {
                    userId = Integer.valueOf(params);
                    report.setUserId(userId);
                } catch (NumberFormatException e) {
                    // 如果params不为数字的用户ID， 再是来自己于其他系统，比如借帮帮等, 获取相应用户ID。
                    try {
                        // 兼容 jbb老用户, 通过userId获取用户
                        JSONObject jsonObjet = JSONObject.fromObject(params);
                        User user = userDao.selectJbbUserById(jsonObjet.getInt("userId"), Constants.JBB_ORG);

                        // 兼容 小金条的一个bug, 通过userId获取用户
                        if (user == null) {
                            user = userDao.selectUserById(jsonObjet.getInt("userId"));
                        }
                        if (user != null) {
                            userId = user.getUserId();
                        }
                    } catch (JSONException ex) {
                        // notthing to do;
                        logger.info("ignore, notify to jbb server,   parse json error: " + ex.getMessage());
                    }

                }
            }
            report.setUserId(userId);
            if (rsp.getCode() == ClubQueryResponse.SUCCES_CODE) {
                if (Constants.NOTIFY_EVENT_SUCCESS.equals(event)) {
                    report.setStatus(ClubReport.STATUS_SUCC);

                    // 获取并存储至OSS

                    // 通知JBB APP服务器，获取认证成功
                    // 后续重构此部分逻辑
                    try {
                        notifyToJbbVerifyMobileSuccess(params, rsp);
                    } catch (Exception e) {
                        logger.warn("notify to JBB verify mobile error, error=" + e.getMessage());
                    }

                    if (ClubReport.TYPE_YYS.equals(report.getChannelType())) {
                        // 获取运营商报告
                        // delay 2000ms to get yys report
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e1) {
                            // nothing to do
                        }
                        getYysReportFromClub(taskId, userId);
                    } else if (ClubReport.TYPE_DS.equals(report.getChannelType())
                        && ClubReport.TYPE_TB.equals(report.getChannelCode())) {
                        // 淘宝
                        User user = userDao.selectUserById(userId);
                        if (user != null) {
                            user.setUserId(userId);
                            user.setTaobaoVerified(true);
                            userDao.updateUser(user);
                        }
                    }

                    // 存储至数据库
                } else if (Constants.NOTIFY_EVENT_CREATED.equals(event)) {
                    // 创建
                    report.setStatus(ClubReport.STATUS_CREATED);
                } else if (Constants.NOTIFY_EVENT_TIMEOUT.equals(event)) {
                    // 超时
                    report.setStatus(ClubReport.STATUS_TIMEOUT);
                } else if (Constants.NOTIFY_EVENT_FAILURE.equals(event)) {
                    report.setStatus(ClubReport.STATUS_FAILURE);
                    // 失败
                }
                rtnFlag =  true;
            }else{
                rtnFlag =  false;
            }
            clubReportDao.saveReport(report);
            return rtnFlag;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通知JBB服务器，运营信息认证成功
     * 
     * @param params 透传的参数 {"env":"jbb", "userId":1111}
     * @param rsp
     */
    private void notifyToJbbVerifyMobileSuccess(String params, ClubQueryResponse rsp) {
        JSONObject jsonObjet = null;
        String env = null;

        try {
            jsonObjet = JSONObject.fromObject(params);
            env = jsonObjet.getString("env");
        } catch (JSONException ex) {
            // notthing to do;
            logger.info("<notifyToJbbVerifyMobileSuccess()  parse json error: " + ex.getMessage());
        }

        if (ClubServiceImpl.NOTIFY_JBB.equals(env)) {
            int userId = jsonObjet.getInt("userId");
            String mobile = rsp.getData().getUserMobile();
            postToJbbMobileAuth(userId, mobile);
        }
    }

    private void postToJbbMobileAuth(int userId, String mobile) {
        logger.debug(">postToJbbMobileAuth(), userId=" + userId + " , mobile=" + mobile);
        String url = PropertyManager.getProperty("jbb.app.server.url.authMobile");
        String appkey = PropertyManager.getProperty("jbb.app.server.appkey");
        url += "?phoneNumber=" + mobile + "&userId=" + userId;
        String[][] heads = {{"API_KEY", appkey}};
        try {
            HttpResponse response =
                HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url, HttpUtil.CONTENT_TYPE_JSON, null, heads);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {

                logger.debug("<postToJbbMobileAuth() success " + response.getResponseCode());
            } else {
                logger.debug("<postToJbbMobileAuth() with error response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.debug("<postToJbbMobileAuth() error, " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<ClubReport> getClubReportStatus(int userId, Timestamp startTs, Timestamp endTs, String[] channelTypes) {
        return clubReportDao.selectClubReportStatus(userId, startTs, endTs, channelTypes);
    }

}
