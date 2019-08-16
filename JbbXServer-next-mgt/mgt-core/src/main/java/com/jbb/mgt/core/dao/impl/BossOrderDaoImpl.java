package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.BossOrderDao;
import com.jbb.mgt.core.dao.mapper.BossOrderMapper;
import com.jbb.mgt.core.domain.BossOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("BossOrderDao")
public class BossOrderDaoImpl implements BossOrderDao {

    @Autowired
    private BossOrderMapper bossOrderMapper;

    @Override
    public void insertBossOrder(BossOrder bossOrder) {
        bossOrderMapper.insertBossOrder(bossOrder);
    }

    @Override
    public BossOrder selectBossOrderByApplyId(int applyId) {
        return bossOrderMapper.selectBossOrderByApplyId(applyId);
    }

    @Override public BossOrder selectBossOrderByOrderId(String orderId) {
        return bossOrderMapper.selectBossOrderByOrderId(orderId);
    }

    @Override
    public void updateBossOrder(BossOrder bossOrder) {
        bossOrderMapper.updateBossOrder(bossOrder);
    }
}
