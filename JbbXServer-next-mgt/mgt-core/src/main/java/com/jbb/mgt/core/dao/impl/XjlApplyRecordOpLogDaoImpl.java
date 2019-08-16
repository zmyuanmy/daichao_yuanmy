package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.XjlApplyRecordOpLogDao;
import com.jbb.mgt.core.dao.mapper.XjlApplyRecordOpLogMapper;
import com.jbb.mgt.core.domain.XjlApplyRecordOpLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 用户操作日志Dao实现类
 *
 * @author wyq
 * @date 2018/8/21 15:37
 */
@Repository("XjlApplyRecordOpLogDao")
public class XjlApplyRecordOpLogDaoImpl implements XjlApplyRecordOpLogDao {

    @Autowired
    private XjlApplyRecordOpLogMapper mapper;

    @Override
    public void insertXjlApplyRecordOpLog(XjlApplyRecordOpLog xjlApplyRecordOpLog) {
        mapper.insertXjlApplyRecordOpLog(xjlApplyRecordOpLog);
    }
}
