package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserLocationDao;
import com.jbb.mgt.core.dao.mapper.UserLocationMapper;
import com.jbb.mgt.core.domain.Location;

@Repository("UserLocationDao")
public class UserLocationDaoImpl implements UserLocationDao {
    @Autowired
    private UserLocationMapper mapper;

    @Override
    public void insertLocation(Location location) {
        mapper.insertLocation(location);

    }

    @Override
    public List<Location> selectLocations(int userId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectLocations(userId, startDate, endDate);
    }

    @Override
    public Location selectLastLocation(int userId) {
        return mapper.selectLastLocation(userId);
    }

}
