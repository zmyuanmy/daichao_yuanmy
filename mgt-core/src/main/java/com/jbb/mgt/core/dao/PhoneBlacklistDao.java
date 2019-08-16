package com.jbb.mgt.core.dao;

/**
 * Created by inspironsun on 2019/4/11
 */
public interface PhoneBlacklistDao {

    boolean checkPhoneNumberExist(String phoneNumber);
}
