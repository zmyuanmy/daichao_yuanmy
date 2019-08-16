package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.YxReportDao;
import com.jbb.mgt.core.dao.mapper.YxReportMapper;
import com.jbb.mgt.core.domain.YxReport;

@Repository("YxReportDao")
public class YxReportDaoImpl implements YxReportDao {

    @Autowired
    YxReportMapper yxReportMapper;

    /**
     * 保存数据
     */
    @Override
    public void saveReportInfo(YxReport report) {
        yxReportMapper.saveReportInfo(report);

    }

    /**
     * 获取报告
     */
    @Override
    public YxReport selectReport(Integer userId, Integer applyId, String taskId, String reportType) {
        return yxReportMapper.selectReport(userId, applyId, taskId, reportType);
    }

}
