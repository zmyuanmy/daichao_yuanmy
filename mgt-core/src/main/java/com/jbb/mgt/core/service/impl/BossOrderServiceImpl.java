package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.BossOrderDao;
import com.jbb.mgt.core.domain.BossOrder;
import com.jbb.mgt.core.service.BossOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BossOrderService")
public class BossOrderServiceImpl implements BossOrderService {

    @Autowired
    private BossOrderDao bossOrderDao;

    @Override
    public void insertBossOrder(BossOrder bossOrder) {
        bossOrderDao.insertBossOrder(bossOrder);
    }

    @Override
    public BossOrder selectBossOrderByApplyId(int applyId) {
        return bossOrderDao.selectBossOrderByApplyId(applyId);
    }

    @Override public BossOrder selectBossOrderByOrderId(String orderId) {
        return bossOrderDao.selectBossOrderByOrderId(orderId);
    }

    @Override
    public void updateBossOrder(BossOrder bossOrder) {
        bossOrderDao.updateBossOrder(bossOrder);
    }
}
