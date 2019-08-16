package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.UserContantDao;
import com.jbb.mgt.core.dao.mapper.UserContantMapper;
import com.jbb.mgt.core.domain.UserContant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserContantDao")
public class UserContantDaoImpl implements UserContantDao{

    @Autowired
    private UserContantMapper mapper;

    @Override
    public void insertOrUpdateUserContant(List<UserContant> userContants) {
        mapper.insertOrUpdateUserContant(userContants);
    }

    @Override
    public List<UserContant> selectUserContantByJbbUserId(Integer jbbUserId) {
        return mapper.selectUserContantByJbbUserId(jbbUserId);
    }

}
