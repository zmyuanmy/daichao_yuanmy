package com.jbb.mall.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mall.core.dao.OrderDao;
import com.jbb.mall.core.dao.domain.Order;
import com.jbb.mall.core.service.OrderService;

/**
 * @author ThinkPad
 * @date 2019/01/21
 */

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> getOrderList(String type, Integer userId, Integer status) {
        return orderDao.selectOrderList(type, userId, status);
    }

    @Override
    public void insertOrder(Order order) {
        orderDao.insertOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderDao.selectOrderById(orderId);
    }

    @Override
    public void deleteOder(Integer orderId) {
        orderDao.deleteOder(orderId);
    }

    @Override
    public void updateOrderStatus(Integer orderId, Integer status) {
        orderDao.updateOrderStatus(orderId, status);
    }

}
