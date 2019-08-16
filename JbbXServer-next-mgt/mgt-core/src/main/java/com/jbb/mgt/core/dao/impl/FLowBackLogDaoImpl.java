package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.FlowBackLogDao;
import com.jbb.mgt.core.dao.mapper.FlowBackLogMapper;
import com.jbb.mgt.core.domain.FlowBackLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository("FlowBackLogDao")
public class FLowBackLogDaoImpl implements FlowBackLogDao {

    @Autowired
    private FlowBackLogMapper flowBackLogMapper;

    @Override
    public void insertFlowBackLog(FlowBackLog flowBackLog) {
        flowBackLogMapper.insertFlowBackLog(flowBackLog);
    }

    @Override
    public boolean checkFlowBackExist(Timestamp startDate, Timestamp endDate, int userId) {
        return flowBackLogMapper.checkFlowBackExist(startDate, endDate, userId) > 0;
    }
}
