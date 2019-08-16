package com.jbb.mgt.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Created by inspironsun on 2019/4/11
 */
public interface PhoneBlacklistMapper {

    int checkPhoneNumberExist(@Param("phoneNumber") String phoneNumber);

}
