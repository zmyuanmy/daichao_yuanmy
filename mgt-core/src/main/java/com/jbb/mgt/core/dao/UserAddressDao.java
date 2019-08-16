package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.UserAddresses;
import org.apache.ibatis.annotations.Param;

/**
 * 用户地址信息Dao接口
 *
 * @author wyq
 * @date 2018/8/16 10:56
 */
public interface UserAddressDao {
    void insertUserAddresses(UserAddresses userAddresses);

    void updateUserAddresses(UserAddresses userAddresses);

    UserAddresses selectUserAddressesByAddressId(int addressId);

    void deleteUserAddressesByAddressId(int addressId);

    void insertUserAddresses(int userId, List<UserAddresses> addresses);

    UserAddresses selectUserAddressesUserIdAndType(int userId, int type);
}
