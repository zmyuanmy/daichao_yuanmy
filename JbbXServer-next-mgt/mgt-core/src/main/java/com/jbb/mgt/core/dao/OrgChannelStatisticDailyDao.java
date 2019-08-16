package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.OrgChannelStatisticDaily;

public interface OrgChannelStatisticDailyDao {

    // 进件/注册报表
    List<OrgChannelStatisticDaily> selectFinOrgStatisticDaily(Integer type, Integer salesId, String startDate,
        String endDate);
}
