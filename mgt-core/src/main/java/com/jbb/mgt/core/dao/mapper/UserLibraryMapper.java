package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.UserLibraryInfo;

public interface UserLibraryMapper {

    List<UserLibraryInfo> getUserLibraryInfo(@Param("channelCode") String channelCode,
        @Param("phoneNumber") String phoneNumber, @Param("ipAddress") String ipAddress,
        @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

}
