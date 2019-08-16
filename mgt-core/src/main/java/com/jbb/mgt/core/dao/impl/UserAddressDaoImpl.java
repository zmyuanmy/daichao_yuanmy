package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserAddressDao;
import com.jbb.mgt.core.dao.mapper.UserAddressMapper;
import com.jbb.mgt.core.domain.UserAddresses;

/**
 * 用户地址信息Dao接口实现类
 *
 * @author wyq
 * @date 2018/8/16 10:56
 */
@Repository("UserAddressesDao")
public class UserAddressDaoImpl implements UserAddressDao {

    @Autowired
    private UserAddressMapper mapper;

    @Override
    public void insertUserAddresses(UserAddresses userAddresses) {
        mapper.insertUserAddresses(userAddresses);
    }

    @Override
    public void updateUserAddresses(UserAddresses userAddresses) {
        mapper.updateUserAddresses(userAddresses);
    }

    @Override
    public UserAddresses selectUserAddressesByAddressId(int addressId) {
        return mapper.selectUserAddressesByAddressId(addressId);
    }

    @Override
    public void deleteUserAddressesByAddressId(int addressId) {
        mapper.deleteUserAddressesByAddressId(addressId);
    }

    @Override
    public void insertUserAddresses(int userId, List<UserAddresses> addresses) {
        if (addresses == null || addresses.size() == 0) {
            return;
        }
        for (UserAddresses userAddresses : addresses) {
            userAddresses.setUserId(userId);
            mapper.insertUserAddresses(userAddresses);
        }
    }

    @Override
    public UserAddresses selectUserAddressesUserIdAndType(int userId, int type) {
        return mapper.selectUserAddressesUserIdAndType(userId, type);
    }
}
