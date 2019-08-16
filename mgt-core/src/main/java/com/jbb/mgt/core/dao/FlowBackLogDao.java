package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.FlowBackLog;

import java.sql.Timestamp;

public interface FlowBackLogDao {

    void insertFlowBackLog(FlowBackLog flowBackLog);

    boolean checkFlowBackExist(Timestamp startDate,Timestamp endDate,int userId);
}
