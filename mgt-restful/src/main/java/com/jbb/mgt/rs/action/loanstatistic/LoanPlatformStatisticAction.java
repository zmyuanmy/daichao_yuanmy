package com.jbb.mgt.rs.action.loanstatistic;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jbb.mgt.core.domain.FinOpLog;
import com.jbb.mgt.core.domain.LoanPlatformStatistic;
import com.jbb.mgt.core.domain.LoanPlatformStatisticByDate;
import com.jbb.mgt.core.domain.LoanPlatformStatisticByGroup;
import com.jbb.mgt.core.domain.Platform;
import com.jbb.mgt.core.service.FinOpLogService;
import com.jbb.mgt.core.service.LoanPlatformReportService;
import com.jbb.mgt.core.service.LoanPlatformService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateFormatUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

import lombok.Data;
import net.sf.json.JSONObject;

@Service(LoanPlatformStatisticAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoanPlatformStatisticAction extends BasicAction {
    public static final String ACTION_NAME = "LoanPlatformStatisticAction";

    private static DefaultTransactionDefinition NEW_TX_DEFINITION =
        new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

    private static Logger logger = LoggerFactory.getLogger(LoanPlatformStatisticAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private LoanPlatformReportService loanPlatformReportService;

    @Autowired
    private LoanPlatformService loanPlatformService;
    @Autowired
    private FinOpLogService finOpLogService;

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

    public void getPlatformByDate(String statisticDate, String groupName, Integer type, Integer salesId) {
        logger.debug(">getPlatformByDate");
        checkParam(1, statisticDate, null);
        // this.response.statisticDate = statisticDate;

        List<LoanPlatformStatisticByGroup> lps = new ArrayList<LoanPlatformStatisticByGroup>();

        List<LoanPlatformStatistic> lpsList =
            loanPlatformReportService.selectPlatformByDateAndGroup(statisticDate, groupName, type, salesId);

        // 处理 LoanPlatformStatistic -> LoanPlatformStatisticByGroup
        if (!lpsList.isEmpty()) {
            // 先转成Map
            Map<String, List<LoanPlatformStatistic>> sMap = new HashMap<String, List<LoanPlatformStatistic>>();
            lpsList.forEach(item -> {
                String gName = item.getPlatform().getGroupName();
                if (gName == null) {
                    gName = String.valueOf(item.getPlatformId()); // 用唯一标识，自成一组
                }
                List<LoanPlatformStatistic> gList = sMap.get(gName);
                if (gList == null) {
                    gList = new ArrayList<LoanPlatformStatistic>();
                    sMap.put(gName, gList);
                }
                gList.add(item);
            });
            // 再将Map转成前端需要的List
            sMap.forEach((gName, list) -> {
                LoanPlatformStatisticByGroup sbg = new LoanPlatformStatisticByGroup(gName, list);
                sbg.calculateTotalBalance(); // 计算余额
                lps.add(sbg);
            });
        }

        // 排序
        Collections.sort(lps, new Comparator<LoanPlatformStatisticByGroup>() {
            @Override
            public int compare(LoanPlatformStatisticByGroup lp1, LoanPlatformStatisticByGroup lp2) {
                return lp1.getTotalBalance() - lp2.getTotalBalance();
            }
        });

        this.response.platformGroupStatistic = lps;

        logger.debug("<getPlatformByDate");
    }

    public void getPlatformById(Integer platformId, String startDate, String endDate, String groupName, Integer type,
        Integer salesId) {
        logger.debug(">getPlatformById");
        if (platformId == null && StringUtil.isEmpty(groupName) && type == null) {
            throw new WrongParameterValueException("jbb.error.platformIdAndGroupName.empty");
        }
        checkParam(1, startDate, endDate);
        Timestamp startTs = Util.parseTimestamp(startDate, this.getTimezone());
        Timestamp endTs = Util.parseTimestamp(endDate, this.getTimezone());

        List<LoanPlatformStatisticByDate> pbdList = new ArrayList<LoanPlatformStatisticByDate>();
        List<LoanPlatformStatistic> lpsList =
            loanPlatformReportService.selectPlatformGroupById(platformId, startTs, endTs, groupName, type, salesId);

        // 处理 LoanPlatformStatistic -> LoanPlatformStatisticByGroup
        if (!lpsList.isEmpty()) {
            // 先转成Map
            Map<Date, List<LoanPlatformStatistic>> sMap = new HashMap<Date, List<LoanPlatformStatistic>>();
            lpsList.forEach(item -> {
                Date sDate = item.getStatisticDate();
                List<LoanPlatformStatistic> gList = sMap.get(sDate);
                if (gList == null) {
                    gList = new ArrayList<LoanPlatformStatistic>();
                    sMap.put(sDate, gList);
                }
                gList.add(item);
            });
            // 再将Map转成前端需要的List
            sMap.forEach((sDate, list) -> {
                LoanPlatformStatisticByDate sbd = new LoanPlatformStatisticByDate(sDate, list);
                pbdList.add(sbd);
            });
        }

        // 排序
        Collections.sort(pbdList, new Comparator<LoanPlatformStatisticByDate>() {
            @Override
            public int compare(LoanPlatformStatisticByDate item1, LoanPlatformStatisticByDate item2) {
                return item1.getStatisticDate().after(item2.getStatisticDate()) ? 1 : -1;
            }
        });

        this.response.platformStatisticByDate = pbdList;

        logger.debug("<getPlatformById");
    }

    public void savePlatform(Integer platformId, String statisticDate, Request req) {
        logger.debug(">savePlatform,platform={} " + req);
        TransactionStatus txStatus = null;

        if (null == req) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "req");
        }
        checkParam(platformId, statisticDate, null);
        LoanPlatformStatistic loanPlatformStatistic =
            loanPlatformReportService.selectPlatformByStaDate(platformId, statisticDate, 0);
        try {
            txStatus = txManager.getTransaction(NEW_TX_DEFINITION);

            if (null == loanPlatformStatistic) {
                if ((req.cnt != null || req.puvCnt != null || req.price != null)
                    && (!this.isSysAdmin() && !this.isOrgAdmin() && !this.isOpSysAdmin() && !this.isOpOrgAdmin()
                        && !this.isPlatformReportor())) {
                    throw new WrongParameterValueException("jbb.mgt.error.exception.finLoanPlatform.Nopermission");
                }
                loanPlatformStatistic = generatePlatform(null, req);
                String statisticDateTime = statisticDate + " 00:00:00";
                java.sql.Date statisticDateTimeParm =
                    new java.sql.Date(DateUtil.parseStrnew(statisticDateTime).getTime());
                loanPlatformStatistic.setStatisticDate(statisticDateTimeParm);
                loanPlatformStatistic.setPlatformId(platformId);
                loanPlatformStatistic.setBalance(updateBalance(loanPlatformStatistic, platformId, statisticDate));
                loanPlatformReportService.insertPlatform(loanPlatformStatistic);
            } else if (null != loanPlatformStatistic) {
                int status = req.status == null ? 0 : req.status;
                if (req.amount != null && (!this.isSysAdmin() && !this.isOrgAdmin() && !this.isOpSysAdmin()
                    && !this.isOpOrgAdmin() && !this.isPlatformReportor() && !this.isReportAmount())) {
                    throw new WrongParameterValueException("jbb.mgt.error.exception.finLoanPlatform.Nopermission");
                }
                if ((req.cnt != null || req.puvCnt != null || req.price != null)
                    && (!this.isSysAdmin() && !this.isOrgAdmin() && !this.isOpSysAdmin() && !this.isOpOrgAdmin()
                        && !this.isPlatformReportor())) {
                    throw new WrongParameterValueException("jbb.mgt.error.exception.finLoanPlatform.Nopermission");
                }
                if (loanPlatformStatistic.getStatus() == 1 && (!this.isSysAdmin() && !this.isOrgAdmin()
                    && !this.isOpSysAdmin() && !this.isOpOrgAdmin() && !this.isFinance())) {
                    throw new WrongParameterValueException("jbb.mgt.error.exception.finLoanPlatform.Nopermission");
                }
                if (status == 1 && (!this.isSysAdmin() && !this.isOrgAdmin() && !this.isOpSysAdmin()
                    && !this.isOpOrgAdmin() && !this.isFinance())) {
                    throw new WrongParameterValueException("jbb.mgt.error.exception.finLoanPlatform.Nopermission");
                }
                loanPlatformStatistic = generatePlatform(loanPlatformStatistic, req);
                loanPlatformStatistic.setBalance(updateBalance(loanPlatformStatistic, platformId, statisticDate));
                loanPlatformReportService.updatePlatform(loanPlatformStatistic);
            }

            txManager.commit(txStatus);
            txStatus = null;
        } finally {
            rollbackTransaction(txStatus);
        }
        // 记录操作日志
        insertFinOpLog(req, platformId, this.account.getAccountId(), statisticDate);
        logger.debug(">savePlatform");
    }

    private void insertFinOpLog(Request req, Integer platformId, Integer accountId, String statisticDate) {
        FinOpLog finOpLog = new FinOpLog();
        finOpLog.setSourceId(platformId.toString());
        finOpLog.setAccountId(accountId);
        finOpLog.setType(FinOpLog.PLATFORM_FLAG);
        finOpLog.setParams("statisticDate:" + statisticDate + ",cnt:" + req.cnt + ",price:" + req.price + ",amount:"
            + req.amount + ",status:" + req.status + ",puvCnt:" + req.puvCnt);
        finOpLog.setOpDate(DateUtil.getCurrentTimeStamp());
        finOpLogService.insertFinOpLog(finOpLog);
    }

    public void getPlatformDaily(int[] platformIds, String startDate, String endDate) {
        logger.debug(">getPlatformDaily");
        checkParam(1, startDate, endDate);
        platformIds = platformIds.length == 0 ? null : platformIds;
        List<LoanPlatformStatistic> finList =
            loanPlatformReportService.selectPlatformDaily(platformIds, startDate, endDate);
        LoanPlatformStatistic daily = new LoanPlatformStatistic();
        if (finList.size() != 0) {
            for (LoanPlatformStatistic loanPlatformStatistic : finList) {
                daily.setUvCnt(loanPlatformStatistic.getUvCnt() + daily.getUvCnt());
                daily.setCnt(loanPlatformStatistic.getCnt() + daily.getCnt());
                daily.setTotalRegisterCnt(loanPlatformStatistic.getTotalRegisterCnt() + daily.getTotalRegisterCnt());
                daily.setMoney(loanPlatformStatistic.getMoney() + daily.getMoney());
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
        logger.debug(">getPlatformDaily");
    }

    public void getPlatformCompare(int[] platformIds, String startDate, String endDate) {
        logger.debug(">getPlatformCompare");
        checkParam(1, startDate, endDate);
        platformIds = platformIds.length == 0 ? null : platformIds;
        loanPlatformReportService.selectPlatformByStaDate(0,
            new SimpleDateFormat("yyyy-MM-dd").format(new Date(DateUtil.getCurrentTime())), null);
        List<LoanPlatformStatistic> finList =
            loanPlatformReportService.selectPlatformCompare(platformIds, startDate, endDate);
        LoanPlatformStatistic daily = new LoanPlatformStatistic();
        if (finList.size() != 0) {
            for (LoanPlatformStatistic loanPlatformStatistic : finList) {
                daily.setUvCnt(loanPlatformStatistic.getUvCnt() + daily.getUvCnt());
                daily.setCnt(loanPlatformStatistic.getCnt() + daily.getCnt());
                daily.setTotalRegisterCnt(loanPlatformStatistic.getTotalRegisterCnt() + daily.getTotalRegisterCnt());
                daily.setMoney(loanPlatformStatistic.getMoney() + daily.getMoney());
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
        logger.debug(">getPlatformCompare");
    }

    private int updateBalance(LoanPlatformStatistic loanPlatformStatistic, Integer platformId, String statisticDate) {
        LoanPlatformStatistic loanPlatformStatistic1 =
            loanPlatformReportService.selectPlatformByStaDate(platformId, statisticDate, null);
        int balance = null != loanPlatformStatistic1 ? loanPlatformStatistic1.getBalance() : 0;// 最近一余额
        int balanceOld = null != loanPlatformStatistic ? loanPlatformStatistic.getBalance() : 0;// 获取原余额
        int balanceNew = balance + loanPlatformStatistic.getAmount() - loanPlatformStatistic.getExpense();// 更改后余额
        // 如果原余额 不等于 改后余额 就算差
        if (balanceOld != balanceNew) {
            balance = balanceNew - balanceOld;
            loanPlatformReportService.updatePlatformByBalance(balance, loanPlatformStatistic.getPlatformId(),
                loanPlatformStatistic.getStatisticDate());
        }
        return balanceNew;
    }

    private void checkParam(Integer platformId, String date, String endDate) {
        if (null == platformId || platformId <= 0) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "platformId");
        }
        if (StringUtil.isEmpty(date)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "date");
        }
        if (!DateFormatUtil.isValidDate(date)) {
            throw new MissingParameterException("jbb.mgt.error.exception.wrong.paramvalue", "zh", "date");
        }
    }

    private LoanPlatformStatistic generatePlatform(LoanPlatformStatistic loanPlatformStatistic, Request req) {
        loanPlatformStatistic = null == loanPlatformStatistic ? new LoanPlatformStatistic() : loanPlatformStatistic;
        if (req.cnt != null) {
            loanPlatformStatistic.setCnt(req.cnt);
        }
        if (req.puvCnt != null) {
            loanPlatformStatistic.setPuvCnt(req.puvCnt);;
        }
        if (req.price != null) {
            loanPlatformStatistic.setPrice(req.price);
        }
        if (req.amount != null) {
            loanPlatformStatistic.setAmount(req.amount);
        }
        if (this.isSysAdmin() || this.isOrgAdmin() || this.isOpSysAdmin() || this.isOpOrgAdmin() || this.isFinance()) {
            if (req.status != null) {
                loanPlatformStatistic.setStatus(req.status);
            }
            if (loanPlatformStatistic.getStatus() == 1) {
                loanPlatformStatistic.setConfrimAccountId(this.account.getAccountId());
                loanPlatformStatistic.setConfirmDate(DateUtil.getCurrentTimeStamp());
            }
        }
        loanPlatformStatistic.setExpense(
            (loanPlatformStatistic.getCnt() + loanPlatformStatistic.getPuvCnt()) * loanPlatformStatistic.getPrice());
        loanPlatformStatistic.setUpdateDate(DateUtil.getCurrentTimeStamp());
        return loanPlatformStatistic;
    }

    public void getPlatformDetailByDate(String startDate, String endDate, Integer salesId) {
        logger.debug(">getPlatformDetailByDate");
        this.response.statistic =
            loanPlatformReportService.selectLoanPlatformDetailStatisticByStatisticDate(startDate, endDate, salesId);
        logger.debug("<getPlatformDetailByDate");
    }

    public void getPlatformDetailById(Integer platformId, String startDate, String endDate, Integer salesId) {
        logger.debug(">getPlatformDetailById");
        checkParam(platformId, startDate, endDate);
        if (StringUtil.isEmpty(endDate)) {
            endDate = new Date(DateUtil.getTodayStartTs() + DateUtil.DAY_MILLSECONDES).toString();
        }
        Timestamp startTs = Util.parseTimestamp(startDate, this.getTimezone());
        Timestamp endTs = Util.parseTimestamp(endDate, this.getTimezone());
        this.response.statistic = loanPlatformReportService.selectLoanPlatformDetailStatistic(platformId,
            new Date(startTs.getTime()), new Date(endTs.getTime()), salesId);
        logger.debug("<getPlatformDetailById");
    }

    public void updatePlatformDetailList(Request req) {
        logger.debug(">updatePlatformDetailList");
        if (null != req && null != req.platformStatistics && req.platformStatistics.size() > 0) {
            loanPlatformReportService.updateLoanPlatformStatistic(req.platformStatistics);
        }
        logger.debug("<updatePlatformDetailList");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private String statisticDate;

        private Platform platform;

        private List<LoanPlatformStatistic> statistic;

        private LoanPlatformStatistic totalS;
        public List<LoanPlatformStatisticByGroup> platformGroupStatistic;
        public List<LoanPlatformStatisticByDate> platformStatisticByDate;

        public String getStatisticDate() {
            return statisticDate;
        }

        public List<LoanPlatformStatistic> getStatistic() {
            return statistic;
        }

        public Platform getPlatform() {
            return platform;
        }

        public LoanPlatformStatistic getTotalS() {
            return totalS;
        }

        public List<LoanPlatformStatisticByGroup> getPlatformGroupStatistic() {
            return platformGroupStatistic;
        }

        public List<LoanPlatformStatisticByDate> getPlatformStatisticByDate() {
            return platformStatisticByDate;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer cnt; // 统计数
        public Integer price; // 单价， 单位为分
        public Integer puvCnt;// 产品上报uv
        public Integer amount; // 收款额
        public Integer status; // 状态：0-待确认， 1- 已确认
        public List<LoanPlatformStatistic> platformStatistics;

    }
}
