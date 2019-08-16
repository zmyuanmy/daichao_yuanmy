package com.jbb.mall.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mall.core.dao.OrderDao;
import com.jbb.mall.core.dao.domain.Order;
import com.jbb.mall.core.dao.mapper.OrderMapper;

/**
 * @author ThinkPad
 * @date 2019/01/21
 */
@Repository("OrderDao")
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderMapper mapper;

    @Override
    public List<Order> selectOrderList(String type, Integer userId, Integer status) {
        return mapper.selectOrderList(type, userId, status);
    }

    @Override
    public void insertOrder(Order order) {
        mapper.insertOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        mapper.updateOrder(order);
    }

    @Override
    public Order selectOrderById(Integer orderId) {
        return mapper.selectOrderById(orderId);
    }

    @Override
    public void deleteOder(Integer orderId) {
        mapper.deleteOder(orderId);
    }

    @Override
    public void updateOrderStatus(Integer orderId, Integer status) {
        mapper.updateOrderStatus(orderId, status);
    }

}
