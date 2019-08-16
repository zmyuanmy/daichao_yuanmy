package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.XjlMessageDetail;

public interface XjlMessageDetailMapper {

    void insertXjlMsgReport(@Param("msgid") String msgid, @Param("applyId") String applyId,
        @Param("accountId") Integer accountId, @Param("mobile") String mobile, @Param("msgDesc") String msgDesc,
        @Param("status") String status, @Param("statusDesc") String statusDesc,
        @Param("notifyDate") Timestamp notifyDate);

    XjlMessageDetail selectXjlMessageDetail(@Param("msgId") String msgId, @Param("mobile") String mobile);
}
