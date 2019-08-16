package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

public interface ChuangLanMapper {

    // 新增短信回调报告
    void insertMsgReport(@Param("msgid") String msgid, @Param("reportTime") String reportTime,
        @Param("mobile") String mobile, @Param("status") String status, @Param("notifyTime") String notifyTime,
        @Param("statusDesc") String statusDesc, @Param("uid") String uid, @Param("length") int length);

    // 新增短信日志记录
    void insertMessageLog(@Param("msgid") String msgid, @Param("phoneNumber") String phoneNumber,
        @Param("channelCode") String channelCode, @Param("remoteAddress") String remoteAddress);
}
