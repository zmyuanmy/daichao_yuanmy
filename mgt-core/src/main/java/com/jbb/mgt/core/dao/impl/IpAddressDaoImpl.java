package com.jbb.mgt.core.dao.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.IpAddressDao;
import com.jbb.mgt.core.dao.mapper.IpAddressMapper;

@Repository("IpAddressDao")
public class IpAddressDaoImpl implements IpAddressDao {

    @Autowired
    private IpAddressMapper ipAddressMapper;

    @Override
    public boolean checkIpAddress(BigInteger ipAddressNum) {
        return ipAddressMapper.checkIpAddress(ipAddressNum) > 0;
    }

    @Override
    public boolean checkIpAddressInCities(BigInteger ipAddressNum, String[] cities) {
         return ipAddressMapper.checkIpAddressInCities(ipAddressNum, cities) > 0;
    }
    
    

}
