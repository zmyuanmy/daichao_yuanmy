package com.jbb.mgt.rs.action.xjtQzReport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Service(XjtQzReportAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjtQzReportAction extends BasicAction {
    public static final String ACTION_NAME = "XjtQzReportAction";

    private static Logger logger = LoggerFactory.getLogger(XjtQzReportAction.class);

    private Response response;

    public void qzReportNotify(Request req) {
        System.out.println(req.token);
        System.out.println(req.taskId);
        System.out.println(req.apiUser);
        System.out.println(req.apiEnc);
        System.out.println(req.apiName);
        System.out.println(req.success);
        System.out.println(req.type);
        logger.info(req.token + "=====" + req.taskId + "=====" + req.apiUser + "====" + req.apiEnc + "===="
            + req.apiName + "===" + req.success + "====" + req.type);

    }

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Request {
        public String taskId;
        public String token;
        public String apiUser;
        public String apiEnc;
        public String apiName;
        public String success;
        public String type;
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private XjlApplyRecord xjlApplyRecord;

        public XjlApplyRecord getXjlApplyRecord() {
            return xjlApplyRecord;
        }

    }

}
