package com.jbb.mgt.rs.action.ChannelStaDailyAction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jbb.mgt.core.domain.*;
import com.jbb.server.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.service.AccountService;
import com.jbb.mgt.core.service.ChannelAccountInfoService;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.core.service.ChannelStatisticDailyService;
import com.jbb.mgt.core.service.FinOpLogService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.DateFormatUtil;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;

@Service(ChannelStaDailyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ChannelStaDailyAction extends BasicAction {

    public static final String ACTION_NAME = "ChannelStaDailyAction";

    private static Logger logger = LoggerFactory.getLogger(ChannelStaDailyAction.class);

    private Response response;

    @Autowired
    private ChannelStatisticDailyService csdService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private ChannelAccountInfoService channelAccountInfoService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private FinOpLogService finOpLogService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void statisticByDate(String statisticDate, String groupName, Integer salesId) throws ParseException {
        logger.debug(">statisticByDate()");
        if (StringUtil.isEmpty(statisticDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "statisticDate");
        }
        long today = DateUtil.getTodayStartTs();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(statisticDate);
        long l1 = date.getTime();
        if (StringUtil.isEmpty(groupName)) {
            groupName = null;
        } else {
            if (groupName.equals("all")) {
                groupName = null;
            } else if (groupName.equals("undefined")) {
                groupName = "undefined";
            }
        }
        if (!this.checkChannelAndSale(Constants.MGT_STATISTIC_SALE_CHECK)) {
            salesId = this.account.getAccountId();
        }
        List<Channels> ch = null;
        if (today <= l1) {
            ch = csdService.getChannelStatisticDailys(statisticDate, null, groupName, salesId);
        } else {
            ch = csdService.getstatisticByDate(statisticDate, groupName, salesId);
        }
        this.response.statisticDate = statisticDate;
        this.response.channels = ch;
        logger.debug("<statisticByDate()");
    }

    public void statisticByChannelCode(String channelCode, String startDate, String endDate, String groupName,
        Integer salesId) throws ParseException {
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        if (!StringUtil.isEmpty(channelCode)) {
            Channel channel = channelService.getChannelByCode(channelCode);
            if (null == channel) {
                throw new WrongParameterValueException("jbb.mgt.exception.channel.notFound");

            }
            Integer orgId = accountService.getAccountById(channel.getCreator(), null, false).getOrgId();
            if (orgId == null || this.account.getOrgId() != orgId) {
                throw new WrongParameterValueException("jbb.mgt.exception.channel.notFound");
            }

            Channel channelByCode = channelService.getChannelByCode(channelCode);
            this.response.channel = channelByCode;
            this.response.channelAccountInfo = channelAccountInfoService.selectChannelAccountInfo(channelCode);
        }
        if (StringUtil.isEmpty(groupName)) {
            groupName = null;
        } else {
            if (groupName.equals("all")) {
                groupName = null;
            } else if (groupName.equals("undefined")) {
                groupName = "undefined";
            }
        }
        if (!this.checkChannelAndSale(Constants.MGT_STATISTIC_SALE_CHECK)) {
            salesId = this.account.getAccountId();
        }
        this.response.statisticGroupName
            = csdService.getStatisticByChannelCode(channelCode, startDate, endDate, groupName, salesId);
    }

    public void statisticStatus(String statisticDate, String channelCode, Request request) {
        logger.debug(">statisticStatus()");
        if (null == request) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "request");
        }
        if (StringUtil.isEmpty(channelCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "channelCode");
        }
        if (StringUtil.isEmpty(statisticDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "statisticDate");
        }

        ChannelStatisticDaily csd1 = csdService.getChannelStatisticDaily(statisticDate, channelCode, true, false);
        ChannelStatisticDaily csd2 = csdService.getChannelStatisticDaily(statisticDate, channelCode, false, true);
        int balance = 0;

        if (csd1 == null) {
            csd1 = new ChannelStatisticDaily();
            csd1.setStatisticDate(DateFormatUtil.stringToDate(statisticDate));
            csd1.setChannelCode(channelCode);
            csdService.saveChannelStatisticDaily(csd1);
        }
        int newbalance = csd1.getBalance();
        if (csd2 != null) {
            balance = csd2.getBalance();
        }
        if (csd1.getStatus() == 1 && (!this.isSysAdmin() && !this.isOpSysAdmin() && !this.isOrgAdmin()
            && !this.isOpOrgAdmin() && !this.isFinance())) {
            throw new WrongParameterValueException("jbb.mgt.exception.channel.hasBeenPayment");
        }
        if (request.status != null && request.status == 1 && (!this.isSysAdmin() && !this.isOpSysAdmin()
            && !this.isOrgAdmin() && !this.isOpOrgAdmin() && !this.isFinance() && !this.isSettle2())) {
            throw new WrongParameterValueException("jbb.mgt.exception.channel.changePaymentState");
        }
        if ((request.amount != null || request.mode != null || request.uvCnt != null || request.price != null)
            && (!this.isSysAdmin() && !this.isOpSysAdmin() && !this.isOrgAdmin() && !this.isOpOrgAdmin()
                && !this.isSettle())) {
            throw new WrongParameterValueException("jbb.error.validateAdminAccess.accessDenied");
        }

        // int amount2 = request.amount == null ? csd1.getAmount() : request.amount;
        // int price2 = request.price == null ? csd1.getPrice() : request.price;
        boolean updateFlag = false;
        if (request.amount != null) {
            csd1.setAmount(request.amount);

        }
        if (request.status != null) {
            csd1.setStatus(request.status);
        }
        if (request.mode != null) {
            csd1.setMode(request.mode);
        }
        if (request.cnt != null) {
            csd1.setCnt(request.cnt);
        }
        if (request.uvCnt != null) {
            csd1.setUvCnt(request.uvCnt);
        }
        if (request.price != null) {
            csd1.setPrice(request.price);

        }
        int cnt = csd1.getMode() == 4 ? csd1.getUvCnt() : csd1.getCnt();
        csd1.setExpense(csd1.getPrice() * cnt);
        int newBalance = csd1.getAmount() + balance - csd1.getPrice() * cnt;
        csd1.setBalance(newBalance);
        csd1.setCreationDate(csd1.getCreationDate());
        csd1.setUpdateDate(DateUtil.getCurrentTimeStamp());
        if (request.status != null && request.status == 1) {
            csd1.setConfirmDate(DateUtil.getCurrentTimeStamp());
            csd1.setConfrimAccountId(this.account.getAccountId());
        }
        int change = newBalance - newbalance;
        if (change != 0) {
            updateFlag = true;
        }
        csdService.updateChannelStatisticDaily(csd1, updateFlag, change);
        insertFinOpLog(request, channelCode, this.account.getAccountId(), statisticDate);
        logger.debug("<statisticStatus()");
    }

    private void insertFinOpLog(Request req, String channelCode, Integer accountId, String statisticDate) {
        FinOpLog finOpLog = new FinOpLog();
        finOpLog.setSourceId(channelCode);
        finOpLog.setAccountId(accountId);
        finOpLog.setType(FinOpLog.CHANNEL_FLAG);
        finOpLog.setParams("statisticDate:" + statisticDate + ",cnt:" + req.cnt + ",price:" + req.price + ",amount:"
            + req.amount + ",status:" + req.status + ",mode:" + req.mode + ",uvCnt:" + req.uvCnt);
        finOpLog.setOpDate(DateUtil.getCurrentTimeStamp());
        finOpLogService.insertFinOpLog(finOpLog);
    }

    public void getStatisticByChannelCode(String sourcePhoneNumber, String passWord, String startDate, String endDate) {
        logger.debug(">getStatisticByChannelCode()");
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        if (StringUtil.isEmpty(endDate)) {
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        }
        Channel channel = channelService.checkPhoneNumberAndPassword(sourcePhoneNumber, passWord);
        this.response.statistic = csdService.selectChannelStatistic(channel.getChannelCode(), startDate, endDate);
        logger.debug("<getStatisticByChannelCode()");
    }

    public void updateStatisticDailies(Request req) {
        logger.debug(">updateStatisticDailies()");
        if (null != req && null != req.channelStatisticDailies && req.channelStatisticDailies.size() > 0) {
            csdService.updateChannelStatisticDailyList(req.channelStatisticDailies);
        }
        logger.debug("<updateStatisticDailies()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private String statisticDate;

        private List<Channels> channels;

        private Channel channel;

        private ChannelAccountInfo channelAccountInfo;

        private List<ChannelStatisticDaily> statistic;

        private List<ChannelStatisticGroupName> statisticGroupName;

        public String getStatisticDate() {
            return statisticDate;
        }

        public List<Channels> getChannels() {
            return channels;
        }

        public Channel getChannel() {
            return channel;
        }

        public ChannelAccountInfo getChannelAccountInfo() {
            return channelAccountInfo;
        }

        public List<ChannelStatisticDaily> getStatistic() {
            return statistic;
        }

        public List<ChannelStatisticGroupName> getStatisticGroupName() {
            return statisticGroupName;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public Integer status;
        public Integer price;
        public Integer amount;
        public Integer mode;
        public Integer uvCnt;
        public Integer cnt;
        public List<ChannelStatisticDaily> channelStatisticDailies;
    }

}
