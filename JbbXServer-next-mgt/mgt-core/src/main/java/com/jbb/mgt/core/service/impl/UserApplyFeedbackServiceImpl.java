package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.UserApplyFeedbackDao;
import com.jbb.mgt.core.domain.UserApplyFeedback;
import com.jbb.mgt.core.service.UserApplyFeedbackService;

@Service("UserApplyFeedbackService")
public class UserApplyFeedbackServiceImpl implements UserApplyFeedbackService {

    @Autowired
    private UserApplyFeedbackDao userApplyFeedbackDao;

    @Override
    public void saveUserApplyFeedback(UserApplyFeedback feedback) {
        userApplyFeedbackDao.saveUserApplyFeedback(feedback);
    }

}
