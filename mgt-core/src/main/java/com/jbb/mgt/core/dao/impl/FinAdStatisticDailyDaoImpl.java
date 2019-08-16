package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.FinAdStatisticDailyDao;
import com.jbb.mgt.core.dao.mapper.FinAdStatisticDailyMapper;
import com.jbb.mgt.core.domain.FinOrgAdDelStatisticDaily;

@Repository("FinAdStatisticDailyDao")
public class FinAdStatisticDailyDaoImpl implements FinAdStatisticDailyDao {
    @Autowired
    private FinAdStatisticDailyMapper mapper;

    @Override
    public List<FinOrgAdDelStatisticDaily> selectFinAdStatisticDaily(Integer salesId, String startDate,
        String endDate) {
        return mapper.selectFinAdStatisticDaily(salesId, startDate, endDate);
    }

}
