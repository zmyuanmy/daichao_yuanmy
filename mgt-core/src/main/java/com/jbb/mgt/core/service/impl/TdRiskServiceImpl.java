package com.jbb.mgt.core.service.impl;

import java.io.IOException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.mgt.core.dao.TdPreloanReportDao;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.PreloanReport;
import com.jbb.mgt.core.domain.TdPreloanRiskResponse;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.UserApplyRecord;
import com.jbb.mgt.core.service.AliyunService;
import com.jbb.mgt.core.service.OrgRechargeDetailService;
import com.jbb.mgt.core.service.OrgRechargesService;
import com.jbb.mgt.core.service.TdRiskService;
import com.jbb.server.common.Constants;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.exception.Call3rdApiException;
import com.jbb.server.common.exception.ObjectNotFoundException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.common.util.IDCardUtil;
import com.jbb.server.common.util.StringUtil;

@Service("TdRiskService")
public class TdRiskServiceImpl implements TdRiskService {
    private static Logger logger = LoggerFactory.getLogger(TdRiskServiceImpl.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    private TdPreloanReportDao preloanReportDao;

    @Autowired
    private AliyunService aliyunService;

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
    public PreloanReport getPreloanReportByUserId(User user, UserApplyRecord userApplyRecord, boolean refreshReport,
        Account account) {
        logger
            .debug("|>getPreloanReportByUserId(), userId = " + user.getUserId() + ", refreshReport = " + refreshReport);
        TransactionStatus txStatus = null;

        int userId = user.getUserId();
        PreloanReport report = null;

        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            if (refreshReport) {
                // 刷新数据时，需要实时获取数据，并扣费
                report = getPreloanReport(user, account, true);
                logger.debug("refresh report, userId = " + userId);
            } else if (userApplyRecord != null && userApplyRecord.getsUserType() == Constants.USER_TYPE_ENTRY
                && userApplyRecord.getsOrgId() == Constants.JBB_ORG
                && userApplyRecord.getFlag() == UserApplyRecord.FLAG_DEFAULT_RECOMMAND) {
                report = preloanReportDao.selectPreloanReportByUserId(userId);
                // 如果是进件用户， 获取相应数据，并判定是否在申请时间之后，如果在之前，需要获取最新数据返回，无须扣费
                if (report != null && report.getStatus() == PreloanReport.STATUS_SUCC
                    && userApplyRecord.getCreationDate().before(report.getReportDate())) {
                    report.setRsp(getReportFromOss(report.getReportId()));
                    logger.debug("get from oss, userId = " + userId);
                } else {
                    // 刷新数据，无需扣费
                    report = getPreloanReport(user, account, false);
                    logger.debug("refresh report at first call, userId = " + userId);
                }
            } else {
                // JBB 流量 或者 组织自有渠道进件, 需提示用户扣费，在用户提交扣费确认后，调用数据，并扣费
                // 返回老的数据
                report = preloanReportDao.selectPreloanReportByUserId(userId);
                logger.debug("get old report, userId = " + userId);
                if (report != null && report.getStatus() == PreloanReport.STATUS_SUCC) {
                    report.setRsp(getReportFromOss(report.getReportId()));
                    logger.debug("get old report success, userId = " + userId);
                }
            }
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            // roll back not committed transaction
            rollbackTransaction(txStatus);
        }
        logger.debug("|<getPreloanReportByUserId()");
        return report;
    }

