package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.OrgChannelStatisticDailyDao;
import com.jbb.mgt.core.dao.mapper.OrgChannelStatisticDailyMapper;
import com.jbb.mgt.core.domain.OrgChannelStatisticDaily;

@Repository("OrgChannelStatisticDailyDao")
public class OrgChannelStatisticDailyDaoImpl implements OrgChannelStatisticDailyDao {

    @Autowired
    private OrgChannelStatisticDailyMapper mapper;
    
    @Override
    public List<OrgChannelStatisticDaily> selectFinOrgStatisticDaily(Integer type, Integer salesId, String startDate,
        String endDate) {
        return mapper.selectFinOrgStatisticDaily(type, salesId, startDate, endDate);
    }

}
