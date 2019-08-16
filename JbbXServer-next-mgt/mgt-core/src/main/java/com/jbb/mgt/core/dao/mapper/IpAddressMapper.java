 package com.jbb.mgt.core.dao.mapper;

import java.math.BigInteger;

import org.apache.ibatis.annotations.Param;

public interface IpAddressMapper {

    int checkIpAddress(BigInteger ipAddressNum);
    
    int checkIpAddressInCities(@Param(value = "ipAddressNum") BigInteger ipAddressNum, @Param(value = "cities") String[] cities);

}
