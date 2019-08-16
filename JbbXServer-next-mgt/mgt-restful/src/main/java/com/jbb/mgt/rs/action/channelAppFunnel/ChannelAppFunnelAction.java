package com.jbb.mgt.rs.action.channelAppFunnel;

import java.util.List;

import com.jbb.mgt.core.domain.ChannelAppFunnel;
import com.jbb.mgt.core.service.ChannelAppFunnelService;
import com.jbb.server.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.ChannelFunnel;
import com.jbb.mgt.core.service.ChannelFunnelService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;

@Service(ChannelAppFunnelAction.ACTION_NAME)
@Scope()
public class ChannelAppFunnelAction extends BasicAction {

    public static final String ACTION_NAME = "ChannelAppFunnelAction";

    private static Logger logger = LoggerFactory.getLogger(ChannelAppFunnelAction.class);
    private Response response;
    @Autowired
    private ChannelAppFunnelService channelAppFunnelService;

    @Override
    protected ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getChannelFunnelDaily(String[] channelCodes, String groupName, String startDate, String endDate,
        Integer salesId) {
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }
        String[] newchannelCodes = channelCodes.length == 0 ? null : channelCodes;
        if (StringUtil.isEmpty(groupName)) {
            groupName = null;
        } else {
            if (groupName.equals("all")) {
                groupName = null;
            } else if (groupName.equals("undefined")) {
                groupName = "undefined";
            }
        }
        this.response.channelAppFunnels
            = channelAppFunnelService.getChannelAppFunnelDaily(newchannelCodes, groupName, startDate, endDate, salesId);
    }

    public void getChannelAppFunnelCompare(String startDate, String endDate, Integer salesId) {
        if (StringUtil.isEmpty(startDate)) {
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "startDate");
        }

        this.response.channelAppFunnels
            = channelAppFunnelService.getChannelAppFunnelCompare(startDate, endDate, salesId);
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        private List<ChannelAppFunnel> channelAppFunnels;

        public List<ChannelAppFunnel> getChannelAppFunnels() {
            return channelAppFunnels;
        }
    }

}
