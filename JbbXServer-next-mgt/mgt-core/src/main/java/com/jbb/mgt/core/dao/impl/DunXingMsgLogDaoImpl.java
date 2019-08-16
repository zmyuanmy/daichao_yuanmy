package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.DunXingMsgLogDao;
import com.jbb.mgt.core.dao.mapper.DunXingMsgLogMapper;
import com.jbb.mgt.core.domain.DunXingMsgLog;
import com.jbb.server.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository("DunXingMsgLogDao")
public class DunXingMsgLogDaoImpl implements DunXingMsgLogDao {

    @Autowired
    private DunXingMsgLogMapper dunXingMsgLogMapper;

    @Override
    public void insertDunXingMsgLog(DunXingMsgLog dunXingMsgLog) {
        dunXingMsgLogMapper.insertDunXingMsgLog(dunXingMsgLog);
    }

    @Override
    public DunXingMsgLog selectDunXingMsgLogById(String orderId) {
        return dunXingMsgLogMapper.selectDunXingMsgLogById(orderId);
    }

    @Override
    public void updateDunXingMsgLog(String orderId, String sendStatus,String errorMsg) {
        dunXingMsgLogMapper.updateDunXingMsgLog(orderId, sendStatus,errorMsg);
    }

    @Override
    public boolean checkExistPhoneNumber(String phoneNumber) {
        return dunXingMsgLogMapper.checkExistPhoneNumber(phoneNumber) > 0;
    }

    @Override
    public List<DunXingMsgLog> selectDunxingMsgLogs(String date, int accountId, String status,String phoneNumber) {
        Timestamp startDate = DateUtil.parseStrnew(date+" 00:00:00");
        Timestamp endDate = new Timestamp(startDate.getTime()+DateUtil.DAY_MILLSECONDES);
        return dunXingMsgLogMapper.selectDunxingMsgLogs(startDate,endDate, accountId,status,"%"+phoneNumber);
    }

    @Override public DunXingMsgLog selectLastDunXingMsgLogByPhoneNumber(String phoneNumber) {
        return dunXingMsgLogMapper.selectLastDunXingMsgLogByPhoneNumber(phoneNumber);
    }
}
