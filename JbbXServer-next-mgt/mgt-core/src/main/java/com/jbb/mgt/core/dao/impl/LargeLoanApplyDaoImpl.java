package com.jbb.mgt.core.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.LargeLoanApplyDao;
import com.jbb.mgt.core.dao.mapper.LargeLoanApplyMapper;
import com.jbb.mgt.core.domain.LargeLoanOrg;
import com.jbb.mgt.core.domain.UserEventLog;

@Repository("LargeLoanApplyDao")
public class LargeLoanApplyDaoImpl implements LargeLoanApplyDao {
    @Autowired
    private LargeLoanApplyMapper mapper;

    @Override
    public List<LargeLoanOrg> getAllLargeLoanOrg() {
        return mapper.getAllLargeLoanOrg();
    }

    @Override
    public void updateUserInfo(int userId, String username, String idcard, String occupation) {
        mapper.updateUserInfo(userId, username, idcard, occupation);

    }

    @Override
    public UserEventLog selectLargeLoanApplyLog(int userId) {
        return mapper.selectLargeLoanApplyLog(userId);
    }

}
