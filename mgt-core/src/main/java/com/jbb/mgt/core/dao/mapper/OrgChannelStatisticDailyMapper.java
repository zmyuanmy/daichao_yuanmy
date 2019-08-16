package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.OrgChannelStatisticDaily;

public interface OrgChannelStatisticDailyMapper {
    
    //进件/注册报表 
    List<OrgChannelStatisticDaily> selectFinOrgStatisticDaily(@Param("type") Integer type,
        @Param("salesId") Integer salesId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
