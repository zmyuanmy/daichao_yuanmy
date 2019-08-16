package com.jbb.mgt.rs.action.fin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.jdbc.Null;
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
import com.jbb.mgt.core.domain.FinMerchantStatisticDaily;
import com.jbb.mgt.core.domain.H5Merchant;
import com.jbb.mgt.core.service.FinMerchantStatisticDailyService;
import com.jbb.mgt.core.service.H5MerchantService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateFormatUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@Service(FinMerchantAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FinMerchantAction extends BasicAction {
    public static final String ACTION_NAME = "FinMerchantAction";

    private static DefaultTransactionDefinition NEW_TX_DEFINITION =
        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    private static Logger logger = LoggerFactory.getLogger(FinMerchantAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private FinMerchantStatisticDailyService finMerchantStatisticDailyService;

    @Autowired
    private H5MerchantService h5MerchantService;

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

    public void getMerchantByDate(String statisticDate) {
        logger.debug(">getMerchantByDate");
        checkParam(1, statisticDate, null);
        this.response.statisticDate = statisticDate;
        this.response.statistic = finMerchantStatisticDailyService.selectMerchantByDate(statisticDate);
        logger.debug("<getMerchantByDate");
    }

    public void getMerchantById(Integer merchantId, String startDate, String endDate) {
        logger.debug(">getMerchantById");
        checkParam(merchantId, startDate, endDate);
        Timestamp startTs = Util.parseTimestamp(startDate, this.getTimezone());
        Timestamp endTs = Util.parseTimestamp(endDate, this.getTimezone());
        this.response.merchant = h5MerchantService.selectH5merchantById(merchantId);
        this.response.statistic = finMerchantStatisticDailyService.selectMerchantById(merchantId, startTs, endTs);
        logger.debug("<getMerchantById");
    }

    public void saveMerchant(Integer merchantId, String statisticDate, Request req) {
        logger.debug(">saveMerchant,Merchant={} " + req);
        TransactionStatus txStatus = null;

        if (null == req) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "req");
        }
        checkParam(merchantId, statisticDate, null);
        FinMerchantStatisticDaily merchant =
            finMerchantStatisticDailyService.selectMerchantByStaDate(merchantId, statisticDate, 0);
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            if (null == merchant) {
                merchant = generateMerchant(null, req);
                merchant.setStatisticDate(DateFormatUtil.stringToDate(statisticDate));
                merchant.setMerchantId(merchantId);
                merchant.setBalance(updateBalance(merchant, merchantId, statisticDate));
                finMerchantStatisticDailyService.insertMerchant(merchant);
            } else if (null != merchant) {
                int status = req.status == null ? 0 : req.status;
                if (merchant.getStatus() == 1 && (!this.isSysAdmin() && !this.isOrgAdmin() && !this.isOpSysAdmin()
                    && !this.isOpOrgAdmin() && !this.isFinance())) {
                    throw new WrongParameterValueException("jbb.mgt.error.exception.finH5.Nopermission");
                }
                if (status == 1 && (!this.isSysAdmin() && !this.isOrgAdmin() && !this.isOpSysAdmin()
                    && !this.isOpOrgAdmin() && !this.isFinance() && !this.isSettle())) {
                    throw new WrongParameterValueException("jbb.mgt.error.exception.finH5.Nopermission");
                }
                merchant = generateMerchant(merchant, req);
                merchant.setBalance(updateBalance(merchant, merchantId, statisticDate));
                finMerchantStatisticDailyService.updateMerchant(merchant);
            }

            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        logger.debug(">saveMerchant");
    }

    public void getMerchantDaily(int[] merchantIds, String startDate, String endDate) {
        logger.debug(">getMerchantDaily");
        checkParam(1, startDate, endDate);
        merchantIds = merchantIds.length == 0 ? null : merchantIds;
        List<FinMerchantStatisticDaily> finList =
            finMerchantStatisticDailyService.selectMerchantDaily(merchantIds, startDate, endDate);
        FinMerchantStatisticDaily daily = new FinMerchantStatisticDaily();
        if (finList.size() != 0) {
            for (FinMerchantStatisticDaily finMerchant : finList) {
                daily.setUvCnt(finMerchant.getUvCnt() + daily.getUvCnt());
                daily.setCnt(finMerchant.getCnt() + daily.getCnt());
                daily.setTotalRegisterCnt(finMerchant.getTotalRegisterCnt() + daily.getTotalRegisterCnt());
                daily.setMoney(finMerchant.getMoney() + daily.getMoney());
            }
        }
        daily.setPrice(daily.getCnt() != 0 ? (int)daily.getMoney() / daily.getCnt() : 0);
        daily.setUvRates(daily.getUvCnt() != 0 ? (double)daily.getCnt() / daily.getUvCnt() : 0);
        daily.setUvPrice(daily.getUvCnt() != 0 ? (double)daily.getMoney() / daily.getUvCnt() : 0);
        daily.setRegisterRates(
            daily.getTotalRegisterCnt() != 0 ? (double)daily.getCnt() / daily.getTotalRegisterCnt() : 0);
        daily.setContributedValue(
            daily.getTotalRegisterCnt() != 0 ? (double)daily.getMoney() / daily.getTotalRegisterCnt() : 0);
        this.response.totalS = daily;
        this.response.statistic = finList;
        logger.debug(">getMerchantDaily");
    }

    public void getMerchantCompare(int[] merchantIds, String startDate, String endDate) {
        logger.debug(">getMerchantCompare");
        checkParam(1, startDate, endDate);
        merchantIds = merchantIds.length == 0 ? null : merchantIds;
        finMerchantStatisticDailyService.selectMerchantByStaDate(0,
            new SimpleDateFormat("yyyy-MM-dd").format(new Date()), null);
        List<FinMerchantStatisticDaily> finList =
            finMerchantStatisticDailyService.selectMerchantCompare(merchantIds, startDate, endDate);
        FinMerchantStatisticDaily daily = new FinMerchantStatisticDaily();
        if (finList.size() != 0) {
            for (FinMerchantStatisticDaily finMerchant : finList) {
                daily.setUvCnt(finMerchant.getUvCnt() + daily.getUvCnt());
                daily.setCnt(finMerchant.getCnt() + daily.getCnt());
                daily.setTotalRegisterCnt(finMerchant.getTotalRegisterCnt() + daily.getTotalRegisterCnt());
                daily.setMoney(finMerchant.getMoney() + daily.getMoney());
            }
        }
        daily.setPrice(daily.getCnt() != 0 ? (int)daily.getMoney() / daily.getCnt() : 0);
        daily.setUvRates(daily.getUvCnt() != 0 ? (double)daily.getCnt() / daily.getUvCnt() : 0);
        daily.setUvPrice(daily.getUvCnt() != 0 ? (double)daily.getMoney() / daily.getUvCnt() : 0);
        daily.setRegisterRates(
            daily.getTotalRegisterCnt() != 0 ? (double)daily.getCnt() / daily.getTotalRegisterCnt() : 0);
        daily.setContributedValue(
            daily.getTotalRegisterCnt() != 0 ? (double)daily.getMoney() / daily.getTotalRegisterCnt() : 0);
        this.response.totalS = daily;
        this.response.statistic = finList;
        logger.debug(">getMerchantCompare");
    }

    private int updateBalance(FinMerchantStatisticDaily merchant, Integer merchantId, String statisticDate) {
        FinMerchantStatisticDaily merchantStatistic =
            finMerchantStatisticDailyService.selectMerchantByStaDate(merchantId, statisticDate, null);
        int balance = null != merchantStatistic ? merchantStatistic.getBalance() : 0;// 最近一余额
        int balanceOld = null != merchant ? merchant.getBalance() : 0;// 获取原余额
        int balanceNew = balance + merchant.getAmount() - merchant.getExpense();// 更改后余额
        // 如果原余额 不等于 改后余额 就算差
        if (balanceOld != balanceNew) {
            balance = balanceNew - balanceOld;
            finMerchantStatisticDailyService.updateMerchantByBalance(balance, merchant.getMerchantId(),
                merchant.getStatisticDate());
        }
        return balanceNew;
    }

    public void updateMerchant(Request req) {
        logger.debug(">updateMerchant");
        if (null != req && null != req.merchants && req.merchants.size() > 0) {
            finMerchantStatisticDailyService.updateMerchantList(req.merchants);
        }
        logger.debug(">updateMerchant");
    }

    private void checkParam(Integer merchantId, String date, String endDate) {
        if (null == merchantId || merchantId <= 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "merchantId");
        }
        if (StringUtil.isEmpty(date)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "date");
        }
        if (!DateFormatUtil.isValidDate(date)) {
            throw new MissingParameterException("jbb.mgt.error.exception.wrong.paramvalue", "zh", "date");
        }
        if (!StringUtil.isEmpty(endDate) && !DateFormatUtil.isValidDate(endDate)) {
            throw new MissingParameterException("jbb.mgt.error.exception.wrong.paramvalue", "zh", "endDate");
        }
    }

    private FinMerchantStatisticDaily generateMerchant(FinMerchantStatisticDaily merchant, Request req) {
        merchant = null == merchant ? new FinMerchantStatisticDaily() : merchant;
        if (req.cnt != null) {
            merchant.setCnt(req.cnt);
        }
        if (req.price != null) {
            merchant.setPrice(req.price);
        }
        if (req.amount != null) {
            merchant.setAmount(req.amount);
        }
        if (this.isSysAdmin() || this.isOrgAdmin() || this.isOpSysAdmin() || this.isOpOrgAdmin() || this.isFinance() || this.isSettle()) {
            if (req.status != null) {
                merchant.setStatus(req.status);
            }
            if (req.status != null && req.status == 1) {
                merchant.setConfrimAccountId(this.account.getAccountId());
                merchant.setConfirmDate(DateUtil.getCurrentTimeStamp());
            }
        }
        merchant.setExpense(merchant.getCnt() * merchant.getPrice());
        merchant.setUpdateDate(DateUtil.getCurrentTimeStamp());
        return merchant;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private String statisticDate;

        private H5Merchant merchant;

        private List<FinMerchantStatisticDaily> statistic;

        private FinMerchantStatisticDaily totalS;

        public String getStatisticDate() {
            return statisticDate;
        }

        public List<FinMerchantStatisticDaily> getStatistic() {
            return statistic;
        }

        public H5Merchant getMerchant() {
            return merchant;
        }

        public FinMerchantStatisticDaily getTotalS() {
            return totalS;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer cnt; // 统计数
        public Integer price; // 单价， 单位为分
        public Integer amount; // 收款额
        public Integer status; // 状态：0-待确认， 1- 已确认
        public List<FinMerchantStatisticDaily> merchants;
    }
}
