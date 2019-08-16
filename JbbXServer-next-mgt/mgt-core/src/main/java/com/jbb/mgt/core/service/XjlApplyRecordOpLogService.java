package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.XjlApplyRecordOpLog;

/**
 * 用户操作日志Service接口
 *
 * @author wyq
 * @date 2018/8/21 10:28
 */
public interface XjlApplyRecordOpLogService {

    void saveXjlApplyRecordOpLogs(XjlApplyRecordOpLog xjlApplyRecordOpLogs);

}