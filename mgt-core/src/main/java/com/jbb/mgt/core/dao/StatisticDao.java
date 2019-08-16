package com.jbb.mgt.core.dao;

import java.sql.Timestamp;

/**
 * 首页显示今日数据
 *
 * @author wyq
 * @date 2018/6/28 09:26
 */
public interface StatisticDao {

    /**
     * 查询当前组织的实际回款额
     * 
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectReturnAmount(int orgId, Timestamp startDate, Timestamp endDate);

    /**
     * 查询当前组织的到期额
     * 
     * @param orgId
     * @param endDate
     * @return
     */
    int selectDueAmount(int orgId, Timestamp startDate, Timestamp endDate);

    /**
     * 查询今日审核数量
     * 
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectAuditAmount(int orgId, Timestamp startDate, Timestamp endDate);

    /**
     * 查询今日进件数量
     * 
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectIntoAmount(int orgId, Timestamp startDate, Timestamp endDate);

    /**
     * 查询今日放款数量
     * 
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectLoanNumber(int orgId, Timestamp startDate, Timestamp endDate);

    /**
     * 查询今日放款额
     * 
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectLoanAmount(int orgId, Timestamp startDate, Timestamp endDate);
}
