package com.jbb.mall.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mall.core.dao.domain.Order;

/**
 * @author ThinkPad
 * @date 2019/01/21
 */
public interface OrderMapper {

    // 获取订单列表
    List<Order> selectOrderList(@Param("type") String type, @Param("userId") Integer userId,
        @Param("status") Integer status);

    // 获取单个订单
    Order selectOrderById(@Param("orderId") Integer orderId);

    // 订单创建
    void insertOrder(Order order);

    // 修改订单
    void updateOrder(Order order);

    // 删除订单
    void deleteOder(@Param("orderId") Integer orderId);

    // 修改订单状态
    void updateOrderStatus(@Param("orderId") Integer orderId, @Param("status") Integer status);
}
