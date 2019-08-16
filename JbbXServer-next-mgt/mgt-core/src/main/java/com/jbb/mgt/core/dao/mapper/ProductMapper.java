package com.jbb.mgt.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    int insertUserProductCount(@Param("userId") int userId, @Param("productName") String productName,
        @Param("count") int count);

    Integer selectUserProductCountForUpdate(@Param("userId") int userId, @Param("productName") String productName);

    int increaseUserProductCount(@Param("userId") int userId, @Param("productName") String productName);

    int reduceUserProductCount(@Param("userId") int userId, @Param("productName") String productName);

    Integer selectUserProductCount(@Param("userId") int userId, @Param("productName") String productName);

}
