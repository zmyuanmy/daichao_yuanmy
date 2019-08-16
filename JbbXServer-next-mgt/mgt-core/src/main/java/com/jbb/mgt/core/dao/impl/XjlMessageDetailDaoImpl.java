package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.XjlMessageDetailDao;
import com.jbb.mgt.core.dao.mapper.XjlMessageDetailMapper;
import com.jbb.mgt.core.domain.XjlMessageDetail;

@Repository(" XjlMessageDetailDao")
public class XjlMessageDetailDaoImpl implements XjlMessageDetailDao {

    @Autowired
    private XjlMessageDetailMapper mapper;

    @Override
    public void insertXjlMsgReport(String msgid, String applyId, Integer accountId, String mobile, String msgDesc,
        String status, String statusDesc, Timestamp notifyDate) {
        mapper.insertXjlMsgReport(msgid, applyId, accountId, mobile, msgDesc, status, statusDesc, notifyDate);
    }

    @Override
    public XjlMessageDetail selectXjlMessageDetail(String msgId, String mobile) {
        return mapper.selectXjlMessageDetail(msgId, mobile);
    }

}
