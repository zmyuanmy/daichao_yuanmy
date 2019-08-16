package com.jbb.mgt.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

/**
 * @author wyq
 * @date 2018/6/28 10:05
 */
public interface StatisticMapper {
    /**
     * 查询当前组织的实际回款额
     * 
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectReturnAmount(@Param(value = "orgId") int orgId, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);

    /**
     * 查询当前组织的到期额
     * 
     * @param orgId
     * @param endDate
     * @return
     */
    int selectDueAmount(@Param(value = "orgId") int orgId, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);

    /**
     * 查询今日审核数量
     *
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectAuditAmount(@Param(value = "orgId") int orgId, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);

    /**
     * 查询今日进件数量
     *
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectIntoAmount(@Param(value = "orgId") int orgId, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);

    /**
     * 查询今日放款数量
     *
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectLoanNumber(@Param(value = "orgId") int orgId, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);

    /**
     * 查询今日放款额
     * 
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectLoanAmount(@Param(value = "orgId") int orgId, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);
}
