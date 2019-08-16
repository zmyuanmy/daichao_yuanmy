package com.jbb.mgt.rs.action.ChannelStaDailyAction;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.StatisticChannelDaily;
import com.jbb.mgt.core.service.StatisticChannelDailyService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;

@Service(ChannelsDailyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ChannelsDailyAction extends BasicAction {

    public static final String ACTION_NAME = "ChannelsDailyAction";

    private static Logger logger = LoggerFactory.getLogger(ChannelsDailyAction.class);

    private Response response;
    @Autowired
    private StatisticChannelDailyService statisticChannelDailyService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getChannelsDaily(String[] channelCodes, String startDate, String endDate, Integer userType) {
        logger.debug(">getChannelsDaily()");
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        String[] newchannelCodes = channelCodes.length == 0 ? null : channelCodes;
        this.response.statistics
            = statisticChannelDailyService.getChannelDailysByChannelCode(newchannelCodes, startDate, endDate, userType);
    }

    public void getChannelsCompare(String startDate, String endDate, Integer userType) {
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        this.response.statistics = statisticChannelDailyService.getChannelsCompare(startDate, endDate, userType);
        logger.debug("<getChannelsDaily()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private StatisticChannelDaily statistic;
        private List<StatisticChannelDaily> statistics;

        public StatisticChannelDaily getStatistic() {
            return statistic;
        }

        public List<StatisticChannelDaily> getStatistics() {
            return statistics;
        }

    }

}
