package com.jbb.mgt.core.service.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jbb.mgt.core.domain.SmsSendRequest;
import com.jbb.mgt.core.domain.SmsSendResponse;
import com.jbb.mgt.core.service.SendMsgToOpService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.ChuangLanSmsUtil;
import com.jbb.server.common.util.CommonUtil;
import com.jbb.server.common.util.StringUtil;

@Service("SendMsgToOpService")
public class SendMsgToOpServiceImpl implements SendMsgToOpService {
    private static Logger logger = LoggerFactory.getLogger(ChuangLanServiceImpl.class);

    @Override
    public void sendMsgToOp(String channelInfo, String phoneNumber) {
        String[] opMobiles = PropertyManager.getProperties("jbb.mgt.channel.op.mobiles");
        
        
        if (!StringUtil.isEmpty(phoneNumber) && !CommonUtil.inArray(phoneNumber, opMobiles)) {
            opMobiles = ArrayUtils.add(opMobiles, phoneNumber);

        }
        
        if(opMobiles == null || opMobiles.length == 0) {
            return;
        }
        
        for (int i = 0; i < opMobiles.length; i++) {
            sendMsgCode(opMobiles[i], channelInfo);
        }

    }

    private void sendMsgCode(String phoneNumber, String channelInfo) {

        String account = PropertyManager.getProperty("jbb.mgt.msgcode.api.account");
        String pswd = PropertyManager.getProperty("jbb.mgt.msgcode.api.pswd");
        String smsSingleRequestServerUrl = PropertyManager.getProperty("jbb.mgt.msgcode.api.smsSingleRequestServerUrl");
        String CodeTemplate = PropertyManager.getProperty("jbb.mgt.msgToOp.api.template");
        String msg = StringUtil.replace(CodeTemplate, "CodeTemplate", channelInfo);
        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phoneNumber);
        String requestJson = JSON.toJSONString(smsSingleRequest);
        try {
            String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
            SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
            if (smsSingleResponse.getCode() != null && "0".equals(smsSingleResponse.getCode())) {
                return;

            } else {
                logger.warn(smsSingleResponse.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

}