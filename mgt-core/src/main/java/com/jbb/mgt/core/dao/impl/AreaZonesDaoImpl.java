 package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.AreaZonesDao;
import com.jbb.mgt.core.dao.mapper.AreaZonesMapper;
import com.jbb.mgt.core.domain.AreaZones;

@Repository("AreaZonesDao")
public class AreaZonesDaoImpl implements AreaZonesDao {

    @Autowired
    private AreaZonesMapper mapper;
    
    @Override
    public AreaZones selectAreazonesByZone(String zone) {
         return mapper.selectAreazonesByZone(zone);
    }

}
