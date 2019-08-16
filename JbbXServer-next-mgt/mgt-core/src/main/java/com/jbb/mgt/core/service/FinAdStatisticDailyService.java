package com.jbb.mgt.core.service;

import java.util.List;

import com.jbb.mgt.core.domain.FinOrgAdDelStatisticDaily;

public interface FinAdStatisticDailyService {
    List<FinOrgAdDelStatisticDaily> selectFinAdStatisticDaily(Integer salesId, String startDate, String endDate);
}
