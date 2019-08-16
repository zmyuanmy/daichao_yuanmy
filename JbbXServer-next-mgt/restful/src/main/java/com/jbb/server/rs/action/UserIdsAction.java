package com.jbb.server.rs.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbb.server.core.domain.UserBasic;
import com.jbb.server.core.domain.User;
import com.jbb.server.core.service.UserIdsService;
import com.jbb.server.rs.pojo.ActionResponse;

@Service(UserIdsAction.ACTION_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserIdsAction extends BasicAction {

	private static Logger logger = LoggerFactory.getLogger(UserIdsAction.class);

	public static final String ACTION_NAME = "UserIdsAction";

	private Response response;

	@Autowired
	private UserIdsService userIdsService;

	@Override
	protected ActionResponse makeActionResponse() {
		return response = new Response();
	}
	


	public void getUserHeadAndNickName(List<Integer> userIds) {
		logger.debug(">getUserHeadAndNickName()");
		
		if(userIds == null || userIds.size() == 0){
		    logger.debug("<getUserHeadAndNickName(), user list is empty.");
		    return;
		}
		
		List<User> users = userIdsService.getUserHeadAndNickName(userIds);
		List<UserBasic> relist = new ArrayList<UserBasic>();
        for(int i=0;i<users.size();i++){
            UserBasic reUser = new UserBasic(users.get(i));
            relist.add(reUser);
        }
        this.response.users= relist;
		logger.debug("<getUserHeadAndNickName()");
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Response extends ActionResponse {
	
		private List<UserBasic>  users;

		public List<UserBasic>  getUsers() {
			return users;
		}

	}

}
