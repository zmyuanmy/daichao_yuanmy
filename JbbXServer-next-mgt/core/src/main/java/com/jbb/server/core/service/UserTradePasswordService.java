package com.jbb.server.core.service;

/**
 * Created by inspironsun on 2018/6/6
 */
public interface UserTradePasswordService {

    void setUserTradePassword(int userId,String password);

    void updateUserTradePassword(int userId,String msgCode,String idCard,String password,String step);
}
