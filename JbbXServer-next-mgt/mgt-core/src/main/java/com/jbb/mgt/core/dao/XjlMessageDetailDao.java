package com.jbb.mgt.core.dao;

import java.sql.Timestamp;

import com.jbb.mgt.core.domain.XjlMessageDetail;

public interface XjlMessageDetailDao {
    
    void insertXjlMsgReport(String msgid, String applyId, Integer accountId, String mobile, String msgDesc, String status,
        String statusDesc, Timestamp notifyDate);

    XjlMessageDetail selectXjlMessageDetail(String msgId, String mobile);
}
