/**
 * 
 */
package com.jbb.mgt.rs.action.userComplaint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.mgt.core.domain.UserComplaint;
import com.jbb.mgt.core.service.UserComplaintService;
import com.jbb.mgt.rs.action.account.AccountAction.Response;
import com.jbb.mgt.server.rs.action.BasicAction;
import com.jbb.mgt.server.rs.pojo.ActionResponse;
import com.jbb.server.common.exception.MissingParameterException;
import com.jbb.server.common.util.StringUtil;

/**
 * @author zcp
 *
 *         2019年1月25日 上午11:22:41
 */

@Service(UserComplaintAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserComplaintAction extends BasicAction {

	public static final String ACTION_NAME = "UserComplaintAction";

	private static Logger logger = LoggerFactory.getLogger(UserComplaintAction.class);

	private Response response;

	@Override
	protected ActionResponse makeActionResponse() {
		return this.response = new Response();
	}

	@Autowired
	private UserComplaintService userComplaintService;

	public void insertUserComplaint(String appName, String content) {
		logger.debug(">insertUserComplaint");
		if (StringUtil.isEmpty(content)) {
			throw new MissingParameterException("jbb.error.exception.missing.param", "zh", "content");
		}
		UserComplaint userComplaint = new UserComplaint(this.user.getUserId(), content, appName);
		userComplaintService.insertUserComplaint(userComplaint);
		logger.debug("<insertUserComplaint");
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Request {
		public String content;

	}

}
