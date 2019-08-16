package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.PlatformMsgGroup;
import com.jbb.mgt.core.domain.PlatformMsgVo;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface MsgToSalesMapper {
    List<PlatformMsgVo> getPlatformMsgVo(@Param("startDate") Timestamp startDate, @Param("balance") Integer balance,
        @Param("cnt") Integer cnt);

    List<PlatformMsgGroup> getPlatformMsgGroup(@Param("startDate") Timestamp startDate);
}
