package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.MessageCodeDao;
import com.jbb.mgt.core.dao.mapper.MessageCodeMapper;
import com.jbb.mgt.core.domain.MessageCode;

@Repository("MessageCodeDao")
public class MessageCodeDaoImpl implements MessageCodeDao {

    @Autowired
    private MessageCodeMapper mapper;

    @Override
    public int saveMessageCode(String phoneNumber, String channelCode, String msgCode, Timestamp creationDate, Timestamp expireDate) {
        return mapper.saveMessageCode(phoneNumber, channelCode, msgCode, creationDate, expireDate);

    }

    @Override
    public boolean checkMsgCode(String phoneNumber, String channelCode, String msgCode, Timestamp date) {
        return mapper.checkMsgCode(phoneNumber, channelCode, msgCode, date) > 0;
    }

    @Override
    public MessageCode selectMessageCode(String phoneNumber, String channelCode) {
         return mapper.selectMessageCode(phoneNumber, channelCode);
    }
    
    

}
