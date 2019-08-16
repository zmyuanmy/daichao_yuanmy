package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.ChuangLanPhoneNumberRsp;
import com.jbb.mgt.core.domain.ChuangLanWoolCheckRsp;

public interface ChuangLanService {

    ChuangLanWoolCheckRsp woolCheck(String mobile, String ipAddress);

    ChuangLanPhoneNumberRsp validateMobile(String mobile, boolean saveData);

    void sendMsgCode(String phoneNumber, String channelCode, String signName,String remoteAddress);

    void sendMsgCodeWithContent(String phoneNumber, String content);

    // 新增短信回调报告
    void insertMsgReport(String msgid, String reportTime, String mobile, String status, String notifyTime,
        String statusDesc, String uid, int length);

    void sendMsgCodeXjlRemind(String applyId, Integer accountId, String phoneNumber, String signName, int status,
        String userName, String money, String date, String day);

    void runXjlRemind(String applyId, Integer accountId, String phoneNumber, String signName, int status,
        String userName, String money, String date, String day);

    // 新增短信日志记录
    void insertMessageLog(String msgid, String phoneNumber, String channelCode, String remoteAddress);

}
