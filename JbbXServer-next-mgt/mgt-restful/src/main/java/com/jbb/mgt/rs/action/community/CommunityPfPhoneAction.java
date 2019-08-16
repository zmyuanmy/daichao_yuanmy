package com.jbb.mgt.rs.action.community;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.HumanLpPhone;
import com.jbb.mgt.core.service.HumanLpPhoneService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

@Service(CommunityPfPhoneAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommunityPfPhoneAction extends BasicAction {

    public static final String ACTION_NAME = "CommunityPfPhoneAction";

    private static Logger logger = LoggerFactory.getLogger(CommunityPfPhoneAction.class);

    @Autowired
    private HumanLpPhoneService humanLpPhoneService;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getCommunityPfPhone(String startDate, String endDate, Integer pageSize) {
        logger
            .debug(">getCommunityPfPhone() startDate= " + startDate + "endDate= " + endDate + "pageSize= " + pageSize);
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "startDate");
        }
        if (StringUtil.isEmpty(endDate)) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "endDate");
        }
        pageSize = pageSize == null ? 20 : pageSize;
        Timestamp startDateTs = Util.parseTimestamp(startDate, getTimezone());
        Timestamp endDateTs = Util.parseTimestamp(endDate, getTimezone());
        List<HumanLpPhone> lpPhones
            = humanLpPhoneService.getHumanLpPhoneByStatus(startDateTs, endDateTs, false, pageSize);
        this.response.humanLpPhones = lpPhones;
        logger.debug("<getCommunityPfPhone()");
    }

    public void updateHumanbyPhone(Request req) {
        logger.debug(">updateHumanbyPhone() req= " + req);
        if (req == null) {
            throw new MissingParameterException("jbb.mgt.error.exception.missing.param", "zh", "req");
        }
        if (req.phones == null || req.phones.length == 0) {
            logger.debug("updateHumanbyPhone() req.phones= " + req.phones);
            return;
        }
        humanLpPhoneService.updateHumanbyPhone(req.phones);
        logger.debug("<updateHumanbyPhone()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public List<HumanLpPhone> humanLpPhones;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String[] phones;
    }
}
