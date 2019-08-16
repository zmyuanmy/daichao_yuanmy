package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.UserApplyFeedbackDao;
import com.jbb.mgt.core.dao.mapper.UserApplyFeedbackMapper;
import com.jbb.mgt.core.domain.UserApplyFeedback;

@Repository("UserApplyFeedbackDao")
public class UserApplyFeedbackDaoImpl implements UserApplyFeedbackDao {

    @Autowired
    private UserApplyFeedbackMapper mapper;

    @Override
    public void saveUserApplyFeedback(UserApplyFeedback feedback) {
        mapper.saveUserApplyFeedback(feedback);
    }

}
