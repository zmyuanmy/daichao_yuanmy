package com.jbb.server.core.dao;

import com.jbb.server.core.domain.Order;

public interface OrdersDao {

    /**
     * @param userId
     * @param productName
     * @param count 插入
     * @return
     */
    boolean insertUserProductCount(int userId, String productName, int count);

    int selectUserProductCountForUpdate(int userId, String productName);

    /**
     * @param userId
     * @param productName 增加指定产品的次数
     * @return
     */
    boolean increaseUserProductCount(int userId, String productName);

    /**
     * @param userId
     * @param productName 减少指定产品的次数
     * @return
     */
    boolean reduceUserProductCount(int userId, String productName);

    boolean insertOrder(Order order);

    boolean upadateOrder(Order order);

    Order selectOrder(String orderNo);

    int selectUserProductCount(int userId, String productName);

}
