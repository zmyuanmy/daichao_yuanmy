package com.jbb.mgt.rs.action.manualRechage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Account;
import com.jbb.mgt.core.domain.OrgRecharges;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.service.AccountService;
import com.jbb.mgt.core.service.OrgRechargeDetailService;
import com.jbb.mgt.core.service.OrgRechargesService;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;

@Service(ManualRechageAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ManualRechageAction extends BasicAction {

    public static final String ACTION_NAME = "ManualRechageAction";

    private static Logger logger = LoggerFactory.getLogger(ManualRechageAction.class);
    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    @Autowired
    private PlatformTransactionManager txManager;

    private Response response;
    @Autowired
    private OrgRechargesService orgRechargesService;

    @Autowired
    private OrgRechargeDetailService orgRechargeDetailService;
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AccountService accountService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

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

    public void manualRechage(int orgId, Integer amount, Integer optype) {

        if (orgId < 1) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "orgId");
        }
        if (amount == null) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "amount");
        }
        // 11 帮帮余额 12流量
        Integer newType = optype == null ? 12 : optype;
        Organization org = organizationService.getOrganizationById(orgId, false);
        if (org == null) {
            throw new WrongParameterValueException("jbb.mgt.exception.orgNotFound");
        }

        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            OrgRecharges orgRecharges = orgRechargesService.selectOrgRechargesForUpdate(orgId);
            Account acc = accountService.selectOrgAdminAccount(orgId, null);
            if (newType == 12) {
                orgRecharges.setTotalDataAmount(orgRecharges.getTotalDataAmount() + amount);
            } else if (newType == 11) {
                orgRecharges.setTotalSmsAmount(orgRecharges.getTotalSmsAmount() + amount);
            }
            orgRechargesService.updateOrgRecharges(orgRecharges);
            orgRechargeDetailService.manualRechage(this.account.getAccountId(), amount, newType, acc.getAccountId());
            logger.debug("manualRechage:accountId = " + this.account.getAccountId() + "amount" + amount);
            txManager.commit(txStatus);
            txStatus = null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("manualRechage false", e);
        } finally {
            rollbackTransaction(txStatus);
        }
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

    }

}
