package com.jbb.mgt.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.Location;

public interface UserLocationDao {
    void insertLocation(Location location);

    List<Location> selectLocations(int userId, Timestamp startDate, Timestamp endDate);

    Location selectLastLocation(int userId);
}
