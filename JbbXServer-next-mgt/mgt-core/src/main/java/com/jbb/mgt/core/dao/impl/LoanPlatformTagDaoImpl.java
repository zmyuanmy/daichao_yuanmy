package com.jbb.mgt.core.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.LoanPlatformTagDao;
import com.jbb.mgt.core.dao.mapper.LoanPlatformTagMapper;
import com.jbb.mgt.core.domain.LoanPlatformTag;

@Repository("LoanPlatformTagDao")
public class LoanPlatformTagDaoImpl implements LoanPlatformTagDao {

    @Autowired
    private LoanPlatformTagMapper mapper;

    @Override
    public void insertLoanPlatformTag(LoanPlatformTag loanPlatformTag) {
        mapper.insertLoanPlatformTag(loanPlatformTag);
    }

    @Override
    public void deleteLoanPlatformTag(Integer platformId, Integer tagId, Integer pos) {
        mapper.deleteLoanPlatformTag(platformId, tagId, pos);
    }

    @Override
    public void updateLoanPlatformTag(LoanPlatformTag loanPlatformTag) {
        mapper.updateLoanPlatformTag(loanPlatformTag);
    }

    @Override
    public LoanPlatformTag selectLoanPlatformTagById(int platformId, int tagId) {
        return mapper.selectLoanPlatformTagById(platformId, tagId);
    }

}
