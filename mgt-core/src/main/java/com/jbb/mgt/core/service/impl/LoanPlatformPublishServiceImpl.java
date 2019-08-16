package com.jbb.mgt.core.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbb.mgt.core.dao.LoanPlatformPublishDao;
import com.jbb.mgt.core.domain.LoanPlatformPublish;
import com.jbb.mgt.core.service.LoanPlatformPublishService;

@Service("LoanPlatformPublishService")
public class LoanPlatformPublishServiceImpl implements LoanPlatformPublishService {

    @Autowired
    private LoanPlatformPublishDao loanPlatformPublishDao;

    @Override
    public void insertPlatformPublish(Integer platformId, Timestamp publishDate) {
        loanPlatformPublishDao.insertPlatformPublish(platformId, publishDate);
    }

    @Override
    public void updatePlatformPublish(Integer id, Integer platformId, Timestamp publishDate) {
        loanPlatformPublishDao.updatePlatformPublish(id, platformId, publishDate);
    }

    @Override
    public void deletePlatformPublish(Integer id) {
        loanPlatformPublishDao.deletePlatformPublish(id);
    }

    @Override
    public LoanPlatformPublish selectPlatformPublish(Integer id, Integer platformId) {
        return loanPlatformPublishDao.selectPlatformPublish(id, platformId);
    }

    @Override
    public List<LoanPlatformPublish> getPlatformPublishByDate(Timestamp startDate, Timestamp endDate,
        boolean isDeleted) {
        return loanPlatformPublishDao.selectPlatformPublishByDate(startDate, endDate, isDeleted);
    }

}
