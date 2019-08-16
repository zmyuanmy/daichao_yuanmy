package com.jbb.mall.core.dao.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

import com.jbb.mall.core.dao.domain.Account;

public interface AccountMapper {

    Account selectAccountByKey(@Param(value = "userKey") String userKey, @Param("expiry") Timestamp expiry);

    int[] selectAccountPermissions(@Param(value = "accountId") int accountId);

}
