package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.Location;

public interface UserLocationMapper {

    void insertLocation(Location location);

    List<Location> selectLocations(@Param(value = "userId") int userId, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);

    Location selectLastLocation(@Param(value = "userId") int userId);

}
