package com.jbb.mgt.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.UserProperty;

public interface UserPropertiesMapper {

    UserProperty selectUserPropertyByUserIdAndName(@Param(value = "userId") int userId,
        @Param(value = "name") String name);

    boolean deleteUserPropertyByUserIdAndName(@Param(value = "userId") int userId, @Param(value = "name") String name);

    boolean updateUserPropertyByUserIdAndName(@Param(value = "userId") int userId, @Param(value = "name") String name,
        @Param(value = "value") String value);

    boolean insertUserProperty(@Param(value = "userId") int userId, @Param(value = "name") String name,
        @Param(value = "value") String value, @Param(value = "isHidden") Boolean isHidden);

    List<UserProperty> selectUserPropertyList(@Param(value = "userId") int userId);

}
