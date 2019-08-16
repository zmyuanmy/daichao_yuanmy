package com.jbb.mgt.rs.action.platformUv;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.PlatformUv;
import com.jbb.mgt.core.service.UserEventLogService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.shared.rs.Util;

/**
 * @author ThinkPad
 * @date 2019/03/14
 */

@Service(PlatformUvAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PlatformUvAction extends BasicAction {
    public static final String ACTION_NAME = "PlatformUvAction";

    private static Logger logger = LoggerFactory.getLogger(PlatformUvAction.class);
    @Autowired
    private UserEventLogService userEventLogService;

    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getPlatformUv(Integer salesId, String groupName, Integer platformId, String startDate, String endDate) {
        logger.debug(">getPlatformUv() salesId=" + salesId + " groupName=" + groupName + " platformId=" + platformId
            + " startDate=" + startDate + " endDate=" + endDate);

        this.response.platformUvs
            = userEventLogService.getPlatformUv(salesId, groupName, platformId, startDate, endDate);

        logger.debug("<getPlatformUv()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        public List<PlatformUv> platformUvs;

    }
}
