package com.jbb.mgt.rs.action.xjlVintage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelAccountInfo;
import com.jbb.mgt.core.domain.ChannelStatisticDaily;
import com.jbb.mgt.core.domain.Channels;
import com.jbb.mgt.core.domain.LoanStatistic;
import com.jbb.mgt.core.service.LoanStatisticService;
import com.jbb.mgt.rs.action.xjlPay.XjlPayBankAction.Response;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@Service(XjlVintageAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlVintageAction extends BasicAction {
    public static final String ACTION_NAME = "XjlVintageAction";

    private static Logger logger = LoggerFactory.getLogger(XjlVintageAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private LoanStatisticService loanStatisticService;

    public void getVintage(String startDate, String endDate) {
        logger.debug(">getVintageAction()");
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        Timestamp endDateTs = null;
        if (StringUtil.isEmpty(endDate)) {
            long endDatel = DateUtil.getTodayStartTs() + DateUtil.DAY_MILLSECONDES;
            endDateTs = new Timestamp(endDatel);
        } else {
            endDateTs = Util.parseTimestamp(endDate, TimeZone.getDefault());
        }
        Timestamp timestampTs = Util.parseTimestamp(startDate, TimeZone.getDefault());

        response.loanStatistic
            = loanStatisticService.getLoanStatistics(timestampTs, endDateTs, this.account.getOrgId());
        logger.debug(">getVintageAction()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<LoanStatistic> loanStatistic;

        public List<LoanStatistic> getLoanStatistic() {
            return loanStatistic;
        }

    }

}
