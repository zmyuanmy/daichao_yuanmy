package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.FinAdStatisticDailyDao;
import com.jbb.mgt.core.domain.FinOrgAdDelStatisticDaily;
import com.jbb.mgt.core.service.FinAdStatisticDailyService;

@Service("FinAdStatisticDailyService")
public class FinAdStatisticDailyServiceImpl implements FinAdStatisticDailyService {
    @Autowired
    private FinAdStatisticDailyDao adStatisticDailyDao;

    @Override
    public List<FinOrgAdDelStatisticDaily> selectFinAdStatisticDaily(Integer salesId, String startDate,
        String endDate) {
        return adStatisticDailyDao.selectFinAdStatisticDaily(salesId, startDate, endDate);
    }

}
