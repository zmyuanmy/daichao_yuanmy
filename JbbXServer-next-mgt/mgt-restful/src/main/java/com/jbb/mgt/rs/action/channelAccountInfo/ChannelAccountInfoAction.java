package com.jbb.mgt.rs.action.channelAccountInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.Channel;
import com.jbb.mgt.core.domain.ChannelAccountInfo;
import com.jbb.mgt.core.service.ChannelAccountInfoService;
import com.jbb.mgt.core.service.ChannelService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 渠道账号信息维护Action
 *
 * @author wyq
 * @date 2018/7/23 11:21
 */
@Service(ChannelAccountInfoAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ChannelAccountInfoAction extends BasicAction{

    public static final String ACTION_NAME = "ChannelAccountInfoAction";

    private static Logger logger = LoggerFactory.getLogger(ChannelAccountInfoAction.class);

    private ChannelAccountInfoAction.Response response;

    @Autowired
    private ChannelAccountInfoService service;

    @Autowired
    private ChannelService channelService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new ChannelAccountInfoAction.Response();
    }

    public void insertChannelAccountInfo(Request req){
        logger.debug(">createChannel, req={}", req);
        if (null == req){
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "channelCode");
        }
        Channel channel = channelService.getChannelByCode(req.channelCode);
        if (null == channel){
            throw new MissingParameterException("jbb.mgt.exception.channel.notFound", "zh", "channelCode");
        }
        ChannelAccountInfo cai = generateChannel(req);
        service.insertChannelAccountInfo(cai);
        logger.debug("<createChannel");
    }

    public void selectChannelAccountInfo(String channelCode){
        logger.debug(">createChannel, req={}", channelCode);
        if (null == channelCode){
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "channelCode");
        }
        ChannelAccountInfo cai = service.selectChannelAccountInfo(channelCode);
        if (null != cai){
            this.response.cai = cai;
        }
        logger.debug("<createChannel");
    }

    private ChannelAccountInfo generateChannel(Request req) {
        ChannelAccountInfo cai = new ChannelAccountInfo();
        if (null == req.channelCode){
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "channelCode");
        }else{
            cai.setChannelCode(req.channelCode);
        }
        if (null != req.name){
            cai.setName(req.name);
        }else{
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "name");
        }
        if (null != req.number){
            cai.setNumber(req.number);
        }else{
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "number");
        }
        if (null != req.bankInfo){
            cai.setBankInfo(req.bankInfo);
        }else{
            throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "bankInfo");
        }
        cai.setUpdateAccountId(this.account.getAccountId());
        return cai;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public ChannelAccountInfo cai;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String channelCode;
        public String name;
        public String number;
        public String bankInfo;
    }
}
