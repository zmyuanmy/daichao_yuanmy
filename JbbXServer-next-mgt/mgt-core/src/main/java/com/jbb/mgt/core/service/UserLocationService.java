package com.jbb.mgt.core.service;

import java.sql.Timestamp;
import java.util.List;

import com.jbb.mgt.core.domain.Location;

public interface UserLocationService {
    void insertLocation(Location location);

    List<Location> getLocations(int userId, Timestamp startDate, Timestamp endDate);

    Location getLastLocation(int userId);

    String getRegeo(double latitude, double longitude);
}
