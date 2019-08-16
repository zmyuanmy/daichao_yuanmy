package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.XjlUserOrderDao;
import com.jbb.mgt.core.dao.mapper.XjlUserOrderMapper;
import com.jbb.mgt.core.domain.XjlUserOrder;
import com.jbb.server.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository("XjlUserOrderDao")
public class XjlUserOrderDaoImpl implements XjlUserOrderDao {

    @Autowired
    private XjlUserOrderMapper xjlUserOrderMapper;

    @Override
    public XjlUserOrder selectXjlUserOrder(int userId, int applyId, String cardNo, String payProductId, String type,
        Timestamp expiryDate) {
        return xjlUserOrderMapper.selectXjlUserOrder(userId, applyId, cardNo, payProductId, type, expiryDate);
    }

    @Override
    public void insertXjlUserOrder(XjlUserOrder xjlUserOrder) {
        xjlUserOrderMapper.insertXjlUserOrder(xjlUserOrder);
    }

    @Override
    public void updateXjlUserOrder(String orderId, String status) {
        xjlUserOrderMapper.updateXjlUserOrder(orderId, status,DateUtil.getCurrentTimeStamp());
    }

    @Override
    public XjlUserOrder selectXjlUserOrderById(String orderId) {
        return xjlUserOrderMapper.selectXjlUserOrderById(orderId);
    }
}
