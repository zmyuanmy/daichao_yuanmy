package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.DunXingMsgLog;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface DunXingMsgLogMapper {

    void insertDunXingMsgLog(DunXingMsgLog dunXingMsgLog);

    DunXingMsgLog selectDunXingMsgLogById(@Param("orderId") String orderId);

    void updateDunXingMsgLog(@Param("orderId") String orderId,@Param("sendStatus") String sendStatus,@Param("errorMsg") String errorMsg);

    int checkExistPhoneNumber(@Param("phoneNumber") String phoneNumber);

    List<DunXingMsgLog> selectDunxingMsgLogs(@Param("startDate") Timestamp startDate,@Param("endDate") Timestamp endDate,@Param("accountId") int accountId,@Param("status") String status,@Param("phoneNumber") String phoneNumber);

    DunXingMsgLog selectLastDunXingMsgLogByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
