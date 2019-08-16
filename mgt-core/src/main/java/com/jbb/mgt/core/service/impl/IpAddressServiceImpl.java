package com.jbb.mgt.core.service.impl;

import java.math.BigInteger;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.IpAddressDao;
import com.jbb.mgt.core.service.IpAddressService;
import com.jbb.server.common.PropertyManager;
import com.jbb.server.common.util.CommonUtil;

@Service("IpAddressService")
public class IpAddressServiceImpl implements IpAddressService {

    @Autowired
    private IpAddressDao ipAddressDao;

    @Override
    public boolean checkIpAddress(String ipAddress) {
        BigInteger ipAddressNum = IPV2LongIP(ipAddress);
        if (ipAddressNum == null) {
            return true;
        }
        return ipAddressDao.checkIpAddress(ipAddressNum);
    }

    @Override
    public boolean checkIpAddressInCities(String ipAddress, String[] cities) {
        BigInteger ipAddressNum = IPV2LongIP(ipAddress);
        if (ipAddressNum == null) {
            return false;
        }
        return ipAddressDao.checkIpAddressInCities(ipAddressNum, cities);
    }

    private BigInteger IPV2LongIP(String ipAddress) {
        if (ipAddress == null) {
            return null;
        }
        java.net.InetAddress ia;
        try {
            ia = java.net.InetAddress.getByName(ipAddress);

            byte byteArr[] = ia.getAddress();
            java.math.BigInteger ipnumber = null;
            if (ia instanceof java.net.Inet4Address) {
                ipnumber = new java.math.BigInteger(1, byteArr);
            }
            if (ia instanceof java.net.Inet6Address) {
                ipnumber = new java.math.BigInteger(1, byteArr);
            }
            return ipnumber;
        } catch (UnknownHostException e) {
            return null;
        }
    }

    @Override
    public boolean isIpShowValidProduct(String ipAddress) {
        
        String[] ignoreIps =  PropertyManager.getProperties("jbb.mgt.compliance.product.ips.ignore");
        //忽略的IP地址
        if(CommonUtil.inArray(ipAddress, ignoreIps)){
            return false;
        }
        
        String[] ips = PropertyManager.getProperties("jbb.mgt.compliance.product.ips");
        if(CommonUtil.inArray(ipAddress, ips)){
            return true;
        }
        
        boolean enabled = PropertyManager.getBooleanProperty("jbb.mgt.compliance.product.enabled", false);
        if (enabled) {
            String[] cities = PropertyManager.getProperties("jbb.mgt.compliance.product.cities");
            boolean inCities = this.checkIpAddressInCities(ipAddress, cities);
            if (inCities) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkReferer(String ref) {
        if (ref == null) {
            return false;
        }
        String[] referers = PropertyManager.getProperties("jbb.app.referer");
        for (String s : referers) {
            if(ref.startsWith(s)) {
                return true;
            }
        }
        return false;
    }
    
    

}
