package com.jbb.server.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbb.server.common.util.DateUtil;
import com.jbb.server.common.util.HttpUtil;
import com.jbb.server.common.util.HttpUtil.HttpResponse;
import com.jbb.server.core.dao.ClubUsersDao;
import com.jbb.server.core.domain.ClubMofang;
import com.jbb.server.core.domain.ClubUser;
import com.jbb.server.core.service.ClubMofangService;

@Service("ClubMofangService")
public class ClubMofangServiceImpl implements ClubMofangService {
	private static Logger logger = LoggerFactory.getLogger(ClubMofangServiceImpl.class);

	private static final String CLUB_CRATETASK = "https://api.shujumohe.com/octopus/task.unify.create/v3";
	private static final String CLUB_SENDPWD = "https://api.shujumohe.com/octopus/yys.unify.acquire/v3";

	private static final String LICENSEKEY = "?partner_code=jbb_mohe&partner_key=ded3095bfd664806ae2145f9e70a7387";
	private static final String CHANNELCODE = "100000";
	private static final String CHANNELTYPE = "YYS";

	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@Autowired
	private ClubUsersDao clubUsersDao;

	@Override
	public boolean postAuthorizationData(String realName, String identityCode, String userMobile, String userPass) {

		logger.debug(">postAuthorizationData");

		String url = CLUB_CRATETASK + LICENSEKEY;

		try {

			logger.debug("url=" + url);

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("channel_code=");
			stringBuilder.append(CHANNELCODE);
			stringBuilder.append("&channel_type=");
			stringBuilder.append(CHANNELTYPE);
			stringBuilder.append("&real_name=");
			stringBuilder.append(realName);
			stringBuilder.append("&identity_code=");
			stringBuilder.append(identityCode);
			stringBuilder.append("&user_mobile=");
			stringBuilder.append(userMobile);
			String data = stringBuilder.toString();
			logger.debug("url=" + url + ", date=" + data);

			HttpResponse response = HttpUtil.sendDataViaHTTPRepeat(HttpUtil.HTTP_POST, url,
					HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED, data, null, "createTask");

			if (response.getResponseCode() == HttpUtil.STATUS_OK) {
				ClubMofang clubMofang = mapper.readValue(new String(response.getResponseBody()), ClubMofang.class);

				if (clubMofang.getCode().equals("0")) {
					if (postAuthorizationPaw(realName, identityCode, clubMofang.getTask_id(), userMobile, userPass) ) {
						return true;
					}else {
						return false;
					}
				} else {
					logger.debug("response = " + clubMofang.getCode());
					return false;
				}

			} else {
				logger.debug("response code = " + response.getResponseCode());
			}

		} catch (Exception e) {
			logger.error("postAuthorizationData error", e);
		}
		logger.debug("<postAuthorizationData");
		return true;
	}

	private boolean postAuthorizationPaw(String realName, String identityCode, String task_id, String userMobile, String userPass) {
		logger.debug(">postAuthorizationPaw");

		String url = CLUB_SENDPWD + LICENSEKEY;

		try {

			logger.debug("url=" + url);

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("task_id=");
			stringBuilder.append(task_id);
			stringBuilder.append("&user_name=");
			stringBuilder.append(userMobile);
			stringBuilder.append("&user_pass=");
			stringBuilder.append(userPass);
			stringBuilder.append("&task_stage=");
			stringBuilder.append("INIT");
			stringBuilder.append("&request_type=");
			stringBuilder.append("submit");
			String data = stringBuilder.toString();
			logger.debug("url=" + url + ", date=" + data);
			

			HttpResponse response = HttpUtil.sendDataViaHTTPRepeat(HttpUtil.HTTP_POST, url,
					HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED, data, null, "createTask");

			if (response.getResponseCode() == HttpUtil.STATUS_OK) {
				ClubMofang clubMofang = mapper.readValue(new String(response.getResponseBody()), ClubMofang.class);

				if (clubMofang.getCode().equals("100")) {
					if (postAuthorizationResult(realName, identityCode, task_id, userMobile, userPass, 12) ) {
						
						return true;
					}else {
						return false;
					}
				}else {
					return false;
				}

			}
		} catch (Exception e) {
			logger.error("postAuthorizationPaw error", e);
		}
		logger.debug("<postAuthorizationPaw");
		return true;
	}
	
	private boolean postAuthorizationResult(String realName, String identityCode, String task_id, String userMobile, String userPass, int count) {
		logger.debug(">postAuthorizationResult");

		String url = CLUB_SENDPWD + LICENSEKEY;

		try {


			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("task_id=");
			stringBuilder.append(task_id);
			stringBuilder.append("&user_name=");
			stringBuilder.append(userMobile);
			stringBuilder.append("&user_pass=");
			stringBuilder.append(userPass);
			stringBuilder.append("&task_stage=");
			stringBuilder.append("INIT");
			stringBuilder.append("&request_type=");
			stringBuilder.append("query");
			String data = stringBuilder.toString();
			logger.debug("url=" + url + ", date=" + data);
			HttpResponse response = HttpUtil.sendDataViaHTTPRepeat(HttpUtil.HTTP_POST, url,
					HttpUtil.CONTENT_TYPE_X_WWW_FORM_URLENCODED, data, null, "createTask");

			if (response.getResponseCode() == HttpUtil.STATUS_OK) {
				ClubMofang clubMofang = mapper.readValue(new String(response.getResponseBody()), ClubMofang.class);
				if (clubMofang.getCode().equals("137") ||clubMofang.getCode().equals("2007")  ) {
					ClubUser clubUser = new ClubUser();
					clubUser.setMobile(userMobile);
					clubUser.setIdcardNo(identityCode);
					clubUser.setCreationDate(DateUtil.getCurrentTimeStamp());
					clubUser.setUpdateDate(DateUtil.getCurrentTimeStamp());
					clubUser.setServerPass(userPass);
					clubUser.setUserName(realName);
					clubUsersDao.insertUser(clubUser);
//					
					return true;	
				}else if( clubMofang.getCode().equals("100"))
				{
					if (count == 0)
						return false;
					Thread.sleep(1000);
					postAuthorizationResult(realName, identityCode, task_id, userMobile, userPass, count-1) ;			
				}

			}
		} catch (Exception e) {
			logger.error("postAuthorizationResult error", e);
		}
		logger.debug("<postAuthorizationResult");
		return true;
	}

}
