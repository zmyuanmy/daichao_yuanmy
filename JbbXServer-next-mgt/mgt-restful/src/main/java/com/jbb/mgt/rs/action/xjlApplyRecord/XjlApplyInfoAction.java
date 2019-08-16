package com.jbb.mgt.rs.action.xjlApplyRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.XjlApplyRecord;
import com.jbb.mgt.core.service.XjlApplyRecordService;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;

@Service(XjlApplyInfoAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlApplyInfoAction extends BasicAction {
    public static final String ACTION_NAME = "XjlApplyInfoAction";

    private static Logger logger = LoggerFactory.getLogger(XjlApplyInfoAction.class);

    private Response response;

    @Autowired
    private XjlApplyRecordService service;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }

    public void getXjlApplyRecord(String applyId) {
        logger.debug(">getXjlApplyRecord()");
        this.response.xjlApplyRecord = service.getXjlApplyRecordByApplyId(applyId, this.account.getOrgId(), false);
        logger.debug("<getXjlApplyRecord()");
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {

        private XjlApplyRecord xjlApplyRecord;

        public XjlApplyRecord getXjlApplyRecord() {
            return xjlApplyRecord;
        }

    }
}
