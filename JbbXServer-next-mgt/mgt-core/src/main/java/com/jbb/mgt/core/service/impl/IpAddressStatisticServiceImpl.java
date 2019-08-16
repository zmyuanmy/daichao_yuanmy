package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.IpAddressStatisticDao;
import com.jbb.mgt.core.domain.IpAddressStatistic;
import com.jbb.mgt.core.domain.IpAddressUser;
import com.jbb.mgt.core.service.IpAddressStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IpAddressStatisticService")
public class IpAddressStatisticServiceImpl implements IpAddressStatisticService {

    @Autowired
    private IpAddressStatisticDao ipStatisticDao;

    @Override
    public List<IpAddressStatistic> getIpAddressStatistic(int registerCnt, String startDate, String endDate) {
        return ipStatisticDao.getIpAddressStatistic(registerCnt, startDate, endDate);
    }

    @Override
    public List<IpAddressUser> getIpAddressUser(String ipAddress, String startDate, String endDate) {
        return ipStatisticDao.getIpAddressUser(ipAddress, startDate, endDate);
    }
}
