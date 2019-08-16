package com.jbb.mgt.core.dao;

import java.sql.Timestamp;

import com.jbb.mgt.core.domain.MessageCode;

public interface MessageCodeDao {

    int saveMessageCode(String phoneNumber, String channelCode, String msgCode, Timestamp creationDate,
        Timestamp expireDate);

    boolean checkMsgCode(String phoneNumber, String channelCode, String msgCode, Timestamp date);
    
    MessageCode selectMessageCode(String phoneNumber, String channelCode);

}
