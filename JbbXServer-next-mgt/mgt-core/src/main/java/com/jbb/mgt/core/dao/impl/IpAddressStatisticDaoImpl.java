package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.IpAddressStatisticDao;
import com.jbb.mgt.core.dao.mapper.IpAddressStatisticMapper;
import com.jbb.mgt.core.domain.IpAddressStatistic;
import com.jbb.mgt.core.domain.IpAddressUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("IpAddressStatisticDao")
public class IpAddressStatisticDaoImpl implements IpAddressStatisticDao {

    @Autowired
    private IpAddressStatisticMapper ipStatisticMapper;

    @Override
    public List<IpAddressStatistic> getIpAddressStatistic(int registerCnt, String startDate, String endDate) {
        return ipStatisticMapper.getIpAddressStatistic(registerCnt, startDate, endDate);
    }

    @Override
    public List<IpAddressUser> getIpAddressUser(String ipAddress, String startDate, String endDate) {
        return ipStatisticMapper.getIpAddressUsers(ipAddress, startDate, endDate);
    }
}
