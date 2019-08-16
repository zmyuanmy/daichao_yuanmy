package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.ChuangLanDao;
import com.jbb.mgt.core.dao.mapper.ChuangLanMapper;

@Repository("ChuangLanDao")
public class ChuangLanDaoImpl implements ChuangLanDao {
    @Autowired
    private ChuangLanMapper mapper;

    @Override
    public void insertMsgReport(String msgid, String reportTime, String mobile, String status, String notifyTime,
        String statusDesc, String uid, int length) {
        mapper.insertMsgReport(msgid, reportTime, mobile, status, notifyTime, statusDesc, uid, length);
    }

    @Override
    public void insertMessageLog(String msgid, String phoneNumber, String channelCode, String remoteAddress) {
        mapper.insertMessageLog(msgid, phoneNumber, channelCode, remoteAddress);
    }
}
