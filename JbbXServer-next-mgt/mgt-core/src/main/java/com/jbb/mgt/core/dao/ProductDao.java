package com.jbb.mgt.core.dao;

public interface ProductDao {

    boolean insertUserProductCount(int userId, String productName, int count);

    int selectUserProductCountForUpdate(int userId, String productName);

    boolean increaseUserProductCount(int userId, String productName);

    boolean reduceUserProductCount(int userId, String productName);

    int selectUserProductCount(int userId, String productName);
}
