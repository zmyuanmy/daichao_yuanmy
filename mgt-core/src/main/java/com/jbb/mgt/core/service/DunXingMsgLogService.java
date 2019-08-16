package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.DunXingMsgLog;

import java.util.List;

public interface DunXingMsgLogService {

    void sendMsgCode(int accountId,String phoneNumber,String inviteCode,String msgModeId);

    void insertDunXingMsgLog(DunXingMsgLog dunXingMsgLog);

    DunXingMsgLog selectDunXingMsgLogById(String orderId);

    void updateDunXingMsgLog(String orderId, String sendStatus,String errorMsg);

    boolean checkExistPhoneNumber(String phoneNumber);

    List<DunXingMsgLog> selectDunxingMsgLogs(String date,int accountId,String status,String phoneNumber);

    void sendMsgCodeNew(int accountId,String phoneNumber,String inviteCode);

    DunXingMsgLog selectLastDunXingMsgLogByPhoneNumber(String phoneNumber);

    void sendMiaoDiMsgCode(int accountId,String phoneNumber,String inviteCode);
}
