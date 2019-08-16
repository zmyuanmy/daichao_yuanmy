package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.PlatformMsgGroup;
import com.jbb.mgt.core.domain.PlatformMsgVo;

import java.sql.Timestamp;
import java.util.List;

public interface MsgToSalesDao {
    List<PlatformMsgVo> getPlatformMsgVo(Timestamp startDate, Integer amount, Integer cnt);

    List<PlatformMsgGroup> getPlatformMsgGroup(Timestamp startDate);
}
