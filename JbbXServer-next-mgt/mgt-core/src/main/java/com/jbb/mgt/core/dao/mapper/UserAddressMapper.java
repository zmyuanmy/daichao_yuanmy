package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.UserAddresses;
import org.apache.ibatis.annotations.Param;

/**
 * 用户地址信息Mapper接口
 *
 * @author wyq
 * @date 2018/8/16 11:00
 */
public interface UserAddressMapper {
    void insertUserAddresses(UserAddresses userAddresses);

    void updateUserAddresses(UserAddresses userAddresses);

    UserAddresses selectUserAddressesByAddressId(@Param("addressId") int addressId);

    void deleteUserAddressesByAddressId(@Param("addressId") int addressId);

    UserAddresses selectUserAddressesUserIdAndType(@Param("userId") int userId,@Param("type") int type);

}
