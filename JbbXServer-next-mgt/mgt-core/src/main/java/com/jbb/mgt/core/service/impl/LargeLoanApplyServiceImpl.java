package com.jbb.mgt.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.LargeLoanApplyDao;
import com.jbb.mgt.core.domain.LargeLoanOrg;
import com.jbb.mgt.core.domain.UserEventLog;
import com.jbb.mgt.core.service.LargeLoanApplyService;

@Service("LargeLoanApplyService")
public class LargeLoanApplyServiceImpl implements LargeLoanApplyService {
    @Autowired
    private LargeLoanApplyDao largeLoanApplyDao;

    @Override
    public List<LargeLoanOrg> getAllLargeLoanOrg() {
        return largeLoanApplyDao.getAllLargeLoanOrg();
    }

    @Override
    public void updateUserInfo(int userId, String username, String idcard, String occupation) {
        largeLoanApplyDao.updateUserInfo(userId, username, idcard, occupation);

    }

    @Override
    public UserEventLog selectLargeLoanApplyLog(int userId) {
        return largeLoanApplyDao.selectLargeLoanApplyLog(userId);
    }

}
