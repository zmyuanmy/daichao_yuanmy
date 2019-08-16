package com.jbb.mgt.core.service.impl;

import com.jbb.mgt.core.dao.XjlApplyRecordOpLogDao;
import com.jbb.mgt.core.domain.XjlApplyRecordOpLog;
import com.jbb.mgt.core.service.XjlApplyRecordOpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户操作日志Service实现类
 *
 * @author wyq
 * @date 2018/8/21 15:36
 */
@Service("XjlApplyRecordOpLogService")
public class XjlApplyRecordOpLogServiceImpl implements XjlApplyRecordOpLogService {

    @Autowired
    private XjlApplyRecordOpLogDao dao;

    @Override
    public void saveXjlApplyRecordOpLogs(XjlApplyRecordOpLog xjlApplyRecordOpLog) {
        dao.insertXjlApplyRecordOpLog(xjlApplyRecordOpLog);
    }
}
