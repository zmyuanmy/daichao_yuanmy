package com.jbb.mgt.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.jbb.mgt.core.domain.LoanPlatformTag;

public interface LoanPlatformTagMapper {

    // 插入平台所放标签位置
    void insertLoanPlatformTag(LoanPlatformTag loanPlatformTag);

    // 物理删除，直接删除表里的记录
    void deleteLoanPlatformTag(@Param("platformId") Integer platformId, @Param("tagId") Integer tagId,
        @Param("pos") Integer pos);

    // 调整平台在某个标签下的位置
    void updateLoanPlatformTag(LoanPlatformTag loanPlatformTag);

    LoanPlatformTag selectLoanPlatformTagById(@Param("platformId") int platformId, @Param("tagId") int tagId);
}
