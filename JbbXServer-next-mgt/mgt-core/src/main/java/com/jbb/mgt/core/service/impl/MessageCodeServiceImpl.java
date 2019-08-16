 package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.MessageCodeDao;
import com.jbb.mgt.core.domain.MessageCode;
import com.jbb.mgt.core.service.MessageCodeService;

@Service("MessageCodeService")
public class MessageCodeServiceImpl implements  MessageCodeService{

    @Autowired
    private MessageCodeDao messageCodeDao;
    
    @Override
    public MessageCode getMessageCode(String phoneNumber, String channelCode) {
       
         return messageCodeDao.selectMessageCode(phoneNumber, channelCode);
    }

    
}
