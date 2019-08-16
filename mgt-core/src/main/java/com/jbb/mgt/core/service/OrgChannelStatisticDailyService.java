package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.OrgChannelStatisticDaily;

public interface OrgChannelStatisticDailyService {
    
    // 进件/注册报表
    List<OrgChannelStatisticDaily> selectFinOrgStatisticDaily(Integer type, Integer salesId, String startDate,
        String endDate);
}
