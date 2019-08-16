package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.BossOrder;

public interface BossOrderDao {

    void insertBossOrder(BossOrder bossOrder);

    BossOrder selectBossOrderByApplyId(int applyId);

    BossOrder selectBossOrderByOrderId(String orderId);

    void updateBossOrder(BossOrder bossOrder);
}
