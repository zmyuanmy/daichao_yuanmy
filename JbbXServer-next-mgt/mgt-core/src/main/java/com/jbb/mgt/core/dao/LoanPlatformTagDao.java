package com.jbb.mgt.core.dao;

import com.jbb.mgt.core.domain.LoanPlatformTag;

public interface LoanPlatformTagDao {

    // 插入平台所放标签位置
    void insertLoanPlatformTag(LoanPlatformTag loanPlatformTag);

    // 物理删除，直接删除表里的记录
    void deleteLoanPlatformTag(Integer platformId, Integer tagId, Integer pos);

    // 调整平台在某个标签下的位置
    void updateLoanPlatformTag(LoanPlatformTag loanPlatformTag);

    LoanPlatformTag selectLoanPlatformTagById(int platformId, int tagId);
}
