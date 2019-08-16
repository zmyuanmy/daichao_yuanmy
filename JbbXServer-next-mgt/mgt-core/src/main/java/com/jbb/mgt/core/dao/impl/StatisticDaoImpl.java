package com.jbb.mgt.core.dao.impl;

import com.jbb.mgt.core.dao.StatisticDao;
import com.jbb.mgt.core.dao.mapper.StatisticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * @author wyq
 * @date 2018/6/28 10:02
 */
@Repository("StatisticDao")
public class StatisticDaoImpl implements StatisticDao {

    @Autowired
    private StatisticMapper mapper;

    @Override
    public int selectReturnAmount(int orgId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectReturnAmount(orgId, startDate, endDate);
    }

    @Override
    public int selectDueAmount(int orgId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectDueAmount(orgId, startDate, endDate);
    }

    @Override
    public int selectAuditAmount(int orgId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectAuditAmount(orgId, startDate, endDate);
    }

    @Override
    public int selectIntoAmount(int orgId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectIntoAmount(orgId, startDate, endDate);
    }

    @Override
    public int selectLoanNumber(int orgId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectLoanNumber(orgId, startDate, endDate);
    }

    @Override
    public int selectLoanAmount(int orgId, Timestamp startDate, Timestamp endDate) {
        return mapper.selectLoanAmount(orgId, startDate, endDate);
    }
}
