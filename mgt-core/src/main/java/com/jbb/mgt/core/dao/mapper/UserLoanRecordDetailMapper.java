package com.jbb.mgt.core.dao.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.Money;
import com.jbb.mgt.core.domain.UserLoanRecordDetail;

public interface UserLoanRecordDetailMapper {
    void insertUserLoanRecordDetail(UserLoanRecordDetail loanRecordDetail);

    List<UserLoanRecordDetail> selectUserLoanRecordDetails(@Param(value = "loanId") int loanId);

    /**
     * 按机构编号查询统计金额数据
     * 
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    Money selectCountMoneyByOrgId(@Param(value = "orgId") Integer orgId,
        @Param(value = "startDate") Timestamp startDate, @Param(value = "endDate") Timestamp endDate);

    /**
     * 查询累计待收金额
     * 
     * @param orgId
     * @return
     */
    int selectDueMoney(@Param(value = "orgId") Integer orgId);

    /**
     * 查询借条金额
     *
     * @param orgId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectBorrowingMoney(@Param(value = "orgId") Integer orgId, @Param(value = "startDate") Timestamp startDate,
        @Param(value = "endDate") Timestamp endDate);
}
