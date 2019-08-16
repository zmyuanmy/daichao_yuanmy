package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.BossOrder;

public interface BossOrderService {

    void insertBossOrder(BossOrder bossOrder);

    BossOrder selectBossOrderByApplyId(int applyId);

    BossOrder selectBossOrderByOrderId(String orderId);

    void updateBossOrder(BossOrder bossOrder);
}
