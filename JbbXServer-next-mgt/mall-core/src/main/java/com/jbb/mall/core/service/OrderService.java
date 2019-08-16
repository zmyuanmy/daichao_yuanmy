package com.jbb.mall.core.service;

import java.util.List;

import com.jbb.mall.core.dao.domain.Order;

/**
 * @author ThinkPad
 * @date 2019/01/21
 */
public interface OrderService {

    // 获取订单列表
    List<Order> getOrderList(String type, Integer userId, Integer status);

    // 获取单个订单
    Order getOrderById(Integer orderId);

    // 订单创建
    void insertOrder(Order order);

    // 修改订单
    void updateOrder(Order order);

    // 删除订单
    void deleteOder(Integer orderId);

    // 修改订单状态
    void updateOrderStatus(Integer orderId, Integer status);
}
