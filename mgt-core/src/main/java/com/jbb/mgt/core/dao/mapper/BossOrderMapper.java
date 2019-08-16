package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.BossOrder;
import org.apache.ibatis.annotations.Param;

public interface BossOrderMapper {

    void insertBossOrder(BossOrder bossOrder);

    BossOrder selectBossOrderByApplyId(@Param(value = "applyId") int applyId);

    BossOrder selectBossOrderByOrderId(@Param(value = "orderId") String orderId);

    void updateBossOrder(BossOrder bossOrder);
}
