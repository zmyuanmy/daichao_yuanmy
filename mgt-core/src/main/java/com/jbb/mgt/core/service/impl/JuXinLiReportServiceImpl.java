package com.jbb.mgt.core.service.impl;

import java.io.IOException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.dao.AccountDao;
import com.jbb.mgt.core.dao.JuXinLiReportDao;
import com.jbb.mgt.core.dao.OrgRechargesDao;
import com.jbb.mgt.core.dao.UserApplyRecordDao;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.DataJuXinLiUrls;
import com.jbb.mgt.core.domain.JuXinLiNotify;
import com.jbb.mgt.core.domain.JuXinLiReport;
import com.jbb.mgt.core.domain.JuXinLiReportRsp;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.service.AliyunService;
import com.jbb.mgt.core.service.DataJuXinLiUrlsService;
import com.jbb.mgt.core.service.JuXinLiReportService;
import com.jbb.mgt.core.service.OrgRechargeDetailService;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.common.util.MD5;
import com.jbb.server.common.util.StringUtil;

@Service("JuXinLiReportService")
public class JuXinLiReportServiceImpl implements JuXinLiReportService {
    private static Logger logger = LoggerFactory.getLogger(YxReportServiceImpl.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    private DataJuXinLiUrlsService dataJuXinLiUrlsService;
    @Autowired
    private JuXinLiReportDao juXinLiReportDao;
    @Autowired
    private UserApplyRecordDao userApplyRecordDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private OrgRechargesDao orgRechargesDao;
    @Autowired
    private OrgRechargeDetailService orgRechargeDetailService;
    @Autowired
    private AliyunService aliyunService;

    @Override
    // 生成链接 暂时不用
    public DataJuXinLiUrls generateH5Url(int userId, int applyId, String reportType) {
        if (StringUtil.isEmpty(reportType) || userId == 0 || applyId == 0) {
            return null;
        }
        DataJuXinLiUrls dataJuXinLiUrls
            = dataJuXinLiUrlsService.selectDataYxUrlsByUserIdAndReportType(userId, reportType);
        if (dataJuXinLiUrls != null) {
            Timestamp creationDate = dataJuXinLiUrls.getCreationDate();
            if ((creationDate.getTime() > DateUtil.getTodayStartCurrentTime())
                && (creationDate.getTime() < (DateUtil.getCurrentTime() - DateUtil.HOUR_MILLSECONDES * 6))) {
                return dataJuXinLiUrls;
            }
        }
        String server = PropertyManager.getProperty("jbb.mgt.juxinli.api.url.authorize");
        String apiKey = PropertyManager.getProperty("jbb.mgt.juxinli.api.apiKey");
        String orgId = PropertyManager.getProperty("jbb.mgt.juxinli.api.Id");
        String website = reportType;
        StringBuffer bf = new StringBuffer();
        String newUserId = generateUserId(userId, applyId);
        String state = MD5.MD5Encode(orgId + website + apiKey);
        bf.append(server).append("/borrowing/").append(website).append("?api_key=").append(apiKey).append("&user_id=")
            .append(newUserId).append("&state=").append(state);
        String h5Url = bf.toString();
        DataJuXinLiUrls dataJuXinLiUrls2
            = new DataJuXinLiUrls(userId, reportType, h5Url, null, DateUtil.getCurrentTimeStamp());

        if (dataJuXinLiUrls != null) {
            dataJuXinLiUrlsService.updateDataJuXinLiUrls(dataJuXinLiUrls2);
        } else {
            dataJuXinLiUrlsService.insertDataJuXinLiUrls(dataJuXinLiUrls2);
        }

        return dataJuXinLiUrls2;

    }

    private String generateUserId(int userId, int applyId) {
        StringBuffer bf = new StringBuffer();
        bf.append(userId).append(JuXinLiReport.SPLIT_TASK_ID_CHAR).append(applyId);
        return bf.toString();
    }

    @Override
    public void juXinLiAuthorizeNotify(JuXinLiNotify notify) {
        if (notify.isSuccess()) {
            /*String apiKey = PropertyManager.getProperty("jbb.mgt.juxinli.api.apiKey");
            String orgId = PropertyManager.getProperty("jbb.mgt.juxinli.api.Id");
            String state1 = MD5.MD5Encode(orgId + notify.getWebsite() + apiKey);
            if(notify.getState()!=null) {
                logger.info(notify.getState() + "==============================");
            }
            // 验证参数
            if (!state1.equals(state)) {
                logger.warn("juXinLiAuthorizeNotify, state error ");
                return;
            }*/
            JuXinLiReport report = new JuXinLiReport(notify);
            logger.info("juXinLiAuthorizeNotify" + "status=" + notify.getPhase());
            if (notify.getPhase().equals(JuXinLiNotify.SimpleAuthFlowEnum.AUTHORIZE)) {
                int pric = PropertyManager.getIntProperty("jbb.mgt.yx.price", 100);
                UserApplyRecord usar = userApplyRecordDao.selectUserApplyRecordInfoByApplyId(report.getApplyId());
                Account acc = accountDao.selectOrgAdminAccount(usar.getOrgId(), null);
                OrgRecharges o = orgRechargesDao.selectOrgRechargesForUpdate(usar.getOrgId());
                o.setTotalSmsExpense(o.getTotalSmsExpense() + pric);
                orgRechargesDao.updateOrgRecharges(o);
                orgRechargeDetailService.handleConsumeYxRep(acc.getAccountId(), report.getUserId(), pric);
                saveReportInfo(report);
            } else if (notify.getPhase().equals(JuXinLiNotify.SimpleAuthFlowEnum.COLLECT)) {
                report.setStatus(JuXinLiReport.STATUS_COllECT);
                saveReportInfo(report);
            } else if (notify.getPhase().equals(JuXinLiNotify.SimpleAuthFlowEnum.PARSE)) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                JuXinLiReportRsp rsp = getReportFormJuXinLi(notify.getToken());
                if (rsp == null || !rsp.getCode().equals(JuXinLiReportRsp.SUCCESS_CODE)) {
                    rsp = getReportFormJuXinLi(notify.getToken());
                }
                logger.info("juXinLiAuthorizeNotify" + "Code=" + rsp.getCode());
                if (rsp.getCode().equals(JuXinLiReportRsp.SUCCESS_CODE)) {
                    saveJuXinLiReportRsp(notify.getToken(), rsp);
                    // 存储至DB
                    report.setStatus(JuXinLiReport.STATUS_COMPLATE);
                    saveReportInfo(report);
                    logger.info("juXinLiAuthorizeNotify：status" + report.getStatus());
                }
            }

        }

    }

