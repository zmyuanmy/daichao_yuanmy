package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.MessageCode;

public interface MessageCodeMapper {
    int saveMessageCode(@Param("phoneNumber") String phoneNumber, @Param("channelCode") String channelCode, @Param("msgCode") String msgCode,
        @Param("creationDate") Timestamp creationDate, @Param("expireDate") Timestamp expireDate);

    int checkMsgCode(@Param("phoneNumber") String phoneNumber, @Param("channelCode") String channelCode, @Param("msgCode") String msgCode,
        @Param("date") Timestamp date);

    MessageCode selectMessageCode(@Param("phoneNumber") String phoneNumber, @Param("channelCode") String channelCode);
}
