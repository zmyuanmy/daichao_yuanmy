package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.IpAddressStatistic;
import com.jbb.mgt.core.domain.IpAddressUser;

import java.util.List;

public interface IpAddressStatisticService {

    List<IpAddressStatistic> getIpAddressStatistic(int registerCnt, String startDate, String endDate);

    List<IpAddressUser> getIpAddressUser(String ipAddress, String startDate, String endDate);
}
