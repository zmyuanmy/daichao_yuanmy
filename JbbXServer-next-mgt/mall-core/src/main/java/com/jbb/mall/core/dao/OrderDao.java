package com.jbb.mall.core.dao;

import java.util.List;

import com.jbb.mall.core.dao.domain.Order;

/**
 * @author ThinkPad
 * @date 2019/01/21
 */
public interface OrderDao {

    // 获取订单列表
    List<Order> selectOrderList(String type, Integer userId, Integer status);

    // 获取单个订单
    Order selectOrderById(Integer orderId);

    // 订单创建
    void insertOrder(Order order);

    // 修改订单删除订单
    void updateOrder(Order order);

    // 删除订单
    void deleteOder(Integer orderId);

    // 修改订单状态
    void updateOrderStatus(Integer orderId, Integer status);

}
