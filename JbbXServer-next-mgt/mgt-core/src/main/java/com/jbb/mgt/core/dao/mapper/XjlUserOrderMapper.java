package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.XjlUserOrder;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

public interface XjlUserOrderMapper {

    XjlUserOrder selectXjlUserOrder(@Param("userId") int userId,@Param("applyId") int applyId,@Param("cardNo") String cardNo,@Param("payProductId") String payProductId,@Param("type") String type,@Param("expiryDate") Timestamp expiryDate);

    void insertXjlUserOrder(XjlUserOrder xjlUserOrder);

    void updateXjlUserOrder(@Param("orderId") String orderId,@Param("status") String status,@Param("updateDate") Timestamp updateDate);

    XjlUserOrder selectXjlUserOrderById(@Param("orderId") String orderId);
}
