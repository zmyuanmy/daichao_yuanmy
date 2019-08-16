package com.jbb.mgt.proc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.domain.mapper.Mapper;
import com.jbb.mgt.core.domain.mq.RegisterEvent;
import com.jbb.mgt.server.core.util.RegisterEventUtil;
import com.jbb.server.mq.MessageListener;

public class UserRegisterEventRemoveListener implements MessageListener {

	private static Logger logger = LoggerFactory.getLogger(UserRegisterEventRemoveListener.class);

	
	@Override
	public void onMessage(byte[] messageBody) {

		RegisterEvent event = Mapper.readRegisterEvent(messageBody);
		logger.debug(">onMessage, event = " + event);

		if (event == null) {
			logger.debug("parse event error, event is null = " + new String(messageBody));
			return;
		}
		
		RegisterEventUtil.removeSendTask(event);
		
		logger.debug("<onMessage");
	}

}
