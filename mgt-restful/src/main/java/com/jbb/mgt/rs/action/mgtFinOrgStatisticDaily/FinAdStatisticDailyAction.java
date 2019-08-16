package com.jbb.mgt.rs.action.mgtFinOrgStatisticDaily;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.FinOrgAdDelStatisticDaily;
import com.jbb.mgt.core.service.FinAdStatisticDailyService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;

@Service(FinAdStatisticDailyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FinAdStatisticDailyAction extends BasicAction {

    public static final String ACTION_NAME = "FinAdStatisticDailyAction";

    private static Logger logger = LoggerFactory.getLogger(FinAdStatisticDailyAction.class);

    private Response response;
    @Autowired
    private FinAdStatisticDailyService finAdStatisticDailyService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getFinAdStatisticDaily(Integer salesId, String startDate, String endDate) {
        logger.debug(">getFinAdStatisticDaily()");
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        this.response.statistic = finAdStatisticDailyService.selectFinAdStatisticDaily(salesId, startDate, endDate);
        logger.debug("<getFinAdStatisticDaily()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public List<FinOrgAdDelStatisticDaily> statistic;

        public List<FinOrgAdDelStatisticDaily> getStatistic() {
            return statistic;
        }

    }
}
