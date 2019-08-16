package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.mapper.FinOpLogMapper;
import com.jbb.mgt.core.domain.FinOpLog;
import com.jbb.mgt.core.service.FinOpLogService;

@Service("FinOpLogService")
public class FinOpLogServiceImpl implements FinOpLogService {
    @Autowired
    private FinOpLogMapper finOpLogMapper;

    @Override
    public void insertFinOpLog(FinOpLog finOpLog) {
        finOpLogMapper.insertFinOpLog(finOpLog);

    }

}
