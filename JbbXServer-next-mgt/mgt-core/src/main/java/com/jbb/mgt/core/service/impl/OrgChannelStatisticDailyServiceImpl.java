package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.OrgChannelStatisticDailyDao;
import com.jbb.mgt.core.domain.OrgChannelStatisticDaily;
import com.jbb.mgt.core.service.OrgChannelStatisticDailyService;

@Service("OrgChannelStatisticDailyService")
public class OrgChannelStatisticDailyServiceImpl implements OrgChannelStatisticDailyService {

    @Autowired
    private OrgChannelStatisticDailyDao orgChannelStatisticDailyDao;

    @Override
    public List<OrgChannelStatisticDaily> selectFinOrgStatisticDaily(Integer type, Integer salesId, String startDate,
        String endDate) {
        return orgChannelStatisticDailyDao.selectFinOrgStatisticDaily(type, salesId, startDate, endDate);
    }

}
