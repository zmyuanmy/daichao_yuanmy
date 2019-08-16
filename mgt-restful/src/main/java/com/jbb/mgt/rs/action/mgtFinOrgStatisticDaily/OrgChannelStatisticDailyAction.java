package com.jbb.mgt.rs.action.mgtFinOrgStatisticDaily;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.OrgChannelStatisticDaily;
import com.jbb.mgt.core.service.OrgChannelStatisticDailyService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;

@Service(OrgChannelStatisticDailyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrgChannelStatisticDailyAction extends BasicAction {

    public static final String ACTION_NAME = "OrgChannelStatisticDailyAction";

    private static Logger logger = LoggerFactory.getLogger(OrgChannelStatisticDailyAction.class);

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @Autowired
    private OrgChannelStatisticDailyService orgChannelStatisticDailyService;

    public void getFinOrgStatisticDaily(Integer type, Integer salesId, String startDate, String endDate) {
        logger.debug(">getFinOrgStatisticDaily");
        if (StringUtil.isEmpty(startDate)) {
            throw new WrongParameterValueException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        this.response.statictic
            = orgChannelStatisticDailyService.selectFinOrgStatisticDaily(type, salesId, startDate, endDate);
        logger.debug("<getFinOrgStatisticDaily");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<OrgChannelStatisticDaily> statictic;

        public List<OrgChannelStatisticDaily> getStatictic() {
            return statictic;
        }

    }
}
