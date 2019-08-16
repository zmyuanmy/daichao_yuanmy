package com.jbb.mgt.core.service;

public interface IpAddressService {
     
     boolean checkIpAddress(String ipAddress);
     
     boolean checkIpAddressInCities(String ipAddress, String[] cities);
     
     boolean isIpShowValidProduct(String ipAddress);
     
     boolean checkReferer(String ref);

}
