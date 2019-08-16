package com.jbb.mgt.rs.action.summaryReport;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.jboss.resteasy.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.ReportSummary;
import com.jbb.mgt.core.service.ReportSummaryService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.Constants;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@Service(SummaryReportAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SummaryReportAction extends BasicAction {

    public static final String ACTION_NAME = "SummaryReportAction";

    private static Logger logger = LoggerFactory.getLogger(SummaryReportAction.class);

    private Response response;
    @Autowired
    private ReportSummaryService reportSummaryService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getSummaryByDate(String startDate, String endDate) throws ParseException {
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        String newEndDate = new Date().toString();
        if (!StringUtil.isEmpty(endDate)) {
            newEndDate = endDate;
        }
        
        if ( !(this.isOpOrgAdmin()|| this.isOpSysAdmin() || this.isOrgAdmin() || this.isSysAdmin())) {
            // hardcord to account, not allow to access data before 2018-11-20
            Timestamp startDateTs = Util.parseTimestamp(startDate, TimeZone.getDefault());
            Timestamp s = Util.parseTimestamp("2018-11-20T00:00:00", TimeZone.getDefault());
            if (startDateTs == null || startDateTs.before(s)) {
                startDate = "2018-11-20";
            }
        }

        this.response.statistic = reportSummaryService.getSummaryByDate(startDate, newEndDate);
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<ReportSummary> statistic;

        public List<ReportSummary> getStatistics() {
            return statistic;
        }

    }

}
