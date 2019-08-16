package com.jbb.mgt.core.service;

import com.jbb.mgt.core.domain.LoanPlatformTag;

public interface LoanPlatformTagService {

    // 插入平台所放标签位置
    void insertLoanPlatformTag(LoanPlatformTag loanPlatformTag);

    // 物理删除，直接删除表里的记录
    void deleteLoanPlatformTag(int platformId, int tagId, int pos);

    // 调整平台在某个标签下的位置
    void updateLoanPlatformTag(LoanPlatformTag loanPlatformTag);

    LoanPlatformTag getLoanPlatformTagById(int platformId, int tagId);

}
