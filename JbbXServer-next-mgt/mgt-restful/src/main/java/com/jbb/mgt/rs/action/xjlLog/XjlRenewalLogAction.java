package com.jbb.mgt.rs.action.xjlLog;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.jbb.mgt.core.domain.XjlRenewalLog;
import com.jbb.mgt.core.service.XjlRenewalLogService;
import com.jbb.mgt.rs.action.xjlUserApply.XjlUserAction.Response;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.WrongParameterValueException;
import com.jbb.server.common.util.StringUtil;
import com.jbb.server.shared.rs.Util;

/**
 * 续期日志Action
 *
 * @author neo
 * @date 2019/1/10 10:32
 */
@Service(XjlRenewalLogAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class XjlRenewalLogAction extends BasicAction {
    public static final String ACTION_NAME = "XjlRenewalLogAction";

    private static Logger logger = LoggerFactory.getLogger(XjlRenewalLogAction.class);
   
    private Response response;

    @Override
    public ActionResponse makeActionResponse() {
        return this.response = new Response();
    }
  
    @Autowired
    private XjlRenewalLogService xjlRenewalLogService;

   

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class Response extends ActionResponse {
        public List<XjlRenewalLog> list;
      
        

    }

	public void getRenewalLog(String applyId,String renewalAmount, Integer userId, String creationDate) {
		logger.debug("<getRenewalLog");
		if (StringUtil.isEmpty(applyId)) {
	          throw new WrongParameterValueException("jbb.error.exception.wrong.paramvalue", "zh", "applyId");
	    }
		
		List<XjlRenewalLog> xjlRenewalLogList = Lists.newArrayList();
		
		Timestamp creation = Util.parseTimestamp(creationDate, getTimezone());	
		
		xjlRenewalLogList = xjlRenewalLogService.queryRenewalLogList(applyId,renewalAmount, userId, creation);
			
		this.response.list= xjlRenewalLogList;
		logger.debug(">getRenewalLog");
	}

}
