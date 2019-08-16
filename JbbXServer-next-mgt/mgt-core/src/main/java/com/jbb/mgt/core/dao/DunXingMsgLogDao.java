package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.DunXingMsgLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DunXingMsgLogDao {

    void insertDunXingMsgLog(DunXingMsgLog dunXingMsgLog);

    DunXingMsgLog selectDunXingMsgLogById(String orderId);

    void updateDunXingMsgLog(String orderId,String sendStatus,String errorMsg);

    boolean checkExistPhoneNumber(String phoneNumber);

    List<DunXingMsgLog> selectDunxingMsgLogs(String date,int accountId,String status,String phoneNumber);

    DunXingMsgLog selectLastDunXingMsgLogByPhoneNumber(String phoneNumber);
}