    public PreloanReport getPreloanReport(User user, Account account, boolean feeFlag) {
        int orgId = account.getOrgId();
        if (feeFlag) {
            // 检查费用是否足够，如果费用不足，则提示用户
            OrgRecharges o = orgRechargesService.selectOrgRechargesForUpdate(orgId);
            if (o.getSmsBudget() <= 0) {
                logger.info(" org's jbb budget < 0 , orgId = " + orgId);
                throw new Call3rdApiException("jbb.mgt.error.api.feeNotEnough");
            }
        }

        // 先提交申请
        PreloanReport report = preloanApply(user, account, feeFlag);
        if (report == null) {
            // 提交失败，请重新获取
            return null;
        }
        // 再获取返回
        TdPreloanRiskResponse rsp = getReportFromTd(report.getReportId());
        if (rsp != null && rsp.getSuccess()) {
            report.setReportDate(new Timestamp(rsp.getReportTime()));
            report.setApplyDate(new Timestamp(rsp.getApplyTime()));
            report.setStatus(PreloanReport.STATUS_SUCC);
            report.setFinalDecision(rsp.getFinalDecision());
            report.setFinalScore(rsp.getFinalScore());
            // 更新状态
            preloanReportDao.updatePreloanReport(report);
            // 存储至OSS服务器
            try {
                String content = mapper.writeValueAsString(rsp);
                report.setRsp(content);// 设备返回结果， JSON串
                saveReportToOss(report.getReportId(), mapper.writeValueAsString(rsp));
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return report;
    }

    private PreloanReport preloanApply(User user, Account account, boolean feeFlag) {

        if (user == null) {
            throw new ObjectNotFoundException("jbb.mgt.exception.userNotFound");
        }

        if (user.getUserName() == null || user.getIdCard() == null) {
            logger.info("username or idcard is empty, userId=" + user.getUserId());
            return null;
        }

        String code = PropertyManager.getProperty("jbb.mgt.td.partner.code");
        String key = PropertyManager.getProperty("jbb.mgt.td.partner.key");
        String url = PropertyManager.getProperty("jbb.mgt.td.preload.apply.url");
        String appname = PropertyManager.getProperty("jbb.mgt.td.partner.appname");
        url += "?partner_code=" + code + "&partner_key=" + key + "&app_name=" + appname;

        if (StringUtil.isEmpty(user.getUserName())) {
            throw new WrongParameterValueException("jbb.mgt.error.td.username.empty");
        }
        if (StringUtil.isEmpty(user.getIdCard()) || !IDCardUtil.validate(user.getIdCard())) {
            throw new WrongParameterValueException("jbb.mgt.error.td.idcard.empty");
        }
        if (StringUtil.isEmpty(user.getPhoneNumber())) {
            throw new WrongParameterValueException("jbb.mgt.error.td.phonenumber.empty");
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("name=");
        stringBuilder.append(HttpUtil.encodeURLParam(user.getUserName()));
        stringBuilder.append("&id_number=");
        stringBuilder.append(user.getIdCard());
        stringBuilder.append("&mobile=");
        stringBuilder.append(user.getPhoneNumber());
        String data = stringBuilder.toString();
        try {
            HttpResponse response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_POST, url,
                HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED, data, null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                TdPreloanRiskResponse tdRsp
                    = mapper.readValue(new String(response.getResponseBody()), TdPreloanRiskResponse.class);
                if (tdRsp.getSuccess()) {
                    PreloanReport report = new PreloanReport();
                    report.setReportId(tdRsp.getReportId());
                    report.setUserId(user.getUserId());
                    report.setReq(data);
                    report.setApplyDate(DateUtil.getCurrentTimeStamp());
                    preloanReportDao.insertPreloanReport(report);
                    if (feeFlag) {
                        int orgId = account.getOrgId();
                        int fee = PropertyManager.getIntProperty("jbb.mgt.td.price", 40);
                        OrgRecharges o = orgRechargesService.selectOrgRechargesForUpdate(orgId);
                        o.setTotalSmsExpense(o.getTotalSmsExpense() + fee);
                        orgRechargesService.updateOrgRecharges(o);
                        orgRechargeDetailService.handleConsumeTdRep(account.getAccountId(), user.getUserId(), fee);
                    }
                    return report;
                } else {
                    logger.debug("response = " + new String(response.getResponseBody()));
                    return null;
                }

            } else {
                logger.debug("response code = " + response.getResponseCode());
            }

        } catch (IOException e) {
            logger.debug("response with error, " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private TdPreloanRiskResponse getReportFromTd(String reportId) {
        String code = PropertyManager.getProperty("jbb.mgt.td.partner.code");
        String key = PropertyManager.getProperty("jbb.mgt.td.partner.key");
        String url = PropertyManager.getProperty("jbb.mgt.td.preload.getreport.url");
        String appname = PropertyManager.getProperty("jbb.mgt.td.partner.appname");
        url += "?partner_code=" + code + "&partner_key=" + key + "&app_name=" + appname + "&report_id=" + reportId;

        try {
            HttpResponse response = HttpUtil.sendDataViaHTTP(HttpUtil.HTTP_GET, url,
                HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED, null, null);
            if (response.getResponseCode() == HttpUtil.STATUS_OK) {
                String content = new String(response.getResponseBody());
                TdPreloanRiskResponse tdRsp = mapper.readValue(content, TdPreloanRiskResponse.class);
                if (tdRsp.getSuccess()) {

                } else {
                    logger.debug("response = " + tdRsp.getSuccess());
                }
                return tdRsp;

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
            return aliyunService.getObject(Constants.OSS_BUCKET_TD_PRELOAN, reportId);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void saveReportToOss(String reportId, String content) {
        aliyunService.putObject(Constants.OSS_BUCKET_TD_PRELOAN, reportId, content);
    }

}
