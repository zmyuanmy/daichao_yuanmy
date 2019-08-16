package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.UserAddresses;

/**
 * 用户地址信息Service接口
 *
 * @author wyq
 * @date 2018/8/16 10:50
 */
public interface UserAddressService {
    void insertUserAddresses(UserAddresses userAddresses);

    void updateUserAddresses(UserAddresses userAddresses);

    UserAddresses getUserAddressesByAddressId(int addressId);

    void deleteUserAddressesByAddressId(int addressId);

    void insertUserAddresses(int userId, List<UserAddresses> addresses);
}
