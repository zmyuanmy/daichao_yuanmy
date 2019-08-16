package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.UserContant;

import java.util.List;

public interface UserContantService {

    void insertOrUpdateUserContant(List<UserContant> userContants);

    List<UserContant> selectUserContantByJbbUserId(Integer jbbUserId);

}
