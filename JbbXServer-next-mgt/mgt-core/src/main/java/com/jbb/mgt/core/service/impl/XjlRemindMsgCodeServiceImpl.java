package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.XjlApplyRecordDao;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.domain.PayRecord;
import com.jbb.mgt.core.domain.User;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.service.AccountService;
import com.jbb.mgt.core.service.ChuangLanService;
import com.jbb.mgt.core.service.OrgRechargesService;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.mgt.core.service.UserService;
import com.jbb.mgt.core.service.XjlRemindMsgCodeService;
import com.jbb.server.common.PropertyManager;

@Service("XjlRemindMsgCodeService")
public class XjlRemindMsgCodeServiceImpl implements XjlRemindMsgCodeService {

    private static Logger logger = LoggerFactory.getLogger(XjlRemindMsgCodeServiceImpl.class);

    @Autowired
    private XjlApplyRecordDao dao;

    @Autowired
    private OrgRechargesService orgRechargesService;

    @Autowired
    private ChuangLanService chuangLanService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    public void getXjlApplyRecordsByStatus(Integer status, String startDate, Integer startDay, Integer endDay) {
        logger.debug(">getXjlApplyRecordsByStatus() status= " + status + "startDate= " + startDate + "startDay= "
            + startDay + "endDay= " + endDay);
        List<XjlApplyRecord> xjlApplyRecords = dao.selectXjlApplyRecordsByStatus(status, startDate, startDay, endDay);
        if (xjlApplyRecords.size() > 0) {
            for (XjlApplyRecord xjlApply : xjlApplyRecords) {
                if (xjlApply != null && xjlApply.getStatus() != null && xjlApply.getOrgId() != null
                    && xjlApply.getUserId() != null) {
                    int smsPrice = PropertyManager.getIntProperty("jbb.mgt.sms.price", 10);
                    OrgRecharges o = orgRechargesService.selectOrgRechargesForUpdate(xjlApply.getOrgId());
                    if (o == null || o.getTotalSmsAmount() - o.getTotalSmsExpense() < smsPrice) {
                        logger.warn("orgId = " + xjlApply.getOrgId() + ",余额不足!!");
                        continue;
                    }
                    Organization org = organizationService.getOrganizationById(xjlApply.getOrgId(), false);
                    Account account = accountService.selectOrgAdminAccount(xjlApply.getOrgId(), org.getOrgType());
                    User user = userService.selectUserById(xjlApply.getUserId());
                    String date = "";
                    if (xjlApply.getLoanDate() != null) {
                        date = xjlApply.getLoanDate().toString().substring(0, 11);
                    }
                    chuangLanService.runXjlRemind(xjlApply.getApplyId(), account.getAccountId(), user.getPhoneNumber(),
                        org.getSmsSignName(), xjlApply.getStatus(), user.getUserName(),
                        String.valueOf(((xjlApply.getAmount() + xjlApply.getServiceFee()) / 100)), date,
                        xjlApply.getOverDay());
                }
            }
        }
        logger.debug("<getXjlApplyRecordsByStatus()");
    }

    public void loanSendCode(PayRecord payRecord, XjlApplyRecord xjlApply) {
        logger.debug(">loanSendCode() payRecord= " + payRecord + "xjlApply= " + xjlApply);
        User user = userService.selectUserById(xjlApply.getUserId());
        Organization org = organizationService.getOrganizationById(xjlApply.getOrgId(), false);
        chuangLanService.runXjlRemind(xjlApply.getApplyId(), payRecord.getAccountId(), user.getPhoneNumber(),
            org.getSmsSignName(), 1, user.getUserName(),
            String.valueOf(((xjlApply.getAmount() + xjlApply.getServiceFee()) / 100)), null, null);
        logger.debug("<loanSendCode()");
    }

}
