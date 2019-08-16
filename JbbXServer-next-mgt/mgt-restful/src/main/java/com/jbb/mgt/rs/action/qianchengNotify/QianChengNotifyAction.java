package com.jbb.mgt.rs.action.qianchengNotify;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.service.XjlApplyRecordService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.UnsupportedEncodingException;

@Service(QianChengNotifyAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class QianChengNotifyAction extends BasicAction {

    public static final String ACTION_NAME = "QianChengNotifyAction";

    private static Logger logger = LoggerFactory.getLogger(QianChengNotifyAction.class);

    private Response response;

    @Autowired
    private XjlApplyRecordService xjlApplyRecordService;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public String notifyQianCheng(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        logger.debug(">getPropertiesByName");
        String openId = request.getParameter("open_id");
        String result = request.getParameter("result");
        String reason = new String(request.getParameter("reason").getBytes("iso-8859-1"), "utf-8");
        XjlApplyRecord xjlApplyRecord = xjlApplyRecordService.selectXjlApplyRecordByOpenId(openId);
        if(xjlApplyRecord!=null){
            logger.info("QianChengData notify result req : userId = "+xjlApplyRecord.getUserId()+"openId = "+openId+" result = "+result+" reason "+reason);
            if(result.equals("0")){
                xjlApplyRecord.setApplyStatus("100");
                xjlApplyRecord.setStatus(1);
            }
            if(result.equals("1")){
                xjlApplyRecord.setApplyStatus("2");
                xjlApplyRecord.setStatus(2);
            }
            xjlApplyRecord.setNotifyStatus("1");
            xjlApplyRecord.setApplyMsg(reason);
            xjlApplyRecordService.updateXjlApplyRecord(xjlApplyRecord);
            logger.debug("<getPropertiesByName");
            return "SUCCESS";
        }else{
            logger.debug("<getPropertiesByName");
            return null;
        }
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        public String open_id;
        public String result;
        public String reason;
    }
}
