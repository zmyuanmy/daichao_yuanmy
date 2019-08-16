package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.FinOrgAdDelStatisticDaily;

public interface FinAdStatisticDailyMapper {

    List<FinOrgAdDelStatisticDaily> selectFinAdStatisticDaily(@Param(value = "salesId") Integer salesId,
        @Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate);

}
