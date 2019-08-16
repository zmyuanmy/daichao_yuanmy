package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.XjlMessageDetailDao;
import com.jbb.mgt.core.domain.XjlMessageDetail;
import com.jbb.mgt.core.service.XjlMessageDetailService;

@Service("XjlMessageDetailService")
public class XjlMessageDetailServiceImpl implements XjlMessageDetailService {

    @Autowired
    private XjlMessageDetailDao xjlMessageDetailDao;

    @Override
    public void insertXjlMsgReport(String msgid, String applyId, Integer accountId, String mobile, String msgDesc,
        String status, String statusDesc, Timestamp notifyDate) {
        xjlMessageDetailDao.insertXjlMsgReport(msgid, applyId, accountId, mobile, msgDesc, status, statusDesc,
            notifyDate);
    }

    @Override
    public XjlMessageDetail selectXjlMessageDetail(String msgId, String mobile) {
        return xjlMessageDetailDao.selectXjlMessageDetail(msgId, mobile);
    }

}
