package com.jbb.mgt.rs.action.fin;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;

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
import com.jbb.mgt.core.domain.FinOrgDelStatisticDaily;
import com.jbb.mgt.core.domain.FinOrgSalesRelation;
import com.jbb.mgt.core.domain.OrgStatisticDailyInfo;
import com.jbb.mgt.core.domain.Organization;
import com.jbb.mgt.core.service.AccountService;
import com.jbb.mgt.core.service.FinOrgDelStatisticDailyService;
import com.jbb.mgt.core.service.FinOrgSalesRelationService;
import com.jbb.mgt.core.service.OrganizationService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateFormatUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@Service(FinOrgDelStaDailyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FinOrgDelStaDailyAction extends BasicAction {
    public static final String ACTION_NAME = "FinOrgDelStaDailyAction";

    private static DefaultTransactionDefinition NEW_TX_DEFINITION
        = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    private static Logger logger = LoggerFactory.getLogger(FinOrgDelStaDailyAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private FinOrgDelStatisticDailyService orgDelStatisticDailyService;

    @Autowired
    private FinOrgSalesRelationService finOrgSalesRelationService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PlatformTransactionManager txManager;

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

    public void getOrgDelStatistics(String statisticDate, Integer salesId) {
        logger.debug(">getOrgDelStatistics()");
        checkParam(1, statisticDate);

        long today = DateUtil.getTodayStartTs();
        Timestamp date1 = Util.parseTimestamp(statisticDate, TimeZone.getDefault());
        long l1 = date1.getTime();
        List<OrgStatisticDailyInfo> list = null;
        if (today <= l1) {
            list = orgDelStatisticDailyService.selectOrgDelStatisticsDaily(statisticDate, salesId);
        } else {
            list = orgDelStatisticDailyService.selectOrgDelStatistics(statisticDate, salesId);
        }
        this.response.statisticDate = statisticDate;
        this.response.orgDels = list;
        logger.debug(">getOrgDelStatistics()");
    }

    public void getFinOrgDelStatisticDailys(Integer orgId, String startDate, String endDate) {
        logger.debug(">getFinOrgDelStatisticDailys()");
        checkParam(orgId, startDate);
        this.response.organization = organizationService.getOrganizationById(orgId, false);

        FinOrgSalesRelation finOrgSalesRelation = finOrgSalesRelationService.selectOrgSalesRelationByOrgId(orgId);
        if (finOrgSalesRelation != null) {
            Account account = accountService.getAccountById(finOrgSalesRelation.getAccountId(), null, false);
            this.response.salesAccount = account;
        }

        this.response.statistic = orgDelStatisticDailyService.getFinOrgDelStatisticDailys(orgId, startDate, endDate);
        logger.debug(">getFinOrgDelStatisticDailys()");
    }

    public void updateFinOrgDelStatistic(Integer orgId, String statisticDate, Request req) {
        logger.debug(">updateFinOrgDelStatistic(),req=" + req);
        checkParam(orgId, statisticDate);
        TransactionStatus txStatus = null;
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);
            FinOrgDelStatisticDaily ods2
                = orgDelStatisticDailyService.getFinOrgDelStatisticDaily(statisticDate, orgId, false);
            if (ods2 == null) {
                ods2 = new FinOrgDelStatisticDaily();
                ods2.setStatisticDate(DateFormatUtil.stringToDate(statisticDate));
                ods2.setOrgId(orgId);
                orgDelStatisticDailyService.saveFinOrgDelStatisticDaily(ods2);
            }
            ods2 = generateOrgDel(ods2, req);
            ods2.setBalance(updateBalance(ods2, orgId, statisticDate));
            orgDelStatisticDailyService.updateFinOrgDelStatisticDaily(ods2);
            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        logger.debug("<updateFinOrgDelStatistic()");
    }

    private FinOrgDelStatisticDaily generateOrgDel(FinOrgDelStatisticDaily orgDelStatistic, Request request) {

        int status = request.status != null ? request.status : 0;

        if (orgDelStatistic.getStatus() == 1 && (!this.isSysAdmin() && !this.isOrgAdmin() &&!this.isOpSysAdmin() && !this.isOpOrgAdmin() && !this.isFinance())) {
            throw new WrongParameterValueException("jbb.mgt.exception.channel.hasBeenPayment");
        }
        if (status == 1 && (!this.isSysAdmin() && !this.isFinance() && !this.isOrgAdmin() &&!this.isOpSysAdmin() && !this.isOpOrgAdmin() && !this.isSettle())) {
            throw new WrongParameterValueException("jbb.mgt.exception.channel.changePaymentState");
        }
        if (request.amount != null) {
            orgDelStatistic.setAmount(request.amount);
        }
        if (request.price != null) {
            orgDelStatistic.setPrice(request.price);
        }
        orgDelStatistic.setStatus(status);
        if (status == 1) {
            orgDelStatistic.setConfrimAccountId(this.account.getAccountId());
            orgDelStatistic.setConfirmDate(DateUtil.getCurrentTimeStamp());
        }
        orgDelStatistic.setExpense(orgDelStatistic.getCnt() * orgDelStatistic.getPrice());
        return orgDelStatistic;
    }

    private int updateBalance(FinOrgDelStatisticDaily orgDelStatistic, Integer orgId, String statisticDate) {
        FinOrgDelStatisticDaily daily
            = orgDelStatisticDailyService.getFinOrgDelStatisticDaily(statisticDate, orgId, true);
        int balance = null != daily ? daily.getBalance() : 0;// 最近一余额
        int balanceOld = null != orgDelStatistic ? orgDelStatistic.getBalance() : 0;// 获取原余额
        int balanceNew = balance + orgDelStatistic.getAmount() - orgDelStatistic.getExpense();// 更改后余额
        // 如果原余额 不等于 改后余额 就算差
        if (balanceOld != balanceNew) {
            balance = balanceNew - balanceOld;
            orgDelStatisticDailyService.updateBalance(balance, orgId, statisticDate);
        }
        return balanceNew;
    }

    private void checkParam(Integer orgId, String statisticDate) {
        if (null == orgId || orgId <= 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "orgId");
        }
        if (StringUtil.isEmpty(statisticDate)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "date");
        }
        if (!DateFormatUtil.isValidDate(statisticDate)) {
            throw new MissingParameterException("jbb.mgt.error.exception.wrong.paramvalue", "zh", "date");
        }
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private String statisticDate;

        private List<OrgStatisticDailyInfo> orgDels;

        private Organization organization;

        private Account salesAccount;

        private List<FinOrgDelStatisticDaily> statistic;

        public String getStatisticDate() {
            return statisticDate;
        }

        public List<OrgStatisticDailyInfo> getOrgDels() {
            return orgDels;
        }

        public Organization getOrganization() {
            return organization;
        }

        public Account getSalesAccount() {
            return salesAccount;
        }

        public List<FinOrgDelStatisticDaily> getStatistic() {
            return statistic;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer price; // 单价， 单位为分
        public Integer amount; // 收款额
        public Integer status; // 状态：0-未确认， 1- 已确认
    }

}
