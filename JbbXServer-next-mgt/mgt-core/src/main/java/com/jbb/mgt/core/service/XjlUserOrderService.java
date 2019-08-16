package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.XjlUserOrder;

import java.sql.Timestamp;

public interface XjlUserOrderService {

    XjlUserOrder selectXjlUserOrder(int userId, int applyId, String cardNo, String payProductId,String type,Timestamp expiryDate);

    void insertXjlUserOrder(XjlUserOrder xjlUserOrder);

    void updateXjlUserOrder(String orderId, String status);

    XjlUserOrder selectXjlUserOrderById(String orderId);

    boolean notifyHeLiPay(String orderId);
}
