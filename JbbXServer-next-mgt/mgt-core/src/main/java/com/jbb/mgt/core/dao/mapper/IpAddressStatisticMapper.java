package com.jbb.mgt.core.dao.mapper;

import com.jbb.mgt.core.domain.IpAddressStatistic;

import java.util.List;

import com.jbb.mgt.core.domain.IpAddressUser;
import org.apache.ibatis.annotations.Param;

public interface IpAddressStatisticMapper {

    List<IpAddressStatistic> getIpAddressStatistic(@Param(value = "registerCnt") int registerCnt,
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate);

    List<IpAddressUser> getIpAddressUsers(@Param(value = "ipAddress") String ipAddress,
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate);

}
