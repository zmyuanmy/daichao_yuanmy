package com.jbb.server.core.service;

import com.jbb.mgt.core.domain.MessageCode;

public interface MessageCodeService {

	void sendMsgCode(String phoneNumber, String channelCode);
	
	MessageCode selectMessageCode(String phoneNumber, String channelCode);
}
