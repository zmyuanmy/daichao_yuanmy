package com.jbb.mgt.rs.action.loanChannel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.LoanChannelStatistic;
import com.jbb.mgt.core.service.LoanChannelStatisticDailyService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;

@Service(LoanChannelAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoanChannelAction extends BasicAction {

    public static final String ACTION_NAME = "LoanChannelAction";

    private static Logger logger = LoggerFactory.getLogger(LoanChannelAction.class);

    private Response response;

    @Autowired
    private LoanChannelStatisticDailyService loanChannelStatisticDailyService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getLoanChannelStatisticCompare(String startDate, String endDate) {
        logger.debug(">getLoanChannelStatisticCompare()");
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        List<LoanChannelStatistic> loanChannelStatistics
            = loanChannelStatisticDailyService.selectLoanChannelStatisticCompare(startDate, endDate);
        this.response.statistics = loanChannelStatistics;
        logger.debug("<getLoanChannelStatisticCompare()");
    }

    public void getLoanChannelStatisticByChannelCode(String[] channelCodes, String startDate, String endDate) {
        logger.debug(">getLoanChannelStatisticByChannelCode()");
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        String[] newchannelCodes = channelCodes.length == 0 ? null : channelCodes;
        List<LoanChannelStatistic> loanChannelStatistics
            = loanChannelStatisticDailyService.selectLoanChannelStatisticByChannelCode(newchannelCodes, startDate, endDate);

        this.response.statistics = loanChannelStatistics;
        logger.debug("<getLoanChannelStatisticByChannelCode()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public List<LoanChannelStatistic> statistics;
    }
}
