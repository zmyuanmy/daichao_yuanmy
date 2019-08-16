package com.jbb.mgt.rs.action.channelUser;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.UserLoginPuv;
import com.jbb.mgt.core.service.ChannelUserService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;

@Service(ChannelUserAction.ACTION_NAME)
@Scope()
public class ChannelUserAction extends BasicAction {

    public static final String ACTION_NAME = "ChannelUserAction";

    private static Logger logger = LoggerFactory.getLogger(ChannelUserAction.class);
    
    private Response response;
    @Autowired
    private ChannelUserService channelUserService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getChannelUserDaily(String channelCode, String startDate, String endDate) {
        logger.debug(">getChannelUserDaily()");
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        if (StringUtil.isEmpty(channelCode)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "channelCode");
        }
        // String[] newchannelCodes = channelCodes.length == 0 ? null : channelCodes;
        this.response.UserLoginPuvs = channelUserService.getChannelUserDaily(channelCode, startDate, endDate);
        logger.debug("<getChannelUserDaily()");

    }

    public void getChannelUserCompare(String statisticDate) {
        logger.debug(">getChannelUserCompare()");
        if (StringUtil.isEmpty(statisticDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "statisticDate");
        }
        this.response.UserLoginPuvs = channelUserService.getChannelUserCompare(statisticDate);
        logger.debug("<getChannelUserCompare()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<UserLoginPuv> UserLoginPuvs;

        public List<UserLoginPuv> getUserLoginPuvs() {
            return UserLoginPuvs;
        }

    }

}
