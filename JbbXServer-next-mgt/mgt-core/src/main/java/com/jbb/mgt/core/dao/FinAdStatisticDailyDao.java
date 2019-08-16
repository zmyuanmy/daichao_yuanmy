package com.jbb.mgt.core.dao;

import java.util.List;

import com.jbb.mgt.core.domain.FinOrgAdDelStatisticDaily;

public interface FinAdStatisticDailyDao {

    List<FinOrgAdDelStatisticDaily> selectFinAdStatisticDaily(Integer salesId, String startDate, String endDate);

}
