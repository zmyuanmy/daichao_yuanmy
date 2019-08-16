package com.jbb.mgt.core.dao;

import java.math.BigInteger;

public interface IpAddressDao {
     
     boolean checkIpAddress(BigInteger ipAddressNum);
     
     /**
      * 检查是不是北京、上海、广州、深圳
      * @param ipAddressNum
      * @return
      */
     boolean checkIpAddressInCities(BigInteger ipAddressNum, String[] cities);

}
