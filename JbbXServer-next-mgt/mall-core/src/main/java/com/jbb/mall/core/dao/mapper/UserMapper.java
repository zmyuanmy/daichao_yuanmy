package com.jbb.mall.core.dao.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

import com.jbb.mall.core.dao.domain.User;

/**
 * 
 * @author hsy
 * 
 *
 */
public interface UserMapper {

    /**
     * 查询 user信息
     * 
     * @param userKey 用户key
     * @return
     */
    User selectUserByUserKey(@Param(value = "userKey") String userKey, @Param("expiry") Timestamp expiry);

}
