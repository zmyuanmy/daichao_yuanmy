package com.jbb.mgt.core.dao;

import java.sql.Timestamp;

public interface ChuangLanDao {

    // 新增短信回调报告
    void insertMsgReport(String msgid, String reportTime, String mobile, String status, String notifyTime,
        String statusDesc, String uid, int length);

    // 新增短信日志记录
    void insertMessageLog(String msgid, String phoneNumber, String channelCode, String remoteAddress);

}
