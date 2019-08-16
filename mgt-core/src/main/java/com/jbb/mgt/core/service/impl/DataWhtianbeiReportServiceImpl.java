package com.jbb.mgt.core.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.dao.DataWhtianbeiReportDao;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.DataWhtianbeiReport;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.TianBeiRsp;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.service.DataWhtianbeiReportService;
import com.jbb.mgt.core.service.OrgRechargeDetailService;
import com.jbb.mgt.core.service.OrgRechargesService;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.Call3rdApiException;
import com.jbb.server.common.exception.JbbCallException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.common.util.StringUtil;

@Service("DataWhtianbeiReportService")
public class DataWhtianbeiReportServiceImpl implements DataWhtianbeiReportService {
    private static Logger logger = LoggerFactory.getLogger(DataWhtianbeiReportService.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    private DataWhtianbeiReportDao dataWhtianbeiReportDao;

    @Autowired
    private OrgRechargeDetailService orgRechargeDetailService;

    @Autowired
    private OrgRechargesService orgRechargesService;

    @Autowired
    private PlatformTransactionManager txManager;

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    private void rollbackTransaction(TransactionStatus txStatus) {
        if (txStatus == null) {
            return;
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception er) {
            logger.warn("Cannot rollback transaction", er);
        }
    }

    @Override
    public DataWhtianbeiReport getDataWhtianbeiReportById(User user, UserApplyRecord userApplyRecord, Account account,
        boolean refreshReport) {
        int orgId = account.getOrgId();
        int userId = user.getUserId();

        DataWhtianbeiReport report = null;
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            if (refreshReport) {
                // 检查组织是否还有足够的费用，调用
                // 检查费用是否足够，如果费用不足，则提示用户
                OrgRecharges o = orgRechargesService.selectOrgRechargesForUpdate(orgId);
                if (o.getSmsBudget() <= 0) {
                    logger.info(" org's jbb budget < 0 , orgId = " + orgId);
                    throw new Call3rdApiException("jbb.mgt.error.api.feeNotEnough");
                }
                report = getReportFromWhitanbei(user, account, true);
            } else if (userApplyRecord != null && userApplyRecord.getsUserType() == Constants.USER_TYPE_ENTRY
                && userApplyRecord.getsOrgId() == Constants.JBB_ORG
                && userApplyRecord.getFlag() == UserApplyRecord.FLAG_DEFAULT_RECOMMAND) {
                report = dataWhtianbeiReportDao.getDataWhtianbeiReportById(userId);
                // 如果是进件用户， 获取相应数据，并判定是否在申请时间之后，如果在之前，需要获取最新数据返回，无须扣费
                if (report != null && userApplyRecord.getCreationDate().before(report.getCreationDate())) {
                    // nothing to do
                    logger.debug("get old report, userId = " + userId);
                } else {
                    // 刷新数据，无需扣费
                    report = getReportFromWhitanbei(user, account, false);
                    logger.debug("refresh report at first call, userId = " + userId);
                }
            } else {
                // JBB 流量 或者 组织自有渠道进件, 需提示用户扣费，在用户提交扣费确认后，调用数据，并扣费
                // 返回老的数据
                report = dataWhtianbeiReportDao.getDataWhtianbeiReportById(userId);
                logger.debug("get old report, userId = " + userId);

            }
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction
            rollbackTransaction(txStatus);
        }

        return report;
    }

    private DataWhtianbeiReport getReportFromWhitanbei(User user, Account account, boolean feeFlag) {
        DataWhtianbeiReport report = new DataWhtianbeiReport();

        if (StringUtil.isEmpty(user.getPhoneNumber()) || StringUtil.isEmpty(user.getUserName())
            || StringUtil.isEmpty(user.getIdCard())) {
            throw new WrongParameterValueException("jbb.mgt.exception.whtianbei.report.loseuserinfo");
        }

        String content1 = getDataWhtianbeiReport(user.getPhoneNumber(), user.getUserName(), user.getIdCard());
        report.setJsonData(content1);

        // 扣费逻辑, 调用黑名单报告，如果成功后，则扣费
        if (feeFlag && content1 != null) {
            int fee = PropertyManager.getIntProperty("jbb.mgt.tb.price", 60);
            OrgRecharges o = orgRechargesService.selectOrgRechargesForUpdate(account.getOrgId());
            o.setTotalSmsExpense(o.getTotalSmsExpense() + fee);
            orgRechargesService.updateOrgRecharges(o);
            orgRechargeDetailService.handleConsumeTbRep(account.getAccountId(), user.getUserId(), fee);
        }

        // 扣费逻辑结束
        try {
            String content2 = getTianBeiReportstatic(user.getPhoneNumber(), user.getUserName(), user.getIdCard());
            report.setBlacklistJson(content2);
        } catch (Exception e) {
            // nothing to do
            logger.error("getReportFromWhitanbei(), call getTianBeiReportstatic error ");
        }

        try {
            String content3 = getTianBeiMfReport(user.getPhoneNumber(), user.getUserName(), user.getIdCard());
            report.setMfJson(content3);
        } catch (Exception e) {
            // nothing to do
            logger.error("getMfReportFromWhitanbei(), call getMfTianBeiReportstatic error ");
        }

        report.setUserId(user.getUserId());
        report.setOrgId(account.getOrgId());
        report.setCreationDate(DateUtil.getCurrentTimeStamp());
        dataWhtianbeiReportDao.insetDataWhtianbeiReport(report);

        return report;
    }

