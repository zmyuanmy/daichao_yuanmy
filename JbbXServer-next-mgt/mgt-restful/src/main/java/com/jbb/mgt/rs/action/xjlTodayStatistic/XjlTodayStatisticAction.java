package com.jbb.mgt.rs.action.xjlTodayStatistic;

import java.sql.Timestamp;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.TodayStatistic;
import com.jbb.mgt.core.service.TodayStatisticService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.shared.rs.Util;

@Service(XjlTodayStatisticAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlTodayStatisticAction extends BasicAction {
    public static final String ACTION_NAME = "XjlTodayStatisticAction";

    private static Logger logger = LoggerFactory.getLogger(XjlTodayStatisticAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private TodayStatisticService todayStatisticService;

    public void getTodayStatistic(String startDate, String endDate) {
        logger.debug(">getTodayStatistic()");
        Timestamp timestampTs = Util.parseTimestamp(startDate, TimeZone.getDefault());
        Timestamp endDateTs = Util.parseTimestamp(endDate, TimeZone.getDefault());
        this.response.todayStatistic
            = todayStatisticService.getTodayStatistic(timestampTs, endDateTs, this.account.getOrgId());
        logger.debug(">getTodayStatistic()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private TodayStatistic todayStatistic;

        public TodayStatistic getTodayStatistic() {
            return todayStatistic;
        }

    }

}
