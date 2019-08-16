package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.UserAddressDao;
import com.jbb.mgt.core.domain.UserAddresses;
import com.jbb.mgt.core.service.UserAddressService;

/**
 * 用户地址信息Service接口实现类
 *
 * @author wyq
 * @date 2018/8/16 10:51
 */
@Service("UserAddressesService")
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressDao dao;

    @Override
    public void insertUserAddresses(UserAddresses userAddresses) {
        dao.insertUserAddresses(userAddresses);
    }

    @Override
    public void updateUserAddresses(UserAddresses userAddresses) {
        dao.updateUserAddresses(userAddresses);
    }

    @Override
    public UserAddresses getUserAddressesByAddressId(int addressId) {
        return dao.selectUserAddressesByAddressId(addressId);
    }

    @Override
    public void deleteUserAddressesByAddressId(int addressId) {
        dao.deleteUserAddressesByAddressId(addressId);
    }

    @Override
    public void insertUserAddresses(int userId, List<UserAddresses> addresses) {
        dao.insertUserAddresses(userId, addresses);
    }
}