    private void saveJuXinLiReportRsp(String token, JuXinLiReportRsp rsp) {
        if (rsp == null) {
            return;
        }
        // 存储至OSS
        token = "token_" + token;
        try {
            aliyunService.putObject(Constants.OSS_BUCKET_YX_PRELOAN, token, mapper.writeValueAsString(rsp));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.warn(e.getMessage());
        }

    }

    @Override
    public void saveReportInfo(JuXinLiReport report) {

        juXinLiReportDao.saveReportInfo(report);

    }

    @Override
    public void deleteOssRepoert(String token) {
        if (token == null) {
            return;
        }
        // 存储至OSS
        token = "token_" + token;
        aliyunService.deleteObject(Constants.OSS_BUCKET_YX_PRELOAN, token);

    }

    @Override
    public JuXinLiReportRsp getReportFormJuXinLi(String token) {
        String api = PropertyManager.getProperty("jbb.mgt.juxinli.api.url.get");
        String apiKey = PropertyManager.getProperty("jbb.mgt.juxinli.api.apiKey");
        String appId = PropertyManager.getProperty("jbb.mgt.juxinli.api.appId");
        StringBuffer bf = new StringBuffer();
        bf.append(api).append("/raw_data/").append(token);
        String url = bf.toString();
        String[][] heads = {{"Authorization", appId + "," + apiKey}};
        try {
            HttpResponse response
                = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_GET, url, HttpUtil.CONTENT_TYPE_JSON, null, heads);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                String content = new String(response.getResponseBody());
                JuXinLiReportRsp rsp = mapper.readValue(content, JuXinLiReportRsp.class);
                logger.debug("response = " + content);
                return rsp;

            } else {
                logger.info("response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.info("response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public JuXinLiReport getReport(Integer userId, Integer applyId, String token, String reportType) {
        if (userId == null && applyId == null && token == null) {
            return null;
        }
        JuXinLiReport report = juXinLiReportDao.selectReport(userId, applyId, token, reportType);
        String data = null;
        if (report == null) {
            logger.info("not found yx user, userId =" + userId);
            return null;
        }
        try {
            String token2 = "token_" + report.getToken();
            data = aliyunService.getObject(Constants.OSS_BUCKET_YX_PRELOAN, token2);
        } catch (Exception e) {
            logger.warn("not found yx report from oss, token =" + report.getToken());
        }
        if ((report.getStatus() != JuXinLiReport.STATUS_COMPLATE) && report.getToken() != null) {
            JuXinLiReportRsp rsp = getReportFormJuXinLi(report.getToken());
            if (rsp == null || rsp.getData() == null || rsp.getData().getStatus() == null
                || !rsp.getData().getStatus().equals("SUCCESS")) {
                return null;
            }
            saveJuXinLiReportRsp(report.getToken(), rsp);
            // 存储至DB
            report.setStatus(JuXinLiReport.STATUS_COMPLATE);
            saveReportInfo(report);

        }
        report.setData(data);
        return report;
    }
}