    @Override
    public String getDataWhtianbeiReport(String phone, String name, String idcard) {

        String appKey = PropertyManager.getProperty("jbb.mgt.tb.api.appKey");
        String[][] heads = {{"X-Mall-Token", appKey}};
        String url = PropertyManager.getProperty("jbb.mgt.tb.api.url");
        url += "?phone=" + phone + "&name=" + HttpUtil.encodeURLParam(name) + "&idcard=" + idcard;
        try {
            HttpResponse response
                = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_GET, url, HttpUtil.CONTENT_TYPE_JSON, null, heads);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                String rspText = new String(response.getResponseBody());
                TianBeiRsp rsp = mapper.readValue(rspText, TianBeiRsp.class);
                if (rsp.getCode() == TianBeiRsp.SUCCES_CODE) {
                    return rspText;
                } else {
                    logger.error("getDataWhtianbeiReport() request =  " + url);
                    logger.error("getDataWhtianbeiReport() response with error, " + rspText);
                    throw new Call3rdApiException("jbb.error.exception.call3rdApi.error", rsp.getMsg());
                }
            } else {
                String bodyStr = new String(response.getResponseBody());
                logger.error("getDataWhtianbeiReport() request =  " + url);
                logger.error("getDataWhtianbeiReport() response with error, " + bodyStr);
                throw new Call3rdApiException("jbb.error.exception.call3rdApi.error", bodyStr);
            }
        } catch (IOException e) {
            logger.error("getDataWhtianbeiReport() response with error, " + e.getMessage());
            e.printStackTrace();
            throw new JbbCallException("jbb.error.exception.call3rdApi.error", e.getMessage());
        }
    }

    @Override
    public String getTianBeiReportstatic(String phone, String name, String idcard) {
        String appKey = PropertyManager.getProperty("jbb.mgt.tb.api.appKey");
        String[][] heads = {{"X-Mall-Token", appKey}};
        String url = PropertyManager.getProperty("jbb.mgt.tb.api.url2");
        url += "?phone=" + phone + "&name=" + HttpUtil.encodeURLParam(name) + "&idcard=" + idcard + "&type=BLACK_LIST";
        try {
            HttpResponse response
                = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_GET, url, HttpUtil.CONTENT_TYPE_JSON, null, heads);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                String rspText = new String(response.getResponseBody());
                TianBeiRsp rsp = mapper.readValue(rspText, TianBeiRsp.class);
                if (rsp.getCode() == TianBeiRsp.SUCCES_CODE) {
                    return rspText;
                } else {
                    logger.error("getTianBeiReportstatic() request =  " + url);
                    logger.error("getTianBeiReportstatic() response with error, " + rspText);
                    throw new Call3rdApiException("jbb.error.exception.call3rdApi.error", rsp.getMsg());
                }
            } else {
                String bodyStr = new String(response.getResponseBody());
                logger.warn("getTianBeiReportstatic() request =  " + url);
                logger.error("getTianBeiReportstatic() response with error, " + bodyStr);
                throw new Call3rdApiException("jbb.error.exception.call3rdApi.error", bodyStr);
            }
        } catch (IOException e) {
            logger.error("getTianBeiReportstatic() response with error, " + e.getMessage());
            e.printStackTrace();
            throw new JbbCallException("jbb.error.exception.call3rdApi.error", e.getMessage());
        }
    }

    @Override
    public String getTianBeiMfReport(String phone, String name, String idcard) {
        String appKey = PropertyManager.getProperty("jbb.mgt.tb.api.appKey");
        String[][] heads = {{"X-Mall-Token", appKey}};
        String url = PropertyManager.getProperty("jbb.mgt.tb.api.mf.url");
        url += "?phone=" + phone + "&name=" + HttpUtil.encodeURLParam(name) + "&idcard=" + idcard;
        try {
            HttpResponse response
                = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_GET, url, HttpUtil.CONTENT_TYPE_JSON, null, heads);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                String rspText = new String(response.getResponseBody());
                return rspText;
            } else {
                String bodyStr = new String(response.getResponseBody());
                logger.warn("getTianBeiMfReport() request =  " + url);
                logger.error("getTianBeiMfReport() response with error, " + bodyStr);
                throw new Call3rdApiException("jbb.error.exception.call3rdApi.error", bodyStr);
            }
        } catch (IOException e) {
            logger.error("getTianBeiMfReport() response with error, " + e.getMessage());
            e.printStackTrace();
            throw new JbbCallException("jbb.error.exception.call3rdApi.error", e.getMessage());
        }
    }

}
