package com.jbb.mgt.core.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbb.mgt.core.dao.LoanPlatformPublishDao;
import com.jbb.mgt.core.dao.mapper.LoanPlatformPublishMapper;
import com.jbb.mgt.core.domain.LoanPlatformPublish;

@Repository("LoanPlatformPublishDao")
public class LoanPlatformPublishDaoImpl implements LoanPlatformPublishDao {

    @Autowired
    private LoanPlatformPublishMapper mapper;

    @Override
    public void insertPlatformPublish(Integer platformId, Timestamp publishDate) {
        mapper.insertPlatformPublish(platformId, publishDate);
    }

    @Override
    public void updatePlatformPublish(Integer id, Integer platformId, Timestamp publishDate) {
        mapper.updatePlatformPublish(id, platformId, publishDate);

    }

    @Override
    public void deletePlatformPublish(Integer id) {
        mapper.deletePlatformPublish(id);
    }

    @Override
    public LoanPlatformPublish selectPlatformPublish(Integer id, Integer platformId) {
        return mapper.selectPlatformPublish(id, platformId);
    }

    @Override
    public List<LoanPlatformPublish> selectPlatformPublishByDate(Timestamp startDate, Timestamp endDate, boolean isDeleted) {
        return mapper.selectPlatformPublishByDate(startDate, endDate, isDeleted);
    }

}
