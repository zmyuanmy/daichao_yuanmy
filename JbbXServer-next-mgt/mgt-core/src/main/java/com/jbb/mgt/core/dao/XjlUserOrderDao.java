package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.XjlUserOrder;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

public interface XjlUserOrderDao {

    XjlUserOrder selectXjlUserOrder(int userId, int applyId, String cardNo, String payProductId, String type,
        Timestamp expiryDate);

    void insertXjlUserOrder(XjlUserOrder xjlUserOrder);

    void updateXjlUserOrder(String orderId, String status);

    XjlUserOrder selectXjlUserOrderById(String orderId);
}
