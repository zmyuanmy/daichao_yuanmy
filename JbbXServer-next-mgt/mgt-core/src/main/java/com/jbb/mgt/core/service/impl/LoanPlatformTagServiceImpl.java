package com.jbb.mgt.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.LoanPlatformTagDao;
import com.jbb.mgt.core.domain.LoanPlatformTag;
import com.jbb.mgt.core.service.LoanPlatformTagService;

@Service("LoanPlatformTagService")
public class LoanPlatformTagServiceImpl implements LoanPlatformTagService {

    @Autowired
    private LoanPlatformTagDao loanPlatformTagDao;

    @Override
    public void insertLoanPlatformTag(LoanPlatformTag loanPlatformTag) {
        loanPlatformTagDao.insertLoanPlatformTag(loanPlatformTag);
    }

    @Override
    public void deleteLoanPlatformTag(int platformId, int tagId, int pos) {
        loanPlatformTagDao.deleteLoanPlatformTag(platformId, tagId, pos);
    }

    @Override
    public void updateLoanPlatformTag(LoanPlatformTag loanPlatformTag) {
        loanPlatformTagDao.updateLoanPlatformTag(loanPlatformTag);
    }

    @Override
    public LoanPlatformTag getLoanPlatformTagById(int platformId, int tagId) {
        return loanPlatformTagDao.selectLoanPlatformTagById(platformId, tagId);
    }

}
