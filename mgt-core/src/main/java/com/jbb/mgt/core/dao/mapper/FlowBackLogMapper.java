package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.FlowBackLog;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

public interface FlowBackLogMapper {

    void insertFlowBackLog(FlowBackLog flowBackLog);

    int checkFlowBackExist(@Param(value = "startDate") Timestamp startDate,@Param(value = "endDate") Timestamp endDate,@Param(value = "userId") int userId);
}
